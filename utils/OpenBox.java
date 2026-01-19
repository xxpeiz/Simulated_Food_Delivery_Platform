package utils;

import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class OpenBox {
	
	public static void openBox(String message){
		MessageBox mb = new MessageBox(new Shell());
		mb.setText("系统消息");
		mb.setMessage(message);
		mb.open();
	}

}
