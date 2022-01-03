package com.kingdee.eas.api.nuonuo;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class NuoNuoBaseEntryCollection extends AbstractObjectCollection 
{
    public NuoNuoBaseEntryCollection()
    {
        super(NuoNuoBaseEntryInfo.class);
    }
    public boolean add(NuoNuoBaseEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(NuoNuoBaseEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(NuoNuoBaseEntryInfo item)
    {
        return removeObject(item);
    }
    public NuoNuoBaseEntryInfo get(int index)
    {
        return(NuoNuoBaseEntryInfo)getObject(index);
    }
    public NuoNuoBaseEntryInfo get(Object key)
    {
        return(NuoNuoBaseEntryInfo)getObject(key);
    }
    public void set(int index, NuoNuoBaseEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(NuoNuoBaseEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(NuoNuoBaseEntryInfo item)
    {
        return super.indexOf(item);
    }
}