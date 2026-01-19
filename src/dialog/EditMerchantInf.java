package dialog;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import utils.OpenBox;

import dao.DbUtils;

public class EditMerchantInf extends Dialog {

	protected Object result;
	protected Shell shell;
	private String id;
	private DbUtils db = new DbUtils();
    private Table table;
    private Text txtName;
    private Text txtPassword;
    private Text txtPhone;
	private Button btnNewButton;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public EditMerchantInf(Shell parent, int style , String id) {
		super(parent, style);
		this.id=id;
		setText("修改商家信息");
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
                lblName.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
                GridData gd_lblName = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 3);
                gd_lblName.widthHint = 149;
                gd_lblName.heightHint = 37;
                lblName.setLayoutData(gd_lblName);
                lblName.setBackground(SWTResourceManager.getColor(224, 255, 255));
                lblName.setText("用户名：");
        txtName = new Text(shell, SWT.BORDER);
        txtName.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
        GridData gd_txtName = new GridData(SWT.LEFT, SWT.CENTER, true, false);
        gd_txtName.widthHint = 367;
        gd_txtName.heightHint = 42;
        txtName.setLayoutData(gd_txtName);
        new Label(shell, SWT.NONE);
        new Label(shell, SWT.NONE);
        
                Label lblPrice = new Label(shell, SWT.NONE);
                lblPrice.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
                GridData gd_lblPrice = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
                gd_lblPrice.heightHint = 35;
                gd_lblPrice.widthHint = 122;
                lblPrice.setLayoutData(gd_lblPrice);
                lblPrice.setBackground(SWTResourceManager.getColor(224, 255, 255));
                lblPrice.setText("密码：");
        Text text_1 = new Text(shell, SWT.BORDER);
        text_1.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
        GridData gd_text_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
        gd_text_1.heightHint = 42;
        gd_text_1.widthHint = 367;
        text_1.setLayoutData(gd_text_1);
        txtPassword = text_1;
        new Label(shell, SWT.NONE);
        new Label(shell, SWT.NONE);
        
                Label lblRating = new Label(shell, SWT.NONE);
                lblRating.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
                GridData gd_lblRating = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 2);
                gd_lblRating.heightHint = 36;
                gd_lblRating.widthHint = 126;
                lblRating.setLayoutData(gd_lblRating);
                lblRating.setBackground(SWTResourceManager.getColor(224, 255, 255));
                lblRating.setText("电话：");
        Text text = new Text(shell, SWT.BORDER);
        text.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
        GridData gd_text = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
        gd_text.heightHint = 42;
        gd_text.widthHint = 367;
        text.setLayoutData(gd_text);
        txtPhone = text;
        new Label(shell, SWT.NONE);
        new Label(shell, SWT.NONE);
        
                
                btnNewButton = new Button(shell, SWT.CENTER);
                btnNewButton.addMouseListener(new MouseAdapter() {
                	@Override
                	public void mouseDown(MouseEvent e) {
                		okPressed();
                		shell.close();
                	}
                });
                GridData gd_btnNewButton = new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1);
                gd_btnNewButton.widthHint = 105;
                btnNewButton.setLayoutData(gd_btnNewButton);
                btnNewButton.setText("确定");

        loadMenuItem();

    }

    private void loadMenuItem() {
        ArrayList<HashMap<String, Object>> menuItem = db.queryForList(
            "SELECT * FROM merchants WHERE merchant_id ='"+id+"'");
        if (!menuItem.isEmpty()) {
            txtName.setText(menuItem.get(0).get("username").toString());
            txtPassword.setText(menuItem.get(0).get("password").toString());
            txtPhone.setText(menuItem.get(0).get("phone").toString());
        }
    }

    protected void okPressed() {
        String name = txtName.getText();
        String password = txtPassword.getText();
        String phone =txtPhone.getText();
        int rows = db.update(
            "UPDATE merchants SET username = '"+name+"', password = '" + password + "', phone = '" + phone + "'  WHERE merchant_id="+id+" "
        );

        if (rows > 0) {
            OpenBox.openBox("信息已更新！");
        } else {
            OpenBox.openBox("更新信息失败！");
        }

    }

}
