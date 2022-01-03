package com.kingdee.eas.custom.wlhllicensemanager.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.custom.wlhllicensemanager.util.WlhlLicenseUtil;

public class WlhlLicenseManagerControllerBean extends AbstractWlhlLicenseManagerControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.custom.wlhllicensemanager.app.WlhlLicenseManagerControllerBean");

	@Override
	protected void _checkLicense(Context ctx, BOSObjectType bosType) throws BOSException, EASBizException {
		WlhlLicenseUtil.checkLicense(ctx, bosType);
	}

	@Override
	protected void _releaseLicense(Context ctx, BOSObjectType bosType) throws BOSException, EASBizException  {
		WlhlLicenseUtil.releaseLicense(ctx, bosType);
	}
    
}