package com.kingdee.eas.custom.wlhllicensemanager;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.Context;
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

public interface IWlhlTemplateBill extends IWlhlBillBase
{
    public WlhlTemplateBillCollection getWlhlTemplateBillCollection() throws BOSException;
    public WlhlTemplateBillCollection getWlhlTemplateBillCollection(EntityViewInfo view) throws BOSException;
    public WlhlTemplateBillCollection getWlhlTemplateBillCollection(String oql) throws BOSException;
    public WlhlTemplateBillInfo getWlhlTemplateBillInfo(IObjectPK pk) throws BOSException, EASBizException;
    public WlhlTemplateBillInfo getWlhlTemplateBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public WlhlTemplateBillInfo getWlhlTemplateBillInfo(String oql) throws BOSException, EASBizException;
    public void audit(WlhlTemplateBillInfo model) throws BOSException, EASBizException;
    public void unAudit(WlhlTemplateBillInfo model) throws BOSException, EASBizException;
}