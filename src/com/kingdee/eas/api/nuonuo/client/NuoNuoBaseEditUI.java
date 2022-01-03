/**
 * output package name
 */
package com.kingdee.eas.api.nuonuo.client;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.api.nuonuo.NuoNuoFacadeFactory;
import com.kingdee.eas.api.nuonuo.utils.NuoNuoConstant;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.custom.comm.utils.tool.BrowserUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
@SuppressWarnings("serial")
public class NuoNuoBaseEditUI extends AbstractNuoNuoBaseEditUI
{

	/**
	 * output class constructor
	 */
	public NuoNuoBaseEditUI() throws Exception
	{
		super();
	}
	/**
	 * output loadFields method
	 */
	public void loadFields()
	{
		super.loadFields();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields()
	{
		super.storeFields();
	}

	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
	{
		return com.kingdee.eas.api.nuonuo.NuoNuoBaseFactory.getRemoteInstance();
	}

	@Override
	public void actionGetToken_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		if(StringUtils.isNotEmpty(txtToken.getText())){
			MsgBox.showInfo("不需要重新获取");
			SysUtil.abort();
		}
		MsgBox.showInfo("接下来将打开一个网址，授权登录后请在10分钟内将返回的新网址复制到授权网址框中，点击确认授权");
		StringBuffer url = new StringBuffer();
		url.append("https://open.nuonuo.com/authorize?")
		.append("appKey=").append(NuoNuoConstant.NuoNuoAppkey_Formal)
		.append("&response_type=code&redirect_uri=").append(NuoNuoConstant.NuoNuoAppRedirect_uri)
		.append("&state=").append("1123");// 此处1123是任意值
		String osName = System.getProperty("os.name", "");  
		if(osName.startsWith("Windows")){
			Desktop desktop = Desktop.getDesktop();
			if (Desktop.isDesktopSupported() && desktop.isSupported(Desktop.Action.BROWSE)) {
				URI uri = new URI(url.toString());
				desktop.browse(uri);
			}
		}else{
			System.out.println("Mac OS 执行下面代码，未必成功");
			BrowserUtils.openURL(url.toString());
		}
		super.actionGetToken_actionPerformed(e);
	}
	@Override
	public void onLoad() throws Exception {
		// TODO Auto-generated method stub
		//		https://www.baidu.com/?code=e8d5e88c04f8a58e5e41c031301884d0&state=1121&taxnum=91371621MA3QFMD94C
		btnConfirm.addMouseListener(new MouseAdapter(){

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				String url = txturl.getText();
				if(StringUtils.isEmpty(url)){
					MsgBox.showInfo("url不能为空");
					txturl.requestFocus();
					SysUtil.abort();
				}
				if(prmtcompany.getValue() == null){
					MsgBox.showInfo("公司不能为空");
					prmtcompany.requestFocus();
					SysUtil.abort();
				}
				CompanyOrgUnitInfo unitInfo = (CompanyOrgUnitInfo) prmtcompany.getValue();
				String result = "";
				try {
					result = NuoNuoFacadeFactory.getRemoteInstance().getISVToken(txturl.getText(), unitInfo.getString("id"));
				} catch (EASBizException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					MsgBox.showInfo(e1.getCause() == null ? e1.getMessage() : e1.getCause().getMessage());
					SysUtil.abort();
				} catch (BOSException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					MsgBox.showInfo(e1.getCause() == null ? e1.getMessage() : e1.getCause().getMessage());
					SysUtil.abort();
				}
				JSONObject json = JSONObject.parseObject(result);
				if(json.containsKey("access_token")){
					txtToken.setText(json.getString("access_token"));
				}else{
					MsgBox.showInfo(result);
				}
			}});
		super.onLoad();
	}

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData()
	{
		com.kingdee.eas.api.nuonuo.NuoNuoBaseInfo objectValue = new com.kingdee.eas.api.nuonuo.NuoNuoBaseInfo();
		objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUserInfo()));
		objectValue.setCompany(SysContext.getSysContext().getCurrentFIUnit());
		return objectValue;
	}

}