/**
 * output package name
 */
package com.kingdee.eas.custom.wlhllicensemanager.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener;
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
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.custom.wlhllicensemanager.IWlhlDataBase;
import com.kingdee.eas.custom.wlhllicensemanager.UsedStatusEnum;
import com.kingdee.eas.custom.wlhllicensemanager.WlhlDataBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.query.QuickFilteringSupportItems;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public abstract class WlhlBaseListUI extends AbstractWlhlBaseListUI
{
	private static final Logger logger = CoreUIObject.getLogger(WlhlBaseListUI.class);
	private boolean isFirstLoad=true;
	/**
	 * output class constructor
	 */
	public WlhlBaseListUI() throws Exception
	{
		super();
	}



	@Override
	public void onLoad() throws Exception {
		// TODO Auto-generated method stub
		super.onLoad();
		this.mBtnApprove.setIcon(EASResource.getIcon("imgTbtn_audit"));
		this.mBtnUnApprove.setIcon(EASResource.getIcon("imgTbtn_unaudit"));
		this.tBtnApprove.setIcon(EASResource.getIcon("imgTbtn_audit"));
		this.tBtnUnApprove.setIcon(EASResource.getIcon("imgTbtn_unaudit"));
		tblMain.addKDTSelectListener(new KDTSelectListener(){
			public void tableSelectChanged(KDTSelectEvent e) {
				selectedRowChanged(e.getSelectBlock().getBeginRow());
			}});
		setMergeColumn();
	}


	@Override
	protected FilterInfo getDefaultFilterForQuery() {
		// TODO Auto-generated method stub
		FilterInfo filterInfo = super.getDefaultFilterForQuery();
		CtrlUnitInfo unitInfo = SysContext.getSysContext().getCurrentCtrlUnit();
		String longNumber = unitInfo.getLongNumber();
		String maskString = filterInfo.getMaskString();
		int size = filterInfo.getFilterItems().size();
		filterInfo.getFilterItems().add(new FilterItemInfo("CU.longNumber", longNumber + "%", CompareType.LIKE));
		if(size > 0){
			filterInfo.setMaskString(maskString + " or #" + size);
		}else{
			filterInfo.setMaskString("#" + size);
		}
		return filterInfo;
	}



	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception
	{
		checkSelected();
		if(this.getBizInterface().getValue(new ObjectUuidPK(getSelectedKeyValue())).get("baseStatus").equals(UsedStatusEnum.APPROVED_VALUE)){
			MsgBox.showWarning("资料已经核准，禁止修改！");
			SysUtil.abort();
		}
		if(this.getBizInterface().getValue(new ObjectUuidPK(getSelectedKeyValue())).get("baseStatus").equals(UsedStatusEnum.ENABLED_VALUE)){
			MsgBox.showWarning("资料已经启用，禁止修改！");
			SysUtil.abort();
		}
		super.actionEdit_actionPerformed(e);
	}

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
		//        super.tblMain_tableSelectChanged(e);
		selectedRowChanged(e.getSelectBlock().getBeginRow());
	}

	/**
	 * 行选择 改变事件
	 * @param rowIndex
	 */
	private void selectedRowChanged(int rowIndex) {
		if(rowIndex>=0) {
			String baseStatus=tblMain.getCell(rowIndex, "baseStatus").getValue()!=null?tblMain.getCell(rowIndex, "baseStatus").getValue().toString():"";
			if(baseStatus.equals("核准")) {
				this.actionApprove.setEnabled(false);
				this.actionUnApprove.setEnabled(true);
				this.actionEdit.setEnabled(false);
			}else if(baseStatus.equals("未核准")) {
				this.actionApprove.setEnabled(true);
				this.actionUnApprove.setEnabled(false);
				this.actionEdit.setEnabled(true);
			}else{
				this.actionApprove.setEnabled(false);
				this.actionUnApprove.setEnabled(false);
				this.actionEdit.setEnabled(true);
			}
		}
	}

	@Override
	public void actionApprove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		IWlhlDataBase is = (IWlhlDataBase)getBizInterface();
		for(int index=0;index<getSelectedIdValues().size();index++) {
			is.approve((WlhlDataBaseInfo) is.getValue(new ObjectUuidPK(getSelectedIdValues().get(index).toString())));
		}
		this.actionRefresh_actionPerformed(null);
	}

	@Override
	public void actionUnApprove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		IWlhlDataBase is = (IWlhlDataBase)getBizInterface();
		for(int index=0;index<getSelectedIdValues().size();index++) {
			is.unApprove((WlhlDataBaseInfo) is.getValue(new ObjectUuidPK(getSelectedIdValues().get(index).toString())));
		}
		this.actionRefresh_actionPerformed(null);
	}

	@Override
	protected ICoreBase getBizInterface() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getEditUIName() {
		// TODO Auto-generated method stub
		return null;
	}

	private void setMergeColumn()
	{
		String[] mergeColumnKeys = getMergeColumnKeys();
		if ((mergeColumnKeys == null) || (mergeColumnKeys.length <= 0))
			return;
		this.tblMain.checkParsed();
		this.tblMain.getGroupManager().setGroup(true);
		IColumn col = null;
		for (int i = 0; i < mergeColumnKeys.length; ++i)
		{
			col = this.tblMain.getColumn(mergeColumnKeys[i]);
			if (col != null) {
				col.setGroup(true);
				col.setMergeable(true);
			} else {
				logger.info("mergeColumn no found:" + mergeColumnKeys[i]);
			}
		}
	}

	public String[] getMergeColumnKeys() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected IQueryExecutor getQueryExecutor(IMetaDataPK arg0, EntityViewInfo ev) {
		//		if(isFirstLoad) {
		//			SorterItemCollection sic=new SorterItemCollection();
		//			SorterItemInfo si =new SorterItemInfo("number");
		//			si.setSortType(SortType.ASCEND);
		//			sic.add(si);
		//			ev.setSorter(sic);
		//			
		//			isFirstLoad=false;
		//		}
		return super.getQueryExecutor(arg0, ev);
	}
	QuickFilteringSupportItems qfsi = new QuickFilteringSupportItems(true, true);
	protected QuickFilteringSupportItems getQuickFilteringSupport()/*      */   {
		//		return this.qfsi.supportExecutionWithoutFilters().notSupportMainOrgUnitFilter();
		if(isNeedQueryFilter()) {
			qfsi.getSystemSolutionItem().setSolutionInfo(getCurrentSolutionInfo());
			qfsi.setMustQueryAtFirst(true);
			return qfsi;
		}else {
			return super.getQuickFilteringSupport();
		}
	}

	protected boolean isNeedQueryFilter() {
		return false;
	}
}