package com.kingdee.eas.custom.wlhllicensemanager;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.BOSException;
import java.lang.Object;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectCollection;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBillBase;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

public interface IWlhlBillBase extends ICoreBillBase
{
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException;
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException;
    public boolean exists(String oql) throws BOSException, EASBizException;
    public WlhlBillBaseInfo getWlhlBillBaseInfo(IObjectPK pk) throws BOSException, EASBizException;
    public WlhlBillBaseInfo getWlhlBillBaseInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public WlhlBillBaseInfo getWlhlBillBaseInfo(String oql) throws BOSException, EASBizException;
    public IObjectPK addnew(WlhlBillBaseInfo model) throws BOSException, EASBizException;
    public void addnew(IObjectPK pk, WlhlBillBaseInfo model) throws BOSException, EASBizException;
    public void update(IObjectPK pk, WlhlBillBaseInfo model) throws BOSException, EASBizException;
    public void updatePartial(WlhlBillBaseInfo model, SelectorItemCollection selector) throws BOSException, EASBizException;
    public void updateBigObject(IObjectPK pk, WlhlBillBaseInfo model) throws BOSException;
    public void delete(IObjectPK pk) throws BOSException, EASBizException;
    public IObjectPK[] getPKList() throws BOSException, EASBizException;
    public IObjectPK[] getPKList(String oql) throws BOSException, EASBizException;
    public IObjectPK[] getPKList(FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException;
    public WlhlBillBaseCollection getWlhlBillBaseCollection() throws BOSException;
    public WlhlBillBaseCollection getWlhlBillBaseCollection(EntityViewInfo view) throws BOSException;
    public WlhlBillBaseCollection getWlhlBillBaseCollection(String oql) throws BOSException;
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException;
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException;
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException;
    public void audit(IObjectValue model) throws BOSException, EASBizException;
    public void unAudit(IObjectValue model) throws BOSException, EASBizException;
    public IObjectPK dynamicSave(WlhlBillBaseInfo model) throws BOSException, EASBizException;
    public IObjectPK[] dynamicSaveBatch(IObjectCollection cols) throws BOSException, EASBizException;
    public void dynamicDelete(IObjectPK[] pks) throws BOSException, EASBizException;
    public Object dynamicExe(String exeType, Object obj) throws BOSException, EASBizException;
}