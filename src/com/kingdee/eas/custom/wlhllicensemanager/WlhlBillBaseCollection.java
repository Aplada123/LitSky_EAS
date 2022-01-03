package com.kingdee.eas.custom.wlhllicensemanager;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class WlhlBillBaseCollection extends AbstractObjectCollection 
{
    public WlhlBillBaseCollection()
    {
        super(WlhlBillBaseInfo.class);
    }
    public boolean add(WlhlBillBaseInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(WlhlBillBaseCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(WlhlBillBaseInfo item)
    {
        return removeObject(item);
    }
    public WlhlBillBaseInfo get(int index)
    {
        return(WlhlBillBaseInfo)getObject(index);
    }
    public WlhlBillBaseInfo get(Object key)
    {
        return(WlhlBillBaseInfo)getObject(key);
    }
    public void set(int index, WlhlBillBaseInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(WlhlBillBaseInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(WlhlBillBaseInfo item)
    {
        return super.indexOf(item);
    }
}