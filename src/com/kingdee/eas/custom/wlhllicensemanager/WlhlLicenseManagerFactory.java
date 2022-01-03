package com.kingdee.eas.custom.wlhllicensemanager;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class WlhlLicenseManagerFactory
{
    private WlhlLicenseManagerFactory()
    {
    }
    public static com.kingdee.eas.custom.wlhllicensemanager.IWlhlLicenseManager getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.wlhllicensemanager.IWlhlLicenseManager)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("501DB823") ,com.kingdee.eas.custom.wlhllicensemanager.IWlhlLicenseManager.class);
    }
    
    public static com.kingdee.eas.custom.wlhllicensemanager.IWlhlLicenseManager getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.wlhllicensemanager.IWlhlLicenseManager)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("501DB823") ,com.kingdee.eas.custom.wlhllicensemanager.IWlhlLicenseManager.class, objectCtx);
    }
    public static com.kingdee.eas.custom.wlhllicensemanager.IWlhlLicenseManager getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.wlhllicensemanager.IWlhlLicenseManager)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("501DB823"));
    }
    public static com.kingdee.eas.custom.wlhllicensemanager.IWlhlLicenseManager getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.wlhllicensemanager.IWlhlLicenseManager)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("501DB823"));
    }
}