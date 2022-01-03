package com.kingdee.eas.custom.wlhllicensemanager;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class WlhlDataBaseTreeCollection extends AbstractObjectCollection 
{
    public WlhlDataBaseTreeCollection()
    {
        super(WlhlDataBaseTreeInfo.class);
    }
    public boolean add(WlhlDataBaseTreeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(WlhlDataBaseTreeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(WlhlDataBaseTreeInfo item)
    {
        return removeObject(item);
    }
    public WlhlDataBaseTreeInfo get(int index)
    {
        return(WlhlDataBaseTreeInfo)getObject(index);
    }
    public WlhlDataBaseTreeInfo get(Object key)
    {
        return(WlhlDataBaseTreeInfo)getObject(key);
    }
    public void set(int index, WlhlDataBaseTreeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(WlhlDataBaseTreeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(WlhlDataBaseTreeInfo item)
    {
        return super.indexOf(item);
    }
}