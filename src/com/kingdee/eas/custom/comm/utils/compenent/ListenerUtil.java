package com.kingdee.eas.custom.comm.utils.compenent;

import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;

public class ListenerUtil {

	/**
	 * 删除f7的值改变事件
	 * @param f7
	 */
	public static void F7RemoveDateChangeListener(KDBizPromptBox f7){
		DataChangeListener[] listenerArray = f7.getListeners(DataChangeListener.class);
		for(int i=0;i<listenerArray.length;i++)
			f7.removeDataChangeListener(listenerArray[i]);
	}
	/**
	 * 获取f7的值改变事件
	 * @param f7
	 * @return
	 */
	public static DataChangeListener[] F7GetDateChangeListener(KDBizPromptBox f7){
		DataChangeListener[] listenerArray = f7.getListeners(DataChangeListener.class);
		return listenerArray;
	}

	/**
	 * f7添加值改变事件
	 * @param f7
	 * @return
	 */
	public static void F7AddDateChangeListener(KDBizPromptBox f7,DataChangeListener[] dataChangeListener){
		for(int i = 0; i < dataChangeListener.length; i++)
			f7.addDataChangeListener(dataChangeListener[i]);
	}


	/**
	 * 文本控件删除值变更事件
	 * @param obj
	 */
	public static void textFieldRemoveDataChangeListener(Object obj){
		if(obj instanceof KDFormattedTextField ){
			DataChangeListener[] listeners = textFieldGetDataChangeListener(obj);
			for(int i=0;i<listeners.length;i++)
				((KDFormattedTextField)obj).removeDataChangeListener(listeners[i]);
		}
	}

	/**
	 * 文本控件获取事件
	 * @param obj
	 * @return
	 */
	public static DataChangeListener[] textFieldGetDataChangeListener(Object obj){
		if(obj instanceof KDFormattedTextField ){
			return ((KDFormattedTextField)obj).getListeners(DataChangeListener.class);
		}else
			return null;
	}



	/**
	 * 文本控件添加事件
	 * @param obj
	 * @return
	 */
	public static void textFieldAddDataChangeListener(Object obj,DataChangeListener[] listeners){
		if(obj instanceof KDFormattedTextField ){
			for(int i=0;i<listeners.length;i++)
				((KDFormattedTextField)obj).addDataChangeListener(listeners[i]);
		}else{
			return;
		}
	}
	/**
	 * 删除值变更事件
	 * @param obj
	 */
	public static void removeDataChangeListener(Object obj){
		
		// txt
		if(obj instanceof KDFormattedTextField ){
			DataChangeListener[] textFieldListener = ((KDFormattedTextField)obj).getListeners(DataChangeListener.class);
			for(int i = textFieldListener.length-1;i >= 0;i--){
				((KDFormattedTextField)obj).removeDataChangeListener(textFieldListener[i]);
			}
		}
		
		// pk
		if(obj instanceof KDDatePicker){
			DataChangeListener[] listenerComp = ((KDDatePicker)obj).getListeners(DataChangeListener.class);
			for(int i = listenerComp.length-1;i >= 0;i--){
				((KDDatePicker)obj).removeDataChangeListener(listenerComp[i]);
			}
		}
		
		// prmt
		if(obj instanceof KDBizPromptBox ){
			DataChangeListener[] listenerComp = ((KDBizPromptBox )obj).getListeners(DataChangeListener.class);
			for(int i = listenerComp.length-1;i >= 0;i--){
				((KDBizPromptBox)obj).removeDataChangeListener(listenerComp[i]);
			}
		}
	}
	
	
}
