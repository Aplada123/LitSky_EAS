package com.kingdee.eas.custom.comm.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import java.lang.Object;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface RycSQLFacadeController extends BizController
{
    public IRowSet excuteQuery(Context ctx, String sql) throws BOSException, RemoteException;
    public IRowSet excuteQuery(Context ctx, String sql, Object[] values) throws BOSException, RemoteException;
    public void excute(Context ctx, String sql) throws BOSException, RemoteException;
    public void excute(Context ctx, String sql, Object[] values) throws BOSException, RemoteException;
}