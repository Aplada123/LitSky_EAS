package com.kingdee.eas.api.nuonuo.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.eas.api.nuonuo.NuoNuoBaseCollection;
import com.kingdee.eas.custom.wlhllicensemanager.app.WlhlDataBaseController;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import java.lang.String;
import com.kingdee.eas.api.nuonuo.NuoNuoBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface NuoNuoBaseController extends WlhlDataBaseController
{
    public NuoNuoBaseInfo getNuoNuoBaseInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public NuoNuoBaseInfo getNuoNuoBaseInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public NuoNuoBaseInfo getNuoNuoBaseInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public NuoNuoBaseCollection getNuoNuoBaseCollection(Context ctx) throws BOSException, RemoteException;
    public NuoNuoBaseCollection getNuoNuoBaseCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public NuoNuoBaseCollection getNuoNuoBaseCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public void getToken(Context ctx, NuoNuoBaseInfo model) throws BOSException, RemoteException;
}