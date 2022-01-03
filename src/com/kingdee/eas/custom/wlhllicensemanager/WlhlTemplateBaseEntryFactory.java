package com.kingdee.eas.custom.wlhllicensemanager;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class WlhlTemplateBaseEntryFactory
{
    private WlhlTemplateBaseEntryFactory()
    {
    }
    public static com.kingdee.eas.custom.wlhllicensemanager.IWlhlTemplateBaseEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.wlhllicensemanager.IWlhlTemplateBaseEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("19A8A170") ,com.kingdee.eas.custom.wlhllicensemanager.IWlhlTemplateBaseEntry.class);
    }
    
    public static com.kingdee.eas.custom.wlhllicensemanager.IWlhlTemplateBaseEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.wlhllicensemanager.IWlhlTemplateBaseEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("19A8A170") ,com.kingdee.eas.custom.wlhllicensemanager.IWlhlTemplateBaseEntry.class, objectCtx);
    }
    public static com.kingdee.eas.custom.wlhllicensemanager.IWlhlTemplateBaseEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.wlhllicensemanager.IWlhlTemplateBaseEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("19A8A170"));
    }
    public static com.kingdee.eas.custom.wlhllicensemanager.IWlhlTemplateBaseEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.wlhllicensemanager.IWlhlTemplateBaseEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("19A8A170"));
    }
}