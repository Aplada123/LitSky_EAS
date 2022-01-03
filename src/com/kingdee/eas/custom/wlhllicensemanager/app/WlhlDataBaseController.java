package com.kingdee.eas.custom.wlhllicensemanager.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.BOSException;
import java.lang.Object;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.custom.wlhllicensemanager.WlhlDataBaseCollection;
import java.lang.String;
import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.eas.custom.wlhllicensemanager.WlhlDataBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.app.DataBaseController;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import net.sf.json.JSONObject;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface WlhlDataBaseController extends DataBaseController
{
    public WlhlDataBaseInfo getWlhlDataBaseInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public WlhlDataBaseInfo getWlhlDataBaseInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public WlhlDataBaseInfo getWlhlDataBaseInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public WlhlDataBaseCollection getWlhlDataBaseCollection(Context ctx) throws BOSException, RemoteException;
    public WlhlDataBaseCollection getWlhlDataBaseCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public WlhlDataBaseCollection getWlhlDataBaseCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public void approve(Context ctx, WlhlDataBaseInfo model) throws BOSException, EASBizException, RemoteException;
    public void unApprove(Context ctx, WlhlDataBaseInfo model) throws BOSException, EASBizException, RemoteException;
    public void dynamicDelete(Context ctx, IObjectPK[] pks) throws BOSException, EASBizException, RemoteException;
    public Object dynamicExe(Context ctx, String functionName, JSONObject json) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] dynamicSaveBatch(Context ctx, AbstractObjectCollection coll) throws BOSException, RemoteException;
}