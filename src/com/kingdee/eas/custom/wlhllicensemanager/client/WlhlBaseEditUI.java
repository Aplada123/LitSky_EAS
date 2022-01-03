/**
 * output package name
 */
package com.kingdee.eas.custom.wlhllicensemanager.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.dao.AbstractObjectValue;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.custom.wlhllicensemanager.IWlhlDataBase;
import com.kingdee.eas.custom.wlhllicensemanager.UsedStatusEnum;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public abstract class WlhlBaseEditUI extends AbstractWlhlBaseEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(WlhlBaseEditUI.class);
    
    /**
     * output class constructor
     */
    public WlhlBaseEditUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
    
    public void loadFields()
    {
    	super.loadFields(); 
    	setControlsByBillStatus();
    }
    

    private void setControlsByBillStatus() {
    	if(this.editData.getInt("baseStatus")==UsedStatusEnum.APPROVED_VALUE) {
			this.actionApprove.setEnabled(false);
			this.actionUnApprove.setEnabled(true);
		}else if (this.editData.getInt("baseStatus")==UsedStatusEnum.ENABLED_VALUE){
			this.actionApprove.setEnabled(false);
			this.actionUnApprove.setEnabled(false);
		}else {
			this.actionApprove.setEnabled(true);
			this.actionUnApprove.setEnabled(true);
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
	public void onLoad() throws Exception {
		// TODO Auto-generated method stub
		super.onLoad();
		this.mBtnApprove.setIcon(EASResource.getIcon("imgTbtn_audit"));
		this.mBtnUnApprove.setIcon(EASResource.getIcon("imgTbtn_unaudit"));
		this.tBtnApprove.setIcon(EASResource.getIcon("imgTbtn_audit"));
		this.tBtnUnApprove.setIcon(EASResource.getIcon("imgTbtn_unaudit"));
		actionSave.setVisible(false);
		setControlsByBillStatus();
	}
    
    /**
     * output actionApprove_actionPerformed
     */
    public void actionApprove_actionPerformed(ActionEvent e) throws Exception
    {
    	storeFields();
		((IWlhlDataBase)getBizInterface()).approve(this.editData);
		doAfterSave(new ObjectUuidPK(editData.getId()));
		setOprtState(STATUS_VIEW);
		lockUIForViewStatus();
		setNextMessageText("核准成功");
		setShowMessagePolicy(0);
		setIsShowTextOnly(false);
		showMessage();
        
    }
    
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		if(this.editData.getInt("baseStatus")==UsedStatusEnum.APPROVED_VALUE) {
    		MsgBox.showWarning("资料已经核准，禁止修改！");
    		SysUtil.abort();
    	}
		if(this.editData.getInt("baseStatus")==UsedStatusEnum.ENABLED_VALUE) {
    		MsgBox.showWarning("资料已经启用，禁止修改！");
    		SysUtil.abort();
    	}
		super.actionEdit_actionPerformed(e);
	}

    /**
     * output actionUnApprove_actionPerformed
     */
    public void actionUnApprove_actionPerformed(ActionEvent e) throws Exception
    {
    	storeFields();
		((IWlhlDataBase)getBizInterface()).unApprove(this.editData);
		doAfterSave(new ObjectUuidPK(editData.getId()));
		setOprtState(STATUS_VIEW);
		lockUIForViewStatus();
		setNextMessageText("反核准成功");
		setShowMessagePolicy(0);
		setIsShowTextOnly(false);
		showMessage();
        
    }

    @Override
	protected void setFieldsNull(AbstractObjectValue newData) {
		// TODO Auto-generated method stub
		super.setFieldsNull(newData);
		newData.put("baseStatus", UsedStatusEnum.ADDNEW_VALUE);
	}

	/**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return null;
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        return null;
    }

}