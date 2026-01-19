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
import org.eclipse.wb.swt.SWTResourceManager;

import utils.OpenBox;

import dao.DbUtils;

public class EditPeiSong extends Dialog {
	private DbUtils db = new DbUtils();
    private String orderId;
	protected Object result;
	protected Shell shell;
	private Combo combo;
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public EditPeiSong(Shell parent, int style,String orderId) {
		super(parent, style);
		this.orderId=orderId;
		setText("修改配送状态");
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
        combo.setBackground(SWTResourceManager.getColor(255, 255, 255));
        combo.setBounds(163, 64, 170, 32);

        combo.add("配送中");
        combo.add("已完成");
        combo.add("已取消");
        
        Label lblNewLabel = new Label(shell, SWT.NONE);
        lblNewLabel.setBackground(SWTResourceManager.getColor(224, 255, 255));
        lblNewLabel.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 11, SWT.NORMAL));
        lblNewLabel.setBounds(33, 64, 124, 32);
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


    }
	protected void okPressed() {
        String status = combo.getText();

        if (status == null || status.isEmpty()) {
            OpenBox.openBox("请选择一个有效的状态！");
            return;
        }

        int rows = db.update(
            "UPDATE orders SET status='" + status + "' WHERE order_id='" + orderId + "'"
        );

        if (rows > 0) {
            OpenBox.openBox("状态已更新为：" + status);
        } else {
            OpenBox.openBox("更新状态失败！");
        }
    }
}


