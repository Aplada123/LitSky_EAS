package com.kingdee.eas.api.nuonuo;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractNuoNuoBaseInfo extends com.kingdee.eas.custom.wlhllicensemanager.WlhlDataBaseInfo implements Serializable 
{
    public AbstractNuoNuoBaseInfo()
    {
        this("id");
    }
    protected AbstractNuoNuoBaseInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:��������'s Tokenproperty 
     */
    public String getToken()
    {
        return getString("Token");
    }
    public void setToken(String item)
    {
        setString("Token", item);
    }
    /**
     * Object:��������'s ��Ȩ��ַproperty 
     */
    public String getUrl()
    {
        return getString("url");
    }
    public void setUrl(String item)
    {
        setString("url", item);
    }
    /**
     * Object: �������� 's ��˾ property 
     */
    public com.kingdee.eas.basedata.org.CompanyOrgUnitInfo getCompany()
    {
        return (com.kingdee.eas.basedata.org.CompanyOrgUnitInfo)get("company");
    }
    public void setCompany(com.kingdee.eas.basedata.org.CompanyOrgUnitInfo item)
    {
        put("company", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("ACF12413");
    }
}