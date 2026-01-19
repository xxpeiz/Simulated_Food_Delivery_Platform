package dialog;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.wb.swt.SWTResourceManager;

import dao.DbUtils;

public class SeeDetailOrder extends Dialog {

	protected Object result;
	protected Shell shell;
	private Table table_1;
	private DbUtils db = new DbUtils();
	private String order_id;
	String username=Login.getUsername();
	ArrayList<HashMap<String, Object>> dpResult = db.queryForList("SELECT delivery_person_id FROM delivery_persons WHERE username = '"+username+"'");
    int dpId = Integer.parseInt(dpResult.get(0).get("delivery_person_id").toString());

	
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public SeeDetailOrder(Shell parent, int style,String order_id ) {
		super(parent, style);
		this.order_id=order_id;
		setText("订单详情");
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
		shell.setSize(1000, 800);
		shell.setText(getText());
		
		Label label = new Label(shell, SWT.NONE);
		label.setBackground(SWTResourceManager.getColor(224, 255, 255));
		label.setAlignment(SWT.CENTER);
		label.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 20, SWT.NORMAL));
		label.setBounds(39, 87, 917, 63);
		label.setText("订单详细");
		
		table_1 = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
		table_1.setLinesVisible(true);
		table_1.setHeaderVisible(true);
		table_1.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 16, SWT.NORMAL));
		table_1.setBounds(39, 187, 917, 325);
		
		TableColumn tableColumn = new TableColumn(table_1, SWT.NONE);
		tableColumn.setWidth(145);
		tableColumn.setText("订单编号");
		
		TableColumn tableColumn_1 = new TableColumn(table_1, SWT.CENTER);
		tableColumn_1.setWidth(230);
		tableColumn_1.setText("餐厅名称");
		
		TableColumn tableColumn_2 = new TableColumn(table_1, SWT.CENTER);
		tableColumn_2.setWidth(207);
		tableColumn_2.setText("菜品名称");
		
		TableColumn tableColumn_3 = new TableColumn(table_1, SWT.CENTER);
		tableColumn_3.setWidth(122);
		tableColumn_3.setText("价格");
		
		TableColumn tableColumn_4 = new TableColumn(table_1, SWT.CENTER);
		tableColumn_4.setWidth(178);
		tableColumn_4.setText("订单状态");
		
		loadOrdersItems();
		

	}
	private void loadOrdersItems() {
    	table_1.removeAll();
        ArrayList<HashMap<String, Object>> list = db.queryForList("SELECT o.order_id, r.name as rname, fi.name, od.subtotal, o.status FROM orders o JOIN restaurants r ON o.restaurant_id = r.restaurant_id JOIN order_details od ON o.order_id = od.order_id JOIN food_items fi ON od.food_id = fi.food_id where delivery_person_id = '"+dpId+"' ");
        
        for (HashMap<String, Object> map : list) {
            TableItem item = new TableItem(table_1, SWT.NONE);
            item.setText(0, map.get("order_id").toString());
            item.setText(1, map.get("rname").toString());
            item.setText(2, map.get("name").toString());
            item.setText(3, map.get("subtotal").toString());
            item.setText(4, map.get("status").toString());
        }
    }

}
