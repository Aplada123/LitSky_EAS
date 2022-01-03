package com.kingdee.eas.custom.comm.app;

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

import com.kingdee.jdbc.rowset.IRowSet;
import java.lang.Object;
import java.lang.String;



public abstract class AbstractRycSQLFacadeControllerBean extends AbstractBizControllerBean implements RycSQLFacadeController
{
    protected AbstractRycSQLFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("EBFD58E3");
    }

    public IRowSet excuteQuery(Context ctx, String sql) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("8623401b-e8f6-4bb1-8305-eff9e555427b"), new Object[]{ctx, sql});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            IRowSet retValue = (IRowSet)_excuteQuery(ctx, sql);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (IRowSet)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected IRowSet _excuteQuery(Context ctx, String sql) throws BOSException
    {    	
        return null;
    }

    public IRowSet excuteQuery(Context ctx, String sql, Object[] values) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("45b7cbe9-def5-4739-a1ac-69abfc90e829"), new Object[]{ctx, sql, values});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            IRowSet retValue = (IRowSet)_excuteQuery(ctx, sql, values);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (IRowSet)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected IRowSet _excuteQuery(Context ctx, String sql, Object[] values) throws BOSException
    {    	
        return null;
    }

    public void excute(Context ctx, String sql) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("1ad6c5f5-ef5a-4373-bcde-b99f0bf981a1"), new Object[]{ctx, sql});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _excute(ctx, sql);
            }
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected void _excute(Context ctx, String sql) throws BOSException
    {    	
        return;
    }

    public void excute(Context ctx, String sql, Object[] values) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("138976fd-d0a5-40e9-a227-05c9890d0012"), new Object[]{ctx, sql, values});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _excute(ctx, sql, values);
            }
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected void _excute(Context ctx, String sql, Object[] values) throws BOSException
    {    	
        return;
    }

}