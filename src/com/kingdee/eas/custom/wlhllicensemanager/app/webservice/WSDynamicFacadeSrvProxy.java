package com.kingdee.eas.custom.wlhllicensemanager.app.webservice;

import org.apache.axis.Message;

import org.apache.axis.MessageContext;

import org.apache.axis.message.SOAPEnvelope;

import org.apache.axis.message.SOAPHeaderElement;

import com.kingdee.bos.webservice.WSConfig;

import com.kingdee.bos.webservice.WSInvokeException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ObjectMultiPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.orm.core.ORMEngine;
import com.kingdee.bos.webservice.BeanConvertHelper;
import com.kingdee.bos.webservice.BOSTypeConvertor;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.webservice.WSConfig;
import com.kingdee.bos.webservice.MetaDataHelper;
import com.kingdee.bos.BOSObjectFactory;

public class WSDynamicFacadeSrvProxy { 

    public String uploadDataByBosType( String bosType , String jsonStr ) throws WSInvokeException {
        try {
            return getController().uploadDataByBosType(
            bosType,
            jsonStr);
        }
        catch( Throwable e ) {
            throw new WSInvokeException( e ) ;
        }
    }

    public String getDataByID( String jsonStr ) throws WSInvokeException {
        try {
            return getController().getDataByID(
            jsonStr);
        }
        catch( Throwable e ) {
            throw new WSInvokeException( e ) ;
        }
    }

    public String downloadBillList( String jsonStr ) throws WSInvokeException {
        try {
            return getController().downloadBillList(
            jsonStr);
        }
        catch( Throwable e ) {
            throw new WSInvokeException( e ) ;
        }
    }

    private com.kingdee.eas.custom.wlhllicensemanager.IDynamicFacade getController() {
        try {
        if (WSConfig.getRomoteLocate()!=null&&WSConfig.getRomoteLocate().equals("false")){
            Message message =MessageContext.getCurrentContext().getRequestMessage();
            SOAPEnvelope soap =message.getSOAPEnvelope();
            SOAPHeaderElement headerElement=soap.getHeaderByName(WSConfig.loginQName,WSConfig.loginSessionId);
            String SessionId=headerElement.getValue();
            return ( com.kingdee.eas.custom.wlhllicensemanager.IDynamicFacade )BOSObjectFactory.createBOSObject( SessionId , "com.kingdee.eas.custom.wlhllicensemanager.DynamicFacade") ; 
        } else {
            return ( com.kingdee.eas.custom.wlhllicensemanager.IDynamicFacade )BOSObjectFactory.createRemoteBOSObject( WSConfig.getSrvURL() , "com.kingdee.eas.custom.wlhllicensemanager.DynamicFacade" , com.kingdee.eas.custom.wlhllicensemanager.IDynamicFacade.class ) ; 
        }
        }
        catch( Throwable e ) {
            return ( com.kingdee.eas.custom.wlhllicensemanager.IDynamicFacade )ORMEngine.createRemoteObject( WSConfig.getSrvURL() , "com.kingdee.eas.custom.wlhllicensemanager.DynamicFacade" , com.kingdee.eas.custom.wlhllicensemanager.IDynamicFacade.class ) ; 
        }
    }

    private BeanConvertHelper getBeanConvertor() {
        return new BeanConvertHelper(); 
    }

}