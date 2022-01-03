package com.kingdee.eas.custom.wlhllicensemanager;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import java.lang.String;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.*;

public interface IWlhlTemplateBase extends IWlhlDataBase
{
    public WlhlTemplateBaseInfo getWlhlTemplateBaseInfo(IObjectPK pk) throws BOSException, EASBizException;
    public WlhlTemplateBaseInfo getWlhlTemplateBaseInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public WlhlTemplateBaseInfo getWlhlTemplateBaseInfo(String oql) throws BOSException, EASBizException;
    public WlhlTemplateBaseCollection getWlhlTemplateBaseCollection() throws BOSException;
    public WlhlTemplateBaseCollection getWlhlTemplateBaseCollection(EntityViewInfo view) throws BOSException;
    public WlhlTemplateBaseCollection getWlhlTemplateBaseCollection(String oql) throws BOSException;
}