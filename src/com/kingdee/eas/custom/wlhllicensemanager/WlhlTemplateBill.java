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

public class WlhlTemplateBill extends WlhlBillBase implements IWlhlTemplateBill
{
    public WlhlTemplateBill()
    {
        super();
        registerInterface(IWlhlTemplateBill.class, this);
    }
    public WlhlTemplateBill(Context ctx)
    {
        super(ctx);
        registerInterface(IWlhlTemplateBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("1611D958");
    }
    private WlhlTemplateBillController getController() throws BOSException
    {
        return (WlhlTemplateBillController)getBizController();
    }
    /**
     *取集合-System defined method
     *@return
     */
    public WlhlTemplateBillCollection getWlhlTemplateBillCollection() throws BOSException
    {
        try {
            return getController().getWlhlTemplateBillCollection(getContext());
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
    public WlhlTemplateBillCollection getWlhlTemplateBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getWlhlTemplateBillCollection(getContext(), view);
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
    public WlhlTemplateBillCollection getWlhlTemplateBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getWlhlTemplateBillCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public WlhlTemplateBillInfo getWlhlTemplateBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getWlhlTemplateBillInfo(getContext(), pk);
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
    public WlhlTemplateBillInfo getWlhlTemplateBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getWlhlTemplateBillInfo(getContext(), pk, selector);
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
    public WlhlTemplateBillInfo getWlhlTemplateBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getWlhlTemplateBillInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *审核-User defined method
     *@param model model
     */
    public void audit(WlhlTemplateBillInfo model) throws BOSException, EASBizException
    {
        try {
            getController().audit(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *反审核-User defined method
     *@param model model
     */
    public void unAudit(WlhlTemplateBillInfo model) throws BOSException, EASBizException
    {
        try {
            getController().unAudit(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}