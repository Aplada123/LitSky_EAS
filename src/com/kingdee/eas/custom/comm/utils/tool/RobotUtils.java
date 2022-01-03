package com.kingdee.eas.custom.comm.utils.tool;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditListener;
import com.kingdee.bos.ctrl.swing.KDButton;

public class RobotUtils {

	// shift+ 按键
	public static void keyPressWithShift(Robot r, int key) {
		//		不生效的解决方式
		//		Toolkit.getDefaultToolkit().setLockingKeyState(KeyEvent.VK_NUM_LOCK, false);

		r.keyPress(KeyEvent.VK_SHIFT);
		r.keyPress(key);
		r.keyRelease(key);
		r.keyRelease(KeyEvent.VK_SHIFT);
		//		延迟1s
		//		r.delay(1000);
	}

	// ctrl+ 按键
	public static void keyPressWithCtrl(Robot r, int key) {
		r.keyPress(KeyEvent.VK_CONTROL);
		r.keyPress(key);
		r.keyRelease(key);
		r.keyRelease(KeyEvent.VK_CONTROL);
	}

	// alt+ 按键
	public static void keyPressWithAlt(Robot r, int key) {
		r.keyPress(KeyEvent.VK_ALT);
		r.keyPress(key);
		r.keyRelease(key);
		r.keyRelease(KeyEvent.VK_ALT);
	}
	//ctrl+alt 按键
	public static void keyPressWithCtrlAlt(Robot r,int key){
		r.keyPress(KeyEvent.VK_CONTROL);
		r.keyPress(KeyEvent.VK_ALT);
		r.keyPress(key);
		r.keyRelease(key);
		r.keyPress(KeyEvent.VK_ALT);
		r.keyPress(KeyEvent.VK_CONTROL);
	}

	//shift+alt 按键
	public static void keyPressWithShiftAlt(Robot r,int key){
		r.keyPress(KeyEvent.VK_SHIFT);
		r.keyPress(KeyEvent.VK_ALT);
		r.keyPress(key);
		r.keyRelease(key);
		r.keyPress(KeyEvent.VK_ALT);
		r.keyPress(KeyEvent.VK_SHIFT);
	}

	//ctrl+shift 按键
	public static void keyPressWithCtrlShift(Robot r,int key){
		r.keyPress(KeyEvent.VK_CONTROL);
		r.keyPress(KeyEvent.VK_SHIFT);
		r.keyPress(key);
		r.keyRelease(key);
		r.keyPress(KeyEvent.VK_SHIFT);
		r.keyPress(KeyEvent.VK_CONTROL);
	}

	//打印出字符串
	public static void keyPressString(Robot r, String str){
		Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();//获取剪切板
		Transferable tText = new StringSelection(str);
		clip.setContents(tText, null); //设置剪切板内容
		keyPressWithCtrl(r, KeyEvent.VK_V);//粘贴
	}
	//单个 按键
	public static void keyPress(Robot r,int key){
		r.keyPress(key);
		r.keyRelease(key);
	}

	/**
	 * 按钮添加enter事件触发点击操作
	 * @param confirm
	 */
	public static void kdButtonAddEnterEvent(final KDButton confirm){
		confirm.addKeyListener(new KeyAdapter(){//设置enter事件  确定
			@SuppressWarnings("static-access")
			public void keyPressed(KeyEvent e){
				if(e.getKeyText(e.getKeyCode()).compareToIgnoreCase("Enter")==0){
					confirm.doClick();
				}
			}
		});
	}


	/**
	 * 分录模拟输入
	 * @param table
	 * @param oldValue
	 * @param newValue
	 * @param e
	 */
	public static void kdtTableInput(KDTable table,Object oldValue,Object newValue,KDTEditEvent e){
		KDTEditEvent kdte = new KDTEditEvent(table, oldValue,newValue , e.getRowIndex(), table.getColumnIndex("qty"), false, 1);  
		Object[] listeners = table.getListenerList().getListenerList();  
		for (int i = listeners.length - 2; i >= 0; i -= 2) {  
			if (listeners[i] == KDTEditListener.class) {  
				((KDTEditListener) listeners[(i + 1)]).editStopped(kdte);  
			}  
		}  
	}


	/**
	 * 获取键盘输入1
	 */
	public static void getKeyBoardInputContent() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("请输入你的姓名：");
		String name = scanner.nextLine();
		System.out.println("请输入你的年龄：");
		int age = scanner.nextInt();
		System.out.println("请输入你的工资：");
		float salary = scanner.nextFloat();
		System.out.println("你的信息如下：");
		System.out.println("姓名："+name+"\n"+"年龄："+age+"\n"+"工资："+salary);
		scanner.close();
	}

	/**
	 * 获取键盘输入2
	 * @throws IOException
	 */
	public static void getKeyBoardInputContent1() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str = null;
		System.out.println("Enter your value:");
		str = br.readLine();
		System.out.println("your value is :"+str);
	}
}
