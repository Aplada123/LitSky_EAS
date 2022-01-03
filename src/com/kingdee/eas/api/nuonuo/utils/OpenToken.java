package com.kingdee.eas.api.nuonuo.utils;

import java.util.UUID;

import nuonuo.open.sdk.NNOpenSDK;

import com.alibaba.fastjson.JSONObject;
import com.kingdee.util.LowTimer;

/**
 * 获取Token
 *
 * 【这是一个Eclipse工程, JDK支持1.7+】
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
	 * 调用诺诺方法 通用
	 * 自用型应用taxNum请置""
	 * @param json
	 * @return
	 */
	public String sendMessageToNuoNuo(JSONObject json){
		String method = json.getString("method");//API方法名
		String content = json.getString("content");//API私有请求参数
		String senId = UUID.randomUUID().toString().replace("-", ""); // 唯一标识，由企业自己生成32位随机码
		String result = sdk.sendPostSyncRequest(this.appAddress, senId, this.appKey, this.appSecret, this.access_token, this.taxNum, method, content);
		return result;
	}

	/**
	 * 发票作废
	 *  自用型应用taxNum请置""
	 *  参数的content应包括 invoiceId，invoiceNo， invoiceCode
	 * @param json
	 * @return
	 */
	public String invoiceCancellation(JSONObject json){
		String method = "nuonuo.electronInvoice.invoiceCancellation"; // API方法名
		String content = json.getString("content");
		String senId = UUID.randomUUID().toString().replace("-", ""); // 唯一标识，32位随机码，无需修改，保持默认即可
		String result = sdk.sendPostSyncRequest(this.appAddress, senId, this.appKey, this.appSecret, this.access_token, this.taxNum, method, content);
		System.out.println(result);
		return result;
	}
	/**
	 * 授权时商户需要先获取一个Code，访问此网站时将返回值的网址提取出
	 * @return
	 */
	private String getCode() {
		StringBuffer sb = new StringBuffer();
		sb.append("https://open.nuonuo.com/authorize?")
		.append("appKey=").append(appKey)
		.append("&response_type=code&redirect_uri=").append(NuoNuoConstant.NuoNuoAppRedirect_uri)
		.append("&state=").append("1123");// 此处1123是任意值
		return sb.toString();
	}
	/**
	 * 商户获取授权码
	 * 自用型应用
	 * 辅助页面 https://open.nuonuo.com/#/dev-doc/auth-business
	 */
	public void getMerchantToken() {
		// 接口调用
		String json = sdk.getMerchantToken(appKey, appSecret);

		// 响应报文解析
		System.out.println("\n\n\n【商户获取Token】");
		System.out.println("\n商户获取Token:" +json);
	}


	/**
	 * ISV获取授权码
	 * 第三方应用
	 * 辅助页面 https://open.nuonuo.com/#/dev-doc/auth-service
	 */
	public String getISVToken(String code) {
		// 接口调用
		String json = sdk.getISVToken(appKey, appSecret, code, taxNum, NuoNuoConstant.NuoNuoAppRedirect_uri);
		// 响应报文解析
		System.out.println("\nISV获取Token:" + json);
		return json;
	}

	/**
	 * ISV刷新授权码
	 *
	 * 辅助页面 https://open.nuonuo.com/#/dev-doc/auth-service
	 */
	public void refreshISVToken() {
		String json = sdk.refreshISVToken(refresh_token, userId, appSecret);
		System.out.println("\nISV刷新Token：" + json);
	}

}
