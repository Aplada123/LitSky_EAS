/**
 * output package name
 */
package com.kingdee.eas.custom.wlhllicensemanager.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.ui.face.CoreUIObject;

/**
 * output class name
 */
public abstract class WlhlListUI extends AbstractWlhlListUI
{
    private static final Logger logger = CoreUIObject.getLogger(WlhlListUI.class);
    
    /**
     * output class constructor
     */
    public WlhlListUI() throws Exception
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
    

}