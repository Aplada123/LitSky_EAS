package com.kingdee.eas.api.nuonuo.app;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.eas.api.nuonuo.INuoNuoBase;
import com.kingdee.eas.api.nuonuo.NuoNuoBaseCollection;
import com.kingdee.eas.api.nuonuo.NuoNuoBaseFactory;
import com.kingdee.eas.api.nuonuo.NuoNuoBaseInfo;
import com.kingdee.eas.api.nuonuo.utils.NuoNuoAPIUtils;
import com.kingdee.eas.api.nuonuo.utils.OpenToken;
import com.kingdee.eas.basedata.org.IOUPartFI;
import com.kingdee.eas.basedata.org.OUPartFICollection;
import com.kingdee.eas.basedata.org.OUPartFIFactory;
import com.kingdee.eas.basedata.org.OUPartFIInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.tm.im.IMakeInvoice;
import com.kingdee.eas.tm.im.MakeInvoiceEntryInfo;
import com.kingdee.eas.tm.im.MakeInvoiceFactory;
import com.kingdee.eas.tm.im.MakeInvoiceInfo;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.util.NumericExceptionSubItem;

@SuppressWarnings("serial")
public class NuoNuoFacadeControllerBean extends AbstractNuoNuoFacadeControllerBean
{
	OpenToken openToken = null;
	@Override
	protected String _invoiceCancellation(Context ctx, String billID,
			String invoiceId, String invoiceCode, String invoiceNo)
	throws BOSException, EASBizException {
		return super._invoiceCancellation(ctx, billID, invoiceId, invoiceCode, invoiceNo);
	}

	/**
	 * 每个公司获取自己的AccessToken
	 */
	@Override
	protected String _getISVToken(Context ctx, String URL, String companyID) throws BOSException,
	EASBizException {
		// TODO Auto-generated method stub
		System.out.println("######URL:" + URL);
		String code = URL.substring(URL.indexOf("code=") + 5,URL.indexOf("&state="));
		System.out.println("######生成的Code:" + code);
		OUPartFIInfo fiInfo = getOUPartFIInfo(ctx, companyID);
		System.out.println("######获取财务资料扩展成功");
		if(StringUtils.isEmpty(fiInfo.getTaxNumber())){
			System.out.println("######获取的财务资料没有税务登记信息");
			throw new EASBizException(new NumericExceptionSubItem("", "当前公司没有维护税务登记信息"));
		}
		openToken = new OpenToken(false, null, fiInfo.getTaxNumber());
		return openToken.getISVToken(code);
	}

	/**
	 * 提交发票
	 */
	@Override
	protected String _requestNewBill(Context ctx, String billID) throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		IMakeInvoice iMakeInvoice = MakeInvoiceFactory.getLocalInstance(ctx);
		INuoNuoBase iNuoNuoBase = NuoNuoBaseFactory.getLocalInstance(ctx);

		SelectorItemCollection slor = new SelectorItemCollection();
		slor.add("*");
		slor.add("company.*");
		slor.add("currAcctCustomer.id");
		slor.add("currAcctCustomer.name");
		slor.add("currAcctCustomer.number");
		slor.add("CurrAcctSupplier.id");
		slor.add("CurrAcctSupplier.name");
		slor.add("CurrAcctSupplier.number");
		slor.add("CurrAcctCompany.id");
		slor.add("CurrAcctCompany.name");
		slor.add("CurrAcctCompany.number");
		slor.add("Entries.*");
		slor.add("Entries.MeasureUnit.id");
		slor.add("Entries.MeasureUnit.name");
		slor.add("Entries.MeasureUnit.number");
		MakeInvoiceInfo info = iMakeInvoice.getMakeInvoiceInfo(new ObjectUuidPK(billID), slor);
		JSONObject json = new JSONObject();
		JSONObject jsonOrder = new JSONObject();
		JSONObject jsonEntry = null;
		if(StringUtils.isEmpty(info.getReceiveCompany())){
			throw new EASBizException(new NumericExceptionSubItem("", "开票名称为空"));
		}
		jsonOrder.put("buyerName", info.getReceiveCompany());
		jsonOrder.put("buyerTaxNum", info.getTaxNumber());

		String[] addressAndCell = getAddressAndCell(info.getAddress());
		jsonOrder.put("buyerTel", addressAndCell[1]);
		jsonOrder.put("buyerAddress", addressAndCell[0]);

		//		String[] bankAndBEBank = getBankAndBEBank(info.getBankAccount());
		jsonOrder.put("buyerAccount", info.getBankAccount());

		OUPartFIInfo fiInfo = getOUPartFIInfo(ctx, info.getCompany().getString("id"));
		NuoNuoBaseInfo baseInfo = null;
		NuoNuoBaseCollection coll = iNuoNuoBase.getNuoNuoBaseCollection("where company='" + info.getCompany().getString("id") + "' and baseStatus=2 and Token is not null");
		if(coll.size() == 0){
			throw new EASBizException(new NumericExceptionSubItem("", "请完成诺诺基础设置"));
		}else{
			slor = new SelectorItemCollection();
			slor.add("*");
			baseInfo = (NuoNuoBaseInfo) iNuoNuoBase.getValue(new ObjectUuidPK(coll.get(0).getString("id")), slor);
		}
		if(StringUtils.isEmpty(fiInfo.getTaxNumber())){
			throw new EASBizException(new NumericExceptionSubItem("", "当前公司没有维护税号"));
		}
		jsonOrder.put("salerTaxNum", fiInfo.getTaxNumber());
		jsonOrder.put("salerTel", fiInfo.getContactPhone());
		jsonOrder.put("salerAddress", fiInfo.getTaxAddress());
		jsonOrder.put("salerAccount", (fiInfo.getBank() == null ? "" : fiInfo.getBank().getName()) + fiInfo.getBankAccount());
		jsonOrder.put("orderNo", info.getNumber());
		jsonOrder.put("invoiceDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		jsonOrder.put("invoiceCode", info.getBlueVoiceCode());
		jsonOrder.put("invoiceNum", info.getBlueVoiceNum());
		jsonOrder.put("billInfoNo", info.getRedVoiceNumber());
		jsonOrder.put("remark", info.getRemark());
		jsonOrder.put("departmentId", "");
		jsonOrder.put("clerkId", "");
		jsonOrder.put("checker", info.getChecktxt());
		jsonOrder.put("payee", info.getPayeetxt());
		jsonOrder.put("clerk", info.getDrawertxt());
		jsonOrder.put("listFlag", info.isIsList() ? 1 : 0);
		jsonOrder.put("listName", info.isIsList() ? "详见销货清单" : "");
		// 推送方式：-1,不推送;0,邮箱;1,手机（默认）;2,邮箱、手机
		jsonOrder.put("pushMode", (StringUtils.isEmpty(info.getCellphone()) && StringUtils.isEmpty(info.getRecBillEmail())) ? -1 
				: (StringUtils.isNotEmpty(info.getCellphone()) && StringUtils.isNotEmpty(info.getRecBillEmail())) ? 2:
					(StringUtils.isNotEmpty(info.getCellphone()) ? 1 : 0));

		jsonOrder.put("buyerPhone", info.getCellphone());
		jsonOrder.put("email", info.getRecBillEmail());
		jsonOrder.put("invoiceType", info.isIsRedVoice() ? 2 : 1);
		jsonOrder.put("invoiceLine", info.getInvoiceType().getValue().equals("0") ? "c" 
				: info.getInvoiceType().getValue().equals("1") ? "s"
						: info.getInvoiceType().getValue().equals("2") ? "p"
								: info.getInvoiceType().getValue().equals("3") ? "b"
										: ""
		);
		jsonOrder.put("productOilFlag", 0);
		jsonOrder.put("proxyInvoiceFlag", 0);
		jsonOrder.put("callBackUrl", "");
		jsonOrder.put("extensionNumber", UIRuleUtil.getBigDecimal(fiInfo.getInvoiceNumber()));
		//		jsonOrder.put("terminalNumber", 1);
		//		jsonOrder.put("machineCode", 1);
		jsonOrder.put("vehicleFlag", 0);
		jsonOrder.put("clerk", info.getDrawertxt());
		JSONArray jsonArray = new JSONArray();
		MakeInvoiceEntryInfo entryInfo = null;
		for (int i = 0, size = info.getEntries().size(); i < size; i++) {
			entryInfo = info.getEntries().get(i);
			jsonEntry = new JSONObject();
			jsonEntry.put("goodsName", entryInfo.getProduceName());
			jsonEntry.put("goodsCode", entryInfo.getTaxClassificationCode());
			jsonEntry.put("selfCode", "");
			jsonEntry.put("withTaxFlag", UIRuleUtil.getBigDecimal(entryInfo.getTaxRate()).compareTo(BigDecimal.ZERO) != 0 ? 1 : 0);
			jsonEntry.put("price", entryInfo.getPrice());
			jsonEntry.put("num", entryInfo.getQuantity());
			jsonEntry.put("unit", entryInfo.getMeasureUnit().getName());
			jsonEntry.put("specType", entryInfo.getSpecs());
			jsonEntry.put("tax", entryInfo.getTaxAmt());
			jsonEntry.put("taxRate", entryInfo.getTaxRate());
			jsonEntry.put("taxExcludedAmount", entryInfo.getNoTaxAmt());
			jsonEntry.put("taxIncludedAmount", entryInfo.getPriceTax());
			jsonEntry.put("invoiceLineProperty", 0);
			//			jsonEntry.put("favouredPolicyFlag", entryInfo.isHasPreferential() ? 1 : 0);
			//			jsonEntry.put("favouredPolicyName", entryInfo.getPreferentialPolicies() == null ? "" : entryInfo.getPreferentialPolicies().getName());
			jsonEntry.put("favouredPolicyFlag", UIRuleUtil.getBigDecimal(entryInfo.getTaxRate()).compareTo(BigDecimal.ZERO) == 0 ? 1 : 0);
			jsonEntry.put("favouredPolicyName", UIRuleUtil.getBigDecimal(entryInfo.getTaxRate()).compareTo(BigDecimal.ZERO) == 0 ? "免税" : "");
			jsonEntry.put("deduction", 0);
			jsonEntry.put("zeroRateFlag", UIRuleUtil.getBigDecimal(entryInfo.getTaxRate()).compareTo(BigDecimal.ZERO) == 0 ? 1 : "");
			jsonArray.add(jsonEntry);
		}
		jsonOrder.put("invoiceDetail", jsonArray);
		json.put("order", jsonOrder);
		System.out.println("######传入诺诺的参数为:" + json.toString());
		openToken = new OpenToken(false, baseInfo.getToken(), fiInfo.getTaxNumber());
		String result = NuoNuoAPIUtils.requestBillingNew(openToken, json.toString());
		JSONObject rstJson = JSONObject.parseObject(result);
		if(rstJson.containsKey("describe") && rstJson.getString("describe").equals("开票提交成功")){
			JSONObject rst1Json = rstJson.getJSONObject("result");
			if(rst1Json.containsKey("invoiceSerialNum") && StringUtils.isNotEmpty(rst1Json.getString("invoiceSerialNum"))){
				System.out.println("######更新发票序列号update T_IM_MakeInvoice set CFInvoiceSerialNos=? where FID=?" + rst1Json.getString("invoiceSerialNum") + "\t" + billID);
				DbUtil.execute(ctx, "update T_IM_MakeInvoice set CFInvoiceSerialNos=? where FID=?", new Object[]{rst1Json.getString("invoiceSerialNum"), billID});
			}
		}
		return result;
	}

	@Override
	protected String _queryInvoiceResult(Context ctx, String billID) throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		System.out.println("######发票查验结果" + billID);
		IMakeInvoice iMakeInvoice = MakeInvoiceFactory.getLocalInstance(ctx);
		INuoNuoBase iNuoNuoBase = NuoNuoBaseFactory.getLocalInstance(ctx);

		SelectorItemCollection slor = new SelectorItemCollection();
		slor.add("*");
		slor.add("company.*");
		MakeInvoiceInfo info = iMakeInvoice.getMakeInvoiceInfo(new ObjectUuidPK(billID), slor);
		OUPartFIInfo fiInfo = getOUPartFIInfo(ctx, info.getCompany().getString("id"));
		NuoNuoBaseInfo baseInfo = null;
		NuoNuoBaseCollection coll = iNuoNuoBase.getNuoNuoBaseCollection("where company='" + info.getCompany().getString("id") + "' and baseStatus=2 and Token is not null");
		if(coll.size() == 0){
			throw new EASBizException(new NumericExceptionSubItem("", "请完成诺诺基础设置"));
		}else{
			slor = new SelectorItemCollection();
			slor.add("*");
			baseInfo = (NuoNuoBaseInfo) iNuoNuoBase.getValue(new ObjectUuidPK(coll.get(0).getString("id")), slor);
		}
		ArrayList<String> serialNos = new ArrayList<String>();
		if(StringUtils.isNotEmpty(info.getString("invoiceSerialNos"))){
			serialNos.add(info.getString("invoiceSerialNos"));
		}
		ArrayList<String> orderNos = new ArrayList<String>();
		orderNos.add(info.getNumber());
		openToken = new OpenToken(false, baseInfo.getToken(), fiInfo.getTaxNumber());
		String result = NuoNuoAPIUtils.queryInvoiceResult(openToken, serialNos, orderNos, 0);
		JSONObject rstJson = JSONObject.parseObject(result);
		if(rstJson.containsKey("describe") && rstJson.getString("describe").equals("获取成功")){
			JSONArray array = rstJson.getJSONArray("result");
			JSONObject rst1Json = array.getJSONObject(0);
			if(rst1Json.containsKey("status") && rst1Json.getString("status").equals("2")){
				if(rst1Json.containsKey("serialNo") && StringUtils.isNotEmpty(rst1Json.getString("serialNo"))){
					DbUtil.execute(ctx, "update T_IM_MakeInvoice set CFInvoiceId=? where FID=?", new Object[]{rst1Json.getString("serialNo"), billID});
				}
				if(rst1Json.containsKey("invoiceCode") && StringUtils.isNotEmpty(rst1Json.getString("invoiceCode"))){
					DbUtil.execute(ctx, "update T_IM_MakeInvoice set CFInvoiceCode=? where FID=?", new Object[]{rst1Json.getString("invoiceCode"), billID});
				}
				if(rst1Json.containsKey("invoiceNo") && StringUtils.isNotEmpty(rst1Json.getString("invoiceNo"))){
					DbUtil.execute(ctx, "update T_IM_MakeInvoice set CFInvoiceNos=? where FID=?", new Object[]{rst1Json.getString("invoiceNo"), billID});
				}
			}
		}
		return result;
	}

	/**
	 * 根据公司获取财务组织单元扩展内的内容wr
	 * @param orgUnitInfo
	 * @return
	 */
	private static OUPartFIInfo getOUPartFIInfo(Context ctx, String id) {
		// TODO Auto-generated method stub
		try {
			IOUPartFI iUPartFI = OUPartFIFactory.getLocalInstance(ctx);
			OUPartFICollection coll = iUPartFI.getOUPartFICollection("where unit = '" + id + "'" );
			if(coll.size() > 0){
				SelectorItemCollection slor = new SelectorItemCollection();
				slor.add("*");
				slor.add("bank.*");
				return iUPartFI.getOUPartFIInfo(new ObjectUuidPK(coll.get(0).getString("id")), slor );
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
	/**
	 * 查询字符串中的电话及位置信息
	 * @param str
	 * @return
	 */
	private static String[] getAddressAndCell(String str) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(str)){
			return new String[]{"", ""};
		}
		String cell = "";
		String address = "";
		Pattern pattern = Pattern.compile("((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}");
		Matcher matcher = pattern.matcher(str);
		if(matcher.find()){
			cell = matcher.group();
		}else{
			pattern = Pattern.compile("(0\\d{2}-\\d{8}(-\\d{1,4})?)|(0\\d{3}-\\d{7,8}(-\\d{1,4})?)");
			matcher = pattern.matcher(str);
			if(matcher.find()){
				cell = matcher.group();
			}
		}
		address = str.replace(cell, "");
		return new String[]{address, cell};
	}
}