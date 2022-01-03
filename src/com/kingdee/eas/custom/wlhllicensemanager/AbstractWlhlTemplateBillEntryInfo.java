package com.kingdee.eas.custom.wlhllicensemanager;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractWlhlTemplateBillEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractWlhlTemplateBillEntryInfo()
    {
        this("id");
    }
    protected AbstractWlhlTemplateBillEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ·ÖÂ¼ 's null property 
     */
    public com.kingdee.eas.custom.wlhllicensemanager.WlhlTemplateBillInfo getParent()
    {
        return (com.kingdee.eas.custom.wlhllicensemanager.WlhlTemplateBillInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.custom.wlhllicensemanager.WlhlTemplateBillInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F25855FA");
    }
}