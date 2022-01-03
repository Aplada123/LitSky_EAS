package com.kingdee.eas.custom.comm;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import java.lang.Object;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.custom.comm.app.*;
import com.kingdee.bos.util.*;

public class RycSQLFacade extends AbstractBizCtrl implements IRycSQLFacade
{
    public RycSQLFacade()
    {
        super();
        registerInterface(IRycSQLFacade.class, this);
    }
    public RycSQLFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IRycSQLFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("EBFD58E3");
    }
    private RycSQLFacadeController getController() throws BOSException
    {
        return (RycSQLFacadeController)getBizController();
    }
    /**
     *执行查询sql-User defined method
     *@param sql sql
     *@return
     */
    public IRowSet excuteQuery(String sql) throws BOSException
    {
        try {
            return getController().excuteQuery(getContext(), sql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *执行查询sql-User defined method
     *@param sql sql
     *@param values values
     *@return
     */
    public IRowSet excuteQuery(String sql, Object[] values) throws BOSException
    {
        try {
            return getController().excuteQuery(getContext(), sql, values);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *excute-User defined method
     *@param sql sql
     */
    public void excute(String sql) throws BOSException
    {
        try {
            getController().excute(getContext(), sql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *excute-User defined method
     *@param sql sql
     *@param values values
     */
    public void excute(String sql, Object[] values) throws BOSException
    {
        try {
            getController().excute(getContext(), sql, values);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}