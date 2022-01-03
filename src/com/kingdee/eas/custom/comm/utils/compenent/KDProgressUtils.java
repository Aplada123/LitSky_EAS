package com.kingdee.eas.custom.comm.utils.compenent;

import java.awt.Component;
import java.awt.Frame;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import org.apache.commons.lang.StringUtils;

import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.base.permission.client.longtime.LongTimeDialog;
import com.kingdee.eas.util.client.MsgBox;

public class KDProgressUtils {
	public static Component comp;
	public static String message;
	public static String tips;

	public KDProgressUtils(Component comp, String message, String tips){
		KDProgressUtils.comp = comp;
		KDProgressUtils.message = message;
		KDProgressUtils.tips = tips;
		doSth();
	}
	protected void doSth(){
		LongTimeDialog dialog = new LongTimeDialog((Frame) SwingUtilities.getWindowAncestor(comp));    
		dialog.setLongTimeTask(new ILongTimeTask() {    
			public Object exec() throws Exception {    
				Object obj = "12345";    
				getData();
				return obj;    
			}    

			public void afterExec(Object result) throws Exception {   
				if(StringUtils.isNotEmpty(tips)){
					MsgBox.showInfo(tips);
				}
			}    
		});    
		Component[] cps=dialog.getContentPane().getComponents();    
		for(Component cp:cps){    
			if(cp instanceof JLabel){    
				((JLabel) cp).setText(message);    
			}    
		}    
		dialog.show();
	}

	public static void getData() {
		// TODO Auto-generated method stub

	}
}
