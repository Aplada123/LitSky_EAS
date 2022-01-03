package com.kingdee.eas.custom.wlhllicensemanager;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.Context;
import com.kingdee.eas.custom.wlhllicensemanager.app.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;

public class WlhlLicenseManager extends AbstractBizCtrl implements IWlhlLicenseManager
{
    public WlhlLicenseManager()
    {
        super();
        registerInterface(IWlhlLicenseManager.class, this);
    }
    public WlhlLicenseManager(Context ctx)
    {
        super(ctx);
        registerInterface(IWlhlLicenseManager.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("501DB823");
    }
    private WlhlLicenseManagerController getController() throws BOSException
    {
        return (WlhlLicenseManagerController)getBizController();
    }
    /**
     *checkLicense-User defined method
     *@param bosType bosType
     */
    public void checkLicense(BOSObjectType bosType) throws BOSException, EASBizException
    {
        try {
            getController().checkLicense(getContext(), bosType);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *releaseLicense-User defined method
     *@param bosType bosType
     */
    public void releaseLicense(BOSObjectType bosType) throws BOSException, EASBizException
    {
        try {
            getController().releaseLicense(getContext(), bosType);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}