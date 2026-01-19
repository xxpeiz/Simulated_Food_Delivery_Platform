package dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import dao.DbUtils;

import utils.OpenBox;

public class RegisterDialog extends Dialog {

	protected Object result;
	protected Shell shell;
	private Text text;
	private Text text_1;
	private Text text_2;
	private DbUtils db = new DbUtils();
	private Combo combo;
	private Text text_3;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public RegisterDialog(Shell parent, int style) {
		super(parent, style);
		setText("注册");
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
		shell.setSize(1000, 1130);
		shell.setText(getText());
		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setBackground(SWTResourceManager.getColor(224, 255, 255));
		lblNewLabel.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 16, SWT.NORMAL));
		lblNewLabel.setBounds(216, 200, 128, 41);
		lblNewLabel.setText("用户名：");
		
			text = new Text(shell, SWT.BORDER);
			text.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 16, SWT.NORMAL));
			text.setBounds(350, 200, 279, 41);
		
			Label lblNewLabel_1 = new Label(shell, SWT.NONE);
			lblNewLabel_1.setBackground(SWTResourceManager.getColor(224, 255, 255));
			lblNewLabel_1.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 16, SWT.NORMAL));
			lblNewLabel_1.setBounds(216, 364, 128, 41);
			lblNewLabel_1.setText("密码：");
		
			text_1 = new Text(shell, SWT.BORDER | SWT.PASSWORD);
			text_1.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 16, SWT.NORMAL));
			text_1.setBounds(350, 364, 279, 41);
		
			Label lblNewLabel_2 = new Label(shell, SWT.NONE);
			lblNewLabel_2.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 16, SWT.NORMAL));
			lblNewLabel_2.setBackground(SWTResourceManager.getColor(224, 255, 255));
			lblNewLabel_2.setBounds(216, 528, 128, 41);
			lblNewLabel_2.setText("电话：");
			
			text_2 = new Text(shell, SWT.BORDER);
			text_2.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 16, SWT.NORMAL));
			text_2.setBounds(350, 528, 279, 41);
			
			combo = new Combo(shell, SWT.READ_ONLY);
			combo.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 16, SWT.NORMAL));
			combo.setBounds(350, 692, 279, 41);
			
			Label lblNewLabel_3 = new Label(shell, SWT.NONE);
			lblNewLabel_3.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 16, SWT.NORMAL));
			lblNewLabel_3.setBackground(SWTResourceManager.getColor(224, 255, 255));
			lblNewLabel_3.setBounds(216, 692, 128, 41);
			lblNewLabel_3.setText("身份：");
		
			combo.add("用户");
			combo.add("商家");
			combo.add("配送员");
			
			Label lblNewLabel_4 = new Label(shell, SWT.NONE);
			lblNewLabel_4.setBackground(SWTResourceManager.getColor(224, 255, 255));
			lblNewLabel_4.setAlignment(SWT.CENTER);
			lblNewLabel_4.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 32, SWT.NORMAL));
			lblNewLabel_4.setBounds(216, 53, 547, 95);
			lblNewLabel_4.setText("注册");
			
			Button btnNewButton = new Button(shell, SWT.NONE);
			btnNewButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseDown(MouseEvent e) {
					okPressed();
					shell.close();
				}
			});
			btnNewButton.setBounds(438, 1020, 114, 34);
			btnNewButton.setText("确定");
			
			Label lblNewLabel_5 = new Label(shell, SWT.NONE);
			lblNewLabel_5.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 16, SWT.NORMAL));
			lblNewLabel_5.setBackground(SWTResourceManager.getColor(224, 255, 255));
			lblNewLabel_5.setBounds(216, 856, 128, 41);
			lblNewLabel_5.setText("地址：");
			
			text_3 = new Text(shell, SWT.BORDER);
			text_3.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 16, SWT.NORMAL));
			text_3.setBounds(350, 856, 279, 41);
			
			Label lblNewLabel_6 = new Label(shell, SWT.NONE);
			lblNewLabel_6.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
			lblNewLabel_6.setBackground(SWTResourceManager.getColor(224, 255, 255));
			lblNewLabel_6.setBounds(664, 864, 297, 32);
			lblNewLabel_6.setText("用户必填，其他身份可不填");
			

	}
	protected void okPressed() {
        String username = text.getText().trim();
        String password = text_1.getText().trim();
        String phone = text_2.getText().trim();
        String address = text_3.getText().trim();
        String role = combo.getText();

        if (username.isEmpty() || password.isEmpty() || phone.isEmpty() || role.isEmpty()) {
            OpenBox.openBox("所有字段均为必填项！");
            return;
        }
        if("用户".equals(role)){
        	if (address.isEmpty()) {
                OpenBox.openBox("所有字段均为必填项！");
                return;
            }
        	int rows = db.update(
                    "INSERT INTO users (username, password, phone, address) VALUES ('" + username + "', '" + password + "', '" + phone + "', '" + address + "')"
                );
        	if (rows > 0) {
                OpenBox.openBox("注册成功！请返回登录界面进行登录");
            } else {
                OpenBox.openBox("注册失败！");
            }
        }else if("商家".equals(role)){
        	int rows = db.update(
                    "INSERT INTO merchants (username, password, phone) VALUES ('" + username + "', '" + password + "', '" + phone + "')"
                );
        	if (rows > 0) {
                OpenBox.openBox("注册成功！请返回登录界面进行登录");
            } else {
                OpenBox.openBox("注册失败！");
            }
        }else if("配送员".equals(role)){
        	int rows = db.update(
                    "INSERT INTO delivery_persons (username, password, phone) VALUES ('" + username + "', '" + password + "', '" + phone + "')"
                );
        	if (rows > 0) {
                OpenBox.openBox("注册成功！请返回登录界面进行登录");
            } else {
                OpenBox.openBox("注册失败！");
            }
        }else{
        	OpenBox.openBox("目前只可以选择三种身份！");
        }
    }

}
