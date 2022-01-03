package com.kingdee.eas.custom.wlhllicensemanager;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.Context;
import com.kingdee.eas.custom.wlhllicensemanager.app.*;
import com.kingdee.bos.BOSException;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;

public class DynamicFacade extends AbstractBizCtrl implements IDynamicFacade
{
    public DynamicFacade()
    {
        super();
        registerInterface(IDynamicFacade.class, this);
    }
    public DynamicFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IDynamicFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("FAA341FB");
    }
    private DynamicFacadeController getController() throws BOSException
    {
        return (DynamicFacadeController)getBizController();
    }
    /**
     *getDataByID-User defined method
     *@param jsonStr jsonStr
     *@return
     */
    public String getDataByID(String jsonStr) throws BOSException, EASBizException
    {
        try {
            return getController().getDataByID(getContext(), jsonStr);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *uploadDataByBosType-User defined method
     *@param bosType bosType
     *@param jsonStr jsonStr
     *@return
     */
    public String uploadDataByBosType(String bosType, String jsonStr) throws BOSException, EASBizException
    {
        try {
            return getController().uploadDataByBosType(getContext(), bosType, jsonStr);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *downloadBillList-User defined method
     *@param jsonStr jsonStr
     *@return
     */
    public String downloadBillList(String jsonStr) throws BOSException, EASBizException
    {
        try {
            return getController().downloadBillList(getContext(), jsonStr);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取枚举对象-User defined method
     *@param enumPathJson 路径
     *@return
     */
    public String getEumInfo(String enumPathJson) throws BOSException, EASBizException
    {
        try {
            return getController().getEumInfo(getContext(), enumPathJson);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *上传数据-User defined method
     *@param bosType bosType
     *@param jsonStr jsonStr
     *@return
     */
    public String uploadData(String bosType, String jsonStr) throws BOSException, EASBizException
    {
        try {
            return getController().uploadData(getContext(), bosType, jsonStr);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *deleteData-User defined method
     *@param jsonStr jsonStr
     *@return
     */
    public String deleteData(String jsonStr) throws BOSException, EASBizException
    {
        try {
            return getController().deleteData(getContext(), jsonStr);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *exeFunciton-User defined method
     *@param jsonStr jsonStr
     *@return
     */
    public String exeFunciton(String jsonStr) throws BOSException, EASBizException
    {
        try {
            return getController().exeFunciton(getContext(), jsonStr);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getRptData-User defined method
     *@param jsonStr jsonStr
     *@return
     */
    public String getRptData(String jsonStr) throws BOSException, EASBizException
    {
        try {
            return getController().getRptData(getContext(), jsonStr);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *uploadAttachment-User defined method
     *@param jsonStr jsonStr
     *@return
     */
    public String uploadAttachment(String jsonStr) throws BOSException, EASBizException
    {
        try {
            return getController().uploadAttachment(getContext(), jsonStr);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getAttachmentList-User defined method
     *@param jsonStr jsonStr
     *@return
     */
    public String getAttachmentList(String jsonStr) throws BOSException, EASBizException
    {
        try {
            return getController().getAttachmentList(getContext(), jsonStr);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getAttachmentData-User defined method
     *@param jsonStr jsonStr
     *@return
     */
    public String getAttachmentData(String jsonStr) throws BOSException, EASBizException
    {
        try {
            return getController().getAttachmentData(getContext(), jsonStr);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *deleteAttachment-User defined method
     *@param jsonStr jsonStr
     *@return
     */
    public String deleteAttachment(String jsonStr) throws BOSException, EASBizException
    {
        try {
            return getController().deleteAttachment(getContext(), jsonStr);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}