package com.kingdee.eas.api.nuonuo;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.Context;
import com.kingdee.eas.custom.wlhllicensemanager.IWlhlDataBase;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import java.lang.String;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.*;

public interface INuoNuoBase extends IWlhlDataBase
{
    public NuoNuoBaseInfo getNuoNuoBaseInfo(IObjectPK pk) throws BOSException, EASBizException;
    public NuoNuoBaseInfo getNuoNuoBaseInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public NuoNuoBaseInfo getNuoNuoBaseInfo(String oql) throws BOSException, EASBizException;
    public NuoNuoBaseCollection getNuoNuoBaseCollection() throws BOSException;
    public NuoNuoBaseCollection getNuoNuoBaseCollection(EntityViewInfo view) throws BOSException;
    public NuoNuoBaseCollection getNuoNuoBaseCollection(String oql) throws BOSException;
    public void getToken(NuoNuoBaseInfo model) throws BOSException;
}