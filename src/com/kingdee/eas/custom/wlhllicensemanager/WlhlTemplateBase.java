package com.kingdee.eas.custom.wlhllicensemanager;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.Context;
import com.kingdee.eas.custom.wlhllicensemanager.app.*;
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

public class WlhlTemplateBase extends WlhlDataBase implements IWlhlTemplateBase
{
    public WlhlTemplateBase()
    {
        super();
        registerInterface(IWlhlTemplateBase.class, this);
    }
    public WlhlTemplateBase(Context ctx)
    {
        super(ctx);
        registerInterface(IWlhlTemplateBase.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("1611BC22");
    }
    private WlhlTemplateBaseController getController() throws BOSException
    {
        return (WlhlTemplateBaseController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public WlhlTemplateBaseInfo getWlhlTemplateBaseInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getWlhlTemplateBaseInfo(getContext(), pk);
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
    public WlhlTemplateBaseInfo getWlhlTemplateBaseInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getWlhlTemplateBaseInfo(getContext(), pk, selector);
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
    public WlhlTemplateBaseInfo getWlhlTemplateBaseInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getWlhlTemplateBaseInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public WlhlTemplateBaseCollection getWlhlTemplateBaseCollection() throws BOSException
    {
        try {
            return getController().getWlhlTemplateBaseCollection(getContext());
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
    public WlhlTemplateBaseCollection getWlhlTemplateBaseCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getWlhlTemplateBaseCollection(getContext(), view);
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
    public WlhlTemplateBaseCollection getWlhlTemplateBaseCollection(String oql) throws BOSException
    {
        try {
            return getController().getWlhlTemplateBaseCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}