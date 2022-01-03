package com.kingdee.eas.custom.wlhllicensemanager;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public abstract class AbstractWlhlDataBaseTreeInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractWlhlDataBaseTreeInfo()
    {
        this("id");
    }
    protected AbstractWlhlDataBaseTreeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: Wlhl基础资料组别 's 父节点 property 
     */
    public com.kingdee.eas.custom.wlhllicensemanager.WlhlDataBaseTreeInfo getParent()
    {
        return (com.kingdee.eas.custom.wlhllicensemanager.WlhlDataBaseTreeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.custom.wlhllicensemanager.WlhlDataBaseTreeInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("38C66990");
    }
}