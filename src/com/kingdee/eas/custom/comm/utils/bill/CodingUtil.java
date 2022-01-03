package com.kingdee.eas.custom.comm.utils.bill;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.framework.CoreBaseInfo;

public class CodingUtil {

	/**
	 * 根据编码规则自动生成一个编码
	 * @param ctx
	 * @param tempInfo
	 * @return
	 */
	public static String getNewNumberByCodingRule(Context ctx,CoreBaseInfo tempInfo){
		String str = "00000000-0000-0000-0000-000000000000CCE7AED4";
		try {
			if(ctx!=null){
				if(CodingRuleManagerFactory.getLocalInstance(ctx).isExist((IObjectValue)tempInfo, str)){
					ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
					String number = iCodingRuleManager.getNumber((IObjectValue)tempInfo, str);
					return number;
				}
			}else{
				if(CodingRuleManagerFactory.getRemoteInstance().isExist((IObjectValue)tempInfo, str)){
					ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
					String number = iCodingRuleManager.getNumber((IObjectValue)tempInfo, str);
					return number;
				}
			}
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 根据编码规则自动生成一个编码
	 * @param ctx
	 * @param tempInfo
	 * @return
	 */
	public static String createNumberByCodingRule(Context ctx,CoreBaseInfo tempInfo,Object companyInfoOrID){
		String str="";
		if(UIRuleUtil.isNotNull(companyInfoOrID)){
			if(companyInfoOrID instanceof IObjectValue)
				str=((IObjectValue)companyInfoOrID).getString("id");
			else if(companyInfoOrID instanceof String)
				str=(String)companyInfoOrID;
			else
				str = "00000000-0000-0000-0000-000000000000CCE7AED4";
		}else			
			str = "00000000-0000-0000-0000-000000000000CCE7AED4";
		try {
			ICodingRuleManager iCodingRuleManager;
			if(ctx!=null) 
				iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
			else
				iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();

			if(iCodingRuleManager.isExist((IObjectValue)tempInfo, str)){
				return iCodingRuleManager.getNumber((IObjectValue)tempInfo, str);
			}
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
