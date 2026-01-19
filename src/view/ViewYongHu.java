package view;

import java.awt.event.FocusAdapter;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
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
import utils.OpenView;

import dao.DbUtils;
import dialog.Login;
import dialog.RestaurantDetailDialog;



public class ViewYongHu extends ViewPart {

	public static final String ID = "view.ViewYongHu"; //$NON-NLS-1$
	private Table table;
	private DbUtils db = new DbUtils();
	private Text text;
	int pageRows = 5; 
	int start = 1; 
	int countRows = 0;     
	int countPages = 0;
	

	private Button first; 
	private Button pre; 
	private Button next; 
	private Button last; 
	private Table table_1;
	private Composite container;
	
	public ViewYongHu() {
	}

	String username=Login.getUsername();
	ArrayList<HashMap<String, Object>> userResult = db.queryForList("SELECT user_id FROM users where username='"+username+"'");
	int userId = Integer.parseInt(userResult.get(0).get("user_id").toString());
	//ArrayList<HashMap<String, Object>> restaurantResult = db.queryForList("SELECT restaurant_id FROM merchants WHERE username = '"+username+"'");
    //int merchantId = Integer.parseInt(restaurantResult.get(0).get("merchant_id").toString());

	/**
	 * Create contents of the view part.
	 * @param parent
	 */

	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setBackground(SWTResourceManager.getColor(224, 255, 255));
		//container.setBackgroundMode(SWT.INHERIT_DEFAULT);
		//container.setBackgroundImage(ResourceManager.getPluginImage("gui_demo", "icons/bj1.jpg"));
		
		Label lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setBackground(SWTResourceManager.getColor(224, 255, 255));
		lblNewLabel.setAlignment(SWT.CENTER);
		lblNewLabel.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 32, SWT.NORMAL));
		lblNewLabel.setBounds(816, 32, 804, 92);
		lblNewLabel.setText("浏览餐厅");
		
		table = new Table(container, SWT.BORDER | SWT.FULL_SELECTION);
		table.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 16, SWT.NORMAL));
		table.setBounds(816, 211, 917, 325);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn.setWidth(91);
		tblclmnNewColumn.setText("编号");
		
		TableColumn tblclmnNewColumn_1 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_1.setWidth(197);
		tblclmnNewColumn_1.setText("餐厅名称");
		
		TableColumn tblclmnNewColumn_2 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_2.setWidth(191);
		tblclmnNewColumn_2.setText("电话");
		
		TableColumn tblclmnNewColumn_3 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_3.setWidth(169);
		tblclmnNewColumn_3.setText("评分");
		
		TableColumn tblclmnNewColumn_4 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_4.setWidth(233);
		tblclmnNewColumn_4.setText("地址");
		
		Menu menu = new Menu(table);
		table.setMenu(menu);

		MenuItem mntmNewItem = new MenuItem(menu, SWT.NONE);
		mntmNewItem.addSelectionListener(new SelectionAdapter() {
		    @Override
		    public void widgetSelected(SelectionEvent e) {
		        TableItem tableItem = table.getItem(table.getSelectionIndex());
		        String id = tableItem.getText(0);

		        ArrayList<HashMap<String, Object>> result = db.queryForList("SELECT is_open FROM restaurants WHERE restaurant_id = '" + id + "'");
		        if (result.isEmpty()) {
		            OpenBox.openBox("未找到餐厅信息！");
		            return;
		        }

		        String is_open = result.get(0).get("is_open").toString();
		        if ("正在营业".equals(is_open)) {
		            RestaurantDetailDialog dialog = new RestaurantDetailDialog(new Shell(), SWT.CLOSE, id);
		            dialog.open();
		        } else {
		            OpenBox.openBox("餐厅未营业，无法查看菜品！");
		        }
		    }
		});
		mntmNewItem.setText("查看菜品");
		
		
		text = new Text(container, SWT.BORDER);
		text.addFocusListener(new org.eclipse.swt.events.FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				text.setText("");
			}
			@Override
			public void focusLost(FocusEvent e) {
				if("".equals(text.getText().trim())){
					text.setText("请检索：");
				}
			}
		});
		
		text.setBounds(816, 146, 343, 34);
		text.setText("请检索：");

		
		Button btnNewButton_1 = new Button(container, SWT.NONE);
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				getReByKey();
			}
		});
		btnNewButton_1.setBounds(1219, 146, 68, 34);
		btnNewButton_1.setText("检索");
		
		
		first = new Button(container, SWT.NONE);
		first.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				start = 1;
				getAllRe();
			}
		});
		first.setBounds(816, 587, 98, 30);
		first.setText("首页");
		
		pre = new Button(container, SWT.NONE);
		pre.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				start --;
				getAllRe();
			}
		});
		pre.setBounds(966, 587, 98, 30);
		pre.setText("上一页");
		
		next = new Button(container, SWT.NONE);
		next.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				start ++;
				getAllRe();
			}
		});
		next.setBounds(1339, 587, 98, 30);
		next.setText("下一页");
		
		last = new Button(container, SWT.NONE);
		last.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				start = countPages;
				getAllRe();
			}
		});
		last.setBounds(1482, 587, 98, 30);
		last.setText("尾页");
		
		table_1 = new Table(container, SWT.BORDER | SWT.FULL_SELECTION);
		table_1.setLinesVisible(true);
		table_1.setHeaderVisible(true);
		table_1.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 16, SWT.NORMAL));
		table_1.setBounds(816, 811, 917, 325);
		
		TableColumn tableColumn = new TableColumn(table_1, SWT.NONE);
		tableColumn.setWidth(91);
		tableColumn.setText("编号");
		
		TableColumn tableColumn_1 = new TableColumn(table_1, SWT.NONE);
		tableColumn_1.setWidth(197);
		tableColumn_1.setText("菜品名称");
		
		TableColumn tableColumn_2 = new TableColumn(table_1, SWT.NONE);
		tableColumn_2.setWidth(191);
		tableColumn_2.setText("价格");
		
		TableColumn tableColumn_3 = new TableColumn(table_1, SWT.NONE);
		tableColumn_3.setWidth(169);
		tableColumn_3.setText("份数");
		
		TableColumn tableColumn_4 = new TableColumn(table_1, SWT.NONE);
		tableColumn_4.setWidth(233);
		tableColumn_4.setText("备注");
		
		
		Label label = new Label(container, SWT.NONE);
		label.setBackground(SWTResourceManager.getColor(224, 255, 255));
		label.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 20, SWT.NORMAL));
		label.setBounds(816, 742, 380, 52);
		label.setText("购物车");
		
		Button button = new Button(container, SWT.NONE);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				placeOrder();
				table_1.removeAll();
			}
		});
		button.setBounds(1439, 1172, 114, 34);
		button.setText("下单");
		
		Button button_1 = new Button(container, SWT.NONE);
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				loadShoppingCartItems();
			}
		});
		button_1.setBounds(1593, 754, 114, 34);
		button_1.setText("刷新");
		
		Button button_2 = new Button(container, SWT.NONE);
		button_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				OpenView.openView(ViewOrders.ID);
			}
		});
		
		button_2.setBounds(1593, 1172, 114, 34);
		button_2.setText("已下单订单");
		
		
		
		getAllRe();

		createActions();
		initializeToolBar();
		initializeMenu();
	}
	
 
	public void getAllRe(){
		countRows = Integer.parseInt(db.queryForList("SELECT COUNT(*) FROM restaurants").get(0).get("COUNT(*)").toString());
		countPages = countRows/pageRows+(countRows%pageRows == 0 ? 0 : 1);
		
		table.removeAll();
		ArrayList<HashMap<String,Object>> list = db.queryForList("select * from restaurants order by restaurant_id asc limit "+(start-1)*pageRows+","+pageRows+""
				);
		
		for(HashMap<String,Object> map:list){
			 String restaurant_id=map.get("restaurant_id").toString();
			 ArrayList<HashMap<String, Object>> ratelist = db.queryForList("SELECT COUNT(*) AS count, AVG(rating) AS avg_rating FROM reviews WHERE restaurant_id='" + restaurant_id + "'"
			        );
			        double rating = 5.0;
			        if (!ratelist.isEmpty() && ratelist.get(0).get("avg_rating") != null) {
			            try {
			                rating = Double.parseDouble(ratelist.get(0).get("avg_rating").toString());
			            } catch (NumberFormatException e) {
			            }
			        }
			        TableItem tableItem = new TableItem(table, SWT.NONE);
			        tableItem.setText(0, map.get("restaurant_id").toString());
			        tableItem.setText(1, map.get("name").toString());
			        tableItem.setText(2, map.get("phone").toString());
			        tableItem.setText(3, String.format("%.2f", rating));
			        tableItem.setText(4, map.get("address").toString());
		}
		
		
		first.setEnabled(true);
		pre.setEnabled(true);
		next.setEnabled(true);
		last.setEnabled(true);

		if(start == 1){
			first.setEnabled(false);
			pre.setEnabled(false);
		}
		if(start == countPages){
			next.setEnabled(false);
			last.setEnabled(false);
		}

	}

	public void getReByKey(){
		String key = text.getText().trim();
		
		if("请检索：".equals(key)){
			key = "";
		}
		
		table.removeAll();
		countRows = Integer.parseInt(db.queryForList("SELECT COUNT(*) FROM restaurants WHERE name LIKE '%" + key + "%' OR restaurant_id LIKE '%" + key + "%' OR rating LIKE '%" + key + "%' OR address LIKE '%" + key + "%'").get(0).get("COUNT(*)").toString());
	    countPages = countRows / pageRows + (countRows % pageRows == 0 ? 0 : 1);

	    // 重置分页起始页
	    start = 1;

		ArrayList<HashMap<String,Object>> list = db.queryForList("SELECT * FROM restaurants WHERE name LIKE '%" + key + "%' OR restaurant_id LIKE '%" + key + "%' OR rating LIKE '%" + key + "%' OR address LIKE '%" + key + "%' ORDER BY restaurant_id ASC");
		
		for(HashMap<String,Object> map:list){
			String restaurant_id=map.get("restaurant_id").toString();
			 ArrayList<HashMap<String, Object>> ratelist = db.queryForList("SELECT COUNT(*) AS count, AVG(rating) AS avg_rating FROM reviews WHERE restaurant_id='" + restaurant_id + "'"
			        );
			        double rating = 5.0;
			        if (!ratelist.isEmpty() && ratelist.get(0).get("avg_rating") != null) {
			            try {
			                rating = Double.parseDouble(ratelist.get(0).get("avg_rating").toString());
			            } catch (NumberFormatException e) {
			            }
			        }
			TableItem tableItem = new TableItem(table, SWT.NONE);
			tableItem.setText(0,map.get("restaurant_id").toString());
			tableItem.setText(1,map.get("name").toString());
			tableItem.setText(2,map.get("phone").toString());
			tableItem.setText(3,String.format("%.2f", rating));
			tableItem.setText(4,map.get("address").toString());
		}
		 first.setEnabled(start != 1);
		 pre.setEnabled(start != 1);
		 next.setEnabled(start != countPages);
		 last.setEnabled(start != countPages);
	}
	
	private void loadShoppingCartItems() {
		table_1.removeAll();
        DbUtils db = new DbUtils();
        if (table_1 == null || table_1.isDisposed()) {
            return;
        }

        ArrayList<HashMap<String, Object>> cartResult = db.queryForList("SELECT cart_id FROM shopping_carts WHERE user_id ='"+userId+"' order by cart_id desc");

        if (cartResult == null || cartResult.isEmpty()) {
        	OpenBox.openBox("购物车中没有菜品");
            //table_1.removeAll();
            return;
        }
        for(int i=0;i<cartResult.size();i++){
        int cartId = Integer.parseInt(cartResult.get(i).get("cart_id").toString());

        ArrayList<HashMap<String, Object>> cartItems = db.queryForList("SELECT f.food_id, f.name, f.price, ci.quantity,ci.special_instructions " +
                "FROM cart_items ci JOIN food_items f ON ci.food_id = f.food_id " +
                "WHERE ci.cart_id = "+cartId+"");
        
        if (cartItems == null || cartItems.isEmpty()) {
            OpenBox.openBox("购物车中没有菜品");
            return;
        }

        for (HashMap<String, Object> map : cartItems) {
            TableItem tableItem = new TableItem(table_1, SWT.NONE);
            tableItem.setText(0, map.get("food_id") != null ? map.get("food_id").toString() : "无");
            tableItem.setText(1, map.get("name") != null ? map.get("name").toString() : "无");
            tableItem.setText(2, map.get("price") != null ? map.get("price").toString() : "无");
            tableItem.setText(3, map.get("quantity") != null ? map.get("quantity").toString() : "无");
            tableItem.setText(4, map.get("special_instructions") != null ? map.get("special_instructions").toString() : "无");
        }
        }
    }

	private void placeOrder() {
		 ArrayList<HashMap<String, Object>> cartResult = db.queryForList("SELECT cart_id FROM shopping_carts WHERE user_id = '" + userId + "' ORDER BY cart_id DESC");
		 if (cartResult.isEmpty()) {
            OpenBox.openBox("购物车为空，无法下单");
            return;
        }
		for(int i=0;i<cartResult.size();i++){
        int cartId = Integer.parseInt(cartResult.get(i).get("cart_id").toString());
        ArrayList<HashMap<String, Object>> restaurantResult = db.queryForList("SELECT restaurant_id FROM shopping_carts WHERE cart_id = '" + cartId + "'");
        if (restaurantResult.isEmpty() || restaurantResult.get(0).get("restaurant_id") == null) {
            OpenBox.openBox("购物车中没有餐厅信息");
            return;
        }

        int restaurantId = Integer.parseInt(restaurantResult.get(0).get("restaurant_id").toString());

        ArrayList<HashMap<String, Object>> cartItems = db.queryForList("SELECT food_id, quantity FROM cart_items WHERE cart_id = '" + cartId + "'");
        if (cartItems.isEmpty()) {
            OpenBox.openBox("购物车中没有菜品");
            return;
        }

        double totalAmount = 0.0;

        for (HashMap<String, Object> item : cartItems) {
            int foodId = Integer.parseInt(item.get("food_id").toString());
            int quantity = Integer.parseInt(item.get("quantity").toString());
            double price = getFoodItemPrice(foodId);
            totalAmount += price * quantity;
        }
        
        int deliveryPersonId = getRandomDeliveryPersonId();
        if (deliveryPersonId == -1) {
            OpenBox.openBox("当前没有配送员！");
            return;
        }
        db.update(
            "INSERT INTO orders (user_id, restaurant_id, delivery_person_id, total_amount, status) " +
            "VALUES ('" + userId + "', '" + restaurantId + "', '" + deliveryPersonId + "', '" + totalAmount + "', '待接单')"
        );

        ArrayList<HashMap<String, Object>> orderIdResult = db.queryForList("SELECT LAST_INSERT_ID() AS order_id");
        int orderId = Integer.parseInt(orderIdResult.get(0).get("order_id").toString());

        for (HashMap<String, Object> item : cartItems) {
            int foodId = Integer.parseInt(item.get("food_id").toString());
            int quantity = Integer.parseInt(item.get("quantity").toString());
            double price = getFoodItemPrice(foodId);
            double subtotal = price * quantity;

            db.update(
                "INSERT INTO order_details (order_id, food_id, quantity, subtotal) VALUES ('" + orderId + "', '" + foodId + "', '" + quantity + "', '" + subtotal + "')"
            );
        }

        db.update("DELETE FROM cart_items WHERE cart_id = '" + cartId + "'");
        db.update("DELETE FROM shopping_carts WHERE cart_id = '" + cartId + "'");

		 }
		OpenBox.openBox("下单成功");
    }

	
	private int getRandomDeliveryPersonId() {
	    ArrayList<HashMap<String, Object>> freeResult = db.queryForList(
	        "SELECT delivery_person_id FROM delivery_persons WHERE status = '空闲'"
	    );

	    if (!freeResult.isEmpty()) {
	        int randomIndex = (int) (Math.random() * freeResult.size());
	        int deliveryPersonId = Integer.parseInt(freeResult.get(randomIndex).get("delivery_person_id").toString());
	        db.update("UPDATE delivery_persons SET status = '忙碌' WHERE delivery_person_id = " + deliveryPersonId);
	        return deliveryPersonId;
	    } else {
	        ArrayList<HashMap<String, Object>> allResult = db.queryForList(
	            "SELECT delivery_person_id FROM delivery_persons"
	        );
	        if (allResult.isEmpty()) {
	            return -1;
	        }
	        int randomIndex = (int) (Math.random() * allResult.size());
	        return Integer.parseInt(allResult.get(randomIndex).get("delivery_person_id").toString());
	    }
	}


    private double getFoodItemPrice(int foodId) {
        DbUtils db = new DbUtils();
        ArrayList<HashMap<String, Object>> result = db.queryForList("SELECT price FROM food_items WHERE food_id = '"+foodId+"'");
        return Double.parseDouble(result.get(0).get("price").toString());
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