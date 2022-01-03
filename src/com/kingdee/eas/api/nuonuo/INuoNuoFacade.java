package com.kingdee.eas.api.nuonuo;

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

public interface INuoNuoFacade extends IBizCtrl
{
    public String requestNewBill(String billID) throws BOSException, EASBizException;
    public String invoiceCancellation(String companyID, String invoiceId, String invoiceCode, String invoiceNo) throws BOSException, EASBizException;
    public String getISVToken(String URL, String unitID) throws BOSException, EASBizException;
    public String queryInvoiceResult(String billID) throws BOSException, EASBizException;
}