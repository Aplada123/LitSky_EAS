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
	 * @param component 扩展报表ID，查询SQL：SELECT * FROM  T_BAS_DefineReport Where FName_l2 like '%reportName%'
	 * @param reportID  过滤条件，
	 */
	public static void openReport(String rptID, ArrayList<IParameter> paramList) throws EASBizException, BOSException{
//		DefineReportInfo reportInfo = ExtEasBillUtil.showReportDialog();
//		if (reportInfo == null) {
//			// 点击取消，返回
//			return;
//		}
		DefineReportInfo reportInfo = DefineReportFactory.getRemoteInstance().getDefineReportInfo(new ObjectUuidPK(rptID));
		String reportID = reportInfo.getId().toString(); // 报表ID
		String reportName = reportInfo.getName(); // 报表名称
		String systemID = reportInfo.getSystemID(); // 子系统路径
		String orgID = reportInfo.getOrgID().toString();
		// 单据需要把以上的reportID、reportName、systemID, orgID保留起来
		// 如果传入的参数mapFilter不等于null，不会出现报表过滤界面，否则弹出报表的过滤界面
		// 传入的参数名称、数据类型必须和数据集中定义的一致
		// 参数类型：0 字符串、1 数值、2 日期(yyyy-MM-dd格式)、3 布尔、4 日期时间(yyyy-MM-dd HH:mm:ss格式)、5 时间(HH:mm:ss格式)
		Map mapFilter = new HashMap();
		for(IParameter i : paramList){
			mapFilter.put(i.getName(), i);
		}

//		IParameter p1 = new ParameterImpl();
//		p1.setName("Name"); // 姓名
//		p1.setValue(RunReportParam.getVariant("李", 0));
//		p1.setDataType(0);
//		mapFilter.put(p1.getName(), p1);
		
//
//		IParameter p2 = new ParameterImpl();
//		p2.setName("Birthday"); // 出生日前
//		p2.setValue(RunReportParam.getVariant("1980-08-08", 2));
//		p2.setDataType(2);
//		mapFilter.put(p2.getName(), p2);
//
//		// 定义一个多选的参数
//		IParameter p3 = new ParameterImpl();
//		p3.setName("Gender"); // 性别
//		p3.setValue(RunReportParam.getVariant(new String[]{"1", "2"}, 1));
//		p3.setDataType(1);
//		mapFilter.put(p3.getName(), p3);
//
//		// 如果是下拉框、多选框、F7选择，每个参数都对应一个别名,在原参数后加_text
//		IParameter p4 = new ParameterImpl();
//		p4.setName("Gender_text"); // 性别别名
//		p4.setValue(RunReportParam.getVariant(new String[]{"男", "女"}, 0));
//		p4.setDataType(0); // 别名的数据类型都是字符串
//		mapFilter.put(p4.getName(), p4);

		// 执行报表
		/**
			* 在单据调用扩展报表窗口
			* @param component 当前UI
		    * @param reportID 目标报表ID
		    * @param reportName 目标报表名称
		    * @param systemID 目标报表的子系统树路径
		    * @param orgID 目标报表的创建组织，如果为null，系统默认取当前登录组织
		    * @param showType 新窗口展现方式: 0:弹出窗口， 1新建页签,注意：如果发出请求的单据是弹出窗口，不能使用新建页签的方式
		    * @param params 参数map
		   	*/
		ExtEasBillUtil.executeWithinWindow(null, reportID, reportName, systemID, orgID, 0, mapFilter);
	}

}
