package dialog;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.*;
import org.eclipse.wb.swt.SWTResourceManager;

import utils.OpenBox;
import utils.OpenView;


import dao.DbUtils;

public class Login extends Dialog {

    protected Object result;
    protected Shell shell;
    private static Text text;
    private Text text_1;
    
    // 静态成员变量
    public static String role;
    public static String username; // 添加静态变量存储用户名

    /**
     * Create the dialog.
     * @param parent
     * @param style
     */
    public Login(Shell parent, int style) {
        super(parent, style);
        setText("用户登录");
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
        shell.setSize(791, 540);
        shell.setText(getText());
        
        Label lblNewLabel = new Label(shell, SWT.NONE);
        lblNewLabel.setForeground(SWTResourceManager.getColor(30, 144, 255));
        lblNewLabel.setBackground(SWTResourceManager.getColor(224, 255, 255));
        lblNewLabel.setAlignment(SWT.CENTER);
        lblNewLabel.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 16, SWT.NORMAL));
        lblNewLabel.setBounds(53, 31, 697, 60);
        lblNewLabel.setText("欢迎进入美味送达外卖订餐系统");
        
        Label lblNewLabel_1 = new Label(shell, SWT.NONE);
        lblNewLabel_1.setForeground(SWTResourceManager.getColor(30, 144, 255));
        lblNewLabel_1.setBackground(SWTResourceManager.getColor(224, 255, 255));
        lblNewLabel_1.setBounds(271, 120, 78, 23);
        lblNewLabel_1.setText("用户名:");
        
        text = new Text(shell, SWT.BORDER);
        text.setBounds(358, 117, 155, 26);
        
        Label lblNewLabel_2 = new Label(shell, SWT.NONE);
        lblNewLabel_2.setForeground(SWTResourceManager.getColor(30, 144, 255));
        lblNewLabel_2.setBackground(SWTResourceManager.getColor(224, 255, 255));
        lblNewLabel_2.setBounds(271, 194, 57, 23);
        lblNewLabel_2.setText("密 码:");
        
        text_1 = new Text(shell, SWT.BORDER | SWT.PASSWORD);
        text_1.setBounds(358, 191, 155, 26);
        
        Button btnNewButton = new Button(shell, SWT.NONE);
        btnNewButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDown(MouseEvent e) {
                String username = text.getText().trim();
                String password = text_1.getText().trim();
                
                if("".equals(username) || "".equals(password)){
                    OpenBox.openBox("对不起!用户名或密码不能为空!");
                } else {
                    DbUtils db = new DbUtils();

                    ArrayList<HashMap<String, Object>> list = 
                        db.queryForList("SELECT role, username, password FROM (" +
                "SELECT role, username, password FROM users " +
                "UNION " +
                "SELECT role, username, password FROM merchants " +
                "UNION " +
                "SELECT role, username, password FROM delivery_persons " +
                "UNION " +
                "SELECT role, username, password FROM admins" +
                ") AS all_users WHERE username = '"+username+"'");

                    if(list.size() > 0){
                        HashMap<String, Object> user = list.get(0);
                        if(password.equals(user.get("password").toString())){
                            role = user.get("role").toString();
                            Login.username = username; // 设置静态变量
                            OpenBox.openBox("登录成功!欢迎" + role + "进入外卖系统");
                            result = "成功";
                            shell.dispose();
                        } else {
                            OpenBox.openBox("登录失败!密码不正确!");
                            result = "失败";
                        }
                    } else {
                        OpenBox.openBox("登录失败!用户名不存在请注册!");
                        result = "失败";
                    }
                }
            }
        });
        btnNewButton.setBounds(414, 303, 98, 30);
        btnNewButton.setText("登录");
        
        Button btnNewButton_1 = new Button(shell, SWT.NONE);
        btnNewButton_1.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseDown(MouseEvent e) {
        		RegisterDialog dialog=new RegisterDialog(new Shell(),SWT.CLOSE);
        		dialog.open();
        	}
        });
        btnNewButton_1.setBounds(582, 303, 98, 32);
        btnNewButton_1.setText("注册");
    }

    /**
     * 获取用户名
     * @return
     */
    public static String getUsername() {
        return username;
    }
}