package com.kingdee.eas.custom.wlhllicensemanager;

import java.io.Serializable;

public abstract class WlhlDataBaseTreeInfo extends AbstractWlhlDataBaseTreeInfo implements Serializable 
{
    public WlhlDataBaseTreeInfo()
    {
        super();
    }
    protected WlhlDataBaseTreeInfo(String pkField)
    {
        super(pkField);
    }
}