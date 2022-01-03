package com.kingdee.eas.custom.wlhllicensemanager.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.custom.wlhllicensemanager.WlhlDataBaseTreeCollection;
import com.kingdee.bos.Context;
import com.kingdee.eas.custom.wlhllicensemanager.WlhlDataBaseTreeInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.app.TreeBaseController;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface WlhlDataBaseTreeController extends TreeBaseController
{
    public WlhlDataBaseTreeInfo getWlhlDataBaseTreeInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public WlhlDataBaseTreeInfo getWlhlDataBaseTreeInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public WlhlDataBaseTreeInfo getWlhlDataBaseTreeInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public WlhlDataBaseTreeCollection getWlhlDataBaseTreeCollection(Context ctx) throws BOSException, RemoteException;
    public WlhlDataBaseTreeCollection getWlhlDataBaseTreeCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public WlhlDataBaseTreeCollection getWlhlDataBaseTreeCollection(Context ctx, String oql) throws BOSException, RemoteException;
}