package com.kingdee.eas.custom.wlhllicensemanager.interfaces;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.framework.CoreBaseInfo;

/**
 * У��ӿ�
 * @author dai_andong
 *
 */
public interface IDynamicUpLoad {
	CoreBaseInfo dealBeforeSave(Context ctx,CoreBaseInfo info) throws BOSException,EASBizException;
	void dealAfterSave(Context ctx,CoreBaseInfo info) throws BOSException,EASBizException;
}