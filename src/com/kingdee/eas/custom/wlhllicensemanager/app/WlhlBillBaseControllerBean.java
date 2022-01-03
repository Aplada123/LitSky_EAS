package com.kingdee.eas.custom.wlhllicensemanager.app;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.custom.wlhllicensemanager.WlhlBillBaseInfo;
import com.kingdee.eas.custom.wlhllicensemanager.util.WlhlDynamicConstant;
import com.kingdee.eas.scm.common.BillBaseStatusEnum;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;

public abstract class WlhlBillBaseControllerBean extends AbstractWlhlBillBaseControllerBean
{
	private static Logger logger =
		Logger.getLogger("com.kingdee.eas.custom.wlhllicensemanager.app.WlhlBillBaseControllerBean");

	@Override
	protected void _audit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		WlhlBillBaseInfo info = (WlhlBillBaseInfo) model;
		if(!info.getBillStatus().equals(BillBaseStatusEnum.SUBMITED)){
			throw new EASBizException(new NumericExceptionSubItem("001",info.getNumber()+" 提交状态单据才允许审核！"));
		}
		info.setBillStatus(BillBaseStatusEnum.AUDITED);
		info.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
		info.setAuditTime(new java.util.Date());
		SelectorItemCollection selector=new SelectorItemCollection();
		selector.add("billStatus");
		selector.add("auditor");
		selector.add("auditTime");
		super._updatePartial(ctx, info, selector);
	}

	@Override
	protected IObjectPK _dynamicSave(Context ctx, IObjectValue model)
	throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		dealDataBeforeSave(ctx,model);
		IObjectPK pk=//_save(ctx, model);
			_submit(ctx, model);
		dealDataAfterSave(ctx,model);
		return pk;
	}
	protected void dealDataAfterSave(Context ctx,IObjectValue model) throws BOSException, EASBizException {

	}

	protected void dealDataBeforeSave(Context ctx,IObjectValue model) throws BOSException, EASBizException {
	}

	@Override
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		WlhlBillBaseInfo info=(WlhlBillBaseInfo) getValue(ctx, pk);
		if (info.getBillStatus().equals(BillBaseStatusEnum.AUDITED)) {
			throw new EASBizException(new NumericExceptionSubItem("001",info.getNumber()+" 单据已经审核，禁止删除！"));
		}
		if(checkHasUsed(ctx, pk)) {
			throw new EASBizException(new NumericExceptionSubItem("001",info.getNumber()+" 单据已经引用，禁止删除！"));
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
		WlhlBillBaseInfo info=(WlhlBillBaseInfo) model;
		if(info.getBillStatus()==null||info.getBillStatus().getValue()==-1||info.getBillStatus().equals(BillBaseStatusEnum.ADD)) {
			info.setBillStatus(BillBaseStatusEnum.TEMPORARILYSAVED);
		}
		return super._save(ctx, info);
	}

	@Override
	protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		WlhlBillBaseInfo info=(WlhlBillBaseInfo) model;
		if(info.getBillStatus()==null||info.getBillStatus().getValue()==-1||info.getBillStatus().equals(BillBaseStatusEnum.ADD)||info.getBillStatus().equals(BillBaseStatusEnum.TEMPORARILYSAVED)) {
			info.setBillStatus(BillBaseStatusEnum.SUBMITED);
		}
		return super._submit(ctx, info);
	}

	@Override
	protected void _unAudit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		WlhlBillBaseInfo info=(WlhlBillBaseInfo) model;
		if (!info.getBillStatus().equals(BillBaseStatusEnum.AUDITED)) {
			throw new EASBizException(new NumericExceptionSubItem("001",info.getNumber()+" 单据尚未审核！"));
		}
		if(checkRelationsExist(ctx,info.getString("id"),null,null)) {
			throw new EASBizException(new NumericExceptionSubItem("001",info.getNumber()+" 已存在下游单据，禁止反审核！"));
		}
		info.setBillStatus(BillBaseStatusEnum.TEMPORARILYSAVED);
		info.setAuditor(null);
		info.setAuditTime(null);
		SelectorItemCollection selector=new SelectorItemCollection();
		selector.add("billStatus");
		selector.add("auditor");
		selector.add("auditTime");
		super._updatePartial(ctx, info, selector);
	}

	/**
	 * 添加关联关系 
	 * @param ctx 
	 * @param srcBostype 
	 * @param srcObjectID
	 * @param destBostype
	 * @param destObjectID
	 * @throws BOSException 
	 */
	public boolean  checkRelationsExist(Context ctx,String srcObjectID,String destBOSType,String destObjectID) throws BOSException{
		StringBuilder sql = new StringBuilder("select 1 from t_bot_relation where 1=1");
		sql.append(" and FSrcObjectID ='").append(srcObjectID).append("'");
		if(StringUtils.isNotEmpty(destBOSType)) {
			sql.append(" and FDestEntityID='").append(destBOSType).append("'");
		}
		if(StringUtils.isNotEmpty(destObjectID)) {
			sql.append(" and FDestObjectID='").append(destBOSType).append("'");
		}
		IRowSet rs = DbUtil.executeQuery(ctx, sql.toString());
		if(rs.size()>0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	protected IObjectPK[] _dynamicSaveBatch(Context ctx, IObjectCollection cols) throws BOSException, EASBizException {
		for(int index=0;index<cols.size();index++) {
			dealDataBeforeSave(ctx, cols.getObject(index));
		}
		IObjectPK[] pks = _saveBatchData(ctx,cols);
		for(int index=0;index<cols.size();index++) {
			dealDataAfterSave(ctx, cols.getObject(index));
		}
		return pks;
	}
	@Override
	protected void _dynamicDelete(Context ctx,IObjectPK[] pk) throws BOSException, EASBizException  {
		dealDataBeforeDelete(ctx, pk);
		_delete(ctx, pk);
		dealDataAfterDelete(ctx, pk);
	}
	protected void dealDataAfterDelete(Context ctx,IObjectPK[] pks) throws BOSException, EASBizException {

	}

	protected void dealDataBeforeDelete(Context ctx,IObjectPK[] pks) throws BOSException, EASBizException {
	}
	
	@Override
	protected Object _dynamicExe(Context ctx,String exeType, Object obj) throws BOSException, EASBizException {
		Object result="success";
		String permissionItemNum=getPermissionItemNum(exeType);
		if(StringUtils.isNotBlank(permissionItemNum)) {
			//权限验证
			IObjectPK userPK=new ObjectUuidPK(ContextUtil.getCurrentUserInfo(ctx).getId());
			IObjectPK orgPK=new ObjectUuidPK(ContextUtil.getCurrentCtrlUnit(ctx).getId());
			PermissionFactory.getLocalInstance(ctx).checkFunctionPermission(userPK, orgPK, permissionItemNum);
		}
		JSONObject json=JSONObject.parseObject(obj.toString());
		if(exeType.equals(WlhlDynamicConstant.OP_DEL)) {
			dynamicDelete(ctx, (IObjectPK[]) obj);
		}else if(exeType.equals(WlhlDynamicConstant.OP_AUDIT)) {
			_audit(ctx,getWlhlBillBaseInfo(ctx, new ObjectUuidPK(json.getString("id"))));
		}
		
		return result;
	}
	/**
	 * 获取权限项名称
	 * @return
	 */
	protected String getPermissionItemNum(String exeType) {
		return null;
	}
}