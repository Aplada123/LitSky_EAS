package com.kingdee.eas.custom.wlhllicensemanager;

import java.io.Serializable;

public abstract class WlhlBillBaseInfo extends AbstractWlhlBillBaseInfo implements Serializable 
{
    public WlhlBillBaseInfo()
    {
        super();
    }
    protected WlhlBillBaseInfo(String pkField)
    {
        super(pkField);
    }
}