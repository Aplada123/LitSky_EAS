package com.kingdee.eas.custom.wlhllicensemanager;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class WlhlTemplateBillFactory
{
    private WlhlTemplateBillFactory()
    {
    }
    public static com.kingdee.eas.custom.wlhllicensemanager.IWlhlTemplateBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.wlhllicensemanager.IWlhlTemplateBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("1611D958") ,com.kingdee.eas.custom.wlhllicensemanager.IWlhlTemplateBill.class);
    }
    
    public static com.kingdee.eas.custom.wlhllicensemanager.IWlhlTemplateBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.wlhllicensemanager.IWlhlTemplateBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("1611D958") ,com.kingdee.eas.custom.wlhllicensemanager.IWlhlTemplateBill.class, objectCtx);
    }
    public static com.kingdee.eas.custom.wlhllicensemanager.IWlhlTemplateBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.wlhllicensemanager.IWlhlTemplateBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("1611D958"));
    }
    public static com.kingdee.eas.custom.wlhllicensemanager.IWlhlTemplateBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.wlhllicensemanager.IWlhlTemplateBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("1611D958"));
    }
}