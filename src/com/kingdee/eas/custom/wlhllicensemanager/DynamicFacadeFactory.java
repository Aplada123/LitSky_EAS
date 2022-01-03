package com.kingdee.eas.custom.wlhllicensemanager;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DynamicFacadeFactory
{
    private DynamicFacadeFactory()
    {
    }
    public static com.kingdee.eas.custom.wlhllicensemanager.IDynamicFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.wlhllicensemanager.IDynamicFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("FAA341FB") ,com.kingdee.eas.custom.wlhllicensemanager.IDynamicFacade.class);
    }
    
    public static com.kingdee.eas.custom.wlhllicensemanager.IDynamicFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.wlhllicensemanager.IDynamicFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("FAA341FB") ,com.kingdee.eas.custom.wlhllicensemanager.IDynamicFacade.class, objectCtx);
    }
    public static com.kingdee.eas.custom.wlhllicensemanager.IDynamicFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.wlhllicensemanager.IDynamicFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("FAA341FB"));
    }
    public static com.kingdee.eas.custom.wlhllicensemanager.IDynamicFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.wlhllicensemanager.IDynamicFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("FAA341FB"));
    }
}