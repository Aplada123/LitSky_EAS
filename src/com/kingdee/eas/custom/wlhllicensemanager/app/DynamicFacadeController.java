package com.kingdee.eas.custom.wlhllicensemanager.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface DynamicFacadeController extends BizController
{
    public String getDataByID(Context ctx, String jsonStr) throws BOSException, EASBizException, RemoteException;
    public String uploadDataByBosType(Context ctx, String bosType, String jsonStr) throws BOSException, EASBizException, RemoteException;
    public String downloadBillList(Context ctx, String jsonStr) throws BOSException, EASBizException, RemoteException;
    public String getEumInfo(Context ctx, String enumPathJson) throws BOSException, EASBizException, RemoteException;
    public String uploadData(Context ctx, String bosType, String jsonStr) throws BOSException, EASBizException, RemoteException;
    public String deleteData(Context ctx, String jsonStr) throws BOSException, EASBizException, RemoteException;
    public String exeFunciton(Context ctx, String jsonStr) throws BOSException, EASBizException, RemoteException;
    public String getRptData(Context ctx, String jsonStr) throws BOSException, EASBizException, RemoteException;
    public String uploadAttachment(Context ctx, String jsonStr) throws BOSException, EASBizException, RemoteException;
    public String getAttachmentList(Context ctx, String jsonStr) throws BOSException, EASBizException, RemoteException;
    public String getAttachmentData(Context ctx, String jsonStr) throws BOSException, EASBizException, RemoteException;
    public String deleteAttachment(Context ctx, String jsonStr) throws BOSException, EASBizException, RemoteException;
}