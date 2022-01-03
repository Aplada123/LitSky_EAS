package com.kingdee.eas.custom.comm.app;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

@SuppressWarnings("serial")
public class RycSQLFacadeControllerBean extends AbstractRycSQLFacadeControllerBean{
	@Override
	protected void _excute(Context ctx, String sql, Object[] values)
	throws BOSException {
		// TODO Auto-generated method stub
		DbUtil.execute(ctx, sql, values);
	}

	@Override
	protected void _excute(Context ctx, String sql) throws BOSException {
		// TODO Auto-generated method stub
		DbUtil.execute(ctx, sql);
	}

	@Override
	protected IRowSet _excuteQuery(Context ctx, String sql, Object[] values)
	throws BOSException {
		// TODO Auto-generated method stub
		return DbUtil.executeQuery(ctx, sql, values);
	}

	@Override
	protected IRowSet _excuteQuery(Context ctx, String sql) throws BOSException {
		// TODO Auto-generated method stub
		return DbUtil.executeQuery(ctx, sql);
	}
}