package com.kingdee.eas.custom.wlhllicensemanager.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import net.sf.json.JSONObject;
import sun.misc.BASE64Decoder;

import com.kingdee.bos.Context;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.eas.base.license.LicenseException;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.framework.client.EditUI;
import com.kingdee.util.NumericExceptionSubItem;

/**
 * 
 * 类说明：wlhl许可功能类
 * @author dai_andong
 * 2017-7-10
 */
public class WlhlLicenseUtil {
	private static HashMap<String,Integer> licenseOnLineMap = null;
	private static HashMap<String,String> modulesMap = null;
	
	/**
	 * 获取序列号
	 * @throws LicenseException 
	 * @throws EASBizException 
	 */
	public static void setLicenseMap(Context ctx) throws  EASBizException {
		if(licenseOnLineMap!=null) {
//			return;
		}
		String eas_home=System.getProperty("EAS_HOME");
		//当前class路径
        String jarPath = WlhlLicenseUtil.class.getResource("").getFile();
        System.out.println("jar/class路径："+jarPath);
        jarPath=jarPath.substring(1, jarPath.length())+"WlhlLicenseUtil.jar";
		String split=File.separator;
		String licensePath=eas_home+split+"server"+split+"properties"+split+"wlhlLicense.dat";
		try {
			File file=new File(licensePath);
			if(!file.exists()) {
				throw new EASBizException(new NumericExceptionSubItem("","License文件不存在！"));
			}

			String cpuID=getCPUID();
			
			FileInputStream in=new FileInputStream(file);
			byte[] b=new byte[1000];
			in.read(b);
			in.close();
			
			String snStr=new String(b).trim();
			String dd="";//MD5Util.getMD5String(getCPUID());
				
			snStr=AESDecode(dd, snStr);
			JSONObject snJo=JSONObject.fromObject(snStr);
			if(!snJo.containsKey("SNKEY")||!snJo.containsKey("JARMD5")) {
				throw new EASBizException(new NumericExceptionSubItem("","License格式不正确"));
			}
			if(!snJo.getString("SNKEY").equals(cpuID)) {
				throw new EASBizException(new NumericExceptionSubItem("","License无效(001)"));
			}
			cpuID="";//MD5Util.getFileMD5String(new File(jarPath));
			if(!snJo.getString("JARMD5").equals(cpuID)) {
				throw new EASBizException(new NumericExceptionSubItem("","License无效(002)"));
			}
			/*if(!snJo.containsKey("MODULES")) {
				throw new EASBizException(new NumericExceptionSubItem("","License无效(003)"));
			}
			modulesMap=new HashMap<String, String>();
			JSONArray moduleJA=snJo.getJSONArray("MODULES");
			for(int i=0;i<moduleJA.size();i++) {
				
			}*/
		} catch (Exception e) {
			throw new EASBizException(new NumericExceptionSubItem("",e.getMessage()));
		}
		licenseOnLineMap=new HashMap<String, Integer>();
	}
	
	/**
	 * 权限校验
	 * @throws EASBizException 
	 */
	public static void checkLicense(Context ctx,BOSObjectType bosType) throws EASBizException {
		setLicenseMap(ctx);
		/*String key=bosType.toString();
		if(!licenseOnLineMap.containsKey(key)) {//||licenseOnLineMap.get(key)<=0
			throw new EASBizException(new NumericExceptionSubItem("","模块的许可数目已经被分配完毕！"));
		}
		licenseOnLineMap.put(key,licenseOnLineMap.get(key)+1);*/
	}
	
	/**
	 * 释放许可
	 * @param ctx
	 * @param billName
	 * @param fuctionName
	 */
	public static void releaseLicense(Context ctx,BOSObjectType bosType) {
		String key=bosType.toString();
		licenseOnLineMap.put(key,licenseOnLineMap.get(key)-1);
	}
	/**
	 * 解密
	 * @param encodeRules
	 * @param content
	 * @return
	 */
	 private static String AESDecode(String encodeRules,String content){
        try {
            //1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator keygen=KeyGenerator.getInstance("AES");
            //2.根据ecnodeRules规则初始化密钥生成器
            //生成一个128位的随机源,根据传入的字节数组
            keygen.init(128, new SecureRandom(encodeRules.getBytes()));
              //3.产生原始对称密钥
            SecretKey original_key=keygen.generateKey();
              //4.获得原始对称密钥的字节数组
            byte [] raw=original_key.getEncoded();
            //5.根据字节数组生成AES密钥
            SecretKey key=new SecretKeySpec(raw, "AES");
              //6.根据指定算法AES自成密码器
            Cipher cipher=Cipher.getInstance("AES");
              //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.DECRYPT_MODE, key);
            //8.将加密并编码后的内容解码成字节数组
            byte [] byte_content= new BASE64Decoder().decodeBuffer(content);
            /*
             * 解密
             */
            byte [] byte_decode=cipher.doFinal(byte_content);
            String AES_decode=new String(byte_decode,"utf-8");
            return AES_decode;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        
        //如果有错就返加nulll
        return null;         
	 }
	 /**
	  * 获取cpu序列号
	  * @throws IOException
	  */
	 private static String getCPUID() throws IOException {
		 Process process = Runtime.getRuntime().exec(new String[] { "wmic", "cpu", "get", "ProcessorId" });
		 process.getOutputStream().close();
		 Scanner sc = new Scanner(process.getInputStream());
		 String property = sc.next();
		 String serial = sc.next();
		 process.getInputStream().close();
//		 System.out.println(property + ": " + serial);
		 return serial;
	 }

	/**
	 * 通過本機IP獲取本機MAC地址
	 * @throws SocketException
	 * @throws UnknownHostException
	 */
	private static String getLocalMac() throws SocketException, UnknownHostException {
		InetAddress ia = InetAddress.getLocalHost();
		// TODO Auto-generated method stub
		//获取网卡，获取地址
		byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
		StringBuffer sb = new StringBuffer("");
		for(int i=0; i<mac.length; i++) {
			if(i!=0) {
				sb.append("-");
			}
			//字节转换为整数
			int temp = mac[i]&0xff;
			String str = Integer.toHexString(temp);
			//System.out.println("每8位:"+str);
			if(str.length()==1) {
				sb.append("0");		
			}else {
				sb.append(str);
			}
		}
		//System.out.println("本机MAC地址:"+sb.toString().toUpperCase());
		return sb.toString().toUpperCase();
	}
}
