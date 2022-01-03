package com.kingdee.eas.custom.comm.utils.EASTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kds.expans.model.data.IParameter;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.rpts.ctrlreport.DefineReportFactory;
import com.kingdee.eas.rpts.ctrlreport.DefineReportInfo;
import com.kingdee.eas.rpts.ctrlreport.util.ExtEasBillUtil;

public class ReportUtils {
	@SuppressWarnings("unchecked")

	/**
	 * @param component ��չ����ID����ѯSQL��SELECT * FROM  T_BAS_DefineReport Where FName_l2 like '%reportName%'
	 * @param reportID  ����������
	 */
	public static void openReport(String rptID, ArrayList<IParameter> paramList) throws EASBizException, BOSException{
//		DefineReportInfo reportInfo = ExtEasBillUtil.showReportDialog();
//		if (reportInfo == null) {
//			// ���ȡ��������
//			return;
//		}
		DefineReportInfo reportInfo = DefineReportFactory.getRemoteInstance().getDefineReportInfo(new ObjectUuidPK(rptID));
		String reportID = reportInfo.getId().toString(); // ����ID
		String reportName = reportInfo.getName(); // ��������
		String systemID = reportInfo.getSystemID(); // ��ϵͳ·��
		String orgID = reportInfo.getOrgID().toString();
		// ������Ҫ�����ϵ�reportID��reportName��systemID, orgID��������
		// �������Ĳ���mapFilter������null��������ֱ�����˽��棬���򵯳�����Ĺ��˽���
		// ����Ĳ������ơ��������ͱ�������ݼ��ж����һ��
		// �������ͣ�0 �ַ�����1 ��ֵ��2 ����(yyyy-MM-dd��ʽ)��3 ������4 ����ʱ��(yyyy-MM-dd HH:mm:ss��ʽ)��5 ʱ��(HH:mm:ss��ʽ)
		Map mapFilter = new HashMap();
		for(IParameter i : paramList){
			mapFilter.put(i.getName(), i);
		}

//		IParameter p1 = new ParameterImpl();
//		p1.setName("Name"); // ����
//		p1.setValue(RunReportParam.getVariant("��", 0));
//		p1.setDataType(0);
//		mapFilter.put(p1.getName(), p1);
		
//
//		IParameter p2 = new ParameterImpl();
//		p2.setName("Birthday"); // ������ǰ
//		p2.setValue(RunReportParam.getVariant("1980-08-08", 2));
//		p2.setDataType(2);
//		mapFilter.put(p2.getName(), p2);
//
//		// ����һ����ѡ�Ĳ���
//		IParameter p3 = new ParameterImpl();
//		p3.setName("Gender"); // �Ա�
//		p3.setValue(RunReportParam.getVariant(new String[]{"1", "2"}, 1));
//		p3.setDataType(1);
//		mapFilter.put(p3.getName(), p3);
//
//		// ����������򡢶�ѡ��F7ѡ��ÿ����������Ӧһ������,��ԭ�������_text
//		IParameter p4 = new ParameterImpl();
//		p4.setName("Gender_text"); // �Ա����
//		p4.setValue(RunReportParam.getVariant(new String[]{"��", "Ů"}, 0));
//		p4.setDataType(0); // �������������Ͷ����ַ���
//		mapFilter.put(p4.getName(), p4);

		// ִ�б���
		/**
			* �ڵ��ݵ�����չ������
			* @param component ��ǰUI
		    * @param reportID Ŀ�걨��ID
		    * @param reportName Ŀ�걨������
		    * @param systemID Ŀ�걨�����ϵͳ��·��
		    * @param orgID Ŀ�걨��Ĵ�����֯�����Ϊnull��ϵͳĬ��ȡ��ǰ��¼��֯
		    * @param showType �´���չ�ַ�ʽ: 0:�������ڣ� 1�½�ҳǩ,ע�⣺�����������ĵ����ǵ������ڣ�����ʹ���½�ҳǩ�ķ�ʽ
		    * @param params ����map
		   	*/
		ExtEasBillUtil.executeWithinWindow(null, reportID, reportName, systemID, orgID, 0, mapFilter);
	}

}
