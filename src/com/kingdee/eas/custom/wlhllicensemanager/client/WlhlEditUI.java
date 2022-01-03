/**
 * output package name
 */
package com.kingdee.eas.custom.wlhllicensemanager.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ui.face.CoreUIObject;

/**
 * output class name
 */
public abstract class WlhlEditUI extends AbstractWlhlEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(WlhlEditUI.class);
    
    /**
     * output class constructor
     */
    public WlhlEditUI() throws Exception
    {
        super();
//        WlhlLicenseManagerFactory.getRemoteInstance().checkLicense(getBOSObjectType());
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }


}