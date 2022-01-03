package com.kingdee.eas.custom.wlhllicensemanager;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class WlhlTemplateBaseCollection extends AbstractObjectCollection 
{
    public WlhlTemplateBaseCollection()
    {
        super(WlhlTemplateBaseInfo.class);
    }
    public boolean add(WlhlTemplateBaseInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(WlhlTemplateBaseCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(WlhlTemplateBaseInfo item)
    {
        return removeObject(item);
    }
    public WlhlTemplateBaseInfo get(int index)
    {
        return(WlhlTemplateBaseInfo)getObject(index);
    }
    public WlhlTemplateBaseInfo get(Object key)
    {
        return(WlhlTemplateBaseInfo)getObject(key);
    }
    public void set(int index, WlhlTemplateBaseInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(WlhlTemplateBaseInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(WlhlTemplateBaseInfo item)
    {
        return super.indexOf(item);
    }
}