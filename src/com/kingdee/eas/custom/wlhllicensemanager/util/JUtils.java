package com.kingdee.eas.custom.wlhllicensemanager.util;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.kingdee.bos.BOSException;

public class JUtils{
	private static final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	
	public static Date getDate(JSONObject json,String key) throws BOSException {
		try {
			if(json.containsKey(key)&&StringUtils.isNotBlank(json.getString(key))) {
				return sdf.parse(json.getString(key));
			}
		}catch(Exception err) {
			throw new BOSException("非法日期格式！");
		}
		return null;
	}
	
	public static Timestamp getTimestamp(JSONObject json,String key) throws BOSException {
		try {
			if(json.containsKey(key)&&StringUtils.isNotBlank(json.getString(key))) {
				return new Timestamp(getDate(json, key).getTime());
			}
		}catch(Exception err) {
			throw new BOSException("非法日期格式！");
		}
		return null;
	}
	
	public static BigDecimal getBigDecimal(JSONObject json,String key) throws BOSException {
		try {
			if(json.containsKey(key)&&StringUtils.isNotBlank(json.getString(key))) {
				return new BigDecimal(json.getString(key));
			}
		}catch(Exception err) {
			throw new BOSException("非法数值格式！");
		}
		return BigDecimal.ZERO;
	}
	
	public static String getString(JSONObject json,String key) throws BOSException {
		try {
			if(json.containsKey(key)&&StringUtils.isNotBlank(json.getString(key))) {
				return  json.getString(key);
			}
		}catch(Exception err) {
			throw new BOSException("非法字符格式！");
		}
		return "";
	}
	
	public static int getInt(JSONObject json,String key) throws BOSException {
		try {
			if(json.containsKey(key)&&StringUtils.isNotBlank(json.getString(key))) {
				return  json.getInt(key);
			}
		}catch(Exception err) {
			throw new BOSException("非法数字格式！");
		}
		return 0;
	}
	public static Boolean getBoolean(JSONObject json,String key) throws BOSException {
		try {
			if(json.containsKey(key)&&StringUtils.isNotBlank(json.getString(key))) {
				return Boolean.valueOf(json.getString(key));
			}
		}catch(Exception err) {
			throw new BOSException("非法数值格式！");
		}
		return false;
	}
}
