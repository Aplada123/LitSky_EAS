package com.kingdee.eas.custom.wlhllicensemanager;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class WlhlTemplateBillEntryCollection extends AbstractObjectCollection 
{
    public WlhlTemplateBillEntryCollection()
    {
        super(WlhlTemplateBillEntryInfo.class);
    }
    public boolean add(WlhlTemplateBillEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(WlhlTemplateBillEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(WlhlTemplateBillEntryInfo item)
    {
        return removeObject(item);
    }
    public WlhlTemplateBillEntryInfo get(int index)
    {
        return(WlhlTemplateBillEntryInfo)getObject(index);
    }
    public WlhlTemplateBillEntryInfo get(Object key)
    {
        return(WlhlTemplateBillEntryInfo)getObject(key);
    }
    public void set(int index, WlhlTemplateBillEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(WlhlTemplateBillEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(WlhlTemplateBillEntryInfo item)
    {
        return super.indexOf(item);
    }
}