package com.kingdee.eas.custom.comm.utils.compenent;

import java.util.ArrayList;

import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;

public class KDBizPromtBoxUtil {


	/**
	 * ����F7�ؼ���¼
	 * @param arrays
	 * @param flag
	 */
	public static void setKDBizPromtBoxRequired(ArrayList<KDBizPromptBox> arrays,boolean flag){
		for(int i = 0;i < arrays.size(); i++){
			arrays.get(i).setRequired(flag);
			arrays.get(i).setValue(arrays.get(i).getValue());
		}
	}

	/**
	 * ����F7�ؼ���¼
	 * @param arrays
	 * @param flag
	 */
	public static void setKDBizPromtBoxRequired(KDBizPromptBox f7,boolean flag){
		f7.setRequired(flag);
		f7.setValue(f7.getValue());
	}

}
