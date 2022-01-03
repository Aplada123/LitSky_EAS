package com.kingdee.eas.api.nuonuo.dep;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.eas.api.nuonuo.NuoNuoFacadeFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.ep.client.UIParam;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

public class MakeInvoiceListUIDep {
	public static void requestMakeInvoice(final UIParam pluginCtx) throws EASBizException, BOSException{
		KDTable tblMain = pluginCtx.getKDTable("tblMain");
		checkBeforeReqNewInvoice(tblMain);
	}
	public static void queryInvoiceResult(final UIParam pluginCtx) throws EASBizException, BOSException{
		KDTable tblMain = pluginCtx.getKDTable("tblMain");
		queryInvoiceResult(tblMain);
	}
	/**
	 * 检查是否有只有一张单据开票
	 * 检验选中的单据诺诺发票ID字段是否已经有值
	 * 返回需要开票单据的ID
	 * @param tblMain
	 * @return
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private static String checkBeforeReqNewInvoice(KDTable tblMain) throws EASBizException, BOSException {
		// TODO Auto-generated method stub
		int[] rows = KDTableUtil.getSelectedRows(tblMain);
		if(rows.length == 0){
			MsgBox.showInfo("请选择记录行");
			SysUtil.abort();
		}
		if(rows.length > 1){
			MsgBox.showInfo("请不要一次选中多行");
			SysUtil.abort();
		}
		IRow row = tblMain.getRow(rows[0]);
		String id = (String) row.getCell("id").getValue();
		String result = NuoNuoFacadeFactory.getRemoteInstance().requestNewBill(id);
		MsgBox.showInfo(result);
		return result;
	}

	/**
	 * 查询开票结果
	 * 检查是否有只有一张单据开票
	 * 检验选中的单据诺诺发票ID字段是否已经有值
	 * 返回需要开票单据的是否已经完成开票
	 * @param tblMain
	 * @return
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private static String queryInvoiceResult(KDTable tblMain) throws EASBizException, BOSException {
		// TODO Auto-generated method stub
		int[] rows = KDTableUtil.getSelectedRows(tblMain);
		if(rows.length == 0){
			MsgBox.showInfo("请选择记录行");
			SysUtil.abort();
		}
		if(rows.length > 1){
			MsgBox.showInfo("请不要一次选中多行");
			SysUtil.abort();
		}
		IRow row = tblMain.getRow(rows[0]);
		String id = (String) row.getCell("id").getValue();
		String result = NuoNuoFacadeFactory.getRemoteInstance().queryInvoiceResult(id);
//		String result = "{\"code\":\"E0000\",\"describe\":\"获取成功\",\"result\":[{\"address\":\"山东省德州市禹城市伦镇工业园（和美食品公司院内南侧）\",\"bankAccount\":\"中国农业银行股份有限公司禹城市支行 15-785101040022979\",\"checker\":\"袁俊英\",\"clerk\":\"吴文娟\",\"clerkId\":\"\",\"createTime\":1640221467000,\"deptId\":\"\",\"extensionNumber\":\"0\",\"imgUrls\":\"https://inv.jss.com.cn/fp/X_lM9afUs1xgQNIDddfgoM-Xyn9jAQW-ISwg2atNAlgznftXjGWQvftlYugy361_SqGY01h2j7JHoPlWKlH00w.jpg\",\"invoiceDate\":1640221466000,\"invoiceType\":\"1\",\"listFlag\":\"0\",\"listName\":\"\",\"notifyEmail\":\"\",\"ofdUrl\":\"\",\"oldInvoiceCode\":\"\",\"oldInvoiceNo\":\"\",\"orderAmount\":\"10000.00\",\"payee\":\"张远雪\",\"phone\":\"\",\"productOilFlag\":0,\"proxyInvoiceFlag\":\"0\",\"remark\":\"\",\"saleName\":\"庆云美神种禽有限公司\",\"salerAccount\":\"中国农业银行股份有限公司庆云县支行15796101040013520\",\"salerAddress\":\"山东省德州市庆云县双河工业园李梓村往北300米路东\",\"salerTaxNum\":\"91371423MA3MAYN49Y\",\"salerTel\":\"0534-8120018\",\"telephone\":\"0534-6155565\",\"terminalNumber\":\"\",\"updateTime\":1640221476000,\"serialNo\":\"21122309042603486711\",\"orderNo\":\"1802-20211223-00040\",\"status\":\"2\",\"statusMsg\":\"开票完成（最终状态）\",\"failCause\":\"\",\"pdfUrl\":\"https://inv.jss.com.cn/fp/X_lM9afUs1xgQNIDddfgoENOXicFihhXVUBxiT1Q9RoP9-lkMOdkzaUugtkMjIPzKP_9MBzDH9JQrkVpyDapTw.pdf\",\"pictureUrl\":\"nnfp.jss.com.cn/pgUbMtxq-gtx1XI\",\"invoiceTime\":1640222371000,\"invoiceCode\":\"037002100111\",\"invoiceNo\":\"61176057\",\"exTaxAmount\":\"10000.00\",\"taxAmount\":\"0.00\",\"payerName\":\"禹城美誉养殖有限公司\",\"payerTaxNo\":\"91371482MA3QKHP948\",\"invoiceKind\":\"增值税电子普通发票\",\"checkCode\":\"83236220062468388512\",\"qrCode\":\"01,10,037002100111,61176057,10000.00,20211223,83236220062468388512,7E7C,\",\"machineCode\":\"661815961687\",\"cipherText\":\"39+2606094-+/27+-4070-5*3964080/10-*3225&lt;894+8+3-4+37597+2&lt;608&lt;5526597/&lt;&lt;*865&gt;812428&lt;/2+05868&lt;5+717*-1+**+96\"}]}";
		JSONObject jsonResult = JSONObject.parseObject(result);
		if(jsonResult.getString("describe").equals("获取成功")){
			JSONArray array = jsonResult.getJSONArray("result");
			if(array.size() > 0){
				JSONObject firstJson = array.getJSONObject(0);
				if(firstJson.getString("status").equals("2")){
					MsgBox.showInfo("开票完成（最终状态）");
				}else{
					MsgBox.showInfo(firstJson.getString("statusMsg") 
							+ "\n" + firstJson.getString("failCause"));
				}
			}else{
				MsgBox.showInfo(result);
			}
		}else{
			MsgBox.showInfo(result);
		}
		return result;
	}
}
