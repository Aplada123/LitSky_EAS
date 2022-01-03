package com.kingdee.eas.api.nuonuo.utils;
import java.util.ArrayList;
import java.util.UUID;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * ����Api
 *
 *������һ��Eclipse����, JDK֧��1.7+��
 */
public class NuoNuoAPIUtils {
	/**
	 * ��Ʊ�����ӿ�
	 * @param openToken		openToken
	 * @param content		Y		����
	 * @return result
	 */
	public static String requestBillingNew(OpenToken openToken, String content){
		String method = "nuonuo.ElectronInvoice.requestBillingNew"; // API������
		String senid = UUID.randomUUID().toString().replace("-", ""); // Ψһ��ʶ��32λ����룬�����޸ģ�����Ĭ�ϼ���
		String result = openToken.sdk.sendPostSyncRequest(openToken.appAddress, senid, openToken.appKey, openToken.appSecret, openToken.access_token, openToken.taxNum, method, content);
		return result;
	}

	/**
	 * ��Ʊ���� ��Ʊ���Ͻӿ�
	 * @param openToken		openToken
	 * @param invoiceId  	Y 	25	��Ʊ���Ͻӿ�
	 * @param invoiceCode	Y 	12	��Ʊ����
	 * @param invoiceNo  	Y 	8	��Ʊ����
	 * @return result ���Ͻ��
	 */
	public static String invoiceCancellation(OpenToken openToken, String invoiceId, String invoiceCode,String invoiceNo){
		String method = "nuonuo.electronInvoice.invoiceCancellation"; // API������
		JSONObject json = new JSONObject();
		json.put("invoiceId", invoiceId);
		json.put("invoiceCode", invoiceCode);
		json.put("invoiceNo", invoiceNo);
		String result = excuteSendMessage(openToken, method, json.toString());
		System.out.println(result);
		return result;
	}


	/**
	 * ��Ʊ��ѯ ��Ʊ�����ѯ�ӿڽӿ�
	 * ���øýӿڻ�ȡ��Ʊ��Ʊ������йط�Ʊ��Ϣ�������ֶ���Ҫ���òŷ���
	 * @param openToken		openToken
	 * @param serialNos N 	50	��Ʊ��ˮ�ţ����ֶζ�ѡһ��ͬʱ��������ˮ��Ϊ׼������50�������ţ�
	 * @param orderNos N 	50	������ţ�����50�������ţ�
	 * @param isOfferInvoiceDetail Y 		�Ƿ���Ҫ�ṩ��ϸ(����Ĭ��false) 0/1
	 * @return result
	 */
	public static String queryInvoiceResult(OpenToken openToken, ArrayList<String> serialNos, ArrayList<String> orderNos, int isOfferInvoiceDetail){
		String method = "nuonuo.ElectronInvoice.queryInvoiceResult"; // API������
		JSONObject json = new JSONObject();
		json.put("serialNos", serialNos);
		json.put("orderNos", orderNos);
		json.put("isOfferInvoiceDetail", isOfferInvoiceDetail);
		String result = excuteSendMessage(openToken, method, json.toString());
		System.out.println(result);
		return result;
	}

	/**
	 * ��Ʊ��ѯ ��Ʊ���½����ӿ�
	 * ͨ���ýӿ����½���ƽ̨���ߵķ�Ʊ�������߶��š�����
	 * @param openToken		openToken
	 * @param taxnum    	Y 	20	����˰��
	 * @param invoiceCode  Y 	12	��Ʊ����
	 * @param invoiceNum   Y 	8	��Ʊ����
	 * @param phone        N/2 	11	�����ֻ��ţ��ͽ�������������һ����Ϊ��
	 * @param mail         N/2 		�����ֻ��ţ��ͽ�������������һ����Ϊ��
	 * @return		result
	 */
	public static String deliveryInvoice(OpenToken openToken, String taxnum, String invoiceCode, String invoiceNum, String phone, String mail){
		String method = "nuonuo.ElectronInvoice.deliveryInvoice"; // API������
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
	 * ��Ʊ��ѯ ��ȡ���ӷ�ƱPDF��ַ�ӿ�
	 * ֧�ֶԽӷ�����ӿڻ�ȡPDF��ַ
	 * @param openToken		openToken
	 * @param invoiceCode   Y ��Ʊ����
	 * @param invoiceNum    Y ��Ʊ����
	 * @param inTaxAmount   N ��˰���
	 * @param exTaxAmount   N ����˰���
	 * @return		result
	 */
	public static String getPDF(OpenToken openToken, String invoiceCode, String invoiceNum, String inTaxAmount, String exTaxAmount){
		String method = "nuonuo.ElectronInvoice.getPDF"; // API������
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
	 * ��Ʊ��ѯ ��ҵ��Ʊ����ѯ�ӿ�
	 * ��ҵ��ͨ����ҵ˰���Լ���Ʊʱ��β�ѯ�ѿ���ɵķ�Ʊ������ ע��Ŀǰ֧�ֲ�ѯ��ҵ��3��Ŀ�Ʊ����
	 * @param openToken		openToken
	 * @param taxnum 			 Y	20	��Ҫ��ѯ����ҵ˰��
	 * @param invoiceTimeStart   Y 	40	��Ҫ��ѯ����ʼʱ�䣻 ��С����С�ڵ�ǰʱ��ǰ�����ꣻ ��ʽ"yyyy-MM-dd HH:mm:ss"
	 * @param invoiceTimeEnd     Y 	40	��Ҫ��ѯ�Ľ�ֹʱ�䣻 ��ʽ"yyyy-MM-dd HH:mm:ss"
	 * @return		result
	 */
	public static String queryInvoiceQuantity(OpenToken openToken, String taxnum, String invoiceTimeStart, String invoiceTimeEnd){
		String method = "nuonuo.electronInvoice.queryInvoiceQuantity"; // API������
		JSONObject json = new JSONObject();
		json.put("taxnum", taxnum);
		json.put("inTaxAmount", invoiceTimeStart);
		json.put("exTaxAmount", invoiceTimeEnd);
		String result = excuteSendMessage(openToken, method, json.toString());
		System.out.println(result);
		return result;
	}

	/**
	 * ��ȡ��ҵ�´���Ʊ�б�ӿ�
	 * ������ͨ����Ʊ����˰�š��ֻ��Ų�ѯ��Ʊ��ͨ�����ٿ�Ʊ����Ŀ�Ʊ������Ϣ������72Сʱ���������ݣ�
	 * @param openToken		openToken
	 * @param extensionNum	N	2	�ֻ��ţ�û�зֻ�����0���
	 * @return		result
	 */
	public static String querySpeedBilling(OpenToken openToken, String extensionNum){
		String method = "nuonuo.speedBilling.querySpeedBilling"; // API������
		JSONObject json = new JSONObject();
		json.put("inTaxAmount", extensionNum);
		String result = excuteSendMessage(openToken, method, json.toString());
		System.out.println(result);
		return result;
	}

	/**
	 * ��ȡֽ�ʷ�Ʊ������ӡ��Žӿ�
	 * ͨ���ýӿ��������ֽ�ʷ�Ʊ��Ϣ����ȡ������ӡ���
	 * @param openToken		openToken
	 * @param invoiceCode	Y		��Ʊ����
	 * @param invoiceNum	Y		��Ʊ����
	 * @param money			Y		��˰�ϼ�
	 * @return		result
	 */
	public static String invoicePrintBatch(OpenToken openToken, String[] invoiceCode, String[] invoiceNum, String[] money){
		String method = "nuonuo.ElectronInvoice.invoicePrintBatch"; // API������
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
	 * ��Ʊ�������ӿ�
	 * ��Ʊ����֧����ҵͨ���ӿڶԽ�ʵ�ַ�Ʊ��α�Ĳ��飬�˲��齨������������������ϣ�
	 * @param openToken		openToken
	 * @param taxNo			N	18	��ҵ˰��
	 * @param invoiceCode	Y	12	��Ʊ����
	 * @param invoiceNo		Y	8	��Ʊ����
	 * @param invoiceDate	Y	10	��Ʊ���� ����ʽ:yyyy-MM-dd��
	 * @param optionField	Y	12	ר�÷�Ʊ/��������Ʊ������˰��� ��ֵ˰��ͨ��Ʊ/��ͨ��Ʊ�����ӣ�/��ͨ��Ʊ����Ʊ��/��ͨ��Ʊ��ͨ�зѣ���У���루��6λ�� ���ֳ���Ʊ�����ۺϼ�
	 * @return		result
	 */
	public static String invoiceInspection(OpenToken openToken, String taxNo, String invoiceCode, String invoiceNo, String invoiceDate, String optionField){
		String method = "nuonuo.electronInvoice.invoiceInspection"; // API������
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
	 * ����ʣ������ӿ�
	 * ���ڲ�ѯ��ҵʣ�෢Ʊ�������
	 * @param openToken		openToken
	 * @param identity	Y	255	�����֤����ŵŵ����������ŵŵ���ṩ��ÿ����ҵһ��
	 * @param taxNo		Y	19	˰��
	 * @return		result
	 */
	public static String queryCount(OpenToken openToken, String identity,String taxNo){
		String method = "nuonuo.electronInvoice.queryCount"; // API������
		JSONObject json = new JSONObject();
		json.put("identity", identity);
		json.put("taxNo", taxNo);
		String result = excuteSendMessage(openToken, method, json.toString());
		System.out.println(result);
		return result;
	}

	/**
	 * ��װ��Json��ʽ�����͹�ȥ
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