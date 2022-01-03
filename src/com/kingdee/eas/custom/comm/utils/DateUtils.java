package com.kingdee.eas.custom.comm.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.basedata.assistant.IPeriod;
import com.kingdee.eas.basedata.assistant.PeriodFactory;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.util.NumericExceptionSubItem;

public class DateUtils {
	/**
	 * 获取两个日期相差的天数
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static int getDiffBetweenTwoDays(Date beginDate,Date endDate){
		Calendar calbegin = Calendar.getInstance();
		Calendar calend = Calendar.getInstance();
		calbegin.setTime(beginDate);
		calend.setTime(endDate);		
		return Integer.parseInt(String.valueOf((calend.getTimeInMillis() - calbegin.getTimeInMillis()) / (24*3600*1000)));		
	}


	/**
	 * 字符串转日期
	 * @param str
	 * @return
	 * @throws EASBizException
	 */
	public static Date getDateByString(String str) throws EASBizException{
		try {
			return StringUtils.isEmpty(str) ? null : (new SimpleDateFormat("yyyy-MM-dd")).parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new EASBizException(new NumericExceptionSubItem("001","字符串转日期错误"));
		}
	}

	/**
	 * 获得某个期间的上一个期间
	 * @param periodInfo
	 * @throws BOSException 
	 */
	public static PeriodInfo getUpperPeriodInfo(PeriodInfo periodInfo) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(periodInfo == null){
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(periodInfo.getBeginDate());
		cal.add(Calendar.DATE, -1);
		Date lastDateOfUpperPeriod = cal.getTime();
		CoreBaseCollection coll;
		PeriodInfo fiscalPeriodInfo = null;
		try {
			coll = PeriodFactory.getRemoteInstance().getCollection(" where beginDate<{ts '" + sdf.format(lastDateOfUpperPeriod) + "'} and endDate>={ts '" + sdf.format(lastDateOfUpperPeriod) + "'}");
			if(coll.size() == 0){
				return null;
			}
			fiscalPeriodInfo = (PeriodInfo)coll.get(0);
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fiscalPeriodInfo;
	}

	/**
	 * 获得某个期间的上一个期间2
	 * @param periodInfo
	 * @throws BOSException 
	 */
	public static PeriodInfo getUpperPeriodInfo(Context ctx,PeriodInfo periodInfo) throws BOSException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(periodInfo == null){
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(periodInfo.getBeginDate());
		cal.add(Calendar.DATE, -1);
		Date lastDateOfUpperPeriod = cal.getTime();
		CoreBaseCollection coll;
		if(ctx == null){
			coll = PeriodFactory.getRemoteInstance().getCollection(" where beginDate<{ts '"+sdf.format(lastDateOfUpperPeriod)+"'} and endDate>={ts '"+sdf.format(lastDateOfUpperPeriod)+"'}");
		}else{
			coll = PeriodFactory.getLocalInstance(ctx).getCollection(" where beginDate<{ts '"+sdf.format(lastDateOfUpperPeriod)+"'} and endDate>={ts '"+sdf.format(lastDateOfUpperPeriod)+"'}");
		}
		return coll.size() == 0 ? null : (PeriodInfo)coll.get(0);
	}

	/**
	 * 获得当前会计期间
	 * @param periodInfo
	 * @throws BOSException 
	 */
	public static PeriodInfo getCurrentPeriodInfo() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		CoreBaseCollection coll;
		try {
			coll = PeriodFactory.getRemoteInstance().getCollection("where number='" + sdf.format(new Date()) + "'");
			return coll.size() == 0 ? null : (PeriodInfo)coll.get(0);
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获得当前会计期间
	 * @param periodInfo
	 * @throws BOSException 
	 */
	public static PeriodInfo getCurrentPeriodInfo(Context ctx) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		CoreBaseCollection coll;
		try {
			IPeriod iPeriod;
			if(ctx != null){
				iPeriod = PeriodFactory.getLocalInstance(ctx);
			}else{
				iPeriod = PeriodFactory.getRemoteInstance();
			}
			coll = iPeriod.getCollection("where number = '" + sdf.format(new Date()) + "'");
			return coll.size() == 0 ? null :(PeriodInfo)coll.get(0);
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * if the year we got is a leap year,return true ,otherwise return false
	 * 闰年判断         うるう年             
	 * @param year
	 * @return
	 */
	public static boolean isLeapYear(int year){
		if(year%100 == 0){
			if(year%400 == 0){
				return true;
			}else{
				return false;
			}
		}
		if(year % 4 == 0){
			return true;
		}
		return false;
	}
	/**
	 * 清除日期的时分秒
	 * @param date
	 * @return
	 */
	public static Date clearDateHMS(Date date){
		if(date == null){
			return null;
		}
		try {
			return (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")).parse((new SimpleDateFormat("yyyy-MM-dd")).format(date)+" 00:00:00");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 日期增加天数
	 * @param endDate
	 * @param days 
	 * @return
	 */
	public static Date addDays(Date date, int days) {
		// TODO Auto-generated method stub
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}

	/**
	 * 获得某月的第一天
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfMonth(Date date) {
		// TODO Auto-generated method stub
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}

	/**
	 * 获得某个期间的下一个期间
	 * @param periodInfo
	 * @throws BOSException 
	 */
	public static PeriodInfo getNextPeriodInfo(Context ctx,PeriodInfo periodInfo) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(periodInfo == null){
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(periodInfo.getEndDate());
		cal.add(Calendar.DATE, +1);
		Date lastDateOfUpperPeriod = cal.getTime();
		CoreBaseCollection coll;
		PeriodInfo fiscalPeriodInfo = null;
		try {
			if(ctx == null){
				coll = PeriodFactory.getRemoteInstance().getCollection(" where beginDate<={ts '"+sdf.format(lastDateOfUpperPeriod)+"'} and endDate>={ts '"+sdf.format(lastDateOfUpperPeriod)+"'}");
			}else{
				coll = PeriodFactory.getLocalInstance(ctx).getCollection(" where beginDate<={ts '"+sdf.format(lastDateOfUpperPeriod)+"'} and endDate>={ts '"+sdf.format(lastDateOfUpperPeriod)+"'}");
			}
			return coll.size() == 0 ? null : (PeriodInfo)coll.get(0);
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fiscalPeriodInfo;
	}


	/**
	 * 获得某个期间的下N个期间
	 * @param periodInfo
	 * @throws BOSException 
	 */
	public static PeriodInfo getPeriodInfo(Context ctx,PeriodInfo periodInfo,int diff) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(periodInfo==null)
			return null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(periodInfo.getEndDate());
		cal.add(Calendar.MONTH, diff);
		Date lastDateOfUpperPeriod=cal.getTime();
		CoreBaseCollection coll;
		PeriodInfo fiscalPeriodInfo = null;
		try {
			if(ctx == null){
				coll = PeriodFactory.getRemoteInstance().getCollection(" where beginDate<={ts '"+sdf.format(lastDateOfUpperPeriod)+"'} and endDate>={ts '"+sdf.format(lastDateOfUpperPeriod)+"'}");
			}else{
				coll = PeriodFactory.getLocalInstance(ctx).getCollection(" where beginDate<={ts '"+sdf.format(lastDateOfUpperPeriod)+"'} and endDate>={ts '"+sdf.format(lastDateOfUpperPeriod)+"'}");
			}
			return coll.size() == 0 ? null : (PeriodInfo)coll.get(0);
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fiscalPeriodInfo;
	}
	public static PeriodInfo getPeriodInfoByDate(Context ctx, Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		CoreBaseCollection coll;
		try {
			if (ctx == null) {
				coll = PeriodFactory.getRemoteInstance().getCollection(
						"where BeginDate <='" + sdf.format(date) + "' and EndDate >='" + sdf.format(date)+"'");
			} else {
				coll = PeriodFactory.getLocalInstance(ctx).getCollection(
						"where BeginDate <='" + sdf.format(date) + "' and EndDate >='" + sdf.format(date)+"'");
			}
			return coll.size() == 0 ? null : (PeriodInfo) coll.get(0);
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
