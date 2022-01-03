package com.kingdee.eas.custom.wlhllicensemanager;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class WlhlTemplateBillEntryFactory
{
    private WlhlTemplateBillEntryFactory()
    {
    }
    public static com.kingdee.eas.custom.wlhllicensemanager.IWlhlTemplateBillEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.wlhllicensemanager.IWlhlTemplateBillEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F25855FA") ,com.kingdee.eas.custom.wlhllicensemanager.IWlhlTemplateBillEntry.class);
    }
    
    public static com.kingdee.eas.custom.wlhllicensemanager.IWlhlTemplateBillEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.wlhllicensemanager.IWlhlTemplateBillEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F25855FA") ,com.kingdee.eas.custom.wlhllicensemanager.IWlhlTemplateBillEntry.class, objectCtx);
    }
    public static com.kingdee.eas.custom.wlhllicensemanager.IWlhlTemplateBillEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.wlhllicensemanager.IWlhlTemplateBillEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F25855FA"));
    }
    public static com.kingdee.eas.custom.wlhllicensemanager.IWlhlTemplateBillEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.wlhllicensemanager.IWlhlTemplateBillEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F25855FA"));
    }
}