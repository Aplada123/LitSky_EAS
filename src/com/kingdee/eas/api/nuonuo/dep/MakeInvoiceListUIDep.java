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
	 * ����Ƿ���ֻ��һ�ŵ��ݿ�Ʊ
	 * ����ѡ�еĵ���ŵŵ��ƱID�ֶ��Ƿ��Ѿ���ֵ
	 * ������Ҫ��Ʊ���ݵ�ID
	 * @param tblMain
	 * @return
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private static String checkBeforeReqNewInvoice(KDTable tblMain) throws EASBizException, BOSException {
		// TODO Auto-generated method stub
		int[] rows = KDTableUtil.getSelectedRows(tblMain);
		if(rows.length == 0){
			MsgBox.showInfo("��ѡ���¼��");
			SysUtil.abort();
		}
		if(rows.length > 1){
			MsgBox.showInfo("�벻Ҫһ��ѡ�ж���");
			SysUtil.abort();
		}
		IRow row = tblMain.getRow(rows[0]);
		String id = (String) row.getCell("id").getValue();
		String result = NuoNuoFacadeFactory.getRemoteInstance().requestNewBill(id);
		MsgBox.showInfo(result);
		return result;
	}

	/**
	 * ��ѯ��Ʊ���
	 * ����Ƿ���ֻ��һ�ŵ��ݿ�Ʊ
	 * ����ѡ�еĵ���ŵŵ��ƱID�ֶ��Ƿ��Ѿ���ֵ
	 * ������Ҫ��Ʊ���ݵ��Ƿ��Ѿ���ɿ�Ʊ
	 * @param tblMain
	 * @return
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private static String queryInvoiceResult(KDTable tblMain) throws EASBizException, BOSException {
		// TODO Auto-generated method stub
		int[] rows = KDTableUtil.getSelectedRows(tblMain);
		if(rows.length == 0){
			MsgBox.showInfo("��ѡ���¼��");
			SysUtil.abort();
		}
		if(rows.length > 1){
			MsgBox.showInfo("�벻Ҫһ��ѡ�ж���");
			SysUtil.abort();
		}
		IRow row = tblMain.getRow(rows[0]);
		String id = (String) row.getCell("id").getValue();
		String result = NuoNuoFacadeFactory.getRemoteInstance().queryInvoiceResult(id);
//		String result = "{\"code\":\"E0000\",\"describe\":\"��ȡ�ɹ�\",\"result\":[{\"address\":\"ɽ��ʡ���������������ҵ԰������ʳƷ��˾Ժ���ϲࣩ\",\"bankAccount\":\"�й�ũҵ���йɷ����޹�˾�����֧�� 15-785101040022979\",\"checker\":\"Ԭ��Ӣ\",\"clerk\":\"���ľ�\",\"clerkId\":\"\",\"createTime\":1640221467000,\"deptId\":\"\",\"extensionNumber\":\"0\",\"imgUrls\":\"https://inv.jss.com.cn/fp/X_lM9afUs1xgQNIDddfgoM-Xyn9jAQW-ISwg2atNAlgznftXjGWQvftlYugy361_SqGY01h2j7JHoPlWKlH00w.jpg\",\"invoiceDate\":1640221466000,\"invoiceType\":\"1\",\"listFlag\":\"0\",\"listName\":\"\",\"notifyEmail\":\"\",\"ofdUrl\":\"\",\"oldInvoiceCode\":\"\",\"oldInvoiceNo\":\"\",\"orderAmount\":\"10000.00\",\"payee\":\"��Զѩ\",\"phone\":\"\",\"productOilFlag\":0,\"proxyInvoiceFlag\":\"0\",\"remark\":\"\",\"saleName\":\"���������������޹�˾\",\"salerAccount\":\"�й�ũҵ���йɷ����޹�˾������֧��15796101040013520\",\"salerAddress\":\"ɽ��ʡ������������˫�ӹ�ҵ԰����������300��·��\",\"salerTaxNum\":\"91371423MA3MAYN49Y\",\"salerTel\":\"0534-8120018\",\"telephone\":\"0534-6155565\",\"terminalNumber\":\"\",\"updateTime\":1640221476000,\"serialNo\":\"21122309042603486711\",\"orderNo\":\"1802-20211223-00040\",\"status\":\"2\",\"statusMsg\":\"��Ʊ��ɣ�����״̬��\",\"failCause\":\"\",\"pdfUrl\":\"https://inv.jss.com.cn/fp/X_lM9afUs1xgQNIDddfgoENOXicFihhXVUBxiT1Q9RoP9-lkMOdkzaUugtkMjIPzKP_9MBzDH9JQrkVpyDapTw.pdf\",\"pictureUrl\":\"nnfp.jss.com.cn/pgUbMtxq-gtx1XI\",\"invoiceTime\":1640222371000,\"invoiceCode\":\"037002100111\",\"invoiceNo\":\"61176057\",\"exTaxAmount\":\"10000.00\",\"taxAmount\":\"0.00\",\"payerName\":\"���������ֳ���޹�˾\",\"payerTaxNo\":\"91371482MA3QKHP948\",\"invoiceKind\":\"��ֵ˰������ͨ��Ʊ\",\"checkCode\":\"83236220062468388512\",\"qrCode\":\"01,10,037002100111,61176057,10000.00,20211223,83236220062468388512,7E7C,\",\"machineCode\":\"661815961687\",\"cipherText\":\"39+2606094-+/27+-4070-5*3964080/10-*3225&lt;894+8+3-4+37597+2&lt;608&lt;5526597/&lt;&lt;*865&gt;812428&lt;/2+05868&lt;5+717*-1+**+96\"}]}";
		JSONObject jsonResult = JSONObject.parseObject(result);
		if(jsonResult.getString("describe").equals("��ȡ�ɹ�")){
			JSONArray array = jsonResult.getJSONArray("result");
			if(array.size() > 0){
				JSONObject firstJson = array.getJSONObject(0);
				if(firstJson.getString("status").equals("2")){
					MsgBox.showInfo("��Ʊ��ɣ�����״̬��");
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
