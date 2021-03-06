package com.kingdee.eas.custom.wlhllicensemanager.util;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.IMetaDataLoader;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.bot.BOTMappingCollection;
import com.kingdee.bos.metadata.bot.BOTMappingFactory;
import com.kingdee.bos.metadata.bot.BOTMappingInfo;
import com.kingdee.bos.metadata.bot.DefineSysEnum;
import com.kingdee.bos.metadata.bot.IBOTMapping;
import com.kingdee.bos.metadata.entity.EntityObjectInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.eas.base.botp.BOTPException;
import com.kingdee.eas.base.btp.BTPManagerFactory;
import com.kingdee.eas.base.btp.BTPTransformResult;
import com.kingdee.eas.base.btp.IBTPManager;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.framework.BillBaseException;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.framework.ICoreBillBase;
import com.kingdee.eas.scm.common.BillBaseStatusEnum;
import com.kingdee.eas.scm.common.ISCMBillBase;
import com.kingdee.eas.scm.common.SCMBillBaseInfo;
import com.kingdee.eas.scm.im.inv.IInvBillBase;
import com.kingdee.eas.scm.im.inv.InvBillBaseInfo;
import com.kingdee.eas.util.StringUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;

public class WlhlBotpCommUtils {
	public static final String MaterialRequest_BOSTYPE="BFBCAD51";//??????????BOSTYPE
	public static final String MaterialReq_BOSTYPE="500AB75E";//??????????BOSTYPE
	public static final String ManufactureRec_BOSTYPE="FA1292B4";//??????????BOSTYPE
	public static final String ReceiveBill_BOSTYPE="FA44FD5B";//??????
	public static final String PaymentBill_BOSTYPE="40284E81";//??????
	public static final String SaleIssueBill_BOSTYPE="CC3E933B";//??????????
	public static final String PurInwarehouse_BOSTYPE="783061E3";//????????
	public static final String TransBill_BOSTYPE="5C2A1F0C";//????????
	
	public static final HashMap<String,String> typeValueMap=new HashMap<String, String>(){{put(MaterialRequest_BOSTYPE,"??????????");put(MaterialReq_BOSTYPE,"??????????");put(ManufactureRec_BOSTYPE,"??????????");}};
	private Context ctx;
	
	public static WlhlBotpCommUtils getInstance(Context ctx) {
		return new WlhlBotpCommUtils(ctx);
	}
	public WlhlBotpCommUtils(Context ctx) {
		this.ctx=ctx;
	}
	/**
	 * ????????????????????
	 * @param ctx
	 * @param pk
	 * @param coreBillInfo
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public void isExitBTPBill(Context ctx, IObjectPK pk, CoreBillBaseInfo coreBillInfo,String destBillBosType) throws BOSException,EASBizException {
		IBTPManager iBTPManager;
		if(ctx!=null)
			iBTPManager= BTPManagerFactory.getLocalInstance(ctx);
		else 
			iBTPManager= BTPManagerFactory.getRemoteInstance();
		/*if (!(iBTPManager.ifHaveDestBills(pk.toString()))) {
			try {
				if (coreBillInfo instanceof CoreBillBaseInfo) {
					iBTPManager.removeAllSRCRelation(coreBillInfo);
				}
			} catch (BTPException ex) {
				throw new BTPException(BTPException.RELATIONDELETEERROR, ex);
			}
		} else {
			throw new BillBaseException(new NumericExceptionSubItem("001","????????????????"));
		}*/
		StringBuffer sql=new StringBuffer();
		sql.append(" select fid from t_bot_relation ");
		sql.append(" where FSrcObjectID='").append(coreBillInfo.getString("id")).append("'");
		if(StringUtils.isNotEmpty(destBillBosType)) {
			sql.append(" and FDestEntityID='").append(destBillBosType).append("'");
		}
		IRowSet rs = DbUtil.executeQuery(ctx, sql.toString());//MaterialRequest_BOSTYPE});,new Object[]{coreBillInfo.getString("id"),destBillBosType}
		try {
			if(rs.next()) {
				throw new BillBaseException(new NumericExceptionSubItem("001","????????????????"+(StringUtils.isNotEmpty(destBillBosType)?typeValueMap.get(destBillBosType):null))); 
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ????number ????Botp info
	 */
	public static BOTMappingInfo getBotpInfoByNumber(Context ctx,String number) {
		try {
			EntityViewInfo ev=new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("number",number,CompareType.EQUALS));
			ev.setFilter(filter);
			BOTMappingCollection col = BOTMappingFactory.getLocalInstance(ctx).getBOTMappingCollection(ev);
			if(col!=null&&col.size()>0) {
				return col.get(0);
			}
		}catch(Exception err){
			err.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * ???????? ??????????????botp ????
	 * @param ctx
	 * @param srcBillType
	 * @param targetBosType
	 * @param defineSys
	 * @param transmitContext
	 * @param srcBillIDs
	 * @param entrieNames
	 * @param entriesKeys
	 * @param botpSelectors
	 * @return
	 * @throws BOSException 
	 * @throws BOTPException 
	 */
	public BOTMappingCollection getAllCanUsedBotps(Context ctx,OrgUnitInfo orgUnit,String srcBillType, String destBillType ,String[] srcBillIDs, String[] entryNames,List entriesKey, SelectorItemCollection botpSelectors) throws BOTPException, BOSException{
		if(botpSelectors == null){
			botpSelectors = new SelectorItemCollection();
		}
		
		Map<String,EntityObjectInfo> destBosTypes = null;
		try {
			destBosTypes=getDestBostype(ctx,srcBillType);
		}catch(Exception e) {
			
		}
		
		BOTMappingCollection botColl = new BOTMappingCollection();
		
//		OrgUnitInfo orgUnit = ContextUtil.getCurrentCtrlUnit(ctx);
		if(destBosTypes==null) 
			return botColl;
		Map transmitContext = new HashMap();
		transmitContext.put("TRANSMITCU", orgUnit);
		// ???????????????? ?????????????????????? 
		if(destBosTypes.size() > 0){
			Set<String> keySet = destBosTypes.keySet();
			Iterator<String> keyIte = keySet.iterator();
			
			IBOTMapping botMapping = BOTMappingFactory.getLocalInstance(ctx);
			while(keyIte.hasNext()){
				// ????????bostype
				String destBOSType = keyIte.next();
				
				if(destBillType!=null&&!destBillType.equals(destBOSType)) {
					continue;
				}
				
				BOTMappingCollection botMappingCols = botMapping.getMappingCollectionForSelectUseRuleFilter(srcBillType,destBOSType, DefineSysEnum.BTP, transmitContext, srcBillIDs, entryNames, entriesKey, botpSelectors);
				// ??????????????
				botColl.addCollection(botMappingCols);
			}
		}
		return botColl;
		
	}
	
	/**
	 * ??????????????
	 * 
	 * @throws BOSException
	 */
	public SCMBillBaseInfo createToSCMBill(Context ctx, CoreBillBaseInfo info, String orderBillBosType,BOTMappingInfo botmappinginfo) throws BOSException {
		IObjectValue destBillInfo = null; // destBillBosType??????????BOS????
		ISCMBillBase iInstace = null;// ????????????????????
		ObjectUuidPK destPK = null;
		IBTPManager btp = null;
		BTPTransformResult result = null;

		try {
			// ***********************************
			IMetaDataLoader loader = MetaDataLoaderFactory.getLocalMetaDataLoader(ctx);
			loader = MetaDataLoaderFactory.getRemoteMetaDataLoader();
			EntityObjectInfo eo = loader.getEntity(BOSObjectType.create(orderBillBosType));
			if (eo == null) {
				return null;
			}
			Class cls = Class.forName(eo.getBusinessImplFactory());
			Method mtd = cls.getMethod("getLocalInstance", new Class[] { com.kingdee.bos.Context.class });
			ICoreBillBase iCoreBase = (ICoreBillBase) mtd.invoke(cls, new Object[] { ctx });
			iInstace = (ISCMBillBase) iCoreBase;

			// ????BOTP????????????????--??????
			btp = BTPManagerFactory.getLocalInstance(ctx);
			// [????]????BTP??????????
			if(botmappinginfo==null)
				result = btp.transform(info, orderBillBosType);
			else 
				result = btp.transform(info, botmappinginfo);
			// [????] result????????????????????????
			
			// [????] ??????????????????
			try {// ???? ??????

				// objectValue????????????????
				for(int index=0;index<result.getBills().size();index++) {
					iInstace.save((CoreBaseInfo) destBillInfo);
				}
				btp.submitRelations(result.getBOTRelationCollection());
				return (SCMBillBaseInfo) destBillInfo;
			} catch (Exception e2) {
				throw new BOSException(e2);
			}
		} catch (Exception e1) {// botp ???? ????????
			try {
				if (((SCMBillBaseInfo) destBillInfo).getBaseStatus().equals(com.kingdee.eas.scm.common.BillBaseStatusEnum.AUDITED))
					iInstace.unAudit(destPK);// ??????
				iInstace.delete(destPK);// ????
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
	 * ????????
	 * 
	 * @throws BOSException
	 */
	public CoreBillBaseInfo createToBill(Context ctx, CoreBillBaseInfo info, String orderBillBosType,BOTMappingInfo botmappinginfo) throws BOSException {
		IObjectValue destBillInfo = null; // destBillBosType??????????BOS????
		ObjectUuidPK destPK = null;
		IBTPManager btp = null;
		BTPTransformResult result = null;

		try {
			// ***********************************
			IMetaDataLoader loader = MetaDataLoaderFactory.getLocalMetaDataLoader(ctx);
			loader = MetaDataLoaderFactory.getRemoteMetaDataLoader();
			EntityObjectInfo eo = loader.getEntity(BOSObjectType.create(orderBillBosType));
			if (eo == null) {
				return null;
			}
			Class cls = Class.forName(eo.getBusinessImplFactory());
			Method mtd = cls.getMethod("getLocalInstance", new Class[] { com.kingdee.bos.Context.class });
			ICoreBillBase iCoreBase = (ICoreBillBase) mtd.invoke(cls, new Object[] { ctx });

			// ????BOTP????????????????--??????
			btp = BTPManagerFactory.getLocalInstance(ctx);
			// [????]????BTP??????????
			if(botmappinginfo==null)
				result = btp.transform(info, orderBillBosType);
			else 
				result = btp.transform(info, botmappinginfo);
			// [????] result????????????????????????
			
			// [????] ??????????????????
			try {// ???? ??????
				destBillInfo=result.getBills().getObject(0);
				iCoreBase.save((CoreBaseInfo) destBillInfo);
				btp.submitRelations(result.getBOTRelationCollection());
				return (CoreBillBaseInfo) destBillInfo;
			} catch (Exception e2) {
				throw new BOSException(e2);
			}
		} catch (Exception e1) {// botp ???? ????????
			throw new BOSException(e1.getMessage());
		}
	}
	
	/**
	 * ????????--??????
	 * 
	 * @throws BOSException
	 */
	public CoreBillBaseInfo createToBillWithMutilyBill(Context ctx, String srcBosType,String[] srcBillIDs,
			String[] entryNames,List entryKeys,
			String orderBillBosType,BOTMappingInfo botmappinginfo) throws BOSException {
		IObjectValue destBillInfo = null; // destBillBosType??????????BOS????
		IBTPManager btp = null;
		BTPTransformResult result = null;

		try {
			// ***********************************
			IMetaDataLoader loader = MetaDataLoaderFactory.getLocalMetaDataLoader(ctx);
			loader = MetaDataLoaderFactory.getRemoteMetaDataLoader();
			EntityObjectInfo eo = loader.getEntity(BOSObjectType.create(orderBillBosType));
			if (eo == null) {
				return null;
			}
			Class cls = Class.forName(eo.getBusinessImplFactory());
			Method mtd = cls.getMethod("getLocalInstance", new Class[] { com.kingdee.bos.Context.class });
			ICoreBillBase iCoreBase = (ICoreBillBase) mtd.invoke(cls, new Object[] { ctx });

			// ????BOTP????????????????--??????
			btp = BTPManagerFactory.getLocalInstance(ctx);
			SelectorItemCollection botpSelector=null;
			// [????]????BTP??????????
				result = btp.transformForBotp(srcBillIDs, entryNames, entryKeys, botpSelector,
						orderBillBosType, new ObjectUuidPK(botmappinginfo.getId()), srcBosType);
				
			// [????] result????????????????????????
			
			// [????] ??????????????????
			try {// ???? ??????
				destBillInfo=result.getBills().getObject(0);
				iCoreBase.save((CoreBaseInfo) destBillInfo);
//				btp.submitRelations(result.getBOTRelationCollection());
				return (CoreBillBaseInfo) destBillInfo;
			} catch (Exception e2) {
				throw new BOSException(e2);
			}
		} catch (Exception e1) {// botp ???? ????????
			throw new BOSException(e1.getMessage());
		}
	}
	
	
	/**
	 * ??????????????
	 * 
	 * @throws BOSException
	 */
	public  void createToSCMBillByEntrties(Context ctx, String[] idList, String[] entryNames, List entryIDs,
			SelectorItemCollection botpSelectors, String destBillTypeBOSType, IObjectPK botpPK, String srcBillType)
			throws BOSException {
		IObjectValue destBillInfo = null; // destBillBosType??????????BOS????
		ISCMBillBase iInstace = null;// ????????????????????
		ObjectUuidPK destPK = null;
		IBTPManager btp = null;
		BTPTransformResult result = null;

		try {
			// ***********************************
			IMetaDataLoader loader = MetaDataLoaderFactory.getLocalMetaDataLoader(ctx);
			loader = MetaDataLoaderFactory.getRemoteMetaDataLoader();
			EntityObjectInfo eo = loader.getEntity(BOSObjectType.create(destBillTypeBOSType));
			if (eo == null) {
				return;
			}
			Class cls = Class.forName(eo.getBusinessImplFactory());
			Method mtd = cls.getMethod("getLocalInstance", new Class[] { com.kingdee.bos.Context.class });
			ICoreBillBase iCoreBase = (ICoreBillBase) mtd.invoke(cls, new Object[] { ctx });
			iInstace = (ISCMBillBase) iCoreBase;

			// ????BOTP????????????????--??????
			btp = BTPManagerFactory.getLocalInstance(ctx);
			// [????]????BTP??????????
			// result = btp.transform(info, botmappinginfo);
			result = btp.transformForBotp(idList, entryNames, entryIDs, botpSelectors, destBillTypeBOSType, botpPK, srcBillType);
			// [????] result????????????????????????
			// objectValue????????????????
			for (int index = 0; index < result.getBills().size(); index++) {
				// if(((SCMBillBaseInfo)destBillInfo).getEntries().size()>0) {
				destBillInfo = result.getBills().getObject(index);
				iInstace.save((CoreBaseInfo) destBillInfo);
				// }
			}
			// destPK = new ObjectUuidPK(((CoreBaseInfo)
			// destBillInfo).getId());// ????????Id

			// [????] ??????????????????
			try {// ???? ??????

				// destBillInfo=iInstace.getValue(destPK);//???????????? ??????????????
				/*
				 * if(destBillInfo.get("baseStatus").equals(BillBaseStatusEnum.
				 * SUBMITED.getValue())) iInstace.audit(destPK);
				 */
				btp.submitRelations(result.getBOTRelationCollection());
			} catch (Exception e2) {
				throw new BOSException(e2);
			}
		} catch (Exception e1) {// botp ???? ????????
			try {
				if (((SCMBillBaseInfo) destBillInfo).getBaseStatus()
						.equals(com.kingdee.eas.scm.common.BillBaseStatusEnum.AUDITED))
					iInstace.unAudit(destPK);// ??????
				iInstace.delete(destPK);// ????
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
	 * ???????? ???????? ??????????????????
	 * @param ctx
	 * @param srcBOSType
	 * @return
	 * @throws BOSException
	 * @throws BOTPException
	 */
	public Map<String,EntityObjectInfo> getDestBostype(Context ctx, String srcBOSType) throws BOSException, BOTPException{
		
		HashMap<String, EntityObjectInfo> destEOI = new HashMap<String, EntityObjectInfo>();
		// ???????? ????????????????????
		IBOTMapping iBOTMapping = BOTMappingFactory.getLocalInstance(ctx);

		String targetTypeAndAliasString = iBOTMapping.getTargetBillTypeList(srcBOSType);
		if (StringUtils.isNotBlank(targetTypeAndAliasString)) {
			String[] targetTypeAndAlias = StringUtil.split(targetTypeAndAliasString, "|");

			String targetBillTypeString = targetTypeAndAlias[0];
			String targetAliasString = targetTypeAndAlias[1];

			String[] targetAlias = StringUtil.split(targetAliasString, ",");
			String[] targetBillType = StringUtil.split(targetBillTypeString, ",");
			// ???????? ????????????eoi??result
			IMetaDataLoader loader = MetaDataLoaderFactory.getLocalMetaDataLoader(ctx);
			for (int i = 0; i < targetBillType.length; i++) {
				String targetType = targetBillType[i];
				EntityObjectInfo tempObj = loader.getEntity(BOSObjectType.create(targetType));
				destEOI.put(targetType, tempObj);
			}

		}
		return destEOI;
	}
	/**
	 * ????BOTP??????????????
	 * @param ctx
	 * @param col
	 */
	public static void deleteBTPBills(Context ctx,CoreBaseCollection col) {
		IInvBillBase iInstace = null;// ????????????????????			
		IMetaDataLoader loader = MetaDataLoaderFactory.getLocalMetaDataLoader(ctx);
		loader = MetaDataLoaderFactory.getRemoteMetaDataLoader();
		InvBillBaseInfo info;
		ObjectUuidPK pk;
		for(int index=0;index<col.size();index++) {
			try{
				info=(InvBillBaseInfo) col.get(index);
				pk=new ObjectUuidPK(info.getId());
				EntityObjectInfo eo = loader.getEntity(BOSObjectType.create(info.getBOSType().toString()));
				if (eo == null) {
					return;
				}
				Class cls = Class.forName(eo.getBusinessImplFactory());
				Method mtd = cls.getMethod("getLocalInstance", new Class[] { com.kingdee.bos.Context.class });
				iInstace = (IInvBillBase) mtd.invoke(cls, new Object[] { ctx });
				info=iInstace.getInvBillBaseInfo(pk);
				if(info.getBaseStatus().equals(BillBaseStatusEnum.AUDITED)) {
					iInstace.unAudit(pk);
				}
				iInstace.delete(pk);
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
	}

	// ????????????
	public static String getAutoCode(Context ctx, IObjectValue objValue, String companyId) throws EASBizException,
			BOSException {
		if(!isCodeRuleEnable(ctx,objValue,companyId)) {
//			SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
//			return format.format(new java.util.Date());
			return null;
		}
		//IOrgSwitchFacade orgSwitch = OrgSwitchFacadeFactory.getLocalInstance(ctx);
		//orgSwitch.orgSwitch(companyId);
		ICodingRuleManager codeRuleMgr = CodingRuleManagerFactory.getLocalInstance(ctx);
		if (codeRuleMgr.isUseIntermitNumber(objValue, companyId)) {
			return codeRuleMgr.readNumber(objValue, companyId);
		} else {
			return codeRuleMgr.getNumber(objValue, companyId);
		}
	}

	// ????????????????
	public static boolean isCodeRuleEnable(Context ctx, IObjectValue objValue, String companyId) throws EASBizException,
			BOSException {
		ICodingRuleManager codeRuleMgr = CodingRuleManagerFactory.getLocalInstance(ctx);
		return codeRuleMgr.isExist(objValue, companyId);
	}
}
