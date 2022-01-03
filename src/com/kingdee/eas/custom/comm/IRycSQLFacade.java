package com.kingdee.eas.custom.comm;

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

public interface IRycSQLFacade extends IBizCtrl
{
    public IRowSet excuteQuery(String sql) throws BOSException;
    public IRowSet excuteQuery(String sql, Object[] values) throws BOSException;
    public void excute(String sql) throws BOSException;
    public void excute(String sql, Object[] values) throws BOSException;
}