package com.kingdee.eas.custom.wlhllicensemanager;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.BOSException;
import java.lang.Object;
import com.kingdee.bos.dao.IObjectPK;
import java.lang.String;
import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.IDataBase;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import net.sf.json.JSONObject;

public interface IWlhlDataBase extends IDataBase
{
    public WlhlDataBaseInfo getWlhlDataBaseInfo(IObjectPK pk) throws BOSException, EASBizException;
    public WlhlDataBaseInfo getWlhlDataBaseInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public WlhlDataBaseInfo getWlhlDataBaseInfo(String oql) throws BOSException, EASBizException;
    public WlhlDataBaseCollection getWlhlDataBaseCollection() throws BOSException;
    public WlhlDataBaseCollection getWlhlDataBaseCollection(EntityViewInfo view) throws BOSException;
    public WlhlDataBaseCollection getWlhlDataBaseCollection(String oql) throws BOSException;
    public void approve(WlhlDataBaseInfo model) throws BOSException, EASBizException;
    public void unApprove(WlhlDataBaseInfo model) throws BOSException, EASBizException;
    public void dynamicDelete(IObjectPK[] pks) throws BOSException, EASBizException;
    public Object dynamicExe(String functionName, JSONObject json) throws BOSException, EASBizException;
    public IObjectPK[] dynamicSaveBatch(AbstractObjectCollection coll) throws BOSException;
}