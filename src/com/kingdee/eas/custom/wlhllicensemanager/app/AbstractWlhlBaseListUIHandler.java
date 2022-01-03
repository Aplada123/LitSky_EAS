/**
 * output package name
 */
package com.kingdee.eas.custom.wlhllicensemanager.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractWlhlBaseListUIHandler extends com.kingdee.eas.framework.app.ListUIHandler

{
	public void handleactionApprove(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionApprove(request,response,context);
	}
	protected void _handleactionApprove(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleactionUnApprove(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionUnApprove(request,response,context);
	}
	protected void _handleactionUnApprove(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}