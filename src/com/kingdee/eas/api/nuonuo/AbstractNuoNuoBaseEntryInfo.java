package com.kingdee.eas.api.nuonuo;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractNuoNuoBaseEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractNuoNuoBaseEntryInfo()
    {
        this("id");
    }
    protected AbstractNuoNuoBaseEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ŵŵ�������÷�¼ 's null property 
     */
    public com.kingdee.eas.api.nuonuo.NuoNuoBaseInfo getParent()
    {
        return (com.kingdee.eas.api.nuonuo.NuoNuoBaseInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.api.nuonuo.NuoNuoBaseInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: ŵŵ�������÷�¼ 's ��˾ property 
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
     * Object:ŵŵ�������÷�¼'s ˰��property 
     */
    public String getTaxNum()
    {
        return getString("taxNum");
    }
    public void setTaxNum(String item)
    {
        setString("taxNum", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("5491881F");
    }
}