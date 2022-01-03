package com.kingdee.eas.custom.wlhllicensemanager;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class WlhlTemplateBillCollection extends AbstractObjectCollection 
{
    public WlhlTemplateBillCollection()
    {
        super(WlhlTemplateBillInfo.class);
    }
    public boolean add(WlhlTemplateBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(WlhlTemplateBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(WlhlTemplateBillInfo item)
    {
        return removeObject(item);
    }
    public WlhlTemplateBillInfo get(int index)
    {
        return(WlhlTemplateBillInfo)getObject(index);
    }
    public WlhlTemplateBillInfo get(Object key)
    {
        return(WlhlTemplateBillInfo)getObject(key);
    }
    public void set(int index, WlhlTemplateBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(WlhlTemplateBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(WlhlTemplateBillInfo item)
    {
        return super.indexOf(item);
    }
}