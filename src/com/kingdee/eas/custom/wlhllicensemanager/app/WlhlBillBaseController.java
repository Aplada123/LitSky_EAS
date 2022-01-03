package com.kingdee.eas.custom.wlhllicensemanager.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.eas.custom.wlhllicensemanager.WlhlBillBaseInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.BOSException;
import java.lang.Object;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectCollection;
import java.lang.String;
import com.kingdee.eas.custom.wlhllicensemanager.WlhlBillBaseCollection;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.app.CoreBillBaseController;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface WlhlBillBaseController extends CoreBillBaseController
{
    public boolean exists(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public boolean exists(Context ctx, FilterInfo filter) throws BOSException, EASBizException, RemoteException;
    public boolean exists(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public WlhlBillBaseInfo getWlhlBillBaseInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public WlhlBillBaseInfo getWlhlBillBaseInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public WlhlBillBaseInfo getWlhlBillBaseInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public IObjectPK addnew(Context ctx, WlhlBillBaseInfo model) throws BOSException, EASBizException, RemoteException;
    public void addnew(Context ctx, IObjectPK pk, WlhlBillBaseInfo model) throws BOSException, EASBizException, RemoteException;
    public void update(Context ctx, IObjectPK pk, WlhlBillBaseInfo model) throws BOSException, EASBizException, RemoteException;
    public void updatePartial(Context ctx, WlhlBillBaseInfo model, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public void updateBigObject(Context ctx, IObjectPK pk, WlhlBillBaseInfo model) throws BOSException, RemoteException;
    public void delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] getPKList(Context ctx) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] getPKList(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] getPKList(Context ctx, FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException, RemoteException;
    public WlhlBillBaseCollection getWlhlBillBaseCollection(Context ctx) throws BOSException, RemoteException;
    public WlhlBillBaseCollection getWlhlBillBaseCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public WlhlBillBaseCollection getWlhlBillBaseCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public IObjectPK[] delete(Context ctx, FilterInfo filter) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] delete(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public void delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException, RemoteException;
    public void audit(Context ctx, IObjectValue model) throws BOSException, EASBizException, RemoteException;
    public void unAudit(Context ctx, IObjectValue model) throws BOSException, EASBizException, RemoteException;
    public IObjectPK dynamicSave(Context ctx, WlhlBillBaseInfo model) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] dynamicSaveBatch(Context ctx, IObjectCollection cols) throws BOSException, EASBizException, RemoteException;
    public void dynamicDelete(Context ctx, IObjectPK[] pks) throws BOSException, EASBizException, RemoteException;
    public Object dynamicExe(Context ctx, String exeType, Object obj) throws BOSException, EASBizException, RemoteException;
}