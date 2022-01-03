package com.kingdee.eas.api.nuonuo;

import java.io.Serializable;

public class NuoNuoBaseEntryInfo extends AbstractNuoNuoBaseEntryInfo implements Serializable 
{
    public NuoNuoBaseEntryInfo()
    {
        super();
    }
    protected NuoNuoBaseEntryInfo(String pkField)
    {
        super(pkField);
    }
}