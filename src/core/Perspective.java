package core;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import utils.OpenBox;
import view.ViewAdmin;
import view.ViewPeiSong;
import view.ViewShangJia;
import view.ViewYongHu;

import dialog.Login;

public class Perspective implements IPerspectiveFactory {

public void createInitialLayout(IPageLayout layout) {
		
		layout.setEditorAreaVisible(false);

		
		if(Login.role.equals("用户")){
			layout.addView(ViewYongHu.ID, 1, 1.0f, layout.getEditorArea());
		}
		if(Login.role.equals("商家")){
			layout.addView(ViewShangJia.ID, 1, 1.0f, layout.getEditorArea());
		}
		if(Login.role.equals("配送员")){
			layout.addView(ViewPeiSong.ID, 1, 1.0f, layout.getEditorArea());
		}
		if(Login.role.equals("管理员")){
			layout.addView(ViewAdmin.ID, 1, 1.0f, layout.getEditorArea());
		}
	}

}
