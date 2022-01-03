/**
 * output package name
 */
package com.kingdee.eas.custom.wlhllicensemanager.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.param.IParamControl;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.custom.wlhllicensemanager.IWlhlBillBase;
import com.kingdee.eas.framework.query.QuickFilteringSupportItems;
import com.kingdee.eas.framework.query.QuickFilteringSupportManager;
import com.kingdee.eas.framework.util.BizOrgEnum;
import com.kingdee.eas.framework.util.DateEnum;
import com.kingdee.eas.scm.common.BillBaseStatusEnum;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.LowTimer;

/**
 * output class name
 */
public abstract class WlhlCoreBillListUI extends AbstractWlhlCoreBillListUI
{
	private static final Logger logger = CoreUIObject.getLogger(WlhlCoreBillListUI.class);
	private boolean isFirstLoad=true;

	/**
	 * output class constructor
	 */
	public WlhlCoreBillListUI() throws Exception
	{
		super();
		//        WlhlLicenseManagerFactory.getRemoteInstance().checkLicense(createNewData().getBOSType());
	}

	protected abstract IObjectValue createNewData();

	/**
	 * output storeFields method
	 */
	public void storeFields()
	{
		super.storeFields();
	}


	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
	{
		super.tblMain_tableSelectChanged(e);
		selectedRowChanged(e.getSelectBlock().getBeginRow());
	}

	/**
	 * 行选择 改变事件
	 * @param rowIndex
	 */
	private void selectedRowChanged(int rowIndex) {
		ArrayList list = this.getSelectedFieldValues("billStatus");
		if(list.size()>0) {
			if(list.get(0).equals("审核")) {
				//				this.actionAudit.setEnabled(false);
				//				this.actionUnAudit.setEnabled(true);
				this.actionEdit.setEnabled(false);
			}else if(list.get(0).equals("提交")) {
				//				this.actionAudit.setEnabled(true);
				//				this.actionUnAudit.setEnabled(false);
				this.actionEdit.setEnabled(true);
			}else{
				//				this.actionAudit.setEnabled(false);
				//				this.actionUnAudit.setEnabled(false);
				this.actionEdit.setEnabled(true);
			}
		}
	}

	@Override
	public void onLoad() throws Exception {
		// TODO Auto-generated method stub
		super.onLoad();
		//		this.mBtnAudit.setIcon(EASResource.getIcon("imgTbtn_audit"));
		//		this.mBtnUnAudit.setIcon(EASResource.getIcon("imgTbtn_unaudit"));
		//		this.tBtnAudit.setIcon(EASResource.getIcon("imgTbtn_audit"));
		//		this.tBtnUnAudit.setIcon(EASResource.getIcon("imgTbtn_unaudit"));

		//		setDefaultQuickFilterInfo();

	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception
	{
		checkSelected();
		if(this.getBizInterface().getValue(new ObjectUuidPK(getSelectedKeyValue())).get("billStatus").equals(BillBaseStatusEnum.AUDITED_VALUE)){
			MsgBox.showWarning("单据已经审核，禁止修改！");
			SysUtil.abort();
		}
		super.actionEdit_actionPerformed(e);
	}

	/**
	 * output actionAudit_actionPerformed
	 */
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception
	{	
		checkSelected();
		IWlhlBillBase is = (IWlhlBillBase)getBizInterface();
		for(int index=0;index<getSelectedIdValues().size();index++) {
			is.audit(is.getValue(new ObjectUuidPK(getSelectedIdValues().get(index).toString())));
		}
		this.actionRefresh_actionPerformed(null);
	}

	/**
	 * output actionUnAudit_actionPerformed
	 */
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
	{
		checkSelected();
		IWlhlBillBase is = (IWlhlBillBase)getBizInterface();
		for(int index=0;index<getSelectedIdValues().size();index++) {
			is.unAudit(is.getValue(new ObjectUuidPK(getSelectedIdValues().get(index).toString())));
		}
		this.actionRefresh_actionPerformed(null);
	}

	@Override
	protected IQueryExecutor getQueryExecutor(IMetaDataPK arg0, EntityViewInfo ev) {
//		if(isFirstLoad) {
//			SorterItemCollection sic=new SorterItemCollection();
//			SorterItemInfo si =new SorterItemInfo("bizDate");
//			si.setSortType(SortType.DESCEND);
//			sic.add(si);
//
//			si = new SorterItemInfo("number");
//			si.setSortType(SortType.DESCEND);
//			sic.add(si);
//			
//			si = new SorterItemInfo("createTime");
//			si.setSortType(SortType.DESCEND);
//			sic.add(si);
//			ev.setSorter(sic);
//
//			isFirstLoad=false;
//		}
		return super.getQueryExecutor(arg0, ev);
	}

	protected FilterInfo getDefaultFilterForQuery() {
		// TODO Auto-generated method stub
		return super.getDefaultFilterForQuery();
	}

	protected boolean initDefaultFilter() {
		return false;
	}

	@Override
	protected CommonQueryDialog initCommonQueryDialog() {
		// TODO Auto-generated method stub
		CommonQueryDialog ds = super.initCommonQueryDialog();
		Date nowDate = new java.util.Date();
		Calendar cal=Calendar.getInstance();
		cal.setTime(nowDate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
		Date beginDate = cal.getTime();
		cal.add(Calendar.MONTH,1);//月增加1天 
		cal.add(Calendar.DAY_OF_MONTH,-1);//日期倒数一日,既得到本月最后一天 
		Date endDate = cal.getTime();

		EntityViewInfo ev=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("bizDate",beginDate,CompareType.GREATER_EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("bizDate",endDate,CompareType.LESS_EQUALS));
		ev.setFilter(filter);
		ds.setDefalutEntityViewInfo(ev);
		return ds;
	}
	QuickFilteringSupportItems qfsi = new QuickFilteringSupportItems(true, true);
	protected QuickFilteringSupportItems getQuickFilteringSupport()  {
		if(isNeedQuickQueryFilter()) {
			//			QuickFilteringSupportItems qfsi = new QuickFilteringSupportItems(true, true);
			qfsi.getSystemSolutionItem().setSolutionInfo(getCurrentSolutionInfo());
			//			qfsi.setMustQueryAtFirst(true);
			return qfsi;
		}else {
			return super.getQuickFilteringSupport();
		}
	}

	protected boolean isNeedQuickQueryFilter() {
		return false;
	}
}