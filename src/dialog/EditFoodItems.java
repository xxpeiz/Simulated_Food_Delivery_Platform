package dialog;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import dao.DbUtils;

import utils.OpenBox;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.wb.swt.SWTResourceManager;

public class EditFoodItems extends Dialog {
	private DbUtils db = new DbUtils();
    private Text txtName;
    private Text txtPrice;
    private Text txtRating;
    private Text txtDescription;
    private String foodId;
	protected Object result;
	protected Shell shell;
	private Button btnNewButton;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public EditFoodItems(Shell parent, int style ,String foodId) {
		super(parent, style);
		this.foodId=foodId;
		setText("编辑菜品");
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
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {

        shell = new Shell(getParent(), getStyle());
        shell.setBackground(SWTResourceManager.getColor(224, 255, 255));
        shell.setSize(809, 675);
        shell.setText(getText());
        shell.setLayout(new GridLayout(1, false));

        Label lblName = new Label(shell, SWT.NONE);
        lblName.setBackground(SWTResourceManager.getColor(224, 255, 255));
        lblName.setText("菜品名称：");
        txtName = new Text(shell, SWT.BORDER);
        GridData gd_txtName = new GridData(SWT.FILL, SWT.CENTER, true, false);
        gd_txtName.heightHint = 52;
        txtName.setLayoutData(gd_txtName);

        Label lblPrice = new Label(shell, SWT.NONE);
        lblPrice.setBackground(SWTResourceManager.getColor(224, 255, 255));
        lblPrice.setText("价格：");
        txtPrice = new Text(shell, SWT.BORDER);
        GridData gd_txtPrice = new GridData(SWT.FILL, SWT.CENTER, true, false);
        gd_txtPrice.heightHint = 47;
        txtPrice.setLayoutData(gd_txtPrice);

        Label lblRating = new Label(shell, SWT.NONE);
        lblRating.setBackground(SWTResourceManager.getColor(224, 255, 255));
        lblRating.setText("评分：");
        txtRating = new Text(shell, SWT.BORDER);
        GridData gd_txtRating = new GridData(SWT.FILL, SWT.CENTER, true, false);
        gd_txtRating.heightHint = 39;
        txtRating.setLayoutData(gd_txtRating);

        Label lblDescription = new Label(shell, SWT.NONE);
        lblDescription.setBackground(SWTResourceManager.getColor(224, 255, 255));
        lblDescription.setText("描述：");
        txtDescription = new Text(shell, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
        txtDescription.setBackground(SWTResourceManager.getColor(255, 255, 255));
        txtDescription.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        
        btnNewButton = new Button(shell, SWT.CENTER);
        btnNewButton.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseDown(MouseEvent e) {
        		okPressed();
        		shell.close();
        	}
        });
        GridData gd_btnNewButton = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
        gd_btnNewButton.widthHint = 789;
        btnNewButton.setLayoutData(gd_btnNewButton);
        btnNewButton.setText("确定");

        loadMenuItem();

    }

    private void loadMenuItem() {
        ArrayList<HashMap<String, Object>> menuItem = db.queryForList(
            "SELECT name, price, rating, description FROM food_items WHERE food_id ='"+foodId+"'");
        if (!menuItem.isEmpty()) {
            txtName.setText(menuItem.get(0).get("name").toString());
            txtPrice.setText(menuItem.get(0).get("price").toString());
            txtRating.setText(menuItem.get(0).get("rating").toString());
            txtDescription.setText(menuItem.get(0).get("description").toString());
        }
    }

    protected void okPressed() {
        String name = txtName.getText();
        double price = Double.parseDouble(txtPrice.getText());
        double rating = Double.parseDouble(txtRating.getText());
        String description = txtDescription.getText();

        int rows = db.update(
            "UPDATE food_items SET name = '"+name+"', price = " + price + ", rating = " + rating + ", description = '" + description + "' WHERE food_id = " + foodId
        );

        if (rows > 0) {
            OpenBox.openBox("菜品已更新！");
        } else {
            OpenBox.openBox("更新菜品失败！");
        }

    }
}

