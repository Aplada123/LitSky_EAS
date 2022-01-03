package com.kingdee.eas.custom.comm.utils.bill;

import java.awt.Component;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.eas.basedata.master.material.MaterialGroupInfo;
import com.kingdee.eas.basedata.master.material.client.F7MaterialTreeListUI;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.scm.common.client.GeneralKDPromptSelectorAdaptor;
import com.kingdee.eas.scm.common.client.SCMGroupClientUtils;

public class F7Utils {
	//QuerySLClientUtils.getBizCustomerF7(prmtCustomer, "com.kingdee.eas.basedata.master.cssp.app.F7CustomerQuery", this, OrgType.Sale);SLClientUtils.getBizMaterialF7(prmtMaterial, null, this, OrgType.Sale, false);
	//	prmtCustomer.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7CustomerQuery");
	//	F7BaseSelector selector = new F7CustomerSimpleSelector(this, prmtCustomer);
	//	prmtCustomer.setSelector(selector);
	public void getMaterialF7ForReportFilterUI(KDBizPromptBox kDBizPromptBoxMaterial) throws Exception {
		GeneralKDPromptSelectorAdaptor selectorLisenterMaterial = null;
		MaterialGroupInfo info = new MaterialGroupInfo();
		selectorLisenterMaterial = new GeneralKDPromptSelectorAdaptor(kDBizPromptBoxMaterial, new F7MaterialTreeListUI(), this, info.getBOSType().toString(), "com.kingdee.eas.basedata.master.material.app.F7MaterialInventoryQuery", "materialGroup.id", false);
		selectorLisenterMaterial.setIsMultiSelect(false);
		selectorLisenterMaterial.setQueryProperty("helpCode", "or");
		kDBizPromptBoxMaterial.setSelector(selectorLisenterMaterial);
		kDBizPromptBoxMaterial.addSelectorListener(selectorLisenterMaterial);
		// kDBizPromptBoxMaterial.setQueryInfo("com.kingdee.eas.basedata.master.material.app.F7MaterialInventoryNoGroupQuery");}/**
	}
	/** ��������ÿͻ�F7<���ڱ�ͷ�ͻ�F7>
	 * @param prmtPromptBox     F7�ؼ�
	 * @param queryInfo     QueryInfo
	 * @param owner     ������
	 * @param orgType   ��֯����
	 */
	public static void getBizCustomerF7(KDBizPromptBox prmtPromptBox, String queryInfo, Component owner, OrgType orgType){
		OrgUnitInfo orgInfo = SysContext.getSysContext().getCurrentOrgUnit(orgType);
		SCMGroupClientUtils.setBizCustomerF7(prmtPromptBox, owner, orgType, queryInfo);
		SCMGroupClientUtils.setApproved4CustomerF7(prmtPromptBox, orgType);
		prmtPromptBox.setCurrentMainBizOrgUnit(orgInfo, orgType);
	}
	/**
	 * �������������F7<���ڱ�ͷ����F7>
	 * @param prmtPromptBox     F7�ؼ�
	 * @param queryInfo     QueryInfo  Query ����Ϊnull���������֯���Ͳ�ѯ
	 * @param owner     ������
	 * @param orgType   ��֯����
	 * @throws BOSException
	 */public static void getBizMaterialF7(KDBizPromptBox prmtPromptBox, String queryInfo, Component owner, OrgType orgType, boolean isMulSelect) throws BOSException{
		 OrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentOrgUnit(orgType);
		 SCMGroupClientUtils.setBizMaterialF7(prmtPromptBox, owner, orgType, isMulSelect, queryInfo);
		 SCMGroupClientUtils.setApproved4MaterialF7(prmtPromptBox, orgType);
		 prmtPromptBox.setCurrentMainBizOrgUnit(orgUnit, orgType);
	 }
}
