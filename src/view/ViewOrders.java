package view;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
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

import utils.OpenBox;

import dao.DbUtils;
import dialog.AddReview;
import dialog.Login;
import dialog.SeeReviews;

public class ViewOrders extends ViewPart {

	public static final String ID = "view.ViewOrders"; //$NON-NLS-1$
	private Table table_1;
	private DbUtils db = new DbUtils();
	private Text text;
	
	public ViewOrders() {
	}

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setBackground(SWTResourceManager.getColor(224, 255, 255));
		
		Label label = new Label(container, SWT.NONE);
		label.setBackground(SWTResourceManager.getColor(224, 255, 255));
		label.setAlignment(SWT.CENTER);
		label.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 20, SWT.NORMAL));
		label.setBounds(816, 131, 917, 63);
		label.setText("订单详细");
        
		table_1 = new Table(container, SWT.BORDER | SWT.FULL_SELECTION);
		table_1.setLinesVisible(true);
		table_1.setHeaderVisible(true);
		table_1.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 16, SWT.NORMAL));
		table_1.setBounds(732, 227, 1116, 325);
		
		TableColumn tableColumn = new TableColumn(table_1, SWT.NONE);
		tableColumn.setWidth(165);
		tableColumn.setText("订单编号");
		
		TableColumn tableColumn_5 = new TableColumn(table_1, SWT.NONE);
		tableColumn_5.setWidth(183);
		tableColumn_5.setText("配送员编号");
		
		TableColumn tableColumn_1 = new TableColumn(table_1, SWT.NONE);
		tableColumn_1.setWidth(197);
		tableColumn_1.setText("餐厅名称");
		
		TableColumn tableColumn_2 = new TableColumn(table_1, SWT.NONE);
		tableColumn_2.setWidth(221);
		tableColumn_2.setText("菜品名称");
		
		TableColumn tableColumn_3 = new TableColumn(table_1, SWT.NONE);
		tableColumn_3.setWidth(142);
		tableColumn_3.setText("价格");
		
		TableColumn tableColumn_4 = new TableColumn(table_1, SWT.NONE);
		tableColumn_4.setWidth(178);
		tableColumn_4.setText("订单状态");
		
		Button button_1 = new Button(container, SWT.NONE);
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				loadOrdersItems();
			}
		});
		button_1.setBounds(1619, 621, 114, 34);
		button_1.setText("刷新");
		
		loadOrdersItems();
		
		Menu menu = new Menu(table_1);
		table_1.setMenu(menu);

		MenuItem mntmNewItem = new MenuItem(menu, SWT.NONE);
		mntmNewItem.addSelectionListener(new SelectionAdapter() {
		    @Override
		    public void widgetSelected(SelectionEvent e) {
		        int selectedIndex = table_1.getSelectionIndex();
                if (selectedIndex != -1) {
                    TableItem selectedItem = table_1.getItem(selectedIndex);
                    String status = selectedItem.getText(5);
                    if ("已完成".equals(status)) {
                        int orderId = Integer.parseInt(selectedItem.getText(0)); // 获取订单编号
                        String restaurantName = selectedItem.getText(2); // 获取餐厅名称
                        ArrayList<HashMap<String, Object>> result = db.queryForList(
                            "SELECT restaurant_id FROM restaurants WHERE name = '" + restaurantName + "'"
                        );
                        int restaurantId = Integer.parseInt(result.get(0).get("restaurant_id").toString());
                    	AddReview dialog = new AddReview(new Shell(), SWT.CLOSE,orderId,userId,restaurantId);
        		        dialog.open();
                    } else {
                        OpenBox.openBox("只有已完成的订单可以进行评价！");
                    }
                }
		    }
		});
		mntmNewItem.setText("添加评价");
		
		MenuItem mntmNewItem1 = new MenuItem(menu, SWT.NONE);
		mntmNewItem1.addSelectionListener(new SelectionAdapter() {
		    @Override
		    public void widgetSelected(SelectionEvent e) {
		        int selectedIndex = table_1.getSelectionIndex();
                if (selectedIndex != -1) {
                    TableItem selectedItem = table_1.getItem(selectedIndex);
                    String status = selectedItem.getText(5);
                    if ("已完成".equals(status)) {
                    	int orderId = Integer.parseInt(selectedItem.getText(0)); // 获取订单编号
                    	String foodname = selectedItem.getText(3);
                    	SeeReviews dialog = new SeeReviews(new Shell(), SWT.CLOSE,orderId,foodname);
        		        dialog.open();
                    } else {
                        OpenBox.openBox("只有已完成的订单可以查看评价！");
                    }
                }
		    }
		});
		mntmNewItem1.setText("查看评价");

        
		createActions();
		initializeToolBar();
		initializeMenu();
	}
	//ArrayList<HashMap<String, Object>> orderResult = db.queryForList("SELECT order_id FROM orders");
	//int orderId = Integer.parseInt(orderResult.get(0).get("order_id").toString());
	String username=Login.getUsername();
	ArrayList<HashMap<String, Object>> userResult = db.queryForList("SELECT user_id FROM users where username='"+username+"'");
	int userId = Integer.parseInt(userResult.get(0).get("user_id").toString());
	//ArrayList<HashMap<String, Object>> restaurantResult = db.queryForList("SELECT restaurant_id FROM orders WHERE user_id = '"+userId+"'");
	//int restaurantId = Integer.parseInt(restaurantResult.get(0).get("restaurant_id").toString());


    private void loadOrdersItems() {
    	table_1.removeAll();
        ArrayList<HashMap<String, Object>> list = db.queryForList("SELECT o.order_id, o.delivery_person_id, r.name as rname, fi.name, od.subtotal, o.status FROM orders o JOIN restaurants r ON o.restaurant_id = r.restaurant_id JOIN order_details od ON o.order_id = od.order_id JOIN food_items fi ON od.food_id = fi.food_id order by o.order_id asc ");
       // int restaurant_id=Integer.parseInt(list.get(0).get(orestaurant_id).toString());
        for (HashMap<String, Object> map : list) {
            TableItem item = new TableItem(table_1, SWT.NONE);
            item.setText(0, map.get("order_id").toString());
            item.setText(1, map.get("delivery_person_id").toString());
            item.setText(2, map.get("rname").toString());
            item.setText(3, map.get("name").toString());
            item.setText(4, map.get("subtotal").toString());
            item.setText(5, map.get("status").toString());
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
