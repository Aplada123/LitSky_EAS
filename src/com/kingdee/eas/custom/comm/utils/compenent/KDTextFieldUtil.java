package com.kingdee.eas.custom.comm.utils.compenent;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import com.kingdee.bos.ctrl.swing.KDTextField;

public class KDTextFieldUtil {

	private static int StringUpper = 1;
	private static int StringLower = 2;
	private static int StringRegex = 3;
	/**
	 * 设置KDTextField输入格式
	 * @param textField1
	 * @param regex
	 */
	public static void setInputModel(KDTextField textField1, int model,String regex) {
		// TODO Auto-generated method stub
		if(1 == StringUpper){
			textField1.setDocument(new KDTextFieldUtil().new UpperDocument());
		}else if(2 == StringLower){
			textField1.setDocument(new KDTextFieldUtil().new LowerDocument());
		}else if(3 == StringRegex){
			textField1.setDocument(new KDTextFieldUtil().new RegexDocument(regex));
		}
	}
	
	@SuppressWarnings("serial")
	class UpperDocument extends PlainDocument {
		//输入字符转化成大写
		@Override
		public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
			// TODO Auto-generated method stub
			if (str == null)
				return;
			char[] upper = str.toCharArray();
			for (int i = 0; i < upper.length; i++){
				upper[i] = Character.toUpperCase(upper[i]);
			}
			super.insertString(offs, new String(upper), a);
		}
	}
	class LowerDocument extends PlainDocument {
		private static final long serialVersionUID = 1L;

		@Override
		public void insertString(int offs, String str, AttributeSet a)
				throws BadLocationException {
			// TODO Auto-generated method stub
			if (str == null)
				return;
			char[] upper = str.toCharArray();
			for (int i = 0; i < upper.length; i++){
				upper[i] = Character.toLowerCase(upper[i]);
			}
			super.insertString(offs, new String(upper), a);
		}

	}
	class RegexDocument extends PlainDocument {

		/**
		 * 限制文本框输入内容为  正则表达式
		 */
		private static final long serialVersionUID = 1L;
		private String regex = null;

		public RegexDocument()
		{
			super();
		}

		public RegexDocument(String regex)
		{
			this();
			this.regex = regex;
		}
		@Override
		public void insertString(int offs, String str, AttributeSet a)
		throws BadLocationException {
			if (str == null){
				return;
			}
			if (regex != null){
				if (!new StringBuilder(getText(0, getLength())).insert(offs, str).toString().matches(regex)){
					return;
				}
				super.insertString(offs, str, a);
			}else{
				super.insertString(offs, str, a);
			}
		}
	}

}
