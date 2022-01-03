package com.kingdee.eas.custom.wlhllicensemanager;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class WlhlDataBaseCollection extends AbstractObjectCollection 
{
    public WlhlDataBaseCollection()
    {
        super(WlhlDataBaseInfo.class);
    }
    public boolean add(WlhlDataBaseInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(WlhlDataBaseCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(WlhlDataBaseInfo item)
    {
        return removeObject(item);
    }
    public WlhlDataBaseInfo get(int index)
    {
        return(WlhlDataBaseInfo)getObject(index);
    }
    public WlhlDataBaseInfo get(Object key)
    {
        return(WlhlDataBaseInfo)getObject(key);
    }
    public void set(int index, WlhlDataBaseInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(WlhlDataBaseInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(WlhlDataBaseInfo item)
    {
        return super.indexOf(item);
    }
}