package com.kingdee.eas.custom.wlhllicensemanager.util;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.service.EnactmentServiceProxy;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;

public class WlhlUtils {
	/**
	 * 检查是否处于工作流
	 * @param ctx
	 * @return
	 * @throws BOSException 
	 */
	public static boolean checkIsFlow(Context ctx,String billID) throws BOSException {
		IEnactmentService wfService =null;
		if(ctx!=null)
			wfService=EnactmentServiceProxy.getEnacementService(ctx);
		else
			wfService=EnactmentServiceFactory.createRemoteEnactService();
		ProcessInstInfo[] processInstances = wfService.getProcessInstanceByHoldedObjectId(billID);//不包括已经结束的流程
		//ProcessInstInfo[] processInstances = wfService.getAllProcessInstancesByBizobjId(billID);//包括已经结束的流程
		if(null != processInstances &&  processInstances.length > 0){//存在流程
			return true;
		}
		return false;
	}
}
