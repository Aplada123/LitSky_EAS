package com.kingdee.eas.api.nuonuo;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class NuoNuoFacadeFactory
{
    private NuoNuoFacadeFactory()
    {
    }
    public static com.kingdee.eas.api.nuonuo.INuoNuoFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.api.nuonuo.INuoNuoFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3C04855C") ,com.kingdee.eas.api.nuonuo.INuoNuoFacade.class);
    }
    
    public static com.kingdee.eas.api.nuonuo.INuoNuoFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.api.nuonuo.INuoNuoFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3C04855C") ,com.kingdee.eas.api.nuonuo.INuoNuoFacade.class, objectCtx);
    }
    public static com.kingdee.eas.api.nuonuo.INuoNuoFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.api.nuonuo.INuoNuoFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3C04855C"));
    }
    public static com.kingdee.eas.api.nuonuo.INuoNuoFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.api.nuonuo.INuoNuoFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3C04855C"));
    }
}