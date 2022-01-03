package com.kingdee.eas.api.nuonuo.utils;

import java.util.UUID;

import nuonuo.open.sdk.NNOpenSDK;

import com.alibaba.fastjson.JSONObject;
import com.kingdee.util.LowTimer;

/**
 * ��ȡToken
 *
 * ������һ��Eclipse����, JDK֧��1.7+��
 */
public class OpenToken {

	public boolean isSandbox = true;
	public String access_token = null;
	public String taxNum = null;
	public String appKey = null;
	public String appSecret = null;
	public String appAddress = null;
	public LowTimer lt = null;
	public String refresh_token = null;
	public String userId = null;
	public NNOpenSDK sdk = null;

	public OpenToken(boolean isSandBox, String access_token, String taxNum) {
		this.isSandbox = isSandBox;
		this.access_token = access_token;
		this.taxNum = taxNum;
		this.appKey = isSandBox ? NuoNuoConstant.NuoNuoAppkey_Sandbox : NuoNuoConstant.NuoNuoAppkey_Formal;
		this.appSecret = isSandBox ? NuoNuoConstant.NuoNuoAppSecret_Sandbox : NuoNuoConstant.NuoNuoAppSecret_Formal;
		this.appAddress = isSandBox ? NuoNuoConstant.NuoNuoAppAddress_Sandbox : NuoNuoConstant.NuoNuoAppAddress_Fromal;
		this.sdk = NNOpenSDK.getIntance();
	}


	public static void main(String[] args) throws Exception {

	}

	/**
	 * ����ŵŵ���� ͨ��
	 * ������Ӧ��taxNum����""
	 * @param json
	 * @return
	 */
	public String sendMessageToNuoNuo(JSONObject json){
		String method = json.getString("method");//API������
		String content = json.getString("content");//API˽���������
		String senId = UUID.randomUUID().toString().replace("-", ""); // Ψһ��ʶ������ҵ�Լ�����32λ�����
		String result = sdk.sendPostSyncRequest(this.appAddress, senId, this.appKey, this.appSecret, this.access_token, this.taxNum, method, content);
		return result;
	}

	/**
	 * ��Ʊ����
	 *  ������Ӧ��taxNum����""
	 *  ������contentӦ���� invoiceId��invoiceNo�� invoiceCode
	 * @param json
	 * @return
	 */
	public String invoiceCancellation(JSONObject json){
		String method = "nuonuo.electronInvoice.invoiceCancellation"; // API������
		String content = json.getString("content");
		String senId = UUID.randomUUID().toString().replace("-", ""); // Ψһ��ʶ��32λ����룬�����޸ģ�����Ĭ�ϼ���
		String result = sdk.sendPostSyncRequest(this.appAddress, senId, this.appKey, this.appSecret, this.access_token, this.taxNum, method, content);
		System.out.println(result);
		return result;
	}
	/**
	 * ��Ȩʱ�̻���Ҫ�Ȼ�ȡһ��Code�����ʴ���վʱ������ֵ����ַ��ȡ��
	 * @return
	 */
	private String getCode() {
		StringBuffer sb = new StringBuffer();
		sb.append("https://open.nuonuo.com/authorize?")
		.append("appKey=").append(appKey)
		.append("&response_type=code&redirect_uri=").append(NuoNuoConstant.NuoNuoAppRedirect_uri)
		.append("&state=").append("1123");// �˴�1123������ֵ
		return sb.toString();
	}
	/**
	 * �̻���ȡ��Ȩ��
	 * ������Ӧ��
	 * ����ҳ�� https://open.nuonuo.com/#/dev-doc/auth-business
	 */
	public void getMerchantToken() {
		// �ӿڵ���
		String json = sdk.getMerchantToken(appKey, appSecret);

		// ��Ӧ���Ľ���
		System.out.println("\n\n\n���̻���ȡToken��");
		System.out.println("\n�̻���ȡToken:" +json);
	}


	/**
	 * ISV��ȡ��Ȩ��
	 * ������Ӧ��
	 * ����ҳ�� https://open.nuonuo.com/#/dev-doc/auth-service
	 */
	public String getISVToken(String code) {
		// �ӿڵ���
		String json = sdk.getISVToken(appKey, appSecret, code, taxNum, NuoNuoConstant.NuoNuoAppRedirect_uri);
		// ��Ӧ���Ľ���
		System.out.println("\nISV��ȡToken:" + json);
		return json;
	}

	/**
	 * ISVˢ����Ȩ��
	 *
	 * ����ҳ�� https://open.nuonuo.com/#/dev-doc/auth-service
	 */
	public void refreshISVToken() {
		String json = sdk.refreshISVToken(refresh_token, userId, appSecret);
		System.out.println("\nISVˢ��Token��" + json);
	}

}
