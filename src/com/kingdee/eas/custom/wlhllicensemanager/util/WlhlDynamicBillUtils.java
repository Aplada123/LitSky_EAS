package com.kingdee.eas.custom.wlhllicensemanager.util;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONArray;
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
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.custom.wlhllicensemanager.IWlhlBillBase;
import com.kingdee.eas.custom.wlhllicensemanager.IWlhlDataBase;
import com.kingdee.eas.custom.wlhllicensemanager.WlhlBillBaseInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.enums.Enum;
import com.kingdee.util.enums.EnumUtils;
import com.kingdee.util.enums.FloatEnum;
import com.kingdee.util.enums.IntEnum;
import com.kingdee.util.enums.LongEnum;
import com.kingdee.util.enums.StringEnum;

/**
 * 动态单据接口
 * @author dai_andong
 *
 */
public class WlhlDynamicBillUtils {
	private static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");

	private static Logger logger =
		Logger.getLogger("com.kingdee.eas.custom.wlhllicensemanager.util.WlhlDynamicBillUtils");

	/**
	 * 获取单据摘要 --动态单据配置
	 * @param ctx
	 * @param param 请求json字符串
	 * @param withData 是否携带单据数据
	 * @return
	 * @throws BOSException
	 */
	public String getBillDigest(Context ctx, String param,boolean withData)throws BOSException
	{
		JSONObject jsonParam=JSONObject.fromObject(param);
		JSONObject resultJson = new JSONObject();
		resultJson.put("result", true);

		String billID = jsonParam.getString("billID");

		BOSObjectType bosType = BOSUuid.read(billID).getType();
		IMetaDataLoader loader = MetaDataLoaderFactory.getLocalMetaDataLoader(ctx);
		IMetaDataPK te=loader.getEntityObjectPK(bosType);

		EntityObjectInfo eo = getEntityObject(ctx, bosType.toString());

		HashMap<String, LinkedHashMap<String,Object>> entryPropMap=new HashMap<String,  LinkedHashMap<String,Object>>();//分录属性
		//查询集合
		SelectorItemCollection selector=new SelectorItemCollection();

		StringBuffer sql=new StringBuffer();
		sql.append(" select * from T_WM_BillDigest ")
		.append(" where FMetadataPK='").append(te.getFullName()).append("'")
		.append(" order by FIndex asc");
		JSONObject dataJSON=new JSONObject();
		JSONObject jo;
		String propGroup,proName;
		try {
			IRowSet rs = DbUtil.executeQuery(ctx, sql.toString());
			while(rs.next()) {
				propGroup=rs.getString("FPropertyGroup");
				proName=rs.getString("FPropertyName");

				selector.add(proName);

				jo=new JSONObject();
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

		ICoreBase is = getLocalInstance(ctx, eo);
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
				proName=(String) itr.next();
				proValue=getValueByProName(ctx,info,proName,eo);
				dataJSON.getJSONObject(proName).put("value", proValue);
			}
		}

		//分录值填充
		itr=entryPropMap.entrySet().iterator();
		Map.Entry<String,LinkedHashMap<String,Object>> entry;
		Iterator itr2;//分录属性
		EntityObjectInfo tempeo;
		IObjectCollection tempCols;
		JSONObject tempJO;
		JSONArray tempJA,entrysJA=new JSONArray();
		while(itr.hasNext()) {
			entry= (Entry<String, LinkedHashMap<String,Object>>) itr.next();
			propGroup=entry.getKey();//分录名称

			jo=new JSONObject();
			jo.put("columns",entry.getValue());

			if(withData) {
				tempJA=new JSONArray();//分录行
				//分录info属性
				tempeo = ((LinkPropertyInfo)eo.getPropertyByName(propGroup)).getRelationship().getSupplierObject();
				tempCols=(IObjectCollection) info.get(propGroup);
				for(int j=0;j<tempCols.size();j++) {
					tempJO=new JSONObject();
					//分录字段
					itr2=entry.getValue().keySet().iterator();
					tempJO.put("dateType","entry");
					tempJO.put("index",j);
					while(itr2.hasNext()) {
						proName=(String)itr2.next();
						proValue=getValueByProName(ctx, tempCols.getObject(j),proName, tempeo);
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
		int index=orgProName.indexOf(".");
		String proName=index>=0?orgProName.substring(0,index):orgProName;
		//连接属性
		if(eo.getPropertyByName(proName) instanceof LinkPropertyInfo) {
			if(model.get(proName) instanceof IObjectCollection) {
				return " 不支持子分录";
			}else if(model.get(proName) instanceof IObjectValue) {
				eo=getEntityObject(ctx, model.getObjectValue(proName).getBOSType().toString());
				return getValueByProName(ctx,model.getObjectValue(proName),orgProName.substring(index+1,orgProName.length()),eo);
			}else {
				return "";
			}
		}
		//自有属性
		else if(eo.getPropertyByName(proName) instanceof OwnPropertyInfo) { 
			OwnPropertyInfo ss = ((OwnPropertyInfo)eo.getPropertyByName(proName));
			if(ss.getDataType().getAlias().equalsIgnoreCase("Enum")) {
				return getEnumAliasByValue(ss, model, proName);
			}if(ss.getDataType().getAlias().equalsIgnoreCase("boolean")) {
				return String.valueOf(model.getBoolean(proName));
			}else {
				if(model.get(proName)!=null) {
					return String.valueOf(model.get(proName));
				}else {
					return "";
				}
			}
		}else {
			return "";
		}
	}

	private String getEnumAliasByValue(OwnPropertyInfo  proInfo,IObjectValue model,String proName) {
		try {
			Class cls = Class.forName(proInfo.getMetaDataRef());
			Class className=model.get(proName).getClass();
			if(className .equals(Integer.class)) {
				className=int.class;
			}else if(className .equals(Double.class)) {
				className=double.class;
			}else if(className .equals(Float.class)) {
				className=float.class;
			}else if(className .equals(Long.class)) {
				className=long.class;
			}
			Method mtd = cls.getMethod("getEnum", new Class[] {className});
			Object cusEnum=mtd.invoke(null, new Object[] {model.get(proName)});
			mtd = cls.getMethod("getAlias");
			return String.valueOf(mtd.invoke(cusEnum));
		}catch(Exception err) {
			return  String.valueOf(model.get(proName));
		}
	}

	public static ICoreBase getLocalInstance(Context ctx,EntityObjectInfo eo) throws BOSException {
		ICoreBase iCoreBase = null;
		try {
			Class cls = Class.forName(eo.getBusinessImplFactory());
			Method mtd = cls.getMethod("getLocalInstance", new Class[] {com.kingdee.bos.Context.class });
			iCoreBase = (ICoreBase) mtd.invoke(cls, new Object[] { ctx });
		} catch (Exception e) {
			throw new BOSException(e);
		}
		return iCoreBase;
	}

	public static EntityObjectInfo getEntityObject(Context ctx,String bosType) {
		IMetaDataLoader loader = MetaDataLoaderFactory.getLocalMetaDataLoader(ctx);
		EntityObjectInfo eo = loader.getEntity(BOSObjectType.create(bosType));
		return eo;
	}

	/**
	 * 根据单据id获取单据info 
	 * @param ctx
	 * @param id
	 * @return
	 */
	public String getDataByID(Context ctx,String jsonStr) throws BOSException, EASBizException {
		JSONObject json=JSONObject.fromObject(jsonStr);
		String[] slorArrays=null;
		if(json.containsKey("slor")) {
			slorArrays=new String[json.getJSONArray("slor").size()];
			for(int index=0;index<json.getJSONArray("slor").size();index++) {
				slorArrays[index]=json.getJSONArray("slor").getString(index);
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
		String bosType=BOSUuid.read(id).getType().toString();
		EntityObjectInfo eo = getEntityObject(ctx, bosType);
		CoreBaseInfo info = null;
		if(slorArrays==null||slorArrays.length<=0) {
			info=getLocalInstance(ctx, eo).getValue(new ObjectUuidPK(id));
		}else {
			SelectorItemCollection slor=new SelectorItemCollection();
			for(String s:slorArrays) {
				slor.add(s);
			}
			info=getLocalInstance(ctx, eo).getValue(new ObjectUuidPK(id),slor);
		}
		JSONObject infoJSON=new JSONObject();
		//		setDateValue(info, infoJSON);
		while(eo!=null) {
			setDataValue(ctx,eo, info, infoJSON,"",slorArrays);
			eo=(EntityObjectInfo) eo.getParent();
		}

		return infoJSON.toString();
	}

	/**
	 * 
	 * @param ctx
	 * @param eo 实体对象
	 * @param info 单据对象
	 * @param infoJSON
	 * @param parentProName 父元素名称
	 * @param slorArrays 选择器
	 */
	private void setDataValue(Context ctx,EntityObjectInfo eo,IObjectValue info,JSONObject infoJSON,String parentProName,String[] slorArrays) {
		String proName;//属性名 
		Object proValue;
		for(int k=0;k<eo.getProperties().size();k++) {
			proName=eo.getProperties().get(k).getName();
			proValue=info.get(proName);
			if(proValue == null) {
				continue;
			}
			//分录
			if(proValue instanceof IObjectCollection) {
				JSONArray ja=new JSONArray();
				JSONObject jo;
				IObjectCollection obc=(IObjectCollection)proValue;
				String tempProName = parentProName+(StringUtils.isNotBlank(parentProName)?".":"")+proName;
				for(int index=0;index<obc.size();index++) {
					jo=new JSONObject();
					EntityObjectInfo tempEO;
					tempEO=getEntityObject(ctx,obc.getObject(index).getBOSType().toString());
					while(tempEO!=null) {
						setDataValue(ctx,tempEO,obc.getObject(index), jo,tempProName,slorArrays);
						tempEO=(EntityObjectInfo) tempEO.getParent();
					}
					ja.add(jo);
				}
				infoJSON.put(proName, ja);
			}
			//非分录
			else if(proValue instanceof IObjectValue) {
				JSONObject jo=new JSONObject();
				jo.put("id", ((IObjectValue) proValue).getString("id"));
				jo.put("number", ((IObjectValue) proValue).getString("number"));
				jo.put("name", ((IObjectValue) proValue).getString("name"));
				//判断selector,设置selector的值
				if(slorArrays!=null&&parentProName!=null) {
					String tempProName=parentProName+(StringUtils.isNotBlank(parentProName)?".":"")+proName;
					String slor;
					int proNameIndex;
					String childProName;
					EntityObjectInfo tempEO;
					tempEO=getEntityObject(ctx,((IObjectValue) proValue).getBOSType().toString());
					for(int index=0;index<slorArrays.length;index++) {
						slor=slorArrays[index];
						proNameIndex=slor.indexOf(tempProName);
						if(proNameIndex==0&&(slor.length()>=(tempProName.length()+1))) {
							childProName=slor.substring(tempProName.length()+1);
							if(childProName.equals("*")) {
								for(int j=0;j<tempEO.getProperties().size();j++) {
									childProName=tempEO.getProperties().get(j).getName();
									jo.put(childProName, ((IObjectValue) proValue).getString(childProName));
								}
								break;
							}else {
								jo.put(childProName, ((IObjectValue) proValue).getString(childProName));
							}
						}
					}
				}

				infoJSON.put(proName, jo);
			}else if(proValue instanceof Enum) {
				JSONObject jo=new JSONObject();
				jo.put("name",((Enum) proValue).getName() );
				jo.put("alias",((Enum) proValue).getAlias() );
				try {
					Method mtd = proValue.getClass().getMethod("getValue");
					jo.put("value",String.valueOf(mtd.invoke(proValue, null)));
				} catch (Exception e) {
					e.printStackTrace();
				} 
				infoJSON.put(proName, jo);
			}
			else if(proValue instanceof Date) {
				infoJSON.put(proName, sdf.format(proValue));
			}
			else {//字符串，数值类型，直接取值
				infoJSON.put(proName, proValue.toString());

				if(eo.getProperties().get(k) instanceof OwnPropertyInfo) {
					OwnPropertyInfo ss=(OwnPropertyInfo) eo.getProperties().get(k);
					if(ss.getDataType().getAlias().equalsIgnoreCase("Enum")) {
						JSONObject jo=new JSONObject();
						jo.put("value", proValue.toString());
						jo.put("alias", getEnumAliasByValue(ss, info, proName));
						infoJSON.put(proName, jo.toString());
					}
				}
			}
		}
	}

	/**
	 * 上传 单据
	 * @param ctx
	 * @param bosType
	 * @param obj 
	 * @return 
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	public IObjectPK uploadDataByBosType(Context ctx,String bosType,String jsonStr, Object obj) throws BOSException, EASBizException {
		JSONObject json=JSONObject.fromObject(jsonStr);
		EntityObjectInfo eo = getEntityObject(ctx, bosType);
		CoreBaseInfo info=null;
		try {
			info = (CoreBaseInfo) Class.forName(eo.getBusinessImplName()+"Info").newInstance();
		} catch (Exception e) {
			throw new BOSException(e);
		}
		ICoreBase is = getLocalInstance(ctx, eo);

		while(true) {
			setPropertiesValue(ctx, eo, json, info);
			eo=(EntityObjectInfo) eo.getParent();
			if(eo==null) {
				break;
			}
		}

		//System.out.println("单据info:"+info.toString());
		//id和编码都为空
		if(StringUtils.isEmpty(info.getString("id"))&&StringUtils.isEmpty(info.getString("number"))) {
			String orgID=ContextUtil.getCurrentCtrlUnit(ctx).getString("id");
			info.setString("number", WlhlBotpCommUtils.getAutoCode(ctx, info, orgID));
		}

		IObjectPK pk = null;
		if(is instanceof IWlhlBillBase) {
			pk=((IWlhlBillBase)is).dynamicSave((WlhlBillBaseInfo) info);
		}else if(is instanceof IWlhlDataBase) {
			pk=((IWlhlBillBase)is).dynamicSave((WlhlBillBaseInfo)info);
		}else {
			pk=is.save(info);
		}
		return pk;
	}

	/**
	 * 上传 单据
	 * @param ctx
	 * @param bosType
	 * @return 
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	public IObjectPK[] uploadDataWithArray(Context ctx,String bosType,String jsonArrayStr) throws BOSException, EASBizException {
		JSONArray jsonArray = JSONArray.fromObject(jsonArrayStr);
		if(StringUtils.isEmpty(bosType)){
			bosType = ((JSONObject)jsonArray.get(0)).getString("bosType");
		}
		EntityObjectInfo orgEO = getEntityObject(ctx, bosType);
		EntityObjectInfo eo;
		CoreBaseInfo info=null;
		ICoreBase is = getLocalInstance(ctx, orgEO);
		AbstractObjectCollection cols=null;
		try {
			cols = (AbstractObjectCollection) Class.forName(orgEO.getBusinessImplName()+"Collection").newInstance();
	 	} catch (Exception e1) {
    			throw new BOSException(e1);
		} 
		String orgID=ContextUtil.getCurrentCtrlUnit(ctx).getString("id");
		for(int index=0;index<jsonArray.size();index++) {
			JSONObject json=jsonArray.getJSONObject(index);
			eo=orgEO;
			try {
				info = (CoreBaseInfo) Class.forName(eo.getBusinessImplName()+"Info").newInstance();
			} catch (Exception e) {
				throw new BOSException(e);
			}

			//提交处理
			if(json.containsKey("batchAction") && StringUtils.isNotEmpty(json.getString("batchAction"))){
				info.put("batchAction",json.getString("batchAction"));
			}
			while(true) {
				setPropertiesValue(ctx, eo, json, info);
				eo=(EntityObjectInfo) eo.getParent();
				if(eo==null) {
					break;
				}
			}
			//id和编码都为空
			if(StringUtils.isEmpty(info.getString("id"))&&StringUtils.isEmpty(info.getString("number"))) {
				info.setString("number", WlhlBotpCommUtils.getAutoCode(ctx, info, orgID));
			}

			cols.addObject(info);
		}
		IObjectPK[] pks = null;
		if(is instanceof IWlhlBillBase) {
			pks=((IWlhlBillBase)is).dynamicSaveBatch(cols);
		}else if(is instanceof IWlhlDataBase) {
			pks=((IWlhlDataBase)is).dynamicSaveBatch(cols);
		}else {
			ArrayList<IObjectPK> list = new ArrayList<IObjectPK>();
			for(int i=0;i<cols.size();i++){
				if(cols.getObject(i).containsKey("batchAction") && cols.getObject(i).getString("batchAction").equalsIgnoreCase("submitAction")){
					list.add(is.submit((CoreBaseInfo) cols.getObject(i)));
				}else{
					list.add(is.save((CoreBaseInfo) cols.getObject(i)));
				}
			}
			return list.toArray(new IObjectPK[list.size()]);
		}
		return pks;
	}

	/**
	 * 上传单据-设置值
	 * @param eo
	 * @param json
	 * @param info
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private void setPropertiesValue(Context ctx,EntityObjectInfo eo,JSONObject json,CoreBaseInfo info) throws BOSException, EASBizException {
		OwnPropertyInfo opo;
		LinkPropertyInfo lpo;
		String proName;//属性名
		PropertyInfo pro;//属性
		JSONObject tempJO;
		Object jsonObj;
		Iterator itr = json.keys();
		try {
			String jsonProNames,jsonProName;
			String[] jsonProNamesArray;
			while(itr.hasNext()) {
				jsonProNames=((String)itr.next());
				jsonProNamesArray=jsonProNames.split("\\.");
				jsonProName=jsonProNamesArray[0];//json属性左边第一

				for(int index=0;index<eo.getProperties().size();index++) {
					pro = eo.getProperties().get(index);
					proName=pro.getName();
					//json与entity属性相同
					if(!jsonProName.equals(proName)) {
						continue;
					}

					if(pro instanceof LinkPropertyInfo) {
						lpo=(LinkPropertyInfo) pro;
						Class cls=Class.forName(lpo.getRelationship().getSupplierObject().getBusinessImplName()+"Info");
						//					//分录
						//没有字段就是分录
						EntityObjectInfo entryEO;
						if(lpo.getMappingField() == null){
							for(int j = 0 ; j < json.getJSONArray(proName).size(); j++) {
								CoreBaseInfo entryInfo = (CoreBaseInfo) cls.newInstance();
								entryEO = getEntityObject(ctx,entryInfo.getBOSType().toString());
								entryInfo.setNull("id");
								while(true) {
									setPropertiesValue(ctx,entryEO,json.getJSONArray(proName).getJSONObject(j),entryInfo);
									entryEO=(EntityObjectInfo) entryEO.getParent();
									if(entryEO==null) {
										break;
									}
								}
								//添加分录
								((AbstractObjectCollection)info.get(proName)).addObject(entryInfo);
							}
						}else {
							//根据全名(aa.bb)称获取值
							jsonObj=json.get(jsonProNames);
							if(jsonObj instanceof JSONObject) {
								tempJO=json.getJSONObject(jsonProNames);
							}else {
								tempJO=new JSONObject();
								for(String s:jsonProNamesArray) {
									if(s.equalsIgnoreCase("id")) {
										tempJO.put("id", String.valueOf(jsonObj));
									}else if(s.equalsIgnoreCase("number")) {
										tempJO.put("number", String.valueOf(jsonObj));
									}
								}
							}
							//是ID就设置INFO 
							Object obj=cls.newInstance();
							if(tempJO.containsKey("id")&&BOSUuid.isValid(tempJO.getString("id"), true)) {
								((IObjectValue)obj).setString("id", tempJO.getString("id"));
							}else if(tempJO.containsKey("number")){
								entryEO=getEntityObject(ctx,((IObjectValue)obj).getBOSType().toString());
								//不是ID就默认传number
								obj= getObjectValueByNumber(ctx, entryEO, tempJO.getString("number"));
							}
							//设置值
							info.put(proName, obj);
						}
					}else if(pro instanceof OwnPropertyInfo) {
						opo=(OwnPropertyInfo) pro;
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
							if(json.get(proName) instanceof JSONObject) {
								info.setString(proName,json.getJSONObject(proName).getString("value"));
							}else {
								info.setString(proName,json.getString(proName));
							}
						}
						else {
							info.setString(proName,json.getString(proName));
						}
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
			return getLocalInstance(ctx, eo).getValue("where number='"+number+"'");
		} catch (EASBizException e) {
			return null;
		} 
	}
	/**
	 * 获取枚举数组
	 * @param ctx
	 * @param enumPath
	 * @return
	 * @throws BOSException
	 */
	public JSONArray getEnumArray(Context ctx,String enumPath) throws BOSException {
		JSONArray ja=new JSONArray();
		try {
			Class enumClass=Class.forName(enumPath);
			List<Enum> list = EnumUtils.getEnumList(enumClass);
			JSONObject jo;
			String value=null;
			for(Enum eu:list) {
				jo=new JSONObject();
				jo.put("name", eu.getName());
				jo.put("alias", eu.getAlias());
				if(eu instanceof IntEnum) {
					value=String.valueOf(((IntEnum)eu).getValue());
				}else if(eu instanceof FloatEnum) {
					value=String.valueOf(((FloatEnum)eu).getValue());
				}else if(eu instanceof LongEnum) {
					value=String.valueOf(((LongEnum)eu).getValue());
				}else if(eu instanceof StringEnum) {
					value=((StringEnum)eu).getValue();
				}
				jo.put("value",value);
				ja.add(jo);
			}

		}catch(Exception err) {
			throw new BOSException(err);
		}
		return ja;
	}

	/**
	 * 获取单据列表信息
	 * @param ctx
	 * @param jsonStr
	 * isNeedReplaceSplit 是否需要替换分隔符
	 * @return
	 * @throws BOSException
	 */
	public JSONArray getBillList(Context ctx, String bosType,String queryInfo,String queryStr,String[] queryCols,
			int beginRow,int length,boolean isReplaceSplit) throws BOSException {
		if(StringUtils.isEmpty(queryStr)) {
			throw new BOSException("过滤条件不能为空！");
		}
		if(StringUtils.isEmpty(queryInfo)) {
			EntityObjectInfo eo = getEntityObject(ctx, bosType);
			queryInfo=eo.getExtendedProperty("defaultF7Query");
		}
		JSONArray ja=new JSONArray();
		if(StringUtils.isNotEmpty(queryInfo)) {
			IMetaDataPK pk=new MetaDataPK(queryInfo);

			//						QueryInfo qi = MetaDataLoaderFactory.getLocalMetaDataLoader(ctx).getQuery(pk);
			//			qi.getSelector().get(0).getDisplayName()
			if(beginRow==0&&length==0) {
				beginRow=0;
				length=1000;
			}
			IQueryExecutor iexec = QueryExecutorFactory.getLocalInstance(ctx, pk);
			iexec.setObjectView(queryStr);
			//			iexec.option().isIgnoreOrder = false;
			//			iexec.option().isAutoIgnoreZero = false;
			iexec.option().isAutoTranslateBoolean = true;
			iexec.option().isAutoTranslateEnum = true;
			//			iexec.option().topCount = 1000000;
			//			iexec.option().isIgnoreRowCount = false;
			//			iexec.option().isIgnorePermissionCheck = true;
			//			iexec.option().transactionIsolation = 1;
			//			IRowSet rs = iexec.executeQuery(beginRow,length);

			Map execMap = iexec.doAllQueryTask(iexec.openQuery(), beginRow, length, null, true, "id");
			IRowSet rs  = (IRowSet)execMap.get("rowSet");

			JSONObject jo;
			Object obj;
			try {
				while(rs.next()) {
					jo=new JSONObject();
					//设置值
					if(queryCols!=null&&queryCols.length>0) {
						for(String s:queryCols) {
							if(isReplaceSplit){
								s=s.replaceAll("\\.","_");
							}
							jo.put(s,String.valueOf(rs.getObject(s)));
						}
					}else {
						String s;
						for(int colIndex=1;colIndex<=rs.getMetaData().getColumnCount();colIndex++) {
							obj=rs.getObject(colIndex);
							if(obj instanceof Date&&obj!=null) {
								obj=sdf.format(rs.getObject(colIndex));
							}
							s=rs.getMetaData().getColumnName(colIndex);
							if(isReplaceSplit){
								s=s.replaceAll("\\.","_");
							}
							jo.put(s,String.valueOf(obj));
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
	/**
	 * 执行功能
	 * @param ctx
	 * @param bosType
	 * @param id
	 * @param functionName
	 * @throws BOSException 
	 */
	public Object exeFunciton(Context ctx, String bosType,String id,String functionName,Class<?>[] paramsTypes,Object[] paramValues) throws BOSException {
		ICoreBase is = getLocalInstance(ctx, getEntityObject(ctx, bosType));
		try {
			Class cls = is.getClass();
			Method[] mds = cls.getMethods();
			boolean isExists=false;
			for(Method m:mds) {
				System.out.println(m.getName());
				if(!m.getName().equals(functionName)) {
					continue;
				}
				Class<?>[] pts= m.getParameterTypes();

				if((paramsTypes==null||paramsTypes.length<=0)&&pts.length>0) {
					continue;
				}

				if(paramsTypes!=null&&paramsTypes.length!=pts.length) {
					continue;
				}
				Object obj=null;
				Class pt;
				boolean isRight=true;
				for(int index=0;index<pts.length;index++) {
					pt=pts[index];
					if(!pt.equals(paramsTypes[index])) {
						isRight=false;
						break;
					}
					obj=paramValues[index];
					if(pt.equals(IObjectPK.class)) {
						obj=new ObjectUuidPK(String.valueOf(obj));
					}else if(obj instanceof IObjectValue) {
						//						 obj=new CoreBaseInfo();
					}
					paramValues[index]=obj;
				}
				if(isRight) {
					isExists=true;
				}
				switch(paramValues.length) {
				case 1:
					return m.invoke(is, paramValues[0]);
				case 2:
					return m.invoke(is, paramValues[0],paramValues[1]);
				case 3:
					return m.invoke(is, paramValues[0],paramValues[1],paramValues[2]);
				case 4:
					return m.invoke(is, paramValues[0],paramValues[1],paramValues[2],paramValues[3]);
				case 5:
					return m.invoke(is, paramValues[0],paramValues[1],paramValues[2],paramValues[3],paramValues[4]);
				case 6:
					return m.invoke(is, paramValues[0],paramValues[1],paramValues[2],paramValues[3],paramValues[4],paramValues[5]);
				case 7:
					return m.invoke(is, paramValues[0],paramValues[1],paramValues[2],paramValues[3],paramValues[4],paramValues[5],paramValues[6]);
				case 8:
					return m.invoke(is, paramValues[0],paramValues[1],paramValues[2],paramValues[3],paramValues[4],paramValues[5],paramValues[6],paramValues[7]);
				case 9:
					return m.invoke(is, paramValues[0],paramValues[1],paramValues[2],paramValues[3],paramValues[4],paramValues[5],paramValues[6],paramValues[7],paramValues[8]);
				case 10:
					return m.invoke(is, paramValues[0],paramValues[1],paramValues[2],paramValues[3],paramValues[4],paramValues[5],paramValues[6],paramValues[7],paramValues[8],paramValues[9]);
				}
			}
			if(!isExists) {
				throw new BOSException("不存在该方法！");
			}

		} catch (Exception e) {
			throw new BOSException(e);
		}
		return "success";
	}
	public JSONArray downloadBillList(Context ctx, String bosType, String queryInfo, String queryStr, String[] queryCols, int beginRow, int length)
	throws BOSException
	{
		if (StringUtils.isEmpty(queryStr)) {
			throw new BOSException("过滤条件不能为空！");
		}
		if (StringUtils.isEmpty(queryInfo)) {
			EntityObjectInfo eo = getEntityObject(ctx, bosType);
			queryInfo = eo.getExtendedProperty("defaultF7Query");
		}
		JSONArray ja = new JSONArray();
		if (StringUtils.isNotEmpty(queryInfo)) {
			IMetaDataPK pk = new MetaDataPK(queryInfo);

			IQueryExecutor exec = QueryExecutorFactory.getLocalInstance(ctx, pk);
			exec.option().isIgnorePermissionCheck = true;
			exec.setObjectView(queryStr);
			IRowSet rs = exec.executeQuery(beginRow, length);
			try
			{
				while (rs.next()) {
//					if ((beginRow > 0) && (rs.getRow() < beginRow)) {
//						c ontinue;
//					}
//					if ((length > 0) && (rs.getRow() == length))
//					{
//						break;
//					}
					if ((beginRow == 0) && (length == 0) && (rs.getRow() == 1000))
					{
						break;
					}
					JSONObject jo = new JSONObject();

					if ((queryCols != null) && (queryCols.length > 0)) {
						for (String s : queryCols)
							jo.put(s, rs.getObject(s));
					}
					else {
						for (int colIndex = 1; colIndex <= rs.getMetaData().getColumnCount(); colIndex++) {
							Object obj = rs.getObject(colIndex);
							if (((obj instanceof Date)) && (obj != null)) {
								obj = sdf.format(rs.getObject(colIndex));
							}
							jo.put(rs.getMetaData().getColumnName(colIndex), obj);
						}
					}
					ja.add(jo);
				}
			} catch (SQLException e) {
				throw new BOSException(e);
			}
		}
		System.out.println(ja);
		return ja;
	}

}
