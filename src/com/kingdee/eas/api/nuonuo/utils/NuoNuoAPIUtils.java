package com.kingdee.eas.api.nuonuo.utils;
import java.util.ArrayList;
import java.util.UUID;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 请求Api
 *
 *【这是一个Eclipse工程, JDK支持1.7+】
 */
public class NuoNuoAPIUtils {
	/**
	 * 发票新增接口
	 * @param openToken		openToken
	 * @param content		Y		内容
	 * @return result
	 */
	public static String requestBillingNew(OpenToken openToken, String content){
		String method = "nuonuo.ElectronInvoice.requestBillingNew"; // API方法名
		String senid = UUID.randomUUID().toString().replace("-", ""); // 唯一标识，32位随机码，无需修改，保持默认即可
		String result = openToken.sdk.sendPostSyncRequest(openToken.appAddress, senid, openToken.appKey, openToken.appSecret, openToken.access_token, openToken.taxNum, method, content);
		return result;
	}

	/**
	 * 发票作废 发票作废接口
	 * @param openToken		openToken
	 * @param invoiceId  	Y 	25	发票作废接口
	 * @param invoiceCode	Y 	12	发票代码
	 * @param invoiceNo  	Y 	8	发票号码
	 * @return result 作废结果
	 */
	public static String invoiceCancellation(OpenToken openToken, String invoiceId, String invoiceCode,String invoiceNo){
		String method = "nuonuo.electronInvoice.invoiceCancellation"; // API方法名
		JSONObject json = new JSONObject();
		json.put("invoiceId", invoiceId);
		json.put("invoiceCode", invoiceCode);
		json.put("invoiceNo", invoiceNo);
		String result = excuteSendMessage(openToken, method, json.toString());
		System.out.println(result);
		return result;
	}


	/**
	 * 发票查询 开票结果查询接口接口
	 * 调用该接口获取发票开票结果等有关发票信息，部分字段需要配置才返回
	 * @param openToken		openToken
	 * @param serialNos N 	50	发票流水号，两字段二选一，同时存在以流水号为准（最多查50个订单号）
	 * @param orderNos N 	50	订单编号（最多查50个订单号）
	 * @param isOfferInvoiceDetail Y 		是否需要提供明细(不填默认false) 0/1
	 * @return result
	 */
	public static String queryInvoiceResult(OpenToken openToken, ArrayList<String> serialNos, ArrayList<String> orderNos, int isOfferInvoiceDetail){
		String method = "nuonuo.ElectronInvoice.queryInvoiceResult"; // API方法名
		JSONObject json = new JSONObject();
		json.put("serialNos", serialNos);
		json.put("orderNos", orderNos);
		json.put("isOfferInvoiceDetail", isOfferInvoiceDetail);
		String result = excuteSendMessage(openToken, method, json.toString());
		System.out.println(result);
		return result;
	}

	/**
	 * 发票查询 发票重新交付接口
	 * 通过该接口重新交付平台开具的发票至消费者短信、邮箱
	 * @param openToken		openToken
	 * @param taxnum    	Y 	20	销方税号
	 * @param invoiceCode  Y 	12	发票代码
	 * @param invoiceNum   Y 	8	发票号码
	 * @param phone        N/2 	11	交付手机号，和交付邮箱至少有一个不为空
	 * @param mail         N/2 		交付手机号，和交付邮箱至少有一个不为空
	 * @return		result
	 */
	public static String deliveryInvoice(OpenToken openToken, String taxnum, String invoiceCode, String invoiceNum, String phone, String mail){
		String method = "nuonuo.ElectronInvoice.deliveryInvoice"; // API方法名
		JSONObject json = new JSONObject();
		json.put("taxnum", taxnum);
		json.put("invoiceCode", invoiceCode);
		json.put("invoiceNum", invoiceNum);
		json.put("phone", phone);
		json.put("mail", mail);
		String result = excuteSendMessage(openToken, method, json.toString());
		System.out.println(result);
		return result;
	}

	/**
	 * 发票查询 获取电子发票PDF地址接口
	 * 支持对接方请求接口获取PDF地址
	 * @param openToken		openToken
	 * @param invoiceCode   Y 发票代码
	 * @param invoiceNum    Y 发票号码
	 * @param inTaxAmount   N 含税金额
	 * @param exTaxAmount   N 不含税金额
	 * @return		result
	 */
	public static String getPDF(OpenToken openToken, String invoiceCode, String invoiceNum, String inTaxAmount, String exTaxAmount){
		String method = "nuonuo.ElectronInvoice.getPDF"; // API方法名
		JSONObject json = new JSONObject();
		json.put("invoiceCode", invoiceCode);
		json.put("invoiceNum", invoiceNum);
		json.put("inTaxAmount", inTaxAmount);
		json.put("exTaxAmount", exTaxAmount);
		String result = excuteSendMessage(openToken, method, json.toString());
		System.out.println(result);
		return result;
	}

	/**
	 * 发票查询 企业开票量查询接口
	 * 企业可通过企业税号以及开票时间段查询已开完成的发票数量。 注：目前支持查询企业近3年的开票量。
	 * @param openToken		openToken
	 * @param taxnum 			 Y	20	需要查询的企业税号
	 * @param invoiceTimeStart   Y 	40	需要查询的起始时间； 最小不得小于当前时间前推三年； 格式"yyyy-MM-dd HH:mm:ss"
	 * @param invoiceTimeEnd     Y 	40	需要查询的截止时间； 格式"yyyy-MM-dd HH:mm:ss"
	 * @return		result
	 */
	public static String queryInvoiceQuantity(OpenToken openToken, String taxnum, String invoiceTimeStart, String invoiceTimeEnd){
		String method = "nuonuo.electronInvoice.queryInvoiceQuantity"; // API方法名
		JSONObject json = new JSONObject();
		json.put("taxnum", taxnum);
		json.put("inTaxAmount", invoiceTimeStart);
		json.put("exTaxAmount", invoiceTimeEnd);
		String result = excuteSendMessage(openToken, method, json.toString());
		System.out.println(result);
		return result;
	}

	/**
	 * 获取企业下待开票列表接口
	 * 开发者通过开票方的税号、分机号查询受票方通过极速开票发起的开票请求信息。（限72小时内请求数据）
	 * @param openToken		openToken
	 * @param extensionNum	N	2	分机号（没有分机可填0或不填）
	 * @return		result
	 */
	public static String querySpeedBilling(OpenToken openToken, String extensionNum){
		String method = "nuonuo.speedBilling.querySpeedBilling"; // API方法名
		JSONObject json = new JSONObject();
		json.put("inTaxAmount", extensionNum);
		String result = excuteSendMessage(openToken, method, json.toString());
		System.out.println(result);
		return result;
	}

	/**
	 * 获取纸质发票批量打印编号接口
	 * 通过该接口请求多张纸质发票信息，获取批量打印编号
	 * @param openToken		openToken
	 * @param invoiceCode	Y		发票代码
	 * @param invoiceNum	Y		发票号码
	 * @param money			Y		价税合计
	 * @return		result
	 */
	public static String invoicePrintBatch(OpenToken openToken, String[] invoiceCode, String[] invoiceNum, String[] money){
		String method = "nuonuo.ElectronInvoice.invoicePrintBatch"; // API方法名
		JSONArray array = new JSONArray();
		JSONObject json = null;
		for (int i = 0, size = invoiceCode.length; i < size; i++) {
			json = new JSONObject();
			json.put("taxNum", openToken.taxNum);
			json.put("invoiceCode", invoiceCode[i]);
			json.put("invoiceNum", invoiceNum[i]);
			json.put("money", money[i]);
			array.add(json);
		}
		json.put("outInvoicePrintRequests", array.toString());
		String result = excuteSendMessage(openToken, method, array.toString());
		System.out.println(result);
		return result;
	}

	/**
	 * 发票查验对外接口
	 * 发票查验支持企业通过接口对接实现发票真伪的查验，此查验建立在深入查验服务基础上；
	 * @param openToken		openToken
	 * @param taxNo			N	18	企业税号
	 * @param invoiceCode	Y	12	发票代码
	 * @param invoiceNo		Y	8	发票号码
	 * @param invoiceDate	Y	10	开票日期 （格式:yyyy-MM-dd）
	 * @param optionField	Y	12	专用发票/机动车发票：不含税金额 增值税普通发票/普通发票（电子）/普通发票（卷票）/普通发票（通行费）：校验码（后6位） 二手车发票：车价合计
	 * @return		result
	 */
	public static String invoiceInspection(OpenToken openToken, String taxNo, String invoiceCode, String invoiceNo, String invoiceDate, String optionField){
		String method = "nuonuo.electronInvoice.invoiceInspection"; // API方法名
		JSONObject json = new JSONObject();
		json.put("taxNo", taxNo);
		json.put("invoiceCode", invoiceCode);
		json.put("invoiceNo", invoiceNo);
		json.put("invoiceDate", invoiceDate);
		json.put("optionField", optionField);
		String result = excuteSendMessage(openToken, method, json.toString());
		System.out.println(result);
		return result;
	}

	/**
	 * 查验剩余次数接口
	 * 用于查询企业剩余发票查验次数
	 * @param openToken		openToken
	 * @param identity	Y	255	身份认证，在诺诺网备案后，由诺诺网提供，每个企业一个
	 * @param taxNo		Y	19	税号
	 * @return		result
	 */
	public static String queryCount(OpenToken openToken, String identity,String taxNo){
		String method = "nuonuo.electronInvoice.queryCount"; // API方法名
		JSONObject json = new JSONObject();
		json.put("identity", identity);
		json.put("taxNo", taxNo);
		String result = excuteSendMessage(openToken, method, json.toString());
		System.out.println(result);
		return result;
	}

	/**
	 * 封装成Json格式并发送过去
	 * @param openToken		openToken
	 * @param method
	 * @param content
	 * @return		result
	 */
	protected static String excuteSendMessage(OpenToken openToken,String method, String content){
		JSONObject json = new JSONObject();
		json.put("method", method);
		json.put("content", content);
		return openToken.sendMessageToNuoNuo(json);
	}
}