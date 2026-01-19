package dialog;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import utils.OpenBox;
import dao.DbUtils;

import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.custom.TableCursor;

public class RestaurantDetailDialog extends Dialog {
    DbUtils db = new DbUtils();
    protected Shell shell;
    private String id;
    private String name;
    private Table table;
    private Label lblNewLabel_1;

    public RestaurantDetailDialog(Shell parent, int style, String id) {
        super(parent, style);
        this.id = id;
        setText("餐厅详细信息");
        ArrayList<HashMap<String, Object>> rResult = db.queryForList("SELECT name FROM restaurants WHERE restaurant_id='" + id + "'");
        
        // 检查查询结果是否为空
        if (rResult == null || rResult.isEmpty()) {
            OpenBox.openBox("未找到餐厅信息！");
            return;
        }

        // 获取餐厅名称
        name = rResult.get(0).get("name").toString();
    }

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
        return null;
    }

    private void createContents() {
        shell = new Shell(getParent(), getStyle());
        shell.setBackground(SWTResourceManager.getColor(224, 255, 255));
        shell.setSize(902, 763);
        shell.setText(getText());
        shell.setLayout(null);

        Label lblTitle = new Label(shell, SWT.CENTER);
        lblTitle.setBounds(319, 5, 145, 34);
        lblTitle.setBackground(SWTResourceManager.getColor(224, 255, 255));
        lblTitle.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
        lblTitle.setText("餐厅菜单");

        Label lblNewLabel = new Label(shell, SWT.NONE);
        lblNewLabel.setBounds(10, 45, 530, 37);
        lblNewLabel.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
        lblNewLabel.setBackground(SWTResourceManager.getColor(224, 255, 255));
        lblNewLabel.setText("餐厅名称：" + name);

        table = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
        table.setBounds(10, 88, 587, 498);
        table.setHeaderVisible(true);
        table.setLinesVisible(true);

        TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
        tblclmnNewColumn.setWidth(53);
        tblclmnNewColumn.setText("编号");

        TableColumn tblclmnNewColumn_1 = new TableColumn(table, SWT.NONE);
        tblclmnNewColumn_1.setWidth(131);
        tblclmnNewColumn_1.setText("名称");

        TableColumn tblclmnNewColumn_2 = new TableColumn(table, SWT.NONE);
        tblclmnNewColumn_2.setWidth(69);
        tblclmnNewColumn_2.setText("价格");

        TableColumn tblclmnNewColumn_3 = new TableColumn(table, SWT.NONE);
        tblclmnNewColumn_3.setWidth(69);
        tblclmnNewColumn_3.setText("评分");

        TableColumn tblclmnNewColumn_4 = new TableColumn(table, SWT.NONE);
        tblclmnNewColumn_4.setWidth(223);
        tblclmnNewColumn_4.setText("描述");

        loadMenuItems();
        Menu menu = new Menu(table);
		table.setMenu(menu);

		MenuItem mntmNewItem = new MenuItem(menu, SWT.NONE);
		mntmNewItem.addSelectionListener(new SelectionAdapter() {
		    @Override
		    public void widgetSelected(SelectionEvent e) {
		        TableItem tableItem = table.getItem(table.getSelectionIndex());
		        String foodId = tableItem.getText(0);
		        addToShoppingCart(foodId);
		    }
		});
		mntmNewItem.setText("添加到购物车");

        
        Label lblNewLabel_1 = new Label(shell, SWT.BORDER);
        lblNewLabel_1.setBackground(SWTResourceManager.getColor(224, 255, 255));
        lblNewLabel_1.setBounds(636, 50, 205, 270);
        ArrayList<HashMap<String, Object>> Result = db.queryForList("SELECT photo FROM restaurants WHERE restaurant_id='" + id + "'");
        lblNewLabel_1.setImage(ResourceManager.getPluginImage("WaiMaiDelivery","icons/"+Result.get(0).get("photo").toString()));
     
        Button btnClose = new Button(shell, SWT.NONE);
        btnClose.setBounds(361, 653, 77, 34);
        btnClose.setText("关闭");
        btnClose.addMouseListener(new org.eclipse.swt.events.MouseAdapter() {
            @Override
            public void mouseDown(org.eclipse.swt.events.MouseEvent e) {
                shell.dispose();
            }
        });
    }

    private void loadMenuItems() {
        ArrayList<HashMap<String, Object>> list = db.queryForList("SELECT * FROM food_items WHERE restaurant_id = " + id);

        for (HashMap<String, Object> map : list) {
        	String restaurant_id=map.get("food_id").toString();
			 ArrayList<HashMap<String, Object>> ratelist = db.queryForList("SELECT COUNT(*) AS count, AVG(food_rating) AS avg_rating FROM reviews WHERE restaurant_id='" + restaurant_id + "'"
			        );
			        double rating = 5.0;
			        if (!ratelist.isEmpty() && ratelist.get(0).get("avg_rating") != null) {
			            try {
			                rating = Double.parseDouble(ratelist.get(0).get("avg_rating").toString());
			            } catch (NumberFormatException e) {
			            }
			        }
            TableItem item = new TableItem(table, SWT.NONE);
            item.setText(0, map.get("food_id").toString());
            item.setText(1, map.get("name").toString());
            item.setText(2, map.get("price").toString());
            item.setText(3, String.format("%.2f", rating));
            item.setText(4, map.get("description").toString());
        }
    }

    private void addToShoppingCart(String foodId) {
        String username = Login.getUsername();
        ArrayList<HashMap<String, Object>> userResult = db.queryForList("SELECT user_id FROM users WHERE username='" + username + "'");
        int userId = Integer.parseInt(userResult.get(0).get("user_id").toString());

        db.update("INSERT INTO shopping_carts (user_id, restaurant_id) VALUES ('" + userId + "', '" + id + "')");
        ArrayList<HashMap<String, Object>> cartResult = db.queryForList("SELECT cart_id FROM shopping_carts WHERE user_id = '" + userId + "' ORDER BY cart_id DESC LIMIT 1");
        int cartId = Integer.parseInt(cartResult.get(0).get("cart_id").toString());

        int rows = db.update("INSERT INTO cart_items (cart_id, food_id, quantity) VALUES ('" + cartId + "', '" + Integer.parseInt(foodId) + "', 1)");

        if (rows > 0) {
            OpenBox.openBox("已添加到购物车！");
        } else {
            OpenBox.openBox("添加到购物车失败！");
        }
    }
}