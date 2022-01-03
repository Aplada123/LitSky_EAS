/**
 * output package name
 */
package com.kingdee.eas.custom.wlhllicensemanager.client;

import org.apache.log4j.Logger;

import com.kingdee.bos.ui.face.CoreUIObject;

/**
 * output class name
 */
public abstract class WlhlBaseTreeEditUI extends AbstractWlhlBaseTreeEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(WlhlBaseTreeEditUI.class);
    
    /**
     * output class constructor
     */
    public WlhlBaseTreeEditUI() throws Exception
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