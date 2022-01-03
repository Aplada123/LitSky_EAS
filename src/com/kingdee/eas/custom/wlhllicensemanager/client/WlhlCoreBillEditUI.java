/**
 * output package name
 */
package com.kingdee.eas.custom.wlhllicensemanager.client;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.dao.AbstractObjectValue;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.custom.wlhllicensemanager.IWlhlBillBase;
import com.kingdee.eas.framework.ObjectValueUtil;
import com.kingdee.eas.framework.batchHandler.UtilRequest;
import com.kingdee.eas.scm.common.BillBaseStatusEnum;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public abstract class WlhlCoreBillEditUI extends AbstractWlhlCoreBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(WlhlCoreBillEditUI.class);
    
    /**
     * output class constructor
     */
    public WlhlCoreBillEditUI() throws Exception
    {
        super();
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setPreferredSize(new Dimension((int)(width*0.8),(int)(height*0.8)));
    }
    
    public void loadFields()
    {
    	super.loadFields(); 
    	setControlsByBillStatus();
    }
    
    private void setControlsByBillStatus() {
    	if(this.editData.getInt("billStatus")==BillBaseStatusEnum.AUDITED_VALUE) {
//			this.actionAudit.setEnabled(false);
//			this.actionUnAudit.setEnabled(true);
		}else if (this.editData.getInt("billStatus")==BillBaseStatusEnum.SUBMITED_VALUE){
//			this.actionAudit.setEnabled(true);
//			this.actionUnAudit.setEnabled(false);
		}else {
//			this.actionAudit.setEnabled(true);
//			this.actionUnAudit.setEnabled(true);
		}
    }
    
    @Override
	protected void doAfterSave(IObjectPK pk) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterSave(pk);
		setControlsByBillStatus();
	}

	@Override
	protected void doAfterSubmit(IObjectPK pk) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterSubmit(pk);
		setControlsByBillStatus();
	}

	@Override
	protected IObjectValue createNewDetailData(KDTable arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected KDTable getDetailTable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onLoad() throws Exception {
		// TODO Auto-generated method stub
		super.onLoad();
//      WlhlLicenseManagerFactory.getRemoteInstance().checkLicense(getBOSObjectType());
//		this.mBtnAudit.setIcon(EASResource.getIcon("imgTbtn_audit"));
//		this.mBtnUnAudit.setIcon(EASResource.getIcon("imgTbtn_unaudit"));
//		this.tBtnAudit.setIcon(EASResource.getIcon("imgTbtn_audit"));
//		this.tBtnUnAudit.setIcon(EASResource.getIcon("imgTbtn_unaudit"));
		setControlsByBillStatus();
	}

	
	@Override
	public void onShow() throws Exception {
		super.onShow();
		this.chkMenuItemSubmitAndPrint.setSelected(false);
	}
	 @Override
		protected void setFieldsNull(AbstractObjectValue newData) {
			// TODO Auto-generated method stub
			super.setFieldsNull(newData);
			newData.put("billStatus", BillBaseStatusEnum.ADD_VALUE);
		}

	/**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
    
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		if(this.editData.getInt("billStatus")==BillBaseStatusEnum.AUDITED_VALUE) {
    		MsgBox.showWarning("单据已经审核，禁止修改！");
    		SysUtil.abort();
    	}
		super.actionEdit_actionPerformed(e);
	}

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		storeFields();
		((IWlhlBillBase)getBizInterface()).audit(this.editData);
		doAfterSave(new ObjectUuidPK(editData.getId()));
		setOprtState(STATUS_VIEW);
		lockUIForViewStatus();
		setNextMessageText("审核成功");
		setShowMessagePolicy(0);
		setIsShowTextOnly(false);
		showMessage();
	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		((IWlhlBillBase)getBizInterface()).unAudit(this.editData);
		doAfterSave(new ObjectUuidPK(editData.getId()));
		setOprtState(STATUS_VIEW);
		lockUIForViewStatus();
		unLockUI();
		setNextMessageText("反审核成功");
		setShowMessagePolicy(0);
		setIsShowTextOnly(false);
		showMessage();
	}

	@Override
	protected void applyDefaultValue(IObjectValue vo) {
		super.applyDefaultValue(vo);
		vo.put("billStatus", BillBaseStatusEnum.ADD_VALUE);
		vo.put("bizDate", new java.util.Date());
	}
	@Override
	public void actionCopy_actionPerformed(ActionEvent e) throws Exception {
		//		super.actionCopy_actionPerformed(e);
		if(!UtilRequest.isPrepare("ActionCopy", this))
			checkModified();
		if(editData != null && !OprtState.VIEW.equals(getOprtState()))
		{
			IObjectValue objectValue = (IObjectValue)getUIContext().get("CURRENT.VO");
			if(objectValue != null)
				try
			{
					String id = idList.getID(idList.getCurrentIndex());
					setOprtState("RELEASEALL");
					pubFireVOChangeListener(id);
			}
			catch(Throwable E) { }
		}
		ObjectValueUtil.copy(editData);
		unLockUI();
		setFieldsNull(editData);
		editData.setInt("billStatus",BillBaseStatusEnum.ADD_VALUE);
		//        editData.setNumber(null);
		editData.setBizDate(new java.util.Date());
		setOprtState("ADDNEW");
		setDataObject(editData);
		loadFields();
		showCopyAddNew();
		actionCopy.setEnabled(false);
		chkMenuItemSubmitAndAddNew.setVisible(true);
		setDefaultFocused();

		setMakeRelations(null);
	}
}