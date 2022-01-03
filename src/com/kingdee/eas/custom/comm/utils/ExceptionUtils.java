package com.kingdee.eas.custom.comm.utils;

import com.kingdee.eas.common.EASBizException;
import com.kingdee.util.NumericExceptionSubItem;


public class ExceptionUtils {

	/**
	 * 获取异常的位置1
	 * @param e
	 * @return
	 */
	public static String getExceptionLocation(Exception e) {
		// TODO Auto-generated method stub
		return e.getStackTrace()[0].getFileName() + "\n"+e.getStackTrace()[0].getClassName() + "\n"+e.getStackTrace()[0].getMethodName()+"\n"+e.getStackTrace()[0].getLineNumber();
	}
	/**
	 * 获取异常的位置 仅仅包含类名和行号
	 * @param e
	 * @return
	 */
	public static String getExceptionLocationSimple(Exception e) {
		// TODO Auto-generated method stub
		return e.getStackTrace()[0].getClassName() + "\n" + e.getStackTrace()[0].getLineNumber();
	}

	/**
	 * 获取抛出的异常文字
	 * @param e
	 * @return
	 */
	public static String getExceptionStatement(Exception e) {
		// TODO Auto-generated method stub
		return e.getCause() == null ? e.getMessage() : e.getCause().getMessage();
	}
	/**
	 * 抛出EAS业务异常
	 * @param remark
	 * @throws EASBizException
	 * @author HeMei XinXiBu
	 * @date 2020-7-30 上午09:08:09
	 * <p>Copyright: Copyright (c) 2020HeMeiJiTuan</p>
	 */
	public static void throwEasBizException(String remark) throws EASBizException{
		throw new EASBizException(new NumericExceptionSubItem("", remark));
	}

}
