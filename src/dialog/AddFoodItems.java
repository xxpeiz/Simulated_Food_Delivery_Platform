package dialog;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import dao.DbUtils;

import utils.OpenBox;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.wb.swt.SWTResourceManager;

public class AddFoodItems extends Dialog {
	private DbUtils db = new DbUtils();
    private Text txtName;
    private Text txtPrice;
    private Text txtRating;
    private Text txtDescription;
    private String retsaurantId;
	protected Object result;
	protected Shell shell;
	private Button btnNewButton;
	private Label label;
	private Label lblNewLabel;
	String username=Login.getUsername();
	ArrayList<HashMap<String, Object>> merchantResult = db.queryForList("SELECT merchant_id FROM merchants WHERE username = '"+username+"'");
    int merchantId = Integer.parseInt(merchantResult.get(0).get("merchant_id").toString());
    private Combo combo;
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public AddFoodItems(Shell parent, int style) {
		super(parent, style);

		setText("  Ӳ Ʒ");
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
        shell.setSize(809, 675);
        shell.setText(getText());
        shell.setLayout(new GridLayout(1, false));
        
        lblNewLabel = new Label(shell, SWT.NONE);
        lblNewLabel.setBackground(SWTResourceManager.getColor(224, 255, 255));
        lblNewLabel.setText("       ƣ ");
        
        combo = new Combo(shell, SWT.READ_ONLY);
        combo.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 11, SWT.NORMAL));
        GridData gd_combo = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
        gd_combo.heightHint = 40;
        gd_combo.widthHint = 341;
        combo.setLayoutData(gd_combo);
        ArrayList<HashMap<String, Object>> result = db.queryForList("SELECT name FROM restaurants WHERE merchant_id ='"+merchantId+"'");
        for (HashMap<String, Object> row : result) {
            combo.add(row.get("name").toString());
        }
        
        

        Label lblName = new Label(shell, SWT.NONE);
        lblName.setBackground(SWTResourceManager.getColor(224, 255, 255));
        lblName.setText("  Ʒ   ƣ ");
        txtName = new Text(shell, SWT.BORDER);
        txtName.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
        GridData gd_txtName = new GridData(SWT.FILL, SWT.CENTER, true, false);
        gd_txtName.heightHint = 41;
        txtName.setLayoutData(gd_txtName);

        Label lblPrice = new Label(shell, SWT.NONE);
        lblPrice.setBackground(SWTResourceManager.getColor(224, 255, 255));
        lblPrice.setText(" ۸ ");
        txtPrice = new Text(shell, SWT.BORDER);
        txtPrice.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
        GridData gd_txtPrice = new GridData(SWT.FILL, SWT.CENTER, true, false);
        gd_txtPrice.heightHint = 42;
        txtPrice.setLayoutData(gd_txtPrice);

        Label lblRating = new Label(shell, SWT.NONE);
        lblRating.setBackground(SWTResourceManager.getColor(224, 255, 255));
        lblRating.setText("   ֣ ");
        txtRating = new Text(shell, SWT.BORDER);
        txtRating.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
        GridData gd_txtRating = new GridData(SWT.FILL, SWT.CENTER, true, false);
        gd_txtRating.heightHint = 39;
        txtRating.setLayoutData(gd_txtRating);

        Label lblDescription = new Label(shell, SWT.NONE);
        lblDescription.setBackground(SWTResourceManager.getColor(224, 255, 255));
        lblDescription.setText("      ");
        txtDescription = new Text(shell, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
        txtDescription.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
        txtDescription.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        
        btnNewButton = new Button(shell, SWT.CENTER);
        btnNewButton.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseDown(MouseEvent e) {
        		okPressed();
        		shell.close();
        	}
        });
        GridData gd_btnNewButton = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
        gd_btnNewButton.widthHint = 789;
        btnNewButton.setLayoutData(gd_btnNewButton);
        btnNewButton.setText("ȷ  ");


    }

    protected void okPressed() {
    	String rname=combo.getText();
        String finame = txtName.getText();
        double price = Double.parseDouble(txtPrice.getText());
        double rating = Double.parseDouble(txtRating.getText());
        String description = txtDescription.getText();
        
        ArrayList<HashMap<String, Object>> result = db.queryForList(
        	    "SELECT restaurant_id FROM restaurants WHERE name = '" + rname + "' AND merchant_id ='" + merchantId + "'"
        );

        if (result.isEmpty()) {
            OpenBox.openBox("δ ҵ     : " + rname);
            return;
        }

        int restaurantId = Integer.parseInt(result.get(0).get("restaurant_id").toString());

        int rows = db.update(
            "INSERT INTO food_items (restaurant_id, name, price, rating, description) VALUES ('" + restaurantId + "','" + finame + "', " + price + ", " + rating + ", '" + description + "')"
        );

        if (rows > 0) {
            OpenBox.openBox("  Ʒ    ӣ ");
        } else {
            OpenBox.openBox("  Ӳ Ʒʧ ܣ ");
        }

    }
}
