package com.kingdee.eas.custom.comm.utils.bill;

import java.lang.reflect.Method;
import java.util.HashMap;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.IMetaDataLoader;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.bot.BOTMappingCollection;
import com.kingdee.bos.metadata.bot.BOTMappingFactory;
import com.kingdee.bos.metadata.bot.BOTMappingInfo;
import com.kingdee.bos.metadata.bot.BOTRelationCollection;
import com.kingdee.bos.metadata.bot.BOTRelationFactory;
import com.kingdee.bos.metadata.bot.BOTRelationInfo;
import com.kingdee.bos.metadata.bot.IBOTMapping;
import com.kingdee.bos.metadata.bot.IBOTRelation;
import com.kingdee.bos.metadata.entity.EntityObjectInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.eas.base.btp.BTPManagerFactory;
import com.kingdee.eas.base.btp.BTPTransformResult;
import com.kingdee.eas.base.btp.IBTPManager;
import com.kingdee.eas.basedata.scm.im.inv.InvUpdateTypeInfo;
import com.kingdee.eas.basedata.scm.im.inv.TransactionInvUpdateTypeCollection;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.scm.common.AbstractSCMBillEntryBaseInfo;
import com.kingdee.eas.scm.common.BillBaseStatusEnum;
import com.kingdee.eas.scm.common.ISCMBillBase;
import com.kingdee.eas.scm.im.inv.IInvBillBase;
import com.kingdee.eas.scm.im.inv.InvBillBaseEntryInfo;
import com.kingdee.eas.scm.im.inv.InvBillBaseInfo;
import com.kingdee.eas.scm.im.inv.InvCommonUtils;

public class BotpUtils {

	/**
	 * 获取单据转换规则
	 * @param ctx
	 * @param srcInfo    来源单据
	 * @param destInfo   目标单据
	 * @return           单据转换规则集合
	 */
	public static CoreBaseCollection getBOTMappingInfosByBillInfo(Context ctx,CoreBaseInfo srcInfo,CoreBaseInfo destInfo){
		String srcBosType = srcInfo.getBOSType().toString();
		String destBosType = destInfo.getBOSType().toString();
		IBOTMapping iBOTMapping;
		CoreBaseCollection coll = null;
		try {
			if(ctx != null)
				iBOTMapping = BOTMappingFactory.getLocalInstance(ctx);
			else
				iBOTMapping=BOTMappingFactory.getRemoteInstance();
			coll = iBOTMapping.getCollection("where srcEntityName='"+srcBosType+"' and destEntityName='"+destBosType+"'");
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return coll;
	}


	/**
	 * 对没有分录的单据创建单据转换关系
	 * @param ctx
	 * @param srcInfo
	 * @param destInfo
	 * @return
	 */
	public static boolean buildBOTMappingRelationWithoutEntry(Context ctx,CoreBaseInfo srcInfo,CoreBaseInfo destInfo){
		String srcBosType = srcInfo.getBOSType().toString();
		String destBosType = destInfo.getBOSType().toString();
		IBOTRelation iBotRelation = null;
		try {
			if(ctx!=null)
				iBotRelation=BOTRelationFactory.getLocalInstance(ctx);
			else
				iBotRelation=BOTRelationFactory.getRemoteInstance();
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BOTRelationInfo result=new BOTRelationInfo();
		result.setSrcEntityID(srcBosType);
		result.setDestEntityID(destBosType);
		result.setSrcObjectID(srcInfo.getId().toString());
		result.setSrcEntityID(destInfo.getId().toString());
		try {
			iBotRelation.addnew(result);
			if(result.getId()!=null)
				System.out.println(result.getId().toString());
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 校验单据是否存在上下游单据
	 * @param ctx
	 * @param srcBillInfo         来源单据info
	 * @param srcEntityBosType    来源实体的bosType
	 * @param destBillInfo        目标单据info     
	 * @param destEntityBosType   目标实体的bosType
	 * @param searchType          查询类型 上查UP下查DOWN
	 */
	public static Boolean checkIsExistDownBill(Context ctx,CoreBaseInfo srcBillInfo,String srcEntityBosType,CoreBaseInfo destBillInfo,String destEntityBosType,String searchType){
		EntityViewInfo evi=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		if(searchType.equalsIgnoreCase("UP")){


			/*-----------------------校验------------------------*/
			if(destBillInfo.getId()==null){
				System.out.println("目标对象ID为空");
				return null;
			}
			filter.getFilterItems().add(new FilterItemInfo("destObjectID",destBillInfo.getId().toString()+"'",CompareType.EQUALS));
			if(destEntityBosType!=null)
				filter.getFilterItems().add(new FilterItemInfo("srcEntityID",srcEntityBosType+"'",CompareType.EQUALS));

			evi.setFilter(filter);
			BOTRelationCollection coll;
			try {
				if(ctx==null)
					coll=BOTRelationFactory.getRemoteInstance().getCollection(evi);
				else
					coll=BOTRelationFactory.getLocalInstance(ctx).getCollection(evi);
				if(coll.size()>0)
					return true;
				else
					return false;
			} catch (BOSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		
		}else if(searchType.equalsIgnoreCase("DOWN")){

			/*-----------------------校验------------------------*/
			if(srcBillInfo.getId()==null){
				System.out.println("来源对象ID为空");
				return null;
			}
			filter.getFilterItems().add(new FilterItemInfo("srcObjectID",srcBillInfo.getId().toString(),CompareType.EQUALS));
			if(destEntityBosType!=null)
				filter.getFilterItems().add(new FilterItemInfo("destEntityID",destEntityBosType,CompareType.EQUALS));

			evi.setFilter(filter);
			BOTRelationCollection coll;
			try {
				if(ctx==null)
					coll=BOTRelationFactory.getRemoteInstance().getCollection(evi);
				else
					coll=BOTRelationFactory.getLocalInstance(ctx).getCollection(evi);
				if(coll.size()>0)
					return true;
				else
					return false;
			} catch (BOSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}else{
			System.out.println("查询类型不存在或不匹配");
			return null;
		}
	}

	/**
	 * 生成供应链单据
	 * 
	 * @throws BOSException
	 */
	@SuppressWarnings("unchecked")
	public static void createToSCMBill(Context ctx, CoreBillBaseInfo info, String orderBillBosType) throws BOSException {
		IObjectValue destBillInfo = null; // destBillBosType：目标单据BOS类型
		ISCMBillBase iInstace = null;// 库存业务单据对象接口
		ObjectUuidPK destPK = null;
		IBTPManager btp = null;
		BTPTransformResult result = null;

		try {
			// ***********************************
			IMetaDataLoader loader = MetaDataLoaderFactory.getLocalMetaDataLoader(ctx);
			//loader = MetaDataLoaderFactory.getRemoteMetaDataLoader();
			EntityObjectInfo eo = loader.getEntity(BOSObjectType.create(orderBillBosType));
			if (eo == null) {
				return;
			}
			Class cls = Class.forName(eo.getBusinessImplFactory());
			Method mtd = cls.getMethod("getLocalInstance", new Class[] { com.kingdee.bos.Context.class });
			ICoreBase iCoreBase = (ICoreBase) mtd.invoke(cls, new Object[] { ctx });
			iInstace = (ISCMBillBase) iCoreBase;

			// 调用BOTP接口自动生成单据--入库单
			btp = BTPManagerFactory.getLocalInstance(ctx);
			// [注释]获取BTP的本地接口
			result = btp.transform(info, orderBillBosType);
			// [注释] result：为转换成功生的目标单据
			// objectValue：源单据的值对象
			destBillInfo = result.getBills().getObject(0);

			destPK = new ObjectUuidPK(((CoreBaseInfo) destBillInfo).getId());// 目标单据Id

			// [注释] 保存单据的关联系统
			try {// 提交 和审核
				if(((InvBillBaseInfo) destBillInfo).getTransactionType()!=null) {
					if(iInstace instanceof IInvBillBase) {//库存单据更新 更新类型
						FilterInfo filterInfo = new FilterInfo();
						filterInfo.getFilterItems().add(
								new FilterItemInfo("transactionType.id", ((InvBillBaseInfo) destBillInfo).getTransactionType()
										.getId().toString(), CompareType.EQUALS));
						String prefix = "invUpdateType.";
						filterInfo.getFilterItems().add(
								new FilterItemInfo(prefix + "status", Integer.valueOf(1), CompareType.EQUALS));
						EntityViewInfo ev = new EntityViewInfo();
						// filterInfo.mergeFilter(buildInvUpdateTypeFilterInfo(entry,
						// isF7), "AND");
						filterInfo.setMaskString("#0 AND #1");
						ev.setFilter(filterInfo);

						TransactionInvUpdateTypeCollection updateTypeCol = InvCommonUtils.getTransactionInvUpdateTypeCol(ctx,
								ev);
						InvUpdateTypeInfo updateType = null;
						if (updateTypeCol != null && updateTypeCol.size() > 0) {
							updateType = updateTypeCol.get(0).getInvUpdateType();
						}
						for (int i = 0; i < ((AbstractObjectCollection) destBillInfo.get("entry")).size(); i++) {
							AbstractObjectCollection sd = (AbstractObjectCollection) destBillInfo.get("entry");
							((InvBillBaseEntryInfo) sd.getObject(i)).setInvUpdateType(updateType);
						}
					}
				}
				// iInstace.save((CoreBaseInfo) destBillInfo);
				iInstace.submit((CoreBaseInfo) destBillInfo);
				if (destBillInfo.get("baseStatus").equals(BillBaseStatusEnum.SUBMITED))
					iInstace.audit(destPK);
				btp.submitRelations(result.getBOTRelationCollection());
			} catch (Exception e2) {
				throw new BOSException(e2);
			}
		} catch (Exception e1) {// botp 失败 删除单据
			try {
				if (((AbstractSCMBillEntryBaseInfo) destBillInfo).getBaseStatus().equals(
						com.kingdee.eas.scm.common.BillBaseStatusEnum.AUDITED))
					iInstace.unAudit(destPK);// 反审核
				iInstace.delete(destPK);// 删除
			} catch (Exception e3) {
				e3.printStackTrace();
			}
			try {
				if (destBillInfo != null)
					btp.removeAllRelation((CoreBillBaseInfo) destBillInfo);
			} catch (Exception e3) {
				e3.printStackTrace();
			}
			throw new BOSException(e1.getMessage());
		}
	}


	/**
	 * 校验单据是否存在上下游单据
	 * @param ctx
	 * @param srcBillInfo         来源单据info
	 * @param srcEntityBosType    来源实体的bosType
	 * @param destBillInfo        目标单据info     
	 * @param destEntityBosType   目标实体的bosType
	 * @param searchType          查询类型 上查UP下查DOWN
	 */
	public static HashMap<String,String> checkIsExistUpDownBill(Context ctx,CoreBaseInfo srcBillInfo,String srcEntityBosType,CoreBaseInfo destBillInfo,String destEntityBosType,String searchType){
		EntityViewInfo evi=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		HashMap<String,String> result=new HashMap<String, String>();
		if(searchType.equalsIgnoreCase("UP")){
	
	
			/*-----------------------校验------------------------*/
			if(destBillInfo.getId()==null){
				System.out.println("目标对象ID为空");
				return null;
			}
			filter.getFilterItems().add(new FilterItemInfo("destObjectID",destBillInfo.getId().toString()+"'",CompareType.EQUALS));
			if(destEntityBosType!=null)
				filter.getFilterItems().add(new FilterItemInfo("srcEntityID",srcEntityBosType+"'",CompareType.EQUALS));
	
			evi.setFilter(filter);
			BOTRelationCollection coll;
			try {
				if(ctx==null)
					coll=BOTRelationFactory.getRemoteInstance().getCollection(evi);
				else
					coll=BOTRelationFactory.getLocalInstance(ctx).getCollection(evi);
				if(coll.size()>0){
					result.put("srcBillID", ((BOTRelationInfo)coll.get(0)).getSrcObjectID());
					result.put("destBillID", ((BOTRelationInfo)coll.get(0)).getDestObjectID());
					return result;
				}
				else
					return null;
			} catch (BOSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		
		}else if(searchType.equalsIgnoreCase("DOWN")){
	
			/*-----------------------校验------------------------*/
			if(srcBillInfo.getId()==null){
				System.out.println("来源对象ID为空");
				return null;
			}
			filter.getFilterItems().add(new FilterItemInfo("srcObjectID",srcBillInfo.getId().toString(),CompareType.EQUALS));
			if(destEntityBosType!=null)
				filter.getFilterItems().add(new FilterItemInfo("destEntityID",destEntityBosType,CompareType.EQUALS));
	
			evi.setFilter(filter);
			BOTRelationCollection coll;
			try {
				if(ctx==null)
					coll=BOTRelationFactory.getRemoteInstance().getCollection(evi);
				else
					coll=BOTRelationFactory.getLocalInstance(ctx).getCollection(evi);
				if(coll.size()>0){
					result.put("srcBillID", ((BOTRelationInfo)coll.get(0)).getSrcObjectID());
					result.put("destBillID", ((BOTRelationInfo)coll.get(0)).getDestObjectID());
				}
				else
					return null;
			} catch (BOSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}else{
			System.out.println("查询类型不存在或不匹配");
			return null;
		}
	}
	/**
	 * 获取可用的单据转换规则
	 * @param ctx
	 * @param iBOTMapping
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 * @author HeMei XinXiBu
	 * @date 2020-4-22 上午08:34:26
	 * <p>Copyright: Copyright (c) 2020HeMeiJiTuan</p>
	 */
	public static BOTMappingCollection getCanUseBOTPMappingInfo(Context ctx,IBOTMapping iBOTMapping,String srcEntityID, String destEntityID)  {
		// TODO Auto-generated method stub
		// 查找可用的BOTP转换关系
		String filter = "where SrcEntityName='" + srcEntityID + "' and DestEntityName='" + destEntityID + "'";
		BOTMappingCollection coll;
		BOTMappingCollection resColl = new BOTMappingCollection();
		try {
			coll = iBOTMapping.getBOTMappingCollection(filter );
			BOTMappingInfo info = null;
			for(int i = 0, size = coll.size(); i < size; i++){
				if(coll.get(i).getExtRule().isIsEffected()){
					info = coll.get(i);
					resColl.add(info);
				} 
			}
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resColl;
	}
}
