package dialog;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import utils.OpenBox;

import dao.DbUtils;

public class IsOpen extends Dialog {
	private DbUtils db = new DbUtils();
	protected Object result;
	protected Shell shell;
	private Combo combo;
	private Combo combo_1;
	String username=Login.getUsername();
	ArrayList<HashMap<String, Object>> merchantResult = db.queryForList("SELECT merchant_id FROM merchants WHERE username = '"+username+"'");
    int merchantId = Integer.parseInt(merchantResult.get(0).get("merchant_id").toString());


	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public IsOpen(Shell parent, int style) {
		super(parent, style);
		setText("营业状态");
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
        shell.setSize(449,301);
        shell.setText(getText());
        shell.setLayout(null);
        
        combo = new Combo(shell, SWT.READ_ONLY);
        combo.setBounds(163, 106, 170, 32);
        combo.add("正在营业");
        combo.add("已闭店");
        
        Label lblNewLabel = new Label(shell, SWT.NONE);
        lblNewLabel.setBackground(SWTResourceManager.getColor(224, 255, 255));
        lblNewLabel.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 11, SWT.NORMAL));
        lblNewLabel.setBounds(33, 104, 124, 32);
        lblNewLabel.setText("选择状态：");
	
        Button btnNewButton = new Button(shell, SWT.NONE);
        btnNewButton.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseDown(MouseEvent e) {
        		okPressed();
        		shell.close();
        	}
        });
        btnNewButton.setBounds(33, 195, 114, 34);
        btnNewButton.setText("确定");
        
        Label lblNewLabel_1 = new Label(shell, SWT.NONE);
        lblNewLabel_1.setBackground(SWTResourceManager.getColor(224, 255, 255));
        lblNewLabel_1.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 11, SWT.NORMAL));
        lblNewLabel_1.setBounds(33, 47, 99, 32);
        lblNewLabel_1.setText("餐厅名称：");
        
        combo_1 = new Combo(shell, SWT.READ_ONLY);
        combo_1.setBounds(163, 47, 170, 32);
        
        ArrayList<HashMap<String, Object>> result = db.queryForList("SELECT name FROM restaurants WHERE merchant_id ='"+merchantId+"'");
        for (HashMap<String, Object> row : result) {
            combo_1.add(row.get("name").toString());
        }
        
        
		}
	protected void okPressed() {
        String status=combo.getText();
        String name=combo_1.getText();
        if (status == null || status.isEmpty() || name == null || name.isEmpty()) {
            OpenBox.openBox("请选择一个有效的状态和餐厅！");
            return;
        }
        int rows = db.update(
            "UPDATE restaurants SET is_open='"+status+"' where name='"+name+"'");

        if (rows > 0) {
            OpenBox.openBox("营业状态已更新！");
        } else {
            OpenBox.openBox("更新状态失败！");
        }

    }

}
