package view;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
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
import org.eclipse.ui.part.ViewPart;
import org.eclipse.wb.swt.SWTResourceManager;

import dao.DbUtils;
import dialog.EditPeiSong;
import dialog.Login;
import dialog.SeeDetailOrder;

import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class ViewPeiSong extends ViewPart {

	public static final String ID = "view.ViewPeiSong"; //$NON-NLS-1$
	private Table table_1;
	private DbUtils db = new DbUtils();
	String username=Login.getUsername();
	ArrayList<HashMap<String, Object>> dpResult = db.queryForList("SELECT delivery_person_id FROM delivery_persons WHERE username = '"+username+"'");
    int dpId = Integer.parseInt(dpResult.get(0).get("delivery_person_id").toString());

	public ViewPeiSong() {
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
		label.setText("订单配送");
        
		table_1 = new Table(container, SWT.BORDER | SWT.FULL_SELECTION);
		table_1.setLinesVisible(true);
		table_1.setHeaderVisible(true);
		table_1.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 16, SWT.NORMAL));
		table_1.setBounds(816, 226, 917, 325);
		
		TableColumn tableColumn = new TableColumn(table_1, SWT.NONE);
		tableColumn.setWidth(145);
		tableColumn.setText("订单编号");
		
		TableColumn tableColumn_1 = new TableColumn(table_1, SWT.NONE);
		tableColumn_1.setWidth(127);
		tableColumn_1.setText("用户名");
		
		TableColumn tableColumn_2 = new TableColumn(table_1, SWT.NONE);
		tableColumn_2.setWidth(442);
		tableColumn_2.setText("用户地址");
		
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
                    String orderId = selectedItem.getText(0).toString();
                    EditPeiSong dialog = new EditPeiSong(new Shell(), SWT.CLOSE,orderId);
        		    dialog.open();
                   
                }
		    }
		});
		mntmNewItem.setText("修改状态");
		
		MenuItem mntmNewItem1 = new MenuItem(menu, SWT.NONE);
		mntmNewItem1.addSelectionListener(new SelectionAdapter() {
		
		    public void widgetSelected(SelectionEvent e) {
		        int selectedIndex = table_1.getSelectionIndex();
                if (selectedIndex != -1) {
                    TableItem selectedItem = table_1.getItem(selectedIndex);
                    String orderId = selectedItem.getText(0).toString();
                    SeeDetailOrder dialog = new SeeDetailOrder(new Shell(), SWT.CLOSE,orderId);
        		    dialog.open();
                   
                }
		    }
		});
		mntmNewItem1.setText("查看详细");
		
		
		createActions();
		initializeToolBar();
		initializeMenu();
	}
	
	private void loadOrdersItems() {
    	table_1.removeAll();
        //ArrayList<HashMap<String, Object>> list = db.queryForList("SELECT o.order_id, r.name as rname, fi.name, od.subtotal, o.status FROM orders o JOIN restaurants r ON o.restaurant_id = r.restaurant_id JOIN order_details od ON o.order_id = od.order_id JOIN food_items fi ON od.food_id = fi.food_id where delivery_person_id = '"+dpId+"' ");
        ArrayList<HashMap<String, Object>> list1 = db.queryForList("SELECT o.order_id,u.username AS uname,u.address AS uaddress,o.status FROM orders o JOIN users u ON o.user_id = u.user_id WHERE delivery_person_id ='"+dpId+"'");
        
        for (HashMap<String, Object> map : list1) {
            TableItem item = new TableItem(table_1, SWT.NONE);
            item.setText(0, map.get("order_id").toString());
            item.setText(1, map.get("uname").toString());
            item.setText(2, map.get("uaddress").toString());
            item.setText(3, map.get("status").toString());
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
