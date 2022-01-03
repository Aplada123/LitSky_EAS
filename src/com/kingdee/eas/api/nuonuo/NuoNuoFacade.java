package com.kingdee.eas.api.nuonuo;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.api.nuonuo.app.*;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;

public class NuoNuoFacade extends AbstractBizCtrl implements INuoNuoFacade
{
    public NuoNuoFacade()
    {
        super();
        registerInterface(INuoNuoFacade.class, this);
    }
    public NuoNuoFacade(Context ctx)
    {
        super(ctx);
        registerInterface(INuoNuoFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("3C04855C");
    }
    private NuoNuoFacadeController getController() throws BOSException
    {
        return (NuoNuoFacadeController)getBizController();
    }
    /**
     *新开发票-User defined method
     *@param billID billID
     *@return
     */
    public String requestNewBill(String billID) throws BOSException, EASBizException
    {
        try {
            return getController().requestNewBill(getContext(), billID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *废弃发票-User defined method
     *@param companyID companyID
     *@param invoiceId invoiceId
     *@param invoiceCode invoiceCode
     *@param invoiceNo invoiceNo
     *@return
     */
    public String invoiceCancellation(String companyID, String invoiceId, String invoiceCode, String invoiceNo) throws BOSException, EASBizException
    {
        try {
            return getController().invoiceCancellation(getContext(), companyID, invoiceId, invoiceCode, invoiceNo);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *根据网址获取Token-User defined method
     *@param URL URL
     *@param unitID unitID
     *@return
     */
    public String getISVToken(String URL, String unitID) throws BOSException, EASBizException
    {
        try {
            return getController().getISVToken(getContext(), URL, unitID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *查询开票结果-User defined method
     *@param billID billID
     *@return
     */
    public String queryInvoiceResult(String billID) throws BOSException, EASBizException
    {
        try {
            return getController().queryInvoiceResult(getContext(), billID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}