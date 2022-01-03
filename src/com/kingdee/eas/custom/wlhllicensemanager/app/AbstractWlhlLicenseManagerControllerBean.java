package com.kingdee.eas.custom.wlhllicensemanager.app;

import javax.ejb.*;
import java.rmi.RemoteException;
import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;
import com.kingdee.eas.framework.Result;
import com.kingdee.eas.framework.LineResult;
import com.kingdee.eas.framework.exception.EASMultiException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;

import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.eas.common.EASBizException;



public abstract class AbstractWlhlLicenseManagerControllerBean extends AbstractBizControllerBean implements WlhlLicenseManagerController
{
    protected AbstractWlhlLicenseManagerControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("501DB823");
    }

    public void checkLicense(Context ctx, BOSObjectType bosType) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("a3ca64bf-7b1d-42d4-bf57-4e4db0072707"), new Object[]{ctx, bosType});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _checkLicense(ctx, bosType);
            }
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected void _checkLicense(Context ctx, BOSObjectType bosType) throws BOSException, EASBizException
    {    	
        return;
    }

    public void releaseLicense(Context ctx, BOSObjectType bosType) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("a135edd1-a2ed-4e95-95e2-934fbc9eff2e"), new Object[]{ctx, bosType});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _releaseLicense(ctx, bosType);
            }
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected void _releaseLicense(Context ctx, BOSObjectType bosType) throws BOSException, EASBizException
    {    	
        return;
    }

}