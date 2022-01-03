package com.kingdee.eas.api.nuonuo.app;

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

public interface NuoNuoFacadeController extends BizController
{
    public String requestNewBill(Context ctx, String billID) throws BOSException, EASBizException, RemoteException;
    public String invoiceCancellation(Context ctx, String companyID, String invoiceId, String invoiceCode, String invoiceNo) throws BOSException, EASBizException, RemoteException;
    public String getISVToken(Context ctx, String URL, String unitID) throws BOSException, EASBizException, RemoteException;
    public String queryInvoiceResult(Context ctx, String billID) throws BOSException, EASBizException, RemoteException;
}