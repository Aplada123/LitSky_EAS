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
	/** 描述：获得客户F7<用于表头客户F7>
	 * @param prmtPromptBox     F7控件
	 * @param queryInfo     QueryInfo
	 * @param owner     所有者
	 * @param orgType   组织类型
	 */
	public static void getBizCustomerF7(KDBizPromptBox prmtPromptBox, String queryInfo, Component owner, OrgType orgType){
		OrgUnitInfo orgInfo = SysContext.getSysContext().getCurrentOrgUnit(orgType);
		SCMGroupClientUtils.setBizCustomerF7(prmtPromptBox, owner, orgType, queryInfo);
		SCMGroupClientUtils.setApproved4CustomerF7(prmtPromptBox, orgType);
		prmtPromptBox.setCurrentMainBizOrgUnit(orgInfo, orgType);
	}
	/**
	 * 描述：获得物料F7<用于表头物料F7>
	 * @param prmtPromptBox     F7控件
	 * @param queryInfo     QueryInfo  Query 设置为null，会根据组织类型查询
	 * @param owner     所有者
	 * @param orgType   组织类型
	 * @throws BOSException
	 */public static void getBizMaterialF7(KDBizPromptBox prmtPromptBox, String queryInfo, Component owner, OrgType orgType, boolean isMulSelect) throws BOSException{
		 OrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentOrgUnit(orgType);
		 SCMGroupClientUtils.setBizMaterialF7(prmtPromptBox, owner, orgType, isMulSelect, queryInfo);
		 SCMGroupClientUtils.setApproved4MaterialF7(prmtPromptBox, orgType);
		 prmtPromptBox.setCurrentMainBizOrgUnit(orgUnit, orgType);
	 }
}
