package com.kingdee.eas.custom.wlhllicensemanager.app;

import java.util.Date;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.custom.wlhllicensemanager.UsedStatusEnum;
import com.kingdee.eas.custom.wlhllicensemanager.WlhlDataBaseInfo;
import com.kingdee.eas.custom.wlhllicensemanager.util.WlhlDynamicConstant;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.NumericExceptionSubItem;

public abstract class WlhlDataBaseControllerBean extends AbstractWlhlDataBaseControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.custom.wlhllicensemanager.app.WlhlDataBaseControllerBean");
    /**
     * 核准
     */
	@Override
	protected void _approve(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		WlhlDataBaseInfo info=(WlhlDataBaseInfo) model;
		if(info.getBaseStatus().equals(UsedStatusEnum.APPROVED)||info.getBaseStatus().equals(UsedStatusEnum.ENABLED)) {
			throw new EASBizException(new NumericExceptionSubItem("","资料已经核准或启用！"));
		}
		info.setBaseStatus(UsedStatusEnum.APPROVED);
		info.setApprover(ContextUtil.getCurrentUserInfo(ctx));
		info.setApproveTime(new Date());
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("baseStatus");
		selector.add("approver");
		selector.add("approveTime");
		super._updatePartial(ctx, info, selector);
	}
	/**
	 * 反核准
	 */
	@Override
	protected void _unApprove(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		WlhlDataBaseInfo info=(WlhlDataBaseInfo) model;
		if(info.getBaseStatus().equals(UsedStatusEnum.ENABLED)) {
			throw new EASBizException(new NumericExceptionSubItem("","资料已经启用！"));
		}
		if(!info.getBaseStatus().equals(UsedStatusEnum.APPROVED)) {
			throw new EASBizException(new NumericExceptionSubItem("","资料尚未核准！"));
		}
		info.setBaseStatus(UsedStatusEnum.UNAPPROVE);
		info.setApprover(null);
		info.setApproveTime(null);
		SelectorItemCollection selector=new SelectorItemCollection();
		selector.add("baseStatus");
		selector.add("approver");
		selector.add("approveTime");
		super._updatePartial(ctx, info, selector);
	}
	@Override
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		WlhlDataBaseInfo info=(WlhlDataBaseInfo) getValue(ctx, pk);
		if(info.getBaseStatus().equals(UsedStatusEnum.APPROVED)||info.getBaseStatus().equals(UsedStatusEnum.ENABLED)) {
			throw new EASBizException(new NumericExceptionSubItem("","资料已经核准或启用，禁止删除！"));
		}
		if(checkHasUsed(ctx, pk)) {
			throw new EASBizException(new NumericExceptionSubItem("001",info.getNumber()+" 资料已经引用，禁止删除！"));
		}
		super._delete(ctx, pk);
	}
	
	/**
	 * 检查是否被引用
	 * @param ctx
	 * @param pk
	 * @return
	 */
	protected boolean checkHasUsed(Context ctx,IObjectPK pk) throws BOSException, EASBizException {
		return false;
	}
	
	@Override
	protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		WlhlDataBaseInfo info=(WlhlDataBaseInfo) model;
		if(info.getBaseStatus().equals(UsedStatusEnum.APPROVED)||info.getBaseStatus().equals(UsedStatusEnum.ENABLED)) {
			throw new EASBizException(new NumericExceptionSubItem("","资料已经核准或启用！"));
		}
		info.setBaseStatus(UsedStatusEnum.UNAPPROVE);
		return super._save(ctx, info);
	}
	@Override
	protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		WlhlDataBaseInfo info=(WlhlDataBaseInfo) model;
		if(info.getBaseStatus().equals(UsedStatusEnum.APPROVED)||info.getBaseStatus().equals(UsedStatusEnum.ENABLED)) {
			throw new EASBizException(new NumericExceptionSubItem("","资料已经核准或启用！"));
		}
		info.setBaseStatus(UsedStatusEnum.UNAPPROVE);
		return super._submit(ctx, info);
	}
	
	
	@Override
	protected void _dynamicDelete(Context ctx, IObjectPK[] pks)
			throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected Object _dynamicExe(Context ctx, String functionName,
			JSONObject json) throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected IObjectPK[] _dynamicSaveBatch(Context ctx,
			AbstractObjectCollection coll) throws BOSException {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 获取权限项名称
	 * @return
	 */
	protected String getPermissionItemNum(String exeType) {
		return null;
	}
    
}