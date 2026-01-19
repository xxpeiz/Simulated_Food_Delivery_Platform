package utils;

import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

//在按钮的事件中打开view
public class OpenView {
	
	public static void openView(String id){

		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
			.showView(id);
		} catch (PartInitException e1) {
			e1.printStackTrace();
		}
	}

}
