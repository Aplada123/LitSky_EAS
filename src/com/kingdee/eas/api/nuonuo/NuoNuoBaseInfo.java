package com.kingdee.eas.api.nuonuo;

import java.io.Serializable;

public class NuoNuoBaseInfo extends AbstractNuoNuoBaseInfo implements Serializable 
{
    public NuoNuoBaseInfo()
    {
        super();
    }
    protected NuoNuoBaseInfo(String pkField)
    {
        super(pkField);
    }
}