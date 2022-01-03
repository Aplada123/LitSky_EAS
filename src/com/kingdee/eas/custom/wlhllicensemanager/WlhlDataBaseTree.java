package com.kingdee.eas.custom.wlhllicensemanager;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.bos.dao.IObjectPK;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.TreeBase;
import com.kingdee.eas.custom.wlhllicensemanager.app.*;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

public abstract class WlhlDataBaseTree extends TreeBase implements IWlhlDataBaseTree
{
    public WlhlDataBaseTree()
    {
        super();
        registerInterface(IWlhlDataBaseTree.class, this);
    }
    public WlhlDataBaseTree(Context ctx)
    {
        super(ctx);
        registerInterface(IWlhlDataBaseTree.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("38C66990");
    }
    private WlhlDataBaseTreeController getController() throws BOSException
    {
        return (WlhlDataBaseTreeController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public WlhlDataBaseTreeInfo getWlhlDataBaseTreeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getWlhlDataBaseTreeInfo(getContext(), pk);
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
    public WlhlDataBaseTreeInfo getWlhlDataBaseTreeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getWlhlDataBaseTreeInfo(getContext(), pk, selector);
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
    public WlhlDataBaseTreeInfo getWlhlDataBaseTreeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getWlhlDataBaseTreeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public WlhlDataBaseTreeCollection getWlhlDataBaseTreeCollection() throws BOSException
    {
        try {
            return getController().getWlhlDataBaseTreeCollection(getContext());
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
    public WlhlDataBaseTreeCollection getWlhlDataBaseTreeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getWlhlDataBaseTreeCollection(getContext(), view);
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
    public WlhlDataBaseTreeCollection getWlhlDataBaseTreeCollection(String oql) throws BOSException
    {
        try {
            return getController().getWlhlDataBaseTreeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}