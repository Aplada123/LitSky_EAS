package com.kingdee.eas.custom.comm;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RycSQLFacadeFactory
{
    private RycSQLFacadeFactory()
    {
    }
    public static com.kingdee.eas.custom.comm.IRycSQLFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.comm.IRycSQLFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("EBFD58E3") ,com.kingdee.eas.custom.comm.IRycSQLFacade.class);
    }
    
    public static com.kingdee.eas.custom.comm.IRycSQLFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.comm.IRycSQLFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("EBFD58E3") ,com.kingdee.eas.custom.comm.IRycSQLFacade.class, objectCtx);
    }
    public static com.kingdee.eas.custom.comm.IRycSQLFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.comm.IRycSQLFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("EBFD58E3"));
    }
    public static com.kingdee.eas.custom.comm.IRycSQLFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.comm.IRycSQLFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("EBFD58E3"));
    }
}