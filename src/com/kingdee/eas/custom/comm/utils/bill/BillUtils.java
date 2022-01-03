package com.kingdee.eas.custom.comm.utils.bill;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecutorFactory;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.bos.framework.IDynamicObject;
import com.kingdee.bos.metadata.IMetaDataLoader;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.DataType;
import com.kingdee.bos.metadata.entity.EntityObjectInfo;
import com.kingdee.bos.metadata.entity.LinkPropertyInfo;
import com.kingdee.bos.metadata.entity.OwnPropertyInfo;
import com.kingdee.bos.metadata.entity.PropertyInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.custom.wlhllicensemanager.util.JUtils;
import com.kingdee.eas.custom.wlhllicensemanager.util.WlhlBotpCommUtils;
import com.kingdee.eas.custom.wlhllicensemanager.util.WlhlDynamicBillUtils;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.UuidException;
import com.kingdee.util.enums.Enum;

public class BillUtils {
	public static final String ParamType_ID = "id";
	public static final String ParamType_BosType = "bosType";

	/**
	 * 服务端根据bosType获取Instance
	 * @param ctx
	 * @param bosType
	 * @return
	 * @throws BOSException
	 * @author HeMei XinXiBu
	 * @date 2020-7-30 上午09:45:11
	 * <p>Copyright: Copyright (c) 2020HeMeiJiTuan</p>
	 */
	public static ICoreBase getInstance(Context ctx, String bosType) throws BOSException{
		EntityObjectInfo eo = getEntityObject(ctx, bosType);
		return getInstance(ctx, eo);
	}


	/**
	 * 服务端根据bosType获取实体对象
	 * @param ctx
	 * @param bosType
	 * @return
	 * @author  whoops Ryc
	 * @date 2020-7-30 上午09:43:56
	 * <p>Copyright: Copyright (c) 2020HeMeiJiTuan</p>
	 */
	public static EntityObjectInfo getEntityObject(Context ctx,String bosType) {
		IMetaDataLoader loader = ctx == null ? MetaDataLoaderFactory.getRemoteMetaDataLoader() : MetaDataLoaderFactory.getLocalMetaDataLoader(ctx);
		EntityObjectInfo eo = loader.getEntity(BOSObjectType.create(bosType));
		return eo;
	}
	/**
	 * 根据ID获取单据的Info
	 * @param ctx
	 * @param id
	 * @param slor
	 * @return
	 * @author  whoops Ryc
	 * @date 2020-12-29 下午01:59:00
	 * <p>Copyright: Copyright (c) 2020HeMei Group</p>
	 */
	public static IObjectValue getInfoByID(Context ctx, String id, SelectorItemCollection slor){
		try {
			if(slor == null){
				slor = new SelectorItemCollection();
				slor.add("*");
			}
			ICoreBase coreBase = getInstance(ctx, BOSUuid.read(id).getType().toString());
			return coreBase.getValue(new ObjectUuidPK(id), slor);
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UuidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 从实体配置的EditUI里获取getSelectors()方法的返回值
	 * @param ctx
	 * @param paramStr  id or bosType
	 * @param paramType  ParamType_ID or ParamType_BosType
	 * @return           String[]
	 * @author  whoops Ryc
	 * @date 2021-4-20 上午09:16:39
	 * <p>Copyright: Copyright (c) 2021HeMei Group</p>
	 */
	public static String[] getSelectorItemCollectionStr(String paramStr, String paramType){
		SelectorItemCollection collection = getSelectorItemCollection(paramStr, paramType);
		if(collection != null){
			return collection.toString().split("'");
		}
		return null;
	} 
	/**
	 * 从实体配置的EditUI里获取getSelectors()方法的返回值
	 * @param paramStr    id/bosType
	 * @param paramType   id type/ bosType
	 * @return            SelectorItemCollection
	 * @author  whoops Ryc
	 * @date 2021-4-18 上午10:15:01
	 * <p>Copyright: Copyright (c) 2021HeMei Group</p>
	 */

	public static SelectorItemCollection getSelectorItemCollection(String paramStr, String paramType){
		String bosType = null;
		if(paramType.endsWith(ParamType_ID)){
			bosType = String.valueOf(BOSUuid.read(paramStr).getType());
		}else{
			bosType = paramStr;
		}
		EntityObjectInfo entityObjectInfo = MetaDataLoaderFactory.getRemoteMetaDataLoader().getEntity(BOSObjectType.create(bosType));
		String extendedProperty = entityObjectInfo.getExtendedProperty("editUI");
		if(StringUtils.isNotEmpty(extendedProperty)){
			try {
				Class<?> className = Class.forName(extendedProperty);
				Class<?> superClassName = className.getSuperclass();
				Object instance = superClassName.newInstance();// 获取抽象类
				Method declaredMethod = superClassName.getDeclaredMethod("getSelectors");
				SelectorItemCollection slor = (SelectorItemCollection) declaredMethod.invoke(instance);
				return slor;
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}


	/**
	 * 获取单据列表界面
	 * @param ctx
	 * @param jsonStr
	 * @return
	 * @throws BOSException
	 */
	public static String downloadBillList(Context ctx, String jsonStr) throws BOSException {
		JSONObject resultJson = new JSONObject();
		resultJson.put("result", true);

		JSONObject jo = JSONObject.fromObject(jsonStr);
		String bosType  = jo.getString("bosType");
		String queryStr = "where "+jo.getString("queryStr");
		String[] queryCols = null;
		if(jo.get("queryCols") != null) {
			queryCols = new String[jo.getJSONArray("queryCols").size()];
			for(int index = 0; index < jo.getJSONArray("queryCols").size(); index++) {
				queryCols[index] = jo.getJSONArray("queryCols").getString(index);
			}
		}
		int beginRow = 0,endRow = 0;
		if(jo.containsKey("beginRow")&&UIRuleUtil.isNotNull(jo.get("beginRow")))
			beginRow = jo.getInt("beginRow");

		if(jo.containsKey("endRow")&&UIRuleUtil.isNotNull(jo.get("endRow")))
			endRow = jo.getInt("endRow");
		else
			endRow = Integer.MAX_VALUE;

		JSONArray ja = new JSONArray();

		//错误修正.downloadBillList()修改前传参为(....,beginRow,endRow),修改为正确的(....,beginRow,length)
		int length = endRow - beginRow;

		try {
			ja = new WlhlDynamicBillUtils().downloadBillList(ctx, bosType, jo.containsKey("queryInfo")?jo.getString("queryInfo"):null, queryStr, queryCols, beginRow, length);
			resultJson.put("data", ja);
		}catch(Exception e) {
			resultJson.put("result", false);
			resultJson.put("failReason", e.getMessage());
			resultJson.put("failLocation", e.getStackTrace()[0].getClassName()+"\n"+e.getStackTrace()[0].getLineNumber());
			return resultJson.toString();
		}
		return ja.toString();
	}
	/**
	 * 获取单据摘要 --动态单据配置
	 * @param ctx
	 * @param param 请求json字符串
	 * @param withData 是否携带单据数据
	 * @return
	 * @throws BOSException
	 */
	@SuppressWarnings("unchecked")
	public String getBillDigest(Context ctx, String param,boolean withData)throws BOSException
	{
		JSONObject jsonParam = JSONObject.fromObject(param);
		JSONObject resultJson = new JSONObject();
		resultJson.put("result", true);

		String billID = jsonParam.getString("billID");

		BOSObjectType bosType = BOSUuid.read(billID).getType();
		IMetaDataLoader loader = MetaDataLoaderFactory.getLocalMetaDataLoader(ctx);
		IMetaDataPK te = loader.getEntityObjectPK(bosType);

		EntityObjectInfo eo = getEntityObject(ctx, bosType.toString());

		HashMap<String, LinkedHashMap<String,Object>> entryPropMap = new HashMap<String,  LinkedHashMap<String,Object>>();//分录属性
		//查询集合
		SelectorItemCollection selector = new SelectorItemCollection();

		StringBuffer sql = new StringBuffer();
		sql.append(" select * from T_WM_BillDigest ")
		.append(" where FMetadataPK='").append(te.getFullName()).append("'")
		.append(" order by FIndex asc");
		JSONObject dataJSON = new JSONObject();
		JSONObject jo;
		String propGroup,proName;
		try {
			IRowSet rs = DbUtil.executeQuery(ctx, sql.toString());
			while(rs.next()) {
				propGroup = rs.getString("FPropertyGroup");
				proName = rs.getString("FPropertyName");

				selector.add(proName);

				jo = new JSONObject();
				jo.put("propertyGroup", propGroup);//分组
				jo.put("propertyName", proName);//字段属性名
				jo.put("index", rs.getInt("Findex"));//顺序
				jo.put("showFormate", rs.getString("FShowFormate"));//显示格式
				jo.put("editable", rs.getString("FEditable"));//是否可修改
				jo.put("displayName", rs.getString("FAlias_l2"));//显示名称
				jo.put("value", "");//值
				jo.put("dataType", "");
				//非分录
				if(StringUtils.isBlank(propGroup)) {
					if(StringUtils.isNotBlank(proName)) {
						dataJSON.put(proName, jo);
					}
				}
				//分录
				else {
					if(!entryPropMap.containsKey(propGroup)){
						entryPropMap.put(propGroup, new LinkedHashMap<String,Object>());
					}
					if(StringUtils.isNotBlank(proName)) {
						selector.add(propGroup + "." + proName);
						entryPropMap.get(propGroup).put(proName,jo.toString());
					}
				}
			}
		} catch (Exception e) {
			throw new BOSException(e);
		}

		//测试用*************
		//		selector.add("creator.name");
		//		selector.add("number");
		//		selector.add("Entrys.weighBillType");
		//		selector.add("Entrys.isAuto2Inv");
		//		dataJSON.put("creator.name", new JSONObject());
		//		dataJSON.put("number", new JSONObject());
		//		
		//		entryPropMap.put("Entrys", new LinkedHashMap<String,Object>());
		//		((LinkedHashMap)entryPropMap.get("Entrys")).put("weighBillType", new JSONObject());
		//		((LinkedHashMap)entryPropMap.get("Entrys")).put("isAuto2Inv", new JSONObject());
		//*********************

		ICoreBase is = getInstance(ctx, eo);
		CoreBaseInfo info = null;
		try {
			info = is.getValue(new ObjectUuidPK(billID),selector);
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String proValue;
		Iterator itr;
		if(withData) {
			//表头值填充
			itr = dataJSON.keys();
			while(itr.hasNext()){
				proName = (String) itr.next();
				proValue = getValueByProName(ctx,info,proName,eo);
				dataJSON.getJSONObject(proName).put("value", proValue);
			}
		}

		//分录值填充
		itr = entryPropMap.entrySet().iterator();
		Map.Entry<String,LinkedHashMap<String,Object>> entry;
		Iterator itr2;//分录属性
		EntityObjectInfo tempeo;
		IObjectCollection tempCols;
		JSONObject tempJO;
		JSONArray tempJA,entrysJA = new JSONArray();
		while(itr.hasNext()) {
			entry = (Entry<String, LinkedHashMap<String,Object>>) itr.next();
			propGroup = entry.getKey();//分录名称

			jo = new JSONObject();
			jo.put("columns",entry.getValue());

			if(withData) {
				tempJA = new JSONArray();//分录行
				//分录info属性
				tempeo = ((LinkPropertyInfo)eo.getPropertyByName(propGroup)).getRelationship().getSupplierObject();
				tempCols = (IObjectCollection) info.get(propGroup);
				for(int j = 0;j<tempCols.size();j++) {
					tempJO = new JSONObject();
					//分录字段
					itr2 = entry.getValue().keySet().iterator();
					tempJO.put("dateType","entry");
					tempJO.put("index",j);
					while(itr2.hasNext()) {
						proName = (String)itr2.next();
						proValue = getValueByProName(ctx, tempCols.getObject(j),proName, tempeo);
						tempJO.put(proName, proValue);
						//					tempJO.put("propertyName", proName);
						//					tempJO.put("value", proValue);
					}
					tempJA.add(tempJO);
				}
				jo.put("values",tempJA);
			}
			jo.put("entryName",propGroup);
			//分录属性、值
			//			dataJSON.put(propGroup,jo);
			entrysJA.add(jo);//modify
		}
		dataJSON.put("entrysArray",entrysJA);//modify

		if(StringUtils.isNotBlank(dataJSON.toString())){
			resultJson.put("data", dataJSON.toString());
			System.out.println(resultJson.toString());
		}
		return resultJson.toString();
	}

	/**
	 * 获取属性值
	 * @param model
	 * @param proName
	 * @return
	 */
	private String getValueByProName(Context ctx,IObjectValue model ,String orgProName,EntityObjectInfo eo) {
		//获取第一个属性
		int index = orgProName.indexOf(".");
		String proName = index >= 0 ? orgProName.substring(0,index) : orgProName;
		//连接属性
		if(eo.getPropertyByName(proName) instanceof LinkPropertyInfo) {
			if(model.get(proName) instanceof IObjectCollection) {
				return " 不支持子分录";
			}else if(model.get(proName) instanceof IObjectValue) {
				eo = getEntityObject(ctx, model.getObjectValue(proName).getBOSType().toString());
				return getValueByProName(ctx,model.getObjectValue(proName),orgProName.substring(index+1,orgProName.length()),eo);
			}else {
				return "";
			}
		}
		//自有属性
		else if(eo.getPropertyByName(proName) instanceof OwnPropertyInfo) { 
			OwnPropertyInfo ss = ((OwnPropertyInfo)eo.getPropertyByName(proName));
			if(ss.getDataType().getAlias().equalsIgnoreCase("Enum")) {
				try {
					Class<?> cls = Class.forName(ss.getMetaDataRef());
					Class<?> className = model.get(proName).getClass();
					if(className .equals(Integer.class)) {
						className = int.class;
					}else if(className .equals(Double.class)) {
						className = double.class;
					}else if(className .equals(Float.class)) {
						className = float.class;
					}else if(className .equals(Long.class)) {
						className = long.class;
					}
					Method mtd = cls.getMethod("getEnum", new Class[] {className});
					Object cusEnum = mtd.invoke(null, new Object[] {model.get(proName)});
					mtd = cls.getMethod("getAlias");
					return String.valueOf(mtd.invoke(cusEnum));
				}catch(Exception err) {
					return  String.valueOf(model.get(proName));
				}
			}if(ss.getDataType().getAlias().equalsIgnoreCase("boolean")) {
				return String.valueOf(model.getBoolean(proName));
			}else {
				if(model.get(proName) != null) {
					return String.valueOf(model.get(proName));
				}else {
					return "";
				}
			}
		}else {
			return "";
		}
	}
	/**
	 * 根据EntityObjectInfo获取Instance
	 * @param ctx
	 * @param eo
	 * @return
	 * @throws BOSException
	 */
	public static ICoreBase getInstance(Context ctx, EntityObjectInfo eo) throws BOSException {
		try {
			Class<?> cls = Class.forName(eo.getBusinessImplFactory());
			Method mtd = cls.getMethod(ctx == null ? "getRemoteInstance" : "getLocalInstance", new Class[] {com.kingdee.bos.Context.class });
			return ctx == null ? (ICoreBase) mtd.invoke(cls, new Object[]{}) : (ICoreBase) mtd.invoke(cls, new Object[]{ctx});
		} catch (Exception e) {
			throw new BOSException(e);
		}
	}

	/**
	 * 根据单据id获取单据info 
	 * @param ctx
	 * @param id
	 * @return
	 */
	public String getDataByID(Context ctx,String jsonStr) throws BOSException, EASBizException {
		JSONObject json = JSONObject.fromObject(jsonStr);
		String[] slorArrays = null;
		if(json.containsKey("slor")) {
			slorArrays = new String[json.getJSONArray("slor").size()];
			for(int index = 0;index<json.getJSONArray("slor").size();index++) {
				slorArrays[index] = json.getJSONArray("slor").getString(index);
			}
		}
		return getDataByID(ctx,json.getString("id"),slorArrays);
	}
	/**
	 * 根据单据id获取单据info 
	 * @param ctx
	 * @param id
	 * @return
	 */
	public String getDataByID(Context ctx,String id,String[] slorArrays) throws BOSException, EASBizException {
		String bosType = BOSUuid.read(id).getType().toString();
		EntityObjectInfo eo = getEntityObject(ctx, bosType);
		CoreBaseInfo info = null;
		if(slorArrays == null || slorArrays.length <= 0) {
			info = getInstance(ctx, eo).getValue(new ObjectUuidPK(id));
		}else {
			SelectorItemCollection slor = new SelectorItemCollection();
			for(String s:slorArrays) {
				slor.add(s);
			}
			info = getInstance(ctx, eo).getValue(new ObjectUuidPK(id),slor);
		}

		JSONObject infoJSON = new JSONObject();
		setDateValue(info, infoJSON);


		return infoJSON.toString();
	}

	@SuppressWarnings("unchecked")
	private void setDateValue(IObjectValue info,JSONObject infoJSON) {
		Enumeration itr = info.keys();
		String proName;//属性名 
		Object proValue;
		while(itr.hasMoreElements()) {
			proName = itr.nextElement().toString();
			proValue = info.get(proName);
			if(proValue == null) {
				continue;
			}
			//分录
			if(proValue instanceof IObjectCollection) {
				JSONArray ja = new JSONArray();
				JSONObject jo;
				IObjectCollection obc = (IObjectCollection)proValue;
				for(int index = 0;index<obc.size();index++) {
					jo = new JSONObject();
					setDateValue(obc.getObject(index), jo);
					ja.add(jo);
				}
				infoJSON.put(proName, ja);
			}
			//非分录
			else if(proValue instanceof IObjectValue) {
				JSONObject jo = new JSONObject();
				jo.put("id", ((IObjectValue) proValue).getString("id"));
				jo.put("number", ((IObjectValue) proValue).getString("number"));
				jo.put("name", ((IObjectValue) proValue).getString("name"));

				infoJSON.put(proName, jo);
			}else if(proValue instanceof Enum) {
				JSONObject jo = new JSONObject();
				jo.put("name",((Enum) proValue).getName() );
				jo.put("alias",((Enum) proValue).getAlias() );
				try {
					Method mtd = proValue.getClass().getMethod("getValue");
					jo.put("value", String.valueOf(mtd.invoke(proValue, null)));
				} catch (Exception e) {
					e.printStackTrace();
				} 
				infoJSON.put(proName, jo);
			}
			else if(proValue instanceof Date) {
				infoJSON.put(proName, (new SimpleDateFormat("yyyy-MM-dd MM:mm:dd")).format(proValue));
			}
			else {//字符串，数值类型，直接取值
				infoJSON.put(proName, proValue.toString());
			}
		}
	}

	/**
	 * 上传 单据
	 * @param ctx
	 * @param bosType
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	public void uploadDataByBosType(Context ctx,String bosType,String jsonStr,IUploadVerify iverify) throws BOSException, EASBizException {
		JSONObject json = JSONObject.fromObject(jsonStr);
		EntityObjectInfo eo = getEntityObject(ctx, bosType);
		CoreBaseInfo info = null;
		try {
			info = (CoreBaseInfo) Class.forName(eo.getBusinessImplName()+"Info").newInstance();
		} catch (Exception e) {
			throw new BOSException(e);
		}
		ICoreBase is = getInstance(ctx, eo);
		while(true) {
			setPropertiesValue(ctx, eo, json, info);
			eo = (EntityObjectInfo) eo.getParent();
			if(eo == null) {
				break;
			}
		}
		//		System.out.println("单据info:"+info.toString());
		//校验单据
		if(iverify != null) {
			iverify.verify(info);
		}
		//id和编码都为空
		if(StringUtils.isEmpty(info.getString("id"))&&StringUtils.isEmpty(info.getString("number"))) {
			String orgID = ContextUtil.getCurrentCtrlUnit(ctx).getString("id");
			info.setString("number", WlhlBotpCommUtils.getAutoCode(ctx, info, orgID));
		}

		is.save(info);
	}

	/**
	 * 校验类
	 * @author dai_andong
	 *
	 */
	public interface IUploadVerify {
		void verify(CoreBaseInfo info) throws BOSException,EASBizException;
	}

	/**
	 * 上传单据-设置值
	 * @param eo
	 * @param json
	 * @param info
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	@SuppressWarnings("unchecked")
	private void setPropertiesValue(Context ctx,EntityObjectInfo eo,JSONObject json,CoreBaseInfo info) throws BOSException, EASBizException {
		try {
			OwnPropertyInfo opo;
			LinkPropertyInfo lpo;
			String proName;//属性名
			PropertyInfo pro;//属性
			for(int index = 0;index<eo.getProperties().size();index++) {
				pro = eo.getProperties().get(index);
				proName = pro.getName();
				//不包含该属性
				if(!json.containsKey(proName)) {
					continue;
				} 

				if(pro instanceof LinkPropertyInfo) {
					lpo = (LinkPropertyInfo) pro;
					Class cls = Class.forName(lpo.getRelationship().getSupplierObject().getBusinessImplName()+"Info");
					Object obj = cls.newInstance();
					//					//分录
					//没有字段就是分录
					EntityObjectInfo entryEO;
					if(lpo.getMappingField() == null){
						entryEO = getEntityObject(ctx,((CoreBaseInfo)obj).getBOSType().toString());
						for(int j = 0;j<json.getJSONArray(proName).size();j++) {
							CoreBaseInfo entryInfo = (CoreBaseInfo) obj;
							setPropertiesValue(ctx,entryEO,json.getJSONArray(proName).getJSONObject(j),entryInfo);
							//添加分录
							((AbstractObjectCollection)info.get(proName)).addObject(entryInfo);
						}
					}else {
						//是ID就设置INFO 
						if(BOSUuid.isValid( json.getString(proName), true)) {
							((IObjectValue)obj).setString("id", json.getString(proName));
						}else {
							entryEO = getEntityObject(ctx,((IObjectValue)obj).getBOSType().toString());
							//不是ID就默认传number
							obj = getObjectValueByNumber(ctx, entryEO, json.getString(proName));
						}
						//设置值
						info.put(proName, obj);
					}
				}else if(pro instanceof OwnPropertyInfo) {
					opo = (OwnPropertyInfo) pro;
					if(opo.getDataType().equals(DataType.STRING)) {
						info.setString(proName,json.getString(proName));
					}
					else if(opo.getDataType().equals(DataType.BOOLEAN)) {
						info.setBoolean(proName, json.getBoolean(proName));
					}
					else if(opo.getDataType().equals(DataType.DECIMAL)) {
						info.setBigDecimal(proName, JUtils.getBigDecimal(json, proName));
					}
					else if(opo.getDataType().equals(DataType.DATE)) {
						info.setDate(proName, JUtils.getDate(json, proName));
					}
					else if(opo.getDataType().equals(DataType.TIMESTAMP)) {
						info.setTimestamp(proName, JUtils.getTimestamp(json, proName));
					}
					else if(opo.getDataType().equals(DataType.INTEGER)) {
						info.setInt(proName, JUtils.getInt(json, proName));
					}
					else if(opo.getDataType().equals(DataType.ENUM)) {
						info.setString(proName,json.getString(proName));
					}
				}
			}
		} catch (InstantiationException err) {
			throw new BOSException(err);
		} catch (IllegalAccessException err) {
			throw new BOSException(err);
		} catch (ClassNotFoundException err) {
			throw new BOSException(err);
		}
	}

	/**
	 * 根据编码获取对象
	 * @param ctx
	 * @param eo
	 * @param number
	 * @return 
	 */
	private CoreBaseInfo getObjectValueByNumber(Context ctx,EntityObjectInfo eo,String number) throws BOSException{
		try {
			return getInstance(ctx, eo).getValue("where number='"+number+"'");
		} catch (EASBizException e) {
			return null;
		} 
	}

	/**
	 * 获取单据列表信息
	 * @param ctx
	 * @param jsonStr
	 * @return
	 * @throws BOSException
	 */
	@SuppressWarnings("deprecation")
	public JSONArray downloadBillList(Context ctx, String bosType,String queryInfo,String queryStr,String[] queryCols,int beginRow,int endRow) throws BOSException {
		if(StringUtils.isEmpty(queryStr)) {
			throw new BOSException("过滤条件不能为空！");
		}
		if(StringUtils.isEmpty(queryInfo)) {
			EntityObjectInfo eo = getEntityObject(ctx, bosType);
			queryInfo=eo.getExtendedProperty("defaultF7Query");
		}
		JSONArray ja = new JSONArray();
		if(StringUtils.isNotEmpty(queryInfo)) {
			IMetaDataPK pk = new MetaDataPK(queryInfo);

			IQueryExecutor exec = QueryExecutorFactory.getLocalInstance(ctx, pk);
			exec.setObjectView(queryStr);
			IRowSet rs = exec.executeQuery();
			JSONObject jo;
			Object obj;
			try {
				while(rs.next()) {
					if(beginRow>0&&rs.getRow()<beginRow) {
						continue;
					}
					if(endRow>0&&rs.getRow() == endRow) {
						break;
					}
					//没执行行数最大获取500行数据
					if(beginRow == 0 && endRow == 0 && rs.getRow() == 500) {
						break;
					}

					jo = new JSONObject();
					//设置值
					if(queryCols != null&&queryCols.length>0) {
						for(String s:queryCols) {
							jo.put(s,rs.getObject(s));
						}
					}else {
						for(int colIndex = 1; colIndex <= rs.getMetaData().getColumnCount();colIndex++) {
							obj = rs.getObject(colIndex);
							if(obj instanceof Date&&obj!=null) {
								obj = (new SimpleDateFormat("yyyy-MM-dd MM:mm:dd")).format(rs.getObject(colIndex));
							}
							jo.put(rs.getMetaData().getColumnName(colIndex),obj);
						}
					}
					ja.add(jo);
				}
			} catch (SQLException e) {
				throw new BOSException(e);
			}
		}
		return ja;
	}

	/**
	 * 通过id 获取单据实体对象
	 * @param ctx
	 * @param id
	 * @return
	 * @throws BOSException 
	 */
	public static IObjectValue getObjectValueByID(Context ctx,String id) throws BOSException {
		if(StringUtils.isEmpty(id)) {
			return null;
		}
		BOSObjectType bosType = BOSUuid.read(id).getType();
		IDynamicObject is =null;
		if(ctx!=null) {
			is=DynamicObjectFactory.getLocalInstance(ctx);
		}else {
			is=DynamicObjectFactory.getRemoteInstance();
		}
		return is.getValue(bosType,new ObjectUuidPK(id));
	}



}
