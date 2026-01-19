package view;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.wb.swt.SWTResourceManager;

import dao.DbUtils;
import dialog.AddFoodItems;
import dialog.AddRestauarant;
import dialog.EditFoodItems;
import dialog.EditStatus;
import dialog.IsOpen;
import dialog.Login;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

import utils.OpenBox;

public class ViewShangJia extends ViewPart {

	public static final String ID = "view.ViewShangJia"; //$NON-NLS-1$
	private Table table;
	private Table table_1;
	private DbUtils db = new DbUtils();
	private Text text;
	String username=Login.getUsername();
	
	public ViewShangJia() {
	}
	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setBackground(SWTResourceManager.getColor(224, 255, 255));
		Label lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setBackground(SWTResourceManager.getColor(224, 255, 255));
		lblNewLabel.setAlignment(SWT.RIGHT);
		lblNewLabel.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 32, SWT.NORMAL));
		lblNewLabel.setBounds(816, 32, 547, 83);
		lblNewLabel.setText("菜单管理");
		
		table = new Table(container, SWT.BORDER | SWT.FULL_SELECTION);
		table.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 16, SWT.NORMAL));
		table.setBounds(816, 213, 917, 325);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tblclmnNewColumn = new TableColumn(table, SWT.CENTER);
		tblclmnNewColumn.setWidth(100);
		tblclmnNewColumn.setText("编号");
		
		
		
		TableColumn tblclmnNewColumn_5 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_5.setWidth(187);
		tblclmnNewColumn_5.setText("餐厅名称");
		
		TableColumn tblclmnNewColumn_1 = new TableColumn(table, SWT.CENTER);
		tblclmnNewColumn_1.setWidth(197);
		tblclmnNewColumn_1.setText("菜品名称");
		
		TableColumn tblclmnNewColumn_2 = new TableColumn(table, SWT.CENTER);
		tblclmnNewColumn_2.setWidth(98);
		tblclmnNewColumn_2.setText("价格");
		
		TableColumn tblclmnNewColumn_3 = new TableColumn(table, SWT.CENTER);
		tblclmnNewColumn_3.setWidth(89);
		tblclmnNewColumn_3.setText("评分");
		
		TableColumn tblclmnNewColumn_4 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_4.setWidth(207);
		tblclmnNewColumn_4.setText("描述");
		
		getAllFI();
		
		Menu menu = new Menu(table);
		table.setMenu(menu);

		MenuItem mntmNewItem1 = new MenuItem(menu, SWT.NONE);
		mntmNewItem1.addSelectionListener(new SelectionAdapter() {
		    @Override
		    public void widgetSelected(SelectionEvent e) {
		        TableItem tableItem = table.getItem(table.getSelectionIndex());
		        String foodId = tableItem.getText(0);
		        EditFoodItems dialog = new EditFoodItems(new Shell(), SWT.CLOSE,foodId);
		        dialog.open();
		    }
		});
		mntmNewItem1.setText("编辑菜品");
		
		MenuItem menuItem = new MenuItem(menu, SWT.NONE);
		menuItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				deleteFoodItems();
			}
		});
		menuItem.setText("删除菜品");
		
		Button button = new Button(container, SWT.NONE);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
		        AddFoodItems dialog = new AddFoodItems(new Shell(), SWT.CLOSE);
		        dialog.open();
			}
		});
		button.setBounds(991, 146, 114, 34);
		button.setText("添加菜品");
		
		Button button_1 = new Button(container, SWT.NONE);
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				getAllFI();
			}
		});
		button_1.setBounds(1619, 146, 114, 34);
		button_1.setText("刷新");
		
		
		
		
		table_1 = new Table(container, SWT.BORDER | SWT.FULL_SELECTION);
		table_1.setLinesVisible(true);
		table_1.setHeaderVisible(true);
		table_1.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 16, SWT.NORMAL));
		table_1.setBounds(816, 811, 917, 325);
		
		TableColumn tableColumn = new TableColumn(table_1, SWT.NONE);
		tableColumn.setWidth(150);
		tableColumn.setText("订单编号");
		
		TableColumn tableColumn_5 = new TableColumn(table_1, SWT.NONE);
		tableColumn_5.setText("配送员编号");
		tableColumn_5.setWidth(181);
		
		TableColumn tableColumn_1 = new TableColumn(table_1, SWT.NONE);
		tableColumn_1.setWidth(172);
		tableColumn_1.setText("餐厅名称");
		
		TableColumn tableColumn_2 = new TableColumn(table_1, SWT.NONE);
		tableColumn_2.setWidth(188);
		tableColumn_2.setText("菜品名称");
		
		TableColumn tableColumn_3 = new TableColumn(table_1, SWT.NONE);
		tableColumn_3.setWidth(98);
		tableColumn_3.setText("价格");
		
		TableColumn tableColumn_4 = new TableColumn(table_1, SWT.NONE);
		tableColumn_4.setWidth(118);
		tableColumn_4.setText("状态");
		
		
		Label label = new Label(container, SWT.NONE);
		label.setBackground(SWTResourceManager.getColor(224, 255, 255));
		label.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 20, SWT.NORMAL));
		label.setBounds(816, 742, 468, 52);
		label.setText("订单接收与管理");
		
		getAllOrders();
		
		Button btnNewButton = new Button(container, SWT.NONE);
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				getAllOrders();
			}
		});
		btnNewButton.setBounds(1619, 760, 114, 34);
		btnNewButton.setText("刷新");
		
		Menu menu_1 = new Menu(table_1);
		table_1.setMenu(menu_1);

		MenuItem mntmNewItem2 = new MenuItem(menu_1, SWT.NONE);
		mntmNewItem2.addSelectionListener(new SelectionAdapter() {
		    @Override
		    public void widgetSelected(SelectionEvent e) {
		        TableItem tableItem = table_1.getItem(table_1.getSelectionIndex());
		        String orderId = tableItem.getText(0);
		        EditStatus dialog = new EditStatus(new Shell(), SWT.CLOSE,orderId);
		        dialog.open();
		    }
		});
		mntmNewItem2.setText("编辑");
		
		Button btnNewButton_1 = new Button(container, SWT.NONE);
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				
		        IsOpen dialog = new IsOpen(new Shell(), SWT.CLOSE);
		        dialog.open();
			}
		});
		btnNewButton_1.setBounds(1440, 74, 207, 41);
		btnNewButton_1.setText("营业状态管理");
		
		Button btnNewButton_2 = new Button(container, SWT.NONE);
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				AddRestauarant dialog = new AddRestauarant(new Shell(), SWT.CLOSE);
		        dialog.open();
			}
		});
		btnNewButton_2.setBounds(816, 146, 114, 34);
		btnNewButton_2.setText("添加餐厅");
		
		
		
		createActions();
		initializeToolBar();
		initializeMenu();
	}
	
	ArrayList<HashMap<String, Object>> merchantResult = db.queryForList("SELECT merchant_id FROM merchants WHERE username = '"+username+"'");
    int merchantId = Integer.parseInt(merchantResult.get(0).get("merchant_id").toString());

	public void getAllFI(){
		table.removeAll();
		ArrayList<HashMap<String,Object>> list = db.queryForList("SELECT fi.food_id as id,r.name as rname,fi.name as finame,fi.price as fiprice,fi.rating as firating,fi.description as fide FROM food_items fi JOIN restaurants r ON fi.restaurant_id = r.restaurant_id WHERE r.merchant_id='"+merchantId+"'"
				);
		
		for(HashMap<String,Object> map:list){
			TableItem tableItem = new TableItem(table, SWT.NONE);
			tableItem.setText(0,map.get("id").toString());
			tableItem.setText(1,map.get("rname").toString());
			tableItem.setText(2,map.get("finame").toString());
			tableItem.setText(3,map.get("fiprice").toString());
			tableItem.setText(4,map.get("firating").toString());
			tableItem.setText(5,map.get("fide").toString());
		}
	}
	
	private void deleteFoodItems() {
	    int selectedIndex = table.getSelectionIndex();
	    if (selectedIndex == -1) {
	        OpenBox.openBox("请选择要删除的菜品！");
	        return;
	    }

	    TableItem tableItem = table.getItem(selectedIndex);
	    String foodId = tableItem.getText(0);

	    int rows = db.update("DELETE FROM food_items WHERE food_id = " + foodId);
	    if (rows > 0) {
	        OpenBox.openBox("菜品已删除！");
	        getAllFI();
	    } else {
	        OpenBox.openBox("删除菜品失败！");
	    }
	}
	public void getAllOrders(){
		table_1.removeAll();
		ArrayList<HashMap<String,Object>> list = db.queryForList("SELECT o.order_id AS orderId,o.delivery_person_id as dpId, r.name AS rname,fi.name AS finame, o.total_amount AS total, o.status AS ostatus FROM restaurants r JOIN food_items fi ON fi.restaurant_id = r.restaurant_id JOIN orders o ON o.restaurant_id = r.restaurant_id WHERE r.merchant_id='"+merchantId+"' order by orderId asc "
				);
		
		for(HashMap<String,Object> map:list){
			TableItem tableItem = new TableItem(table_1, SWT.NONE);
			tableItem.setText(0,map.get("orderId").toString());
			tableItem.setText(1,map.get("dpId").toString());
			tableItem.setText(2,map.get("rname").toString());
			tableItem.setText(3,map.get("finame").toString());
			tableItem.setText(4,map.get("total").toString());
			tableItem.setText(5,map.get("ostatus").toString());
		}
	}

	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
	}

	/**
	 * Initialize the toolbar.
	 */
	private void initializeToolBar() {
		IToolBarManager toolbarManager = getViewSite().getActionBars()
				.getToolBarManager();
	}

	/**
	 * Initialize the menu.
	 */
	private void initializeMenu() {
		IMenuManager menuManager = getViewSite().getActionBars()
				.getMenuManager();
	}

	@Override
	public void setFocus() {
		// Set the focus
	}
}
