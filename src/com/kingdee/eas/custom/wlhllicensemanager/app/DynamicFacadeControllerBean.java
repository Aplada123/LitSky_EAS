package com.kingdee.eas.custom.wlhllicensemanager.app;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserCollection;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.custom.wlhllicensemanager.IWlhlBillBase;
import com.kingdee.eas.custom.wlhllicensemanager.IWlhlDataBase;
import com.kingdee.eas.custom.wlhllicensemanager.util.AttachmentUtils;
import com.kingdee.eas.custom.wlhllicensemanager.util.JUtils;
import com.kingdee.eas.custom.wlhllicensemanager.util.WlhlDynamicBillUtils;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.rpts.ctrlreport.bo.KSQLReportBO;
import com.kingdee.eas.rpts.ctrlsqldesign.data.DesignParameter;
import com.kingdee.eas.rpts.ctrlsqldesign.industry.InnerParam;
import com.kingdee.eas.rpts.ctrlsqldesign.model.CtrlDesignDataExecutor;
import com.kingdee.eas.rpts.ctrlsqldesign.model.CtrlDesignQueryModel;
import com.kingdee.eas.util.BASE64Encoder;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class DynamicFacadeControllerBean extends AbstractDynamicFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.custom.wlhllicensemanager.app.DynamicFacadeControllerBean");
    protected String _getDataByID(Context ctx, String jsonStr)throws BOSException, EASBizException
    {
        JSONObject json=new JSONObject();
    	json.put("result", "0");
    	json.put("message", "success");
    	try {
    		String dataStr=new WlhlDynamicBillUtils().getDataByID(ctx, jsonStr);
    		json.put("data", dataStr);
    	}catch(Exception err) {
    		json.put("result", "1");
        	json.put("message", err.getMessage());
    	}
    	return json.toString();
    }
    protected String _uploadDataByBosType(Context ctx, String bosType, String jsonStr)throws BOSException, EASBizException
    {
    	JSONObject json=new JSONObject();
    	json.put("result", "0");
    	json.put("message", "success");
    	try {
    		String id=new WlhlDynamicBillUtils().uploadDataByBosType(ctx, bosType,jsonStr,null).toString();
    		json.put("id", id);
    	}catch(Exception err) {
    		json.put("result", "1");
        	json.put("message", err.getMessage());
    	}
    	return json.toString();
    }
    protected String _downloadBillList(Context ctx, String jsonStr)throws BOSException
    {
    	JSONObject json=new JSONObject();
    	json.put("result", "0");
    	json.put("message", "success");
    	try {
    		JSONObject queryJson=JSONObject.fromObject(jsonStr);
    		String bosType=queryJson.getString("bosType");
    		String queryStr=null;
    		if(queryJson.containsKey("queryStr")) {
    			queryStr="where "+queryJson.getString("queryStr");
    		}
    		String queryInfo=null;
    		if(queryJson.containsKey("queryInfo")) {
    			queryInfo=queryJson.getString("queryInfo");
    		}

    		String[] queryCols=null;
    		if(queryJson.containsKey("queryCols")) {
    			queryCols=new String[queryJson.getJSONArray("queryCols").size()];
    			for(int index=0;index<queryCols.length;index++) {
    				queryCols[index]=queryJson.getJSONArray("queryCols").getString(index);
    			}
    		}
    		int beginRow=0,length=1000;
    		if(queryJson.containsKey("beginRow")) {
    			beginRow=Integer.valueOf(queryJson.getString("beginRow"));
    		}
    		if(queryJson.containsKey("length")) {
    			length=Integer.valueOf(queryJson.getString("length"));
    		}
    		
    		if(bosType.equals("4409E7F0")) {//物料
    			if(queryStr!=null&&(queryStr.equals("where qy_fodder")||queryStr.equals("where qy_drug")||queryStr.equals("where qy_egg"))) {
    				json.put("data", getMaterialFilter(ctx,queryStr, JUtils.getString(queryJson, "batchID")));
    				return json.toString();
    			}
    		}
    		
    		String dataStr=new WlhlDynamicBillUtils().downloadBillList(ctx, bosType, queryInfo, queryStr, queryCols, beginRow, length).toString();
    		json.put("data", dataStr);
    	}catch(Exception err) {
    		json.put("result", "1");
        	json.put("message", err.getMessage());
    	}
    	return json.toString();
    }
    
    @Override
	protected String _getEumInfo(Context ctx, String jsonStr) throws BOSException, EASBizException {
		JSONObject json=new JSONObject();
    	json.put("result", "0");
    	json.put("message", "success");
	    	try {
	    		JSONObject queryJson=JSONObject.fromObject(jsonStr);
	    		String enumPath=queryJson.getString("enumPath");
				String dataStr=new WlhlDynamicBillUtils().getEnumArray(ctx, enumPath).toString();
	    		json.put("data", dataStr);
	    	}catch(Exception err) {
	    		json.put("result", "1");
	        	json.put("message", err.getMessage());
	    	}
    	return json.toString();
	}
    @Override
	protected String _uploadData(Context ctx, String bosType, String jsonStr) throws BOSException, EASBizException {
		JSONObject json=new JSONObject();
    	json.put("result", "0");
    	json.put("message", "success");
    	try {
    		IObjectPK[] pks = new WlhlDynamicBillUtils().uploadDataWithArray(ctx, bosType,jsonStr);
    		String[] ids=new String[pks.length];
    		for(int index=0;index<pks.length;index++) {
    			ids[index]=pks[index].toString();
    		}
    		json.put("id", ids);
    	}catch(Exception err) {
    		logger.error(err);
    		json.put("result", "1");
        	json.put("message", err.getMessage());
    	}
    	return json.toString();
	}
	@Override
	protected String _deleteData(Context ctx, String jsonStr) throws BOSException, EASBizException {
		JSONObject json=new JSONObject();
    	json.put("result", "0");
    	json.put("message", "success");
	    	try {
	    		JSONObject queryJson=JSONObject.fromObject(jsonStr);
	    		String bosType=queryJson.getString("bosType");
	    		JSONArray ja=queryJson.getJSONArray("Ids");	
	    		IObjectPK[] pks=new ObjectUuidPK[ja.size()];
	    		for(int index=0;index<pks.length;index++) {
	    			pks[index]=new ObjectUuidPK(ja.getString(index));
	    		}
	    		ICoreBase is = WlhlDynamicBillUtils.getLocalInstance(ctx, WlhlDynamicBillUtils.getEntityObject(ctx, bosType));
	    	
	    		if(is instanceof IWlhlBillBase) {
	    			((IWlhlBillBase)is).dynamicDelete(pks);
	    		}else if(is instanceof IWlhlDataBase) {
	    			((IWlhlDataBase)is).dynamicDelete(pks);
	    		}else {
	    			is.delete(pks);
	    		}
	    		json.put("message", "delete success");
	    	}catch(Exception err) {
	    		json.put("result", "1");
	        	json.put("message", err.getMessage());
	    	}
    	return json.toString();
	}
	
	@Override
	protected String _exeFunciton(Context ctx, String jsonStr) throws BOSException, EASBizException {
		JSONObject json=new JSONObject();
    	json.put("result", "0");
    	json.put("message", "success");
	    	try {
	    		JSONObject queryJson=JSONObject.fromObject(jsonStr);
	    		String bosType=queryJson.getString("bosType");
	    		String functionName=queryJson.getString("functionName");
	    		ICoreBase is = WlhlDynamicBillUtils.getLocalInstance(ctx, WlhlDynamicBillUtils.getEntityObject(ctx, bosType));
	    		Object obj=null;
	    		if(is instanceof IWlhlBillBase) {
	    			obj=((IWlhlBillBase)is).dynamicExe(functionName, queryJson.getJSONObject("params"));
	    		}else if(is instanceof IWlhlDataBase) {
	    			obj=((IWlhlDataBase)is).dynamicExe(functionName, queryJson.getJSONObject("params"));
	    		}else {
	    			exeFuncitonOther(ctx, jsonStr);
	    		}
	    		json.put("message", String.valueOf(obj));
	    	}catch(Exception err) {
	    		json.put("result", "1");
	        	json.put("message", err.getMessage());
	    	}
    	return json.toString();
	}
	
	protected String exeFuncitonOther(Context ctx, String jsonStr) throws BOSException, EASBizException {
		JSONObject json=new JSONObject();
    	json.put("result", "0");
    	json.put("message", "success");
	    	try {
	    		JSONObject queryJson=JSONObject.fromObject(jsonStr);
	    		String id=queryJson.getString("id");
	    		if(!BOSUuid.isValid(id, true)) {
	    			throw new Exception("id无效");
	    		}
	    		String bosType=BOSUuid.read(id).getType().toString();
	    		String functionName=queryJson.getString("functionName");
	    		Object[] paramValues=null;
	    		if(queryJson.containsKey("paramValues")) {
		    		JSONArray paramArray = queryJson.getJSONArray("paramValues");
		    		paramValues=new Object[paramArray.size()];
		    		for(int index=0;index<paramArray.size();index++) {
		    			paramValues[index]=paramArray.getString(index);
		    		}
	    		}
				Class<?>[] paramsTypes=null;
				if(queryJson.containsKey("paramsTypes")) {
					JSONArray paramArray = queryJson.getJSONArray("paramValues");
					paramsTypes=new Class<?>[paramArray.size()];
		    		for(int index=0;index<paramArray.size();index++) {
		    			paramsTypes[index]=Class.forName(paramArray.getString(index));
		    		}
				}
				Object result=new WlhlDynamicBillUtils().exeFunciton(ctx, bosType, id, functionName, paramsTypes,paramValues);
	    		json.put("exeMsg", result!=null?result.toString():"success");
	    	}catch(Exception err) {
	    		json.put("result", "1");
	        	json.put("message", err.getMessage());
	    	}
    	return json.toString();
	}
	
	/**
	 * 删除附件
	 */
	@Override
	protected String _deleteAttachment(Context ctx, String jsonStr) throws BOSException, EASBizException {
		JSONObject resultJson = new JSONObject();
		resultJson.put("result", "0");
		resultJson.put("message", "success");
    	
		JSONObject jsonParam=JSONObject.fromObject(jsonStr);

		String billId = jsonParam.getString("billId");
		String id = jsonParam.getString("id");

		Map map=null;
		try {
			AttachmentUtils is = new AttachmentUtils(ctx);
			map = is.deleteAttachment(billId, id);
		} catch (Exception e) {
			resultJson.put("result", "1");
			resultJson.put("message", e.getMessage());
		} 
		if(map!=null)
			resultJson.put("data", map);
		return resultJson.toString();
	}
	/**
	 * 获取附件数据
	 */
	@Override
	protected String _getAttachmentData(Context ctx, String jsonStr) throws BOSException, EASBizException {
		JSONObject resultJson = new JSONObject();
		resultJson.put("result", "0");
		resultJson.put("message", "success");
		
		JSONObject jsonParam=JSONObject.fromObject(jsonStr);
		//参数
		String billId = null,id=null;
		if(jsonParam.containsKey("billId")) {
			billId=jsonParam.getString("billId");
		}
		
		if(jsonParam.containsKey("id")) {
			id=jsonParam.getString("id");
		}
		
		if(billId==null&&id==null) {
			resultJson.put("result", "1");
			resultJson.put("message","id和billId不能同时为空！");
			return resultJson.toString();
		}

		StringBuffer sql=new StringBuffer();
		sql.append(" select fid,FAttachmentid from T_BAS_BoAttchAsso")
		.append(" where 1=1 ");
		if(billId!=null) {
			sql.append(" and FBoId='").append(billId).append("'");
		}
		if(id!=null) {
			sql.append(" and FAttachmentid='").append(id).append("'");
		}

		String type="";//BILLATTACH
		JSONArray ja=new JSONArray();
		try {
			IRowSet rs = DbUtil.executeQuery(ctx, sql.toString());
//			WorkflowServerBSFInstance is = WorkflowServerBSFInstance.getInstance(ctx);
			AttachmentUtils is = new AttachmentUtils(ctx);
			BASE64Encoder encoder = new BASE64Encoder();
			while(rs.next()) {
				ja.add(encoder.encode(is.getAttachmentAsBytes(rs.getString("FAttachmentid"), type)));
			}
			resultJson.put("fileData", ja);
		} catch (Exception e) {
			resultJson.put("result", "1");
			resultJson.put("message", e.getMessage());
		} 
		return resultJson.toString();
	}
	@Override
	protected String _getAttachmentList(Context ctx, String jsonStr) throws BOSException, EASBizException {
		JSONObject resultJson = new JSONObject();
		resultJson.put("result", "0");
		resultJson.put("message", "success");
		
		return resultJson.toString();
	}
	/**
	 * 上传附件
	 */
	@Override
	protected String _uploadAttachment(Context ctx, String jsonStr) throws BOSException, EASBizException {
		JSONObject resultJson = new JSONObject();
		resultJson.put("result", "0");
		resultJson.put("message", "success");
		
		JSONObject jsonParam=JSONObject.fromObject(jsonStr);
		String userNumber = null;
		try{
			if(jsonParam.containsKey("userNum") && StringUtils.isNotBlank(jsonParam.getString("userNum"))){
				userNumber = jsonParam.getString("userNum");
				UserCollection ucoll = UserFactory.getLocalInstance(ctx).getUserCollection("where number='" + userNumber + "'");
				ContextUtil.setCurrentUserInfo(ctx, ucoll.get(0));
				ctx.setCaller(new ObjectUuidPK(ucoll.get(0).getId().toString()));
			}
		}catch (Exception e) {
			logger.error(e);
			resultJson.put("result", "1");
			resultJson.put("message", e.getMessage());
		}
		
		String boid = jsonParam.getString("billId");//URLDecoder
		Map m= new HashMap();//附件文件说明
		m.put("type", "getPicthur");
		m.put("fileName", jsonParam.getString("fileName"));
		m.put("fileExt", jsonParam.getString("fileExt"));
		String fileData = jsonParam.getString("fileData");//文件加密字符串 BASE64加密
		Map map = null;
		try {
			AttachmentUtils is = new AttachmentUtils(ctx);
			map = is.addAttachment(boid, m, fileData);
		} catch (Exception e) {
			resultJson.put("result", "1");
			resultJson.put("message", e.getMessage());
			logger.error(e);
		} 
		return resultJson.toString();
	}
	@Override
	protected String _getRptData(Context ctx, String jsonStr) throws BOSException, EASBizException {
		JSONObject resultJson = new JSONObject();
		resultJson.put("result", "0");
		resultJson.put("message", "success");
		
		try {
			CtrlDesignQueryModel model=new CtrlDesignQueryModel();
			
			JSONObject json=JSONObject.fromObject(jsonStr);
			String id=json.getString("id");
			model=KSQLReportBO.initModel(ctx,null,null,null,id,null,null);
			
			//初始化查询参数
//			CtrlDesignUtil.toFilterSolutionXml((ArrayList) model.getCommonQuery().getParameters());
//			String parameterXmlString="";
//			HashMap mapDesignParameter= RunReportParam.ChangWhereValueToMapParams(parameterXmlString);
			HashMap mapDesignParameter=new HashMap();
			InnerParam.putClientDefalutListParamsMap(mapDesignParameter);
			//填充查询条件
			json=json.getJSONObject("params");
			if(json!=null&&!json.isNullObject()&&!json.isEmpty()) {
				for(int index=0;index< model.getCommonQuery().getParameters().size();index++) {
					DesignParameter d=(DesignParameter) model.getCommonQuery().getParameters().get(index);
					if(json.containsKey(d.getName())) {
						d.setCurentValue(json.getString(d.getName()));
						d.setCurentValueAlias(d.getAlias());
						mapDesignParameter.put(d.getName(), d);
					}
				}
				model.getCommonQuery().getProperties();
			}
			
			HashMap mapResult = CtrlDesignDataExecutor.execute(ctx, model,mapDesignParameter, null, 0, -1, true, null);
			IRowSet rs = (IRowSet)mapResult.get("6xx8xxRowset");
			JSONArray ja=new JSONArray();
			JSONObject jo;
			while(rs.next()) {
				jo=new JSONObject();
				for(int j=1;j<=rs.getMetaData().getColumnCount();j++) {
					jo.put(rs.getMetaData().getColumnName(j), rs.getString(j));
				}
				ja.add(jo);
			}
//			System.out.println(rs.size());
			resultJson.put("data", ja);
		} catch (Exception e) {
			resultJson.put("result", "1");
			resultJson.put("message", e.getMessage());
		}
//		System.out.println("报表结果"+resultJson.toString());
		return resultJson.toString();
	}
    
    private String getMaterialFilter(Context ctx,String type,String batchID) throws BOSException {
		StringBuffer sql=new StringBuffer();
    	if(type.equals("where qy_fodder")) {
    		sql.append(" select tm.fid materialID,tm.fnumber materialNum,tm.fname_l2 materialName,tm.fmodel model,")
    		.append(" tu.fid unitID,tu.fname_l2 unitName,isnull(tm.cfunitQty,0) unitQty from (")
			.append(" select distinct tentry.CFHenMaterialID FMaterialID from T_FM_BreedStandardEntry tentry")
			.append(" inner join T_FM_BreedStandard tmain on tmain.fid=tentry.fparentid")
			.append(" inner join T_FM_StockingBatch tbatch on tbatch.FBreedDataID=tmain.FBreedDataID")
			.append(" where tbatch.fid='").append(batchID).append("'")
			.append(" union all")
			.append(" select distinct tentry.CFCockMaterialID FMaterialID from T_FM_BreedStandardEntry tentry")
			.append(" inner join T_FM_BreedStandard tmain on tmain.fid=tentry.fparentid")
			.append(" inner join T_FM_StockingBatch tbatch on tbatch.FBreedDataID=tmain.FBreedDataID")
			.append(" where tbatch.fid='").append(batchID).append("'")
			.append(" ) ttemp ")
			.append(" inner join t_bd_material tm on tm.fid=ttemp.fmaterialid")
			.append(" inner join t_bd_measureunit tu on tu.fid=tm.FBaseUnit")
			;
    	}else if(type.equals("where qy_drug")) {
    		sql.append(" select tm.fid materialID,tm.fnumber materialNum,tm.fname_l2 materialName,tm.fmodel model,")
    		.append(" tu.fid unitID,tu.fname_l2 unitName,isnull(tm.cfunitQty,0) ")
			.append(" from T_FM_BreedStandardDrugEntry tentry")
			.append(" inner join T_FM_BreedStandard tmain on tmain.fid=tentry.fparentid")
			.append(" inner join T_FM_StockingBatch tbatch on tbatch.FBreedDataID=tmain.FBreedDataID")
			.append(" inner join t_bd_material tm on tm.fid=tentry.fmaterialid")
			.append(" inner join t_bd_measureunit tu on tu.fid=tm.FBaseUnit")
			.append(" where tbatch.fid='").append(batchID).append("'");
    	}
    	else if(type.equals("where qy_egg")) {
    		sql.append(" select tm.fid materialID,tm.fnumber materialNum,tm.fname_l2 materialName,tm.fmodel model,")
    		.append(" tu.fid unitID,tu.fname_l2 unitName,isnull(tm.cfunitQty,0) ")
			.append(" from T_FM_BreedDataEntry tentry")
			.append(" inner join T_FM_BreedData tmain on tmain.fid=tentry.fparentid")
			.append(" inner join T_FM_StockingBatch tbatch on tbatch.FBreedDataID=tmain.fid")
			.append(" inner join t_bd_material tm on tm.fid=tentry.fmaterialid")
			.append(" inner join t_bd_measureunit tu on tu.fid=tm.FBaseUnit")
			.append(" where tbatch.fid='").append(batchID).append("'");
    	}
    	IRowSet rs = DbUtil.executeQuery(ctx, sql.toString());
    	JSONArray ja=new JSONArray();
    	try {
    		JSONObject jo;
			while(rs.next()) {
				jo=new JSONObject();
				for(int index=1;index<=rs.getRowSetMetaData().getColumnCount();index++) {
					jo.put(rs.getRowSetMetaData().getColumnName(index), rs.getString(index));
				}
				ja.add(jo);
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
    	return ja.toString();
    }
}