package com.kingdee.eas.custom.wlhllicensemanager.interfaces;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.framework.CoreBaseInfo;
/**
 * 动态单据接口
 * 类说明：
 * @author dai_a
 * 2018-2-26
 */
public interface IDynamic {
	IObjectPK dynamicSave(Context ctx,CoreBaseInfo model) throws BOSException, EASBizException ;
}
