/**
 * output package name
 */
package com.kingdee.eas.custom.wlhllicensemanager.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.custom.wlhllicensemanager.IWlhlDataBase;
import com.kingdee.eas.custom.wlhllicensemanager.WlhlDataBaseInfo;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.query.QuickFilteringSupportItems;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public abstract class WlhlTreeDetailListUI extends AbstractWlhlTreeDetailListUI
{
    private static final Logger logger = CoreUIObject.getLogger(WlhlTreeDetailListUI.class);
    private boolean isFirstLoad=true;
    /**
     * output class constructor
     */
    public WlhlTreeDetailListUI() throws Exception
    {
        super();
    }
    
    @Override
	public void onLoad() throws Exception {
		// TODO Auto-generated method stub
		super.onLoad();
//		this.mBtnApprove.setIcon(EASResource.getIcon("imgTbtn_audit"));
//		this.mBtnUnApprove.setIcon(EASResource.getIcon("imgTbtn_unaudit"));
//		this.tBtnApprove.setIcon(EASResource.getIcon("imgTbtn_audit"));
//		this.tBtnUnApprove.setIcon(EASResource.getIcon("imgTbtn_unaudit"));
		tblMain.addKDTSelectListener(new KDTSelectListener(){
			public void tableSelectChanged(KDTSelectEvent e) {
				selectedRowChanged(e.getSelectBlock().getBeginRow());
			}});
		setMergeColumn();
//        WlhlLicenseManagerFactory.getRemoteInstance().checkLicense(createNewData().getBOSType());
	}
    
    /**
	 * 行选择 改变事件
	 * @param rowIndex
	 */
	private void selectedRowChanged(int rowIndex) {
		if(rowIndex>=0) {
			String baseStatus=tblMain.getCell(rowIndex, "baseStatus").getValue()!=null?tblMain.getCell(rowIndex, "baseStatus").getValue().toString():"";
			if(baseStatus.equals("核准")) {
//				this.actionApprove.setEnabled(false);
//				this.actionUnApprove.setEnabled(true);
				this.actionEdit.setEnabled(false);
			}else if(baseStatus.equals("未核准")) {
//				this.actionApprove.setEnabled(true);
//				this.actionUnApprove.setEnabled(false);
				this.actionEdit.setEnabled(true);
			}else{
//				this.actionApprove.setEnabled(false);
//				this.actionUnApprove.setEnabled(false);
				this.actionEdit.setEnabled(true);
			}
		}
	}

	
	
    @Override
	protected String getGroupEditUIName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getQueryFieldName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected IObjectPK getSelectedTreeKeyValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ITreeBase getTreeInterface() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onShow() throws Exception {
		// TODO Auto-generated method stub
		super.onShow();
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

    public void actionApprove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		IWlhlDataBase is = (IWlhlDataBase)getBizInterface();
        for(int index=0;index<getSelectedIdValues().size();index++) {
        	is.approve((WlhlDataBaseInfo) is.getValue(new ObjectUuidPK(getSelectedIdValues().get(index).toString())));
        }
        this.actionRefresh_actionPerformed(e);
	}

	public void actionUnApprove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		IWlhlDataBase is = (IWlhlDataBase)getBizInterface();
        for(int index=0;index<getSelectedIdValues().size();index++) {
        	is.unApprove((WlhlDataBaseInfo) is.getValue(new ObjectUuidPK(getSelectedIdValues().get(index).toString())));
        }
        this.actionRefresh_actionPerformed(e);
	}

	public String[] getMergeColumnKeys() {
		// TODO Auto-generated method stub
		return null;
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
    }
	@Override
	protected IQueryExecutor getQueryExecutor(IMetaDataPK arg0, EntityViewInfo ev) {
		if(isFirstLoad) {
			SorterItemCollection sic=new SorterItemCollection();
			SorterItemInfo si =new SorterItemInfo("number");
			si.setSortType(SortType.ASCEND);
			sic.add(si);
			ev.setSorter(sic);
			
			isFirstLoad=false;
		}
		return super.getQueryExecutor(arg0, ev);
	}
	QuickFilteringSupportItems qfsi = new QuickFilteringSupportItems(true, true);
	 protected QuickFilteringSupportItems getQuickFilteringSupport()/*      */   {
//			return this.qfsi.supportExecutionWithoutFilters().notSupportMainOrgUnitFilter();
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