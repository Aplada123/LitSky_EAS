package com.kingdee.eas.custom.wlhllicensemanager;

import java.io.Serializable;

public abstract class WlhlDataBaseInfo extends AbstractWlhlDataBaseInfo implements Serializable 
{
    public WlhlDataBaseInfo()
    {
        super();
    }
    protected WlhlDataBaseInfo(String pkField)
    {
        super(pkField);
    }
}