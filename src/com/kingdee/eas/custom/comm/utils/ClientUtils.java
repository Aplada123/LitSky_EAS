package com.kingdee.eas.custom.comm.utils;

import java.awt.Toolkit;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Icon;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDLabelContainer;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.WfException;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.eas.basedata.org.CtrlUnitCollection;
import com.kingdee.eas.basedata.org.CtrlUnitFactory;
import com.kingdee.eas.basedata.org.ICtrlUnit;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.framework.client.EditUI;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

public class ClientUtils {


	public static final Icon AuditIcon = EASResource.getIcon("imgTbtn_audit"); 
	public static final Icon UnAuditIcon = EASResource.getIcon("imgTbtn_unaudit");
	/**
	 * ���List�ظ�����
	 * @param array
	 * @return
	 */
	public static String[] arrayListClearSameValue(String[] array) {
		List<String> list = new ArrayList<String>();
		list.add(array[0]);
		for (int i = 1; i < array.length; i++) {
			if (list.toString().indexOf(array[i]) == -1) {
				list.add(array[i]);
			}
		} 
		String[] arrayResult = list.toArray(new String[list.size()]);
		return arrayResult;
	}

	/**
	 * �жϵ�ǰ����ID��Ӧ�ĵ����Ƿ���������
	 * @param keyIDs
	 * @return
	 * @throws BOSException
	 */
	public static boolean checkBillInWorkFlow(String[] keyIDs)  throws BOSException {
		IEnactmentService service = EnactmentServiceFactory.createRemoteEnactService();

		ArrayList<String> idList = new ArrayList<String>();

		for (int i = 0; i < keyIDs.length; ++i) {
			ProcessInstInfo instInfo = null;
			ProcessInstInfo[] procInsts;
			try {
				procInsts = service.getProcessInstanceByHoldedObjectId(keyIDs[i]);
				int j = 0; for (int n = procInsts.length; j < n; ++j) {
					if ("open.running".equals(procInsts[j].getState()) || "open.not_running.suspended".equals(procInsts[i].getState())) {
						instInfo = procInsts[j];
					}
				}
				if (instInfo != null) {
					idList.add(keyIDs[i]);
					return true;
				}
			} catch (WfException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return false;
	}

	/**
	 * У��f7�ؼ��Ƿ�Ϊ��
	 * @param f7
	 * @return
	 * @author HeMei XinXiBu
	 * @date 2020-8-19 ����11:20:35
	 * <p>Copyright: Copyright (c) 2020HeMeiJiTuan</p>
	 */
	public static final boolean checkIsNull(KDBizPromptBox f7){
		if(f7.getValue() == null && f7.getParent() != null && f7.getParent() instanceof KDLabelContainer){
			String mess = ((KDLabelContainer)f7.getParent()).getBoundLabelText();
			f7.requestFocus();
			MsgBox.showInfo(mess + "����Ϊ��");
			SysUtil.abort();
		}
		return false;
	}

	/**
	 * У��F7�ؼ�����ѡ���Ƿ�Ϊ��
	 * @param f7
	 * @return
	 * @author HeMei XinXiBu
	 * @date 2020-8-19 ����11:20:35
	 * <p>Copyright: Copyright (c) 2020HeMeiJiTuan</p>
	 */
	public static final boolean checkIsNullF7Mul(KDBizPromptBox f7){
		Object[] values = (Object[]) f7.getValue();
		boolean isNull = true;
		for(int i = 0, length = values.length; i < length; i++){
			if(values[i] != null){
				isNull = false;
				break;
			}
		}
		if(isNull && f7.getParent() != null && f7.getParent() instanceof KDLabelContainer){
			String mess = ((KDLabelContainer)f7.getParent()).getBoundLabelText();
			f7.requestFocus();
			MsgBox.showInfo(mess + "����Ϊ��");
			SysUtil.abort();
		}
		return false;
	}

	/**
	 * У��DatePick�ؼ��Ƿ�Ϊ��
	 * @param pkDate
	 * @return
	 * @author HeMei XinXiBu
	 * @date 2020-8-19 ����11:20:35
	 * <p>Copyright: Copyright (c) 2020HeMeiJiTuan</p>
	 */
	public static final boolean checkIsNull(KDDatePicker pkDate){
		if(pkDate.getValue() == null && pkDate.getParent() != null && pkDate.getParent() instanceof KDLabelContainer){
			String mess = ((KDLabelContainer)pkDate.getParent()).getBoundLabelText();
			pkDate.requestFocus();
			MsgBox.showInfo(mess + "����Ϊ��");
			SysUtil.abort();
		}
		return false;
	}
	/**
	 * У��txtField�ؼ��Ƿ�Ϊ��
	 * @param f7
	 * @return
	 * @author HeMei XinXiBu
	 * @date 2020-8-19 ����11:20:35
	 * <p>Copyright: Copyright (c) 2020HeMeiJiTuan</p>
	 */
	public static final boolean checkIsNull(KDFormattedTextField textField){
		if(UIRuleUtil.getBigDecimal(textField.getStringValue()).compareTo(BigDecimal.ZERO) == 0 && textField.getParent() != null && textField.getParent() instanceof KDLabelContainer){
			String mess = ((KDLabelContainer)textField.getParent()).getBoundLabelText();
			textField.requestFocus();
			MsgBox.showInfo(mess + "����Ϊ��");
			SysUtil.abort();
		}
		return false;
	}
	/**
	 * ��ü��ŵ��������ڱ������lable����ʾ
	 * @param ctx
	 * @return
	 * @author HeMei XinXiBu
	 * @throws BOSException 
	 * @date 2019-12-31 ����05:10:29
	 * <p>Copyright: Copyright (c) 2019HeMeiJiTuan</p>
	 */
	public static String getGroupName() throws BOSException{
		ICtrlUnit iCtrl = null;
		iCtrl = CtrlUnitFactory.getRemoteInstance();
		CtrlUnitCollection coll = iCtrl.getCtrlUnitCollection("where level = 1");
		if(coll.size() > 0){
			return coll.get(0).getName();
		}else{
			return "";
		}
	}

	/**
	 * ��EAS�л�ȡϵͳ��ͼ��
	 * @param btnName
	 * @return
	 * @author HeMei Department
	 * @date 2021-11-4 ����07:55:09
	 * <p>Copyright: Copyright (c) 2021HeMei Group</p>
	 */
	public static Icon getIconFromEASResource(String btnName){
		return EASResource.getIcon(btnName);
	}

	/**
	 * ��ȡ����֯������
	 * @return
	 * @throws Exception
	 */
	public static String getRootOrgUnitName(Context ctx) throws Exception{
		IRowSet rs = null;
		if(ctx == null){
			rs = SQLExecutorFactory.getRemoteInstance("select fname_l2 fname from T_ORG_CtrlUnit where FLevel=1").executeSQL();
		}else{
			rs = DbUtil.executeQuery(ctx, "select fname_l2 fname from T_ORG_CtrlUnit where FLevel=1");
		}
		if(rs.next()){
			return rs.getString("fname").replace("����", "");
		}else{
			return "";
		}
	}

	/**
	 * �ͻ��˸�����ʾ���˳�
	 * @param tips   ��ʾ��Ϣ
	 * @author HeMei XinXiBu
	 * @date 2020-1-9 ����09:21:46
	 * <p>Copyright: Copyright (c) 2019HeMeiJiTuan</p>
	 */
	public static void giveUserTipsAndRetire(String tips) {
		MsgBox.showInfo(tips);
		SysUtil.abort();
	}


	/**
	 * ���ӽ���
	 * @param UIFactoryName
	 * @param destBillEditUIClassName
	 * @param hashMap
	 * @param strNull
	 * @param OprtState
	 * @author HeMei XinXiBu
	 * @date 2020-1-9 ����09:24:59
	 * <p>Copyright: Copyright (c) 2019HeMeiJiTuan</p>
	 */
	public static void openChildUIFromParentUI(String UIFactoryName, String destBillEditUIClassName,HashMap<Object,Object> hashMap,Object strNull,String OprtState){ 
		IUIWindow uiWindow = null ;
		try {
			uiWindow = UIFactory.createUIFactory(UIFactoryName).create(destBillEditUIClassName, hashMap, null,OprtState);
		} catch (UIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//��ʼչ��UI
		uiWindow.show();
	}

	/**
	 * ResultSetתMap
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> resultSetToListMap(ResultSet rs)
	throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		int fieldCount = rsmd.getColumnCount();
		List records = new ArrayList();
		Map valueMap = null;
		while (rs.next()) {
			valueMap = new HashMap();
			for (int i = 1; i <= fieldCount; ++i) {
				String fieldName = rsmd.getColumnName(i);
				if (rs.wasNull())
					valueMap.put(fieldName, null);
				else {
					valueMap.put(fieldName, rs.getObject(fieldName));
				}
			}
			records.add(valueMap);
		}
		return records;
	}

	/**
	 * ˢ�½���ͨ�ð�
	 * @param coreBillEditUI
	 * @throws EASBizException
	 * @throws BOSException
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static void refreshEditUI(com.kingdee.eas.framework.client.CoreBillEditUI coreBillEditUI) throws EASBizException, BOSException, Exception {
		CoreBillBaseInfo billInfo = coreBillEditUI.getEditData();
		if (billInfo.getId() != null) {
			com.kingdee.bos.dao.IObjectPK iObjectPk = new ObjectUuidPK(billInfo.getId());
			Method m = null;    
			Class clazz=coreBillEditUI.getClass();    
			while(true){    
				try {    
					m=clazz.getDeclaredMethod("getValue", new Class[]{IObjectPK.class});    
					break;    
				} catch (NoSuchMethodException e) {    
					clazz=clazz.getSuperclass();    
				}    
			}    
			m.setAccessible(true);    
			IObjectValue iObjectValue=(IObjectValue) m.invoke(coreBillEditUI, new Object[]{iObjectPk});   
			coreBillEditUI.setDataObject(iObjectValue);
			coreBillEditUI.loadFields();
			coreBillEditUI.setSave(true);
		}
	}

	/**
	 * ���ñ༭����ȫ��
	 * 
	 * @author HeMei Department
	 * @param editUI 
	 * @date 2021-10-17 ����09:33:18
	 * <p>Copyright: Copyright (c) 2021HeMei Group</p>
	 */
	public static void setPreferredSize(EditUI editUI) {
		// TODO Auto-generated method stub
		editUI.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
	}

	/**
	 * ��ӡ���StringBuffer�ַ���
	 * ��Ϊһ��������ޣ�����5000�ַ���δ�ӡ
	 * @param sb
	 */
	public static void systemOutPrintln(StringBuffer sb) {
		int i = 0;

		while (sb.length() > i * 5000 && sb.length() > (i + 1) * 5000) {
			System.out.println(sb.substring(i * 5000, (i + 1) * 5000));
			i++;
		}  if (sb.length() > i * 5000) {
			System.out.println(sb.substring(i * 5000, sb.length()));
		}
	}

}
