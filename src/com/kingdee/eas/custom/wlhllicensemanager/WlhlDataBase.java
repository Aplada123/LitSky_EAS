package com.kingdee.eas.custom.wlhllicensemanager;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.BOSException;
import java.lang.Object;
import com.kingdee.bos.dao.IObjectPK;
import java.lang.String;
import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.Context;
import com.kingdee.eas.custom.wlhllicensemanager.app.*;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.DataBase;
import com.kingdee.eas.framework.IDataBase;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import net.sf.json.JSONObject;

public abstract class WlhlDataBase extends DataBase implements IWlhlDataBase
{
    public WlhlDataBase()
    {
        super();
        registerInterface(IWlhlDataBase.class, this);
    }
    public WlhlDataBase(Context ctx)
    {
        super(ctx);
        registerInterface(IWlhlDataBase.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("39BC3AD2");
    }
    private WlhlDataBaseController getController() throws BOSException
    {
        return (WlhlDataBaseController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public WlhlDataBaseInfo getWlhlDataBaseInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getWlhlDataBaseInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@param selector 取值
     *@return
     */
    public WlhlDataBaseInfo getWlhlDataBaseInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getWlhlDataBaseInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param oql 取值
     *@return
     */
    public WlhlDataBaseInfo getWlhlDataBaseInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getWlhlDataBaseInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public WlhlDataBaseCollection getWlhlDataBaseCollection() throws BOSException
    {
        try {
            return getController().getWlhlDataBaseCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param view 取集合
     *@return
     */
    public WlhlDataBaseCollection getWlhlDataBaseCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getWlhlDataBaseCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param oql 取集合
     *@return
     */
    public WlhlDataBaseCollection getWlhlDataBaseCollection(String oql) throws BOSException
    {
        try {
            return getController().getWlhlDataBaseCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *核准-User defined method
     *@param model model
     */
    public void approve(WlhlDataBaseInfo model) throws BOSException, EASBizException
    {
        try {
            getController().approve(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *反核准-User defined method
     *@param model model
     */
    public void unApprove(WlhlDataBaseInfo model) throws BOSException, EASBizException
    {
        try {
            getController().unApprove(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *dynamicDelete-User defined method
     *@param pks pks
     */
    public void dynamicDelete(IObjectPK[] pks) throws BOSException, EASBizException
    {
        try {
            getController().dynamicDelete(getContext(), pks);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *dynamicExe-User defined method
     *@param functionName functionName
     *@param json json
     *@return
     */
    public Object dynamicExe(String functionName, JSONObject json) throws BOSException, EASBizException
    {
        try {
            return getController().dynamicExe(getContext(), functionName, json);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *dynamicSaveBatch-User defined method
     *@param coll coll
     *@return
     */
    public IObjectPK[] dynamicSaveBatch(AbstractObjectCollection coll) throws BOSException
    {
        try {
            return getController().dynamicSaveBatch(getContext(), coll);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}