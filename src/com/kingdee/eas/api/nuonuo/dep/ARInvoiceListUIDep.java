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
					rsSb.append(number).append(":\t没有找到生成的开票单\n");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		 MsgBox.showDetailAndOK(null, "诺诺开票完成，具体结果请打开明细信息", rsSb.toString(), 3);
		return rsSb.toString();
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
					if(jsonResult.getString("describe").equals("获取成功")){
						JSONArray array = jsonResult.getJSONArray("result");
						if(array.size() > 0){
							firstJson = array.getJSONObject(0);
							if(firstJson.getString("status").equals("2")){
								rsSb.append(number).append("\t开票完成（最终状态）").append("\n");
							}else{
								rsSb.append(number).append("\t").append(firstJson.getString("statusMsg")).append("\t").append(firstJson.getString("failCause")).append("\n");
							}
						}else{
							rsSb.append(number).append("\t").append("开票失败").append("\n");
						}
					}else{
						rsSb.append(number).append("\t").append("开票失败").append(jsonResult.getString("describe")).append("\n");
					}
				}else{
					rsSb.append(number).append("\t没有找到生成的开票单\n");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		 MsgBox.showDetailAndOK(null, "查询完成，具体结果请打开明细信息", rsSb.toString(), 3);
		 return rsSb.toString();
	}

}
