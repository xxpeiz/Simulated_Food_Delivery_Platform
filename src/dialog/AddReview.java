package dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.GridData;

import utils.OpenBox;

import dao.DbUtils;

import java.util.ArrayList;
import java.util.HashMap;
import org.eclipse.wb.swt.SWTResourceManager;

public class AddReview extends Dialog {
    private DbUtils db = new DbUtils();
    private Shell shell;
    private Text txtRating;
    private Text txtContent;
    private Text txtFoodRating;
    private Text txtDeliveryRating;
    private int orderId;
    private int userId;
    private int restaurantId;

    public AddReview(Shell parent, int style, int orderId, int userId, int restaurantId) {
        super(parent, style);
        this.orderId = orderId;
        this.userId = userId;
        this.restaurantId = restaurantId;
        setText("添加评价");
    }

    public Object open() {
        createContents();
        shell.open();
        shell.layout();
        Display display = getParent().getDisplay();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        return null;
    }

    private void createContents() {
        shell = new Shell(getParent(), getStyle());
        shell.setBackground(SWTResourceManager.getColor(224, 255, 255));
        shell.setSize(400, 550);
        shell.setText(getText());
        shell.setLayout(new GridLayout(2, false));

        Label lblRating = new Label(shell, SWT.NONE);
        lblRating.setBackground(SWTResourceManager.getColor(224, 255, 255));
        lblRating.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
        lblRating.setText("店铺评分（1-5）：");
        txtRating = new Text(shell, SWT.BORDER);
        txtRating.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

        Label lblContent = new Label(shell, SWT.NONE);
        lblContent.setBackground(SWTResourceManager.getColor(224, 255, 255));
        lblContent.setText("评价内容：");
        new Label(shell, SWT.NONE);
        txtContent = new Text(shell, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
        GridData gd_txtContent = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
        gd_txtContent.heightHint = 339;
        txtContent.setLayoutData(gd_txtContent);

        Label lblFoodRating = new Label(shell, SWT.NONE);
        lblFoodRating.setBackground(SWTResourceManager.getColor(224, 255, 255));
        lblFoodRating.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
        lblFoodRating.setText("菜品评分（1-5）：");
        txtFoodRating = new Text(shell, SWT.BORDER);
        txtFoodRating.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

        Label lblDeliveryRating = new Label(shell, SWT.NONE);
        lblDeliveryRating.setBackground(SWTResourceManager.getColor(224, 255, 255));
        lblDeliveryRating.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
        lblDeliveryRating.setText("配送评分（1-5）：");
        txtDeliveryRating = new Text(shell, SWT.BORDER);
        txtDeliveryRating.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

        Button btnSubmit = new Button(shell, SWT.NONE);
        btnSubmit.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 2, 1));
        btnSubmit.setText("提交评价");
        btnSubmit.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                submitReview();
            }
        });
    }

    private void submitReview() {
        String ratingStr = txtRating.getText();
        String content = txtContent.getText();
        String foodRatingStr = txtFoodRating.getText();
        String deliveryRatingStr = txtDeliveryRating.getText();

        if (ratingStr.isEmpty() || content.isEmpty()) {
            OpenBox.openBox("评分和评价内容不能为空！");
            return;
        }

        int rating = Integer.parseInt(ratingStr);
        Integer foodRating = foodRatingStr.isEmpty() ? null : Integer.parseInt(foodRatingStr);
        Integer deliveryRating = deliveryRatingStr.isEmpty() ? null : Integer.parseInt(deliveryRatingStr);

        if (rating < 1 || rating > 5 || (foodRating != null && (foodRating < 1 || foodRating > 5)) || (deliveryRating != null && (deliveryRating < 1 || deliveryRating > 5))) {
            OpenBox.openBox("评分必须在1到5之间！");
            return;
        }

        int rows = db.update(
            "INSERT INTO reviews (order_id, user_id, restaurant_id, rating, content, food_rating, delivery_rating) " +
            "VALUES ('" + orderId + "', '" + userId + "', '" + restaurantId + "', '" + rating + "', '" + content + "', " +
            (foodRating == null ? "NULL" : "'" + foodRating + "'") + ", " +
            (deliveryRating == null ? "NULL" : "'" + deliveryRating + "'") + ")"
        );

        if (rows > 0) {
            OpenBox.openBox("评价已提交！");
            shell.dispose();
        } else {
        	OpenBox.openBox("评价提交失败！");
        }
    }
}
