package com.kingdee.eas.custom.wlhllicensemanager;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractWlhlTemplateBaseEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractWlhlTemplateBaseEntryInfo()
    {
        this("id");
    }
    protected AbstractWlhlTemplateBaseEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ·ÖÂ¼ 's null property 
     */
    public com.kingdee.eas.custom.wlhllicensemanager.WlhlTemplateBaseInfo getParent()
    {
        return (com.kingdee.eas.custom.wlhllicensemanager.WlhlTemplateBaseInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.custom.wlhllicensemanager.WlhlTemplateBaseInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("19A8A170");
    }
}