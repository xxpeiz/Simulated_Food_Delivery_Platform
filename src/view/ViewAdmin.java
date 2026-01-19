package view;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.part.ViewPart;

import dao.DbUtils;
import dialog.EditMerchantInf;
import dialog.EditUserInf;
import dialog.Login;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Label;

import utils.OpenBox;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class ViewAdmin extends ViewPart {

	public static final String ID = "view.ViewAdmin"; //$NON-NLS-1$
	private DbUtils db = new DbUtils();
	String username=Login.getUsername();
	private Table table;
	private Table table_1;
	private Label lblTotalOrders;
	private Label lblTotalAmount;
	
	public ViewAdmin() {
	}

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setBackground(SWTResourceManager.getColor(224, 255, 255));
		
		table = new Table(container, SWT.BORDER | SWT.FULL_SELECTION);
		table.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 16, SWT.NORMAL));
		table.setBounds(816, 168, 917, 325);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn.setWidth(91);
		tblclmnNewColumn.setText("   ");
		
		TableColumn tblclmnNewColumn_1 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_1.setWidth(197);
		tblclmnNewColumn_1.setText(" û   ");
		
		TableColumn tblclmnNewColumn_2 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_2.setWidth(191);
		tblclmnNewColumn_2.setText("    ");
		
		TableColumn tblclmnNewColumn_3 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_3.setWidth(268);
		tblclmnNewColumn_3.setText(" 绰");
		
		TableColumn tblclmnNewColumn_4 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_4.setWidth(119);
		tblclmnNewColumn_4.setText("   ");
		
		loadUserMerchantItems();
		
		Menu menu = new Menu(table);
		table.setMenu(menu);

		MenuItem mntmNewItem = new MenuItem(menu, SWT.NONE);
		mntmNewItem.addSelectionListener(new SelectionAdapter() {
		    @Override
		    public void widgetSelected(SelectionEvent e) {
		        TableItem tableItem = table.getItem(table.getSelectionIndex());
		        String id = tableItem.getText(0);
		        String role = tableItem.getText(4);
		        if (" û ".equals(role)) {
		            EditUserInf dialog = new EditUserInf(new Shell(), SWT.CLOSE, id);
		            dialog.open();
		        }if(" ̼ ".equals(role)){
		        	EditMerchantInf dialog = new EditMerchantInf(new Shell(), SWT.CLOSE, id);
		            dialog.open();
		        }
		    }
		});
		mntmNewItem.setText(" ޸   Ϣ");
		
		MenuItem menuItem = new MenuItem(menu, SWT.NONE);
		menuItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TableItem tableItem = table.getItem(table.getSelectionIndex());
		        String id = tableItem.getText(0);
		        String role = tableItem.getText(4);
				if (" û ".equals(role)) {
					db.update("DELETE FROM users WHERE user_id = '" + id + "'");
					OpenBox.openBox("ɾ   ɹ   ");
		        }if(" ̼ ".equals(role)){
		        	db.update("DELETE FROM merchants WHERE merchant_id = '" + id + "'");
		        	OpenBox.openBox("ɾ   ɹ   ");
		        }
			}
		});
		menuItem.setText("ɾ    Ϣ");
		
		
		table_1 = new Table(container, SWT.BORDER | SWT.FULL_SELECTION);
		table_1.setLinesVisible(true);
		table_1.setHeaderVisible(true);
		table_1.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 16, SWT.NORMAL));
		table_1.setBounds(816, 750, 917, 325);
		
		TableColumn tableColumn = new TableColumn(table_1, SWT.NONE);
		tableColumn.setWidth(91);
		tableColumn.setText("   ");
		
		TableColumn tableColumn_1 = new TableColumn(table_1, SWT.NONE);
		tableColumn_1.setWidth(115);
		tableColumn_1.setText(" û ");
		
		TableColumn tableColumn_2 = new TableColumn(table_1, SWT.NONE);
		tableColumn_2.setWidth(181);
		tableColumn_2.setText(" ̵ ");
		
		TableColumn tableColumn_3 = new TableColumn(table_1, SWT.NONE);
		tableColumn_3.setWidth(112);
		tableColumn_3.setText("    Ա");
		
		TableColumn tableColumn_4 = new TableColumn(table_1, SWT.NONE);
		tableColumn_4.setWidth(116);
		tableColumn_4.setText("   ");
		
		TableColumn tblclmnNewColumn_5 = new TableColumn(table_1, SWT.NONE);
		tblclmnNewColumn_5.setWidth(149);
		tblclmnNewColumn_5.setText("    ״̬");
		
		TableColumn tblclmnNewColumn_6 = new TableColumn(table_1, SWT.NONE);
		tblclmnNewColumn_6.setWidth(145);
		tblclmnNewColumn_6.setText("֧  ״̬");
		
		Label lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 16, SWT.NORMAL));
		lblNewLabel.setBackground(SWTResourceManager.getColor(224, 255, 255));
		lblNewLabel.setBounds(816, 108, 468, 54);
		lblNewLabel.setText(" û    ̼ҹ   ");
		
		Label label = new Label(container, SWT.NONE);
		label.setText("    ͳ  ");
		label.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 16, SWT.NORMAL));
		label.setBackground(SWTResourceManager.getColor(224, 255, 255));
		label.setBounds(816, 690, 147, 54);
		
		
		Button btnNewButton = new Button(container, SWT.NONE);
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				loadUserMerchantItems();
			}
		});
		btnNewButton.setBounds(1619, 113, 114, 42);
		btnNewButton.setText("ˢ  ");
		
		Button button = new Button(container, SWT.NONE);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				loadOrderItems();
			}
		});
		button.setText("ˢ  ");
		button.setBounds(1619, 695, 114, 42);
		
		lblTotalOrders = new Label(container, SWT.NONE);
	    lblTotalOrders.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 16, SWT.NORMAL));
	    lblTotalOrders.setBackground(SWTResourceManager.getColor(224, 255, 255));
	    lblTotalOrders.setBounds(816, 1081, 407, 54);

	    lblTotalAmount = new Label(container, SWT.NONE);
	    lblTotalAmount.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 16, SWT.NORMAL));
	    lblTotalAmount.setBackground(SWTResourceManager.getColor(224, 255, 255));
	    lblTotalAmount.setBounds(1252, 1081, 481, 54);
	    
	    Button btnNewButton_1 = new Button(container, SWT.NONE);
	    btnNewButton_1.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseDown(MouseEvent e) {
	    		ArrayList<HashMap<String, Object>> relist = db.queryForList("SELECT name, restaurant_id FROM restaurants");
	    		ArrayList<HashMap<String, Object>> slist = db.queryForList("SELECT restaurant_id, total_amount FROM orders");
	    		
                if (relist == null || relist.isEmpty()) {
                    OpenBox.openBox("δ ҵ       Ϣ  ");
                    return;
                }

                double totalSales = 0;
                ArrayList<HashMap<String, Object>> qResult = db.queryForList(
                        "SELECT total_amount AS amount FROM orders " 
                    );
                for (HashMap<String, Object> map : qResult) {
                    totalSales += Double.parseDouble(map.get("amount").toString());
                }
                
                HashMap<Integer, Double> salesMap = new HashMap<Integer, Double>();
                for (HashMap<String, Object> map : slist) {
                    int restaurantId = Integer.parseInt(map.get("restaurant_id").toString());
                    double totalAmount = Double.parseDouble(map.get("total_amount").toString());

                    if (salesMap.containsKey(restaurantId)) {
                        salesMap.put(restaurantId, salesMap.get(restaurantId) + totalAmount);
                    } else {
                        salesMap.put(restaurantId, totalAmount);
                    }
                }
                //        ݼ 
                DefaultPieDataset dpd = new DefaultPieDataset();
                for (HashMap<String, Object> map : relist) {
                    int restaurantId = Integer.parseInt(map.get("restaurant_id").toString());
                    String name = map.get("name").toString();

                    if (salesMap.containsKey(restaurantId)) {
                        double sales = salesMap.get(restaurantId);
                        dpd.setValue(name, (sales/totalSales)*100);
                    }
                }

                //       ͼ
                JFreeChart jc = ChartFactory.createPieChart3D("    Ӫҵ     ", dpd, true, true, true);

                //   ʾͼ  
                ChartFrame cf = new ChartFrame("    Ӫҵ     ", jc);
                cf.pack();
                cf.setVisible(true);

	    	}
	    });
	    btnNewButton_1.setBounds(980, 699, 114, 34);
	    btnNewButton_1.setText("   ݿ  ӻ ");
	    
	    loadOrderItems();

		createActions();
		initializeToolBar();
		initializeMenu();
	}
	
	private void loadUserMerchantItems() {
		table.removeAll();
        if (table == null || table.isDisposed()) {
            return;
        }

        ArrayList<HashMap<String, Object>> umResult1 = db.queryForList("SELECT * FROM users WHERE role=' û ' OR role = ' ̼ '");
        ArrayList<HashMap<String, Object>> umResult2 = db.queryForList("SELECT * FROM merchants WHERE role=' û ' OR role = ' ̼ '");

        if (umResult1 == null || umResult1.isEmpty()||umResult2 == null || umResult2.isEmpty()) {
            table.removeAll();
            return;
        }

        for (HashMap<String, Object> map : umResult1) {
            TableItem tableItem = new TableItem(table, SWT.NONE);
            tableItem.setText(0, map.get("user_id").toString());
            tableItem.setText(1, map.get("username").toString());
            tableItem.setText(2, map.get("password").toString());
            tableItem.setText(3, map.get("phone").toString());
            tableItem.setText(4, map.get("role").toString());
        }
        for (HashMap<String, Object> map : umResult2) {
            TableItem tableItem = new TableItem(table, SWT.NONE);
            tableItem.setText(0, map.get("merchant_id").toString());
            tableItem.setText(1, map.get("username").toString());
            tableItem.setText(2, map.get("password").toString());
            tableItem.setText(3, map.get("phone").toString());
            tableItem.setText(4, map.get("role").toString());
        }
    }
	
	private void loadOrderItems() {
        table_1.removeAll();
        if (table_1 == null || table_1.isDisposed()) {
            return;
        }

        ArrayList<HashMap<String, Object>> oResult = db.queryForList(
            "SELECT o.order_id, u.username AS uname, r.name AS rname, d.username AS dname, o.total_amount AS amount, o.status AS ostatus, o.payment_status AS pstatus " +
            "FROM orders o " +
            "JOIN users u ON o.user_id = u.user_id " +
            "JOIN restaurants r ON o.restaurant_id = r.restaurant_id " +
            "JOIN delivery_persons d ON o.delivery_person_id = d.delivery_person_id"
        );

        if (oResult == null || oResult.isEmpty()) {
            table_1.removeAll();
            lblTotalOrders.setText(" ܶ       0");
            lblTotalAmount.setText(" ܽ 0.00");
            return;
        }

        int totalOrders = 0;
        double totalAmount = 0.0;

        for (HashMap<String, Object> map : oResult) {
            TableItem tableItem = new TableItem(table_1, SWT.NONE);
            tableItem.setText(0, map.get("order_id").toString());
            tableItem.setText(1, map.get("uname").toString());
            tableItem.setText(2, map.get("rname").toString());
            tableItem.setText(3, map.get("dname").toString());
            tableItem.setText(4, map.get("amount").toString());
            tableItem.setText(5, map.get("ostatus").toString());
            tableItem.setText(6, map.get("pstatus").toString());

            totalOrders++;
            totalAmount += Double.parseDouble(map.get("amount").toString());
        }

        if (lblTotalOrders != null && !lblTotalOrders.isDisposed()) {
            lblTotalOrders.setText(" ܶ       " + totalOrders);
        }
        if (lblTotalAmount != null && !lblTotalAmount.isDisposed()) {
            lblTotalAmount.setText(" ܽ " + String.format("%.2f", totalAmount));
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
