package com.kingdee.eas.custom.wlhllicensemanager;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class WlhlTemplateBaseFactory
{
    private WlhlTemplateBaseFactory()
    {
    }
    public static com.kingdee.eas.custom.wlhllicensemanager.IWlhlTemplateBase getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.wlhllicensemanager.IWlhlTemplateBase)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("1611BC22") ,com.kingdee.eas.custom.wlhllicensemanager.IWlhlTemplateBase.class);
    }
    
    public static com.kingdee.eas.custom.wlhllicensemanager.IWlhlTemplateBase getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.wlhllicensemanager.IWlhlTemplateBase)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("1611BC22") ,com.kingdee.eas.custom.wlhllicensemanager.IWlhlTemplateBase.class, objectCtx);
    }
    public static com.kingdee.eas.custom.wlhllicensemanager.IWlhlTemplateBase getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.wlhllicensemanager.IWlhlTemplateBase)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("1611BC22"));
    }
    public static com.kingdee.eas.custom.wlhllicensemanager.IWlhlTemplateBase getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.wlhllicensemanager.IWlhlTemplateBase)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("1611BC22"));
    }
}