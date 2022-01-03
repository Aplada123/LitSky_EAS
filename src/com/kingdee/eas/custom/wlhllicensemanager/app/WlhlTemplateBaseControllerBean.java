package com.kingdee.eas.custom.wlhllicensemanager.app;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import com.kingdee.eas.custom.wlhllicensemanager.WlhlTemplateBaseCollection;
import com.kingdee.eas.custom.wlhllicensemanager.WlhlTemplateBaseInfo;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.custom.wlhllicensemanager.WlhlDataBaseCollection;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import java.lang.String;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.DataBaseCollection;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

public class WlhlTemplateBaseControllerBean extends AbstractWlhlTemplateBaseControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.custom.wlhllicensemanager.app.WlhlTemplateBaseControllerBean");

	@Override
	protected void _dynamicDelete(Context ctx, IObjectPK[] pks)
			throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected Object _dynamicExe(Context ctx, String functionName,
			JSONObject json) throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected IObjectPK[] _dynamicSaveBatch(Context ctx,
			AbstractObjectCollection coll) throws BOSException {
		// TODO Auto-generated method stub
		return null;
	}
}