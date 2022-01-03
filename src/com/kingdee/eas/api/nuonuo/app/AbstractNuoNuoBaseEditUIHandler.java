/**
 * output package name
 */
package com.kingdee.eas.api.nuonuo.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractNuoNuoBaseEditUIHandler extends com.kingdee.eas.custom.wlhllicensemanager.app.WlhlBaseEditUIHandler

{
	public void handleActionGetToken(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionGetToken(request,response,context);
	}
	protected void _handleActionGetToken(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}