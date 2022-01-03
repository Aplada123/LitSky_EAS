package com.kingdee.eas.api.nuonuo.dep;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.eas.api.nuonuo.INuoNuoFacade;
import com.kingdee.eas.api.nuonuo.NuoNuoFacadeFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.custom.comm.IRycSQLFacade;
import com.kingdee.eas.custom.comm.RycSQLFacadeFactory;
import com.kingdee.eas.ep.client.UIParam;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

public class ARInvoiceListUIDep {
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
		IRow row = null;
		String id = null;
		String number = null;
		StringBuffer rsSb = new StringBuffer();
		String result = null;
		List<String> invoiceIDs = new ArrayList<String>();
		IRowSet rs = null;
		String str = null;
		IRycSQLFacade iRyc = RycSQLFacadeFactory.getRemoteInstance();
		INuoNuoFacade iNuoNuo = NuoNuoFacadeFactory.getRemoteInstance();
		for(int i = 0, length = rows.length; i < length; i++){
			if(invoiceIDs.contains(id)){
				continue;
			}else{
				invoiceIDs.add(id);
			}
			row = tblMain.getRow(rows[i]);
			id = (String) row.getCell("id").getValue();
			number = (String) row.getCell("number").getValue();
			str = "select distinct t2.FID,t3.FNumber from t_BOT_Relation t1 inner join T_IM_MakeInvoice t2 on t2.FID=t1.FDestObjectID inner join T_AR_OtherBill t3 on t3.FID = t1.FSrcObjectID where t3.FID=?";
			rs = iRyc.excuteQuery(str, new Object[]{id});
			JSONObject obj = null;
			try {
				if(rs.next()){
					id = rs.getString("FID");
					result = iNuoNuo.requestNewBill(id);
					obj = JSONObject.parseObject(result);
					rsSb.append(number).append(":\t").append(obj.getString("describe")).append("\n");
				}else{
					rsSb.append(number).append(":\tû���ҵ����ɵĿ�Ʊ��\n");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		 MsgBox.showDetailAndOK(null, "ŵŵ��Ʊ��ɣ������������ϸ��Ϣ", rsSb.toString(), 3);
		return rsSb.toString();
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
		IRow row = null;
		String id = null;
		StringBuffer rsSb = new StringBuffer();
		String result = null;
		String number = null;
		String str = null;
		IRowSet rs = null;
		List<String> invoiceIDs = new ArrayList<String>();
		JSONObject jsonResult = null;
		JSONObject firstJson = null;
		IRycSQLFacade iRyc = RycSQLFacadeFactory.getRemoteInstance();
		INuoNuoFacade iNuoNuo = NuoNuoFacadeFactory.getRemoteInstance();
		for(int i = 0, length = rows.length; i < length; i++){
			row = tblMain.getRow(rows[i]);
			id = (String) row.getCell("id").getValue();
			if(invoiceIDs.contains(id)){
				continue;
			}else{
				invoiceIDs.add(id);
			}
			number = (String) row.getCell("number").getValue();
			str = "select distinct t2.FID from t_BOT_Relation t1 inner join T_IM_MakeInvoice t2 on t2.FID=t1.FDestObjectID where t1.FSrcObjectID =?";
			rs = iRyc.excuteQuery(str, new Object[]{id});
			try {
				if(rs.next()){
					id = rs.getString("FID");
					result = iNuoNuo.queryInvoiceResult(id);
					jsonResult = JSONObject.parseObject(result);
					if(jsonResult.getString("describe").equals("��ȡ�ɹ�")){
						JSONArray array = jsonResult.getJSONArray("result");
						if(array.size() > 0){
							firstJson = array.getJSONObject(0);
							if(firstJson.getString("status").equals("2")){
								rsSb.append(number).append("\t��Ʊ��ɣ�����״̬��").append("\n");
							}else{
								rsSb.append(number).append("\t").append(firstJson.getString("statusMsg")).append("\t").append(firstJson.getString("failCause")).append("\n");
							}
						}else{
							rsSb.append(number).append("\t").append("��Ʊʧ��").append("\n");
						}
					}else{
						rsSb.append(number).append("\t").append("��Ʊʧ��").append(jsonResult.getString("describe")).append("\n");
					}
				}else{
					rsSb.append(number).append("\tû���ҵ����ɵĿ�Ʊ��\n");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		 MsgBox.showDetailAndOK(null, "��ѯ��ɣ������������ϸ��Ϣ", rsSb.toString(), 3);
		 return rsSb.toString();
	}

}
