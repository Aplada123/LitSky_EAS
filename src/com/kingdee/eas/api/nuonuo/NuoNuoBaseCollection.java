package com.kingdee.eas.api.nuonuo;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class NuoNuoBaseCollection extends AbstractObjectCollection 
{
    public NuoNuoBaseCollection()
    {
        super(NuoNuoBaseInfo.class);
    }
    public boolean add(NuoNuoBaseInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(NuoNuoBaseCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(NuoNuoBaseInfo item)
    {
        return removeObject(item);
    }
    public NuoNuoBaseInfo get(int index)
    {
        return(NuoNuoBaseInfo)getObject(index);
    }
    public NuoNuoBaseInfo get(Object key)
    {
        return(NuoNuoBaseInfo)getObject(key);
    }
    public void set(int index, NuoNuoBaseInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(NuoNuoBaseInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(NuoNuoBaseInfo item)
    {
        return super.indexOf(item);
    }
}