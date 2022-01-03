/**
 * output package name
 */
package com.kingdee.eas.api.nuonuo.client;


/**
 * output class name
 */
@SuppressWarnings("serial")
public class NuoNuoBaseListUI extends AbstractNuoNuoBaseListUI
{
    
    /**
     * output class constructor
     */
    public NuoNuoBaseListUI() throws Exception
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
        return com.kingdee.eas.api.nuonuo.NuoNuoBaseFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.api.nuonuo.NuoNuoBaseInfo objectValue = new com.kingdee.eas.api.nuonuo.NuoNuoBaseInfo();
		
        return objectValue;
    }

}