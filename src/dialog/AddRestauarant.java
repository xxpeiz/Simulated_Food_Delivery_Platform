package dialog;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import dao.DbUtils;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;

import utils.MyUtils;
import utils.OpenBox;
import utils.UploadFile;

public class AddRestauarant extends Dialog {
	private DbUtils db = new DbUtils();
	protected Object result;
	protected Shell shell;
	String username=Login.getUsername();
	ArrayList<HashMap<String, Object>> merchantResult = db.queryForList("SELECT merchant_id FROM merchants WHERE username = '"+username+"'");
    int merchantId = Integer.parseInt(merchantResult.get(0).get("merchant_id").toString());
    private Text text;
    private Text text_1;
    private Text text_2;
    private Text text_3;
    private Label lblNewLabel_4;
    private Button btnNewButton;
    private Button btnNewButton_1;
    private String filePath;
	private String fileName;


	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public AddRestauarant(Shell parent, int style) {
		super(parent, style);
		setText("添加餐厅");
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
		shell.setSize(800, 700);
		shell.setText(getText());
		
		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setBackground(SWTResourceManager.getColor(224, 255, 255));
		lblNewLabel.setBounds(37, 87, 90, 24);
		lblNewLabel.setText("餐厅名称：");
		
		text = new Text(shell, SWT.BORDER);
		text.setBounds(152, 87, 271, 30);
		
		Label lblNewLabel_1 = new Label(shell, SWT.NONE);
		lblNewLabel_1.setBackground(SWTResourceManager.getColor(224, 255, 255));
		lblNewLabel_1.setBounds(37, 161, 90, 24);
		lblNewLabel_1.setText("电话：");
		
		text_1 = new Text(shell, SWT.BORDER);
		text_1.setBounds(152, 161, 271, 30);
		
		Label lblNewLabel_2 = new Label(shell, SWT.NONE);
		lblNewLabel_2.setBackground(SWTResourceManager.getColor(224, 255, 255));
		lblNewLabel_2.setBounds(37, 238, 90, 24);
		lblNewLabel_2.setText("地址：");
		
		text_2 = new Text(shell, SWT.BORDER);
		text_2.setBounds(152, 238, 271, 30);
		
		Label lblNewLabel_3 = new Label(shell, SWT.NONE);
		lblNewLabel_3.setBackground(SWTResourceManager.getColor(224, 255, 255));
		lblNewLabel_3.setBounds(37, 315, 90, 24);
		lblNewLabel_3.setText("描述：");
		
		text_3 = new Text(shell, SWT.BORDER);
		text_3.setBounds(152, 315, 271, 182);
		
		lblNewLabel_4 = new Label(shell, SWT.BORDER);
		lblNewLabel_4.setBackground(SWTResourceManager.getColor(224, 255, 255));
		lblNewLabel_4.setBounds(506, 87, 205, 270);
		lblNewLabel_4.setText("");
		
		btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				FileDialog fd = new FileDialog(new Shell(),SWT.NONE);
				Object result = fd.open();
				
				fileName = fd.getFileName();
				filePath = fd.getFilterPath()+"\\"+fd.getFileName();
				lblNewLabel_4.setImage(SWTResourceManager.getImage(filePath));

			}
		});
		btnNewButton.setBounds(549, 385, 114, 34);
		btnNewButton.setText("选择图片");
		
		btnNewButton_1 = new Button(shell, SWT.NONE);
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				okPressed();
				shell.close();
			}
		});
		
		btnNewButton_1.setBounds(309, 570, 114, 34);
		btnNewButton_1.setText("添加");

	}
	protected void okPressed() {
    	String rname=text.getText();
        String rphone = text_1.getText();
        String raddress = text_2.getText();
        String description = text_3.getText();
        File file = new File(filePath);
        String newFileName = MyUtils.uodateFileName(fileName);
        UploadFile.uploadFile(file, newFileName);

        if (rname.isEmpty() || rphone.isEmpty() || raddress.isEmpty() || description.isEmpty()) {
            OpenBox.openBox("所有字段不能为空，请填写完整信息！");
            return;
        }

        ArrayList<HashMap<String, Object>> result = db.queryForList(
        	    "SELECT restaurant_id FROM restaurants WHERE name = '" + rname + "' AND merchant_id ='" + merchantId + "'"
        );

        if (!result.isEmpty()) {
            OpenBox.openBox("该餐厅已存在！");
            return;
        }


        int rows = db.update(
            "INSERT INTO restaurants (merchant_id, name, description, address, phone, photo) VALUES ('" + merchantId + "','" + rname + "', '" + description + "', '" + raddress + "', '" + rphone + "', '" + newFileName + "')"
        );

        if (rows > 0 ) {
            OpenBox.openBox("餐厅已添加！");
        } else {
            OpenBox.openBox("添加餐厅失败！");
        }

    }
}
