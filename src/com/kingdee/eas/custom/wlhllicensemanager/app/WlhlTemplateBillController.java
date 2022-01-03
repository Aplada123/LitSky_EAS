package com.kingdee.eas.custom.wlhllicensemanager.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.BOSException;
import com.kingdee.eas.custom.wlhllicensemanager.WlhlTemplateBillInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.custom.wlhllicensemanager.WlhlTemplateBillCollection;
import java.lang.String;
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

public interface WlhlTemplateBillController extends WlhlBillBaseController
{
    public WlhlTemplateBillCollection getWlhlTemplateBillCollection(Context ctx) throws BOSException, RemoteException;
    public WlhlTemplateBillCollection getWlhlTemplateBillCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public WlhlTemplateBillCollection getWlhlTemplateBillCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public WlhlTemplateBillInfo getWlhlTemplateBillInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public WlhlTemplateBillInfo getWlhlTemplateBillInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public WlhlTemplateBillInfo getWlhlTemplateBillInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public void audit(Context ctx, WlhlTemplateBillInfo model) throws BOSException, EASBizException, RemoteException;
    public void unAudit(Context ctx, WlhlTemplateBillInfo model) throws BOSException, EASBizException, RemoteException;
}