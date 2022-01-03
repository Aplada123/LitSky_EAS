package com.kingdee.eas.api.nuonuo;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class NuoNuoBaseEntryFactory
{
    private NuoNuoBaseEntryFactory()
    {
    }
    public static com.kingdee.eas.api.nuonuo.INuoNuoBaseEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.api.nuonuo.INuoNuoBaseEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5491881F") ,com.kingdee.eas.api.nuonuo.INuoNuoBaseEntry.class);
    }
    
    public static com.kingdee.eas.api.nuonuo.INuoNuoBaseEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.api.nuonuo.INuoNuoBaseEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5491881F") ,com.kingdee.eas.api.nuonuo.INuoNuoBaseEntry.class, objectCtx);
    }
    public static com.kingdee.eas.api.nuonuo.INuoNuoBaseEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.api.nuonuo.INuoNuoBaseEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5491881F"));
    }
    public static com.kingdee.eas.api.nuonuo.INuoNuoBaseEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.api.nuonuo.INuoNuoBaseEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5491881F"));
    }
}