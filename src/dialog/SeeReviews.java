package dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.GridData;

import dao.DbUtils;

import java.util.ArrayList;
import java.util.HashMap;
import org.eclipse.wb.swt.SWTResourceManager;

public class SeeReviews extends Dialog {
    private DbUtils db = new DbUtils();
    private Shell shell;
    private Table table;
    private int orderId;
    private String foodname;

    /**
     * Create the dialog.
     * @param parent
     * @param style
     * @param orderId
     */
    public SeeReviews(Shell parent, int style, int orderId,String foodname) {
        super(parent, style);
        this.orderId = orderId;
        this.foodname=foodname;
        setText("查看评价");
    }

    /**
     * Open the dialog.
     * @return the result
     */
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

    /**
     * Create contents of the dialog.
     */
    private void createContents() {
        shell = new Shell(getParent(), getStyle());
        shell.setBackground(SWTResourceManager.getColor(224, 255, 255));
        shell.setSize(861, 675);
        shell.setText(getText());
        shell.setLayout(new GridLayout(1, false));

        table = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
        GridData gd_table = new GridData(SWT.FILL, SWT.FILL, true, true);
        gd_table.widthHint = 969;
        table.setLayoutData(gd_table);
        table.setHeaderVisible(true);
        table.setLinesVisible(true);

        TableColumn colRating = new TableColumn(table, SWT.CENTER);
        colRating.setWidth(137);
        colRating.setText("评分");

        TableColumn colContent = new TableColumn(table, SWT.LEFT);
        colContent.setWidth(332);
        colContent.setText("评价内容");

        TableColumn colFoodRating = new TableColumn(table, SWT.CENTER);
        colFoodRating.setWidth(154);
        colFoodRating.setText("菜品评分");

        TableColumn colDeliveryRating = new TableColumn(table, SWT.CENTER);
        colDeliveryRating.setWidth(176);
        colDeliveryRating.setText("配送评分");

        loadReviews();

        Button btnClose = new Button(shell, SWT.NONE);
        btnClose.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false));
        btnClose.setText("关闭");
        btnClose.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                shell.dispose();
            }
        });
    }

    private void loadReviews() {
        table.removeAll();
        ArrayList<HashMap<String, Object>> reviews = db.queryForList(
                "SELECT r.rating, r.content, r.food_rating, r.delivery_rating FROM reviews r JOIN food_items fi ON r.restaurant_id = fi.restaurant_id WHERE fi.name='"+foodname+"'"
        );

        for (HashMap<String, Object> review : reviews) {
            TableItem item = new TableItem(table, SWT.NONE);
            item.setText(0, review.get("rating").toString());
            item.setText(1, review.get("content").toString());
            item.setText(2, review.get("food_rating") == null ? "" : review.get("food_rating").toString());
            item.setText(3, review.get("delivery_rating") == null ? "" : review.get("delivery_rating").toString());
            
        }
    }
}