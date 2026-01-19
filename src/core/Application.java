package core;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import dialog.Login;

/**
 * This class controls all aspects of the application's execution
 */
public class Application implements IApplication {

	/* (non-Javadoc)
	 * @see org.eclipse.equinox.app.IApplication#start(org.eclipse.equinox.app.IApplicationContext)
	 */
	public Object start(IApplicationContext context) throws Exception {
		Display display = PlatformUI.createDisplay();
		try {
			
			Login login = new Login(new Shell(),SWT.CLOSE);
			Object result = login.open();
			
			if(result == "³É¹¦"){
				int returnCode = PlatformUI.createAndRunWorkbench(display, new ApplicationWorkbenchAdvisor());
				return returnCode;
			}
			if(result == "Ê§°Ü"){
				return IApplication.EXIT_RESTART;
			}
			if(result == null){
				return IApplication.EXIT_OK;
			}
			
			return IApplication.EXIT_RESTART;
		} finally {
			display.dispose();
		}
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.equinox.app.IApplication#stop()
	 */
	public void stop() {
		if (!PlatformUI.isWorkbenchRunning())
			return;
		final IWorkbench workbench = PlatformUI.getWorkbench();
		final Display display = workbench.getDisplay();
		display.syncExec(new Runnable() {
			public void run() {
				if (!display.isDisposed())
					workbench.close();
			}
		});
	}
}
