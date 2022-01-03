package com.kingdee.eas.custom.wlhllicensemanager;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class WlhlTemplateBaseEntryCollection extends AbstractObjectCollection 
{
    public WlhlTemplateBaseEntryCollection()
    {
        super(WlhlTemplateBaseEntryInfo.class);
    }
    public boolean add(WlhlTemplateBaseEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(WlhlTemplateBaseEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(WlhlTemplateBaseEntryInfo item)
    {
        return removeObject(item);
    }
    public WlhlTemplateBaseEntryInfo get(int index)
    {
        return(WlhlTemplateBaseEntryInfo)getObject(index);
    }
    public WlhlTemplateBaseEntryInfo get(Object key)
    {
        return(WlhlTemplateBaseEntryInfo)getObject(key);
    }
    public void set(int index, WlhlTemplateBaseEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(WlhlTemplateBaseEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(WlhlTemplateBaseEntryInfo item)
    {
        return super.indexOf(item);
    }
}