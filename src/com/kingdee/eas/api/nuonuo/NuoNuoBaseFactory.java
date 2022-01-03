package com.kingdee.eas.api.nuonuo;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class NuoNuoBaseFactory
{
    private NuoNuoBaseFactory()
    {
    }
    public static com.kingdee.eas.api.nuonuo.INuoNuoBase getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.api.nuonuo.INuoNuoBase)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("ACF12413") ,com.kingdee.eas.api.nuonuo.INuoNuoBase.class);
    }
    
    public static com.kingdee.eas.api.nuonuo.INuoNuoBase getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.api.nuonuo.INuoNuoBase)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("ACF12413") ,com.kingdee.eas.api.nuonuo.INuoNuoBase.class, objectCtx);
    }
    public static com.kingdee.eas.api.nuonuo.INuoNuoBase getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.api.nuonuo.INuoNuoBase)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("ACF12413"));
    }
    public static com.kingdee.eas.api.nuonuo.INuoNuoBase getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.api.nuonuo.INuoNuoBase)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("ACF12413"));
    }
}