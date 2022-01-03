package com.kingdee.eas.api.nuonuo;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.Context;
import com.kingdee.eas.custom.wlhllicensemanager.IWlhlDataBase;
import com.kingdee.eas.api.nuonuo.app.*;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.custom.wlhllicensemanager.WlhlDataBase;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

public class NuoNuoBase extends WlhlDataBase implements INuoNuoBase
{
    public NuoNuoBase()
    {
        super();
        registerInterface(INuoNuoBase.class, this);
    }
    public NuoNuoBase(Context ctx)
    {
        super(ctx);
        registerInterface(INuoNuoBase.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("ACF12413");
    }
    private NuoNuoBaseController getController() throws BOSException
    {
        return (NuoNuoBaseController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public NuoNuoBaseInfo getNuoNuoBaseInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getNuoNuoBaseInfo(getContext(), pk);
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
    public NuoNuoBaseInfo getNuoNuoBaseInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getNuoNuoBaseInfo(getContext(), pk, selector);
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
    public NuoNuoBaseInfo getNuoNuoBaseInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getNuoNuoBaseInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public NuoNuoBaseCollection getNuoNuoBaseCollection() throws BOSException
    {
        try {
            return getController().getNuoNuoBaseCollection(getContext());
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
    public NuoNuoBaseCollection getNuoNuoBaseCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getNuoNuoBaseCollection(getContext(), view);
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
    public NuoNuoBaseCollection getNuoNuoBaseCollection(String oql) throws BOSException
    {
        try {
            return getController().getNuoNuoBaseCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取Token-User defined method
     *@param model model
     */
    public void getToken(NuoNuoBaseInfo model) throws BOSException
    {
        try {
            getController().getToken(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}