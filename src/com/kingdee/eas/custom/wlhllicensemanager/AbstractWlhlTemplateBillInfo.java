package com.kingdee.eas.custom.wlhllicensemanager;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractWlhlTemplateBillInfo extends com.kingdee.eas.custom.wlhllicensemanager.WlhlBillBaseInfo implements Serializable 
{
    public AbstractWlhlTemplateBillInfo()
    {
        this("id");
    }
    protected AbstractWlhlTemplateBillInfo(String pkField)
    {
        super(pkField);
        put("Entrys", new com.kingdee.eas.custom.wlhllicensemanager.WlhlTemplateBillEntryCollection());
    }
    /**
     * Object:业务单据模板's 是否生成凭证property 
     */
    public boolean isFivouchered()
    {
        return getBoolean("Fivouchered");
    }
    public void setFivouchered(boolean item)
    {
        setBoolean("Fivouchered", item);
    }
    /**
     * Object: 业务单据模板 's 公司 property 
     */
    public com.kingdee.eas.basedata.org.CompanyOrgUnitInfo getCompany()
    {
        return (com.kingdee.eas.basedata.org.CompanyOrgUnitInfo)get("company");
    }
    public void setCompany(com.kingdee.eas.basedata.org.CompanyOrgUnitInfo item)
    {
        put("company", item);
    }
    /**
     * Object: 业务单据模板 's 分录 property 
     */
    public com.kingdee.eas.custom.wlhllicensemanager.WlhlTemplateBillEntryCollection getEntrys()
    {
        return (com.kingdee.eas.custom.wlhllicensemanager.WlhlTemplateBillEntryCollection)get("Entrys");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("1611D958");
    }
}