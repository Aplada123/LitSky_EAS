package com.kingdee.eas.custom.comm.utils.tool;

import java.awt.Component;

import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.custom.comm.utils.ExceptionUtils;
import com.kingdee.eas.scm.common.client.PassWordDialog;
import com.kingdee.eas.scm.credit.client.PasswordSetUI;

public class PwdUtils {

	/**
	 * 打开UI设置密码
	 * @param ui
	 * @return
	 * @author HeMei Department
	 * @date 2021-10-9 上午09:13:58
	 * <p>Copyright: Copyright (c) 2021HeMei Group</p>
	 */
	public static String setPassword(Component ui){
		String uiClass = PasswordSetUI.class.getName();
		IUIWindow popUI = null;
		try {
			UIContext uiContext = new UIContext(ui);
			popUI = UIFactory.createUIFactory("com.kingdee.eas.base.uiframe.client.UIModelDialogFactory").create(uiClass, uiContext, null, OprtState.VIEW);
		} catch (UIException e) {
			System.out.println(ExceptionUtils.getExceptionStatement(e));
			return null;
		}
		popUI.show();
		PasswordSetUI passwordSetUI = (PasswordSetUI)popUI.getUIObject();

		return passwordSetUI.getPassword();
	}
	/**
	 * 打开UI设置密码
	 * @param ui
	 * @return
	 * @author HeMei Department
	 * @date 2021-10-13 下午08:05:23
	 * <p>Copyright: Copyright (c) 2021HeMei Group</p>
	 */
	public static String getPassword(Component ui){
		String uiClass = PassWordDialog.class.getName();
		IUIWindow popUI = null;
		try {
			UIContext uiContext = new UIContext(ui);
			popUI = UIFactory.createUIFactory("com.kingdee.eas.base.uiframe.client.UIModelDialogFactory").create(uiClass, uiContext, null, OprtState.VIEW);
		} catch (UIException e) {
			System.out.println(ExceptionUtils.getExceptionStatement(e));
			return null;
		}
		popUI.show();
		PassWordDialog passwordSetUI = (PassWordDialog)popUI.getUIObject();

		return passwordSetUI.getPassWord();
	}
}
