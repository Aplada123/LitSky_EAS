package com.kingdee.eas.custom.comm.utils.compenent;

import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.commons.lang.StringUtils;

import com.kingdee.bos.ctrl.swing.KDButton;
import com.kingdee.bos.ctrl.swing.KDFrame;
import com.kingdee.bos.ctrl.swing.KDLabel;
import com.kingdee.bos.ctrl.swing.KDProgressBar;
import com.kingdee.eas.util.client.MsgBox;

public class LDProgress implements Runnable{
	protected  final int MIN_PROGRESS = 0;
	protected  final int MAX_PROGRESS = 100;
	protected  int currentProgress = MIN_PROGRESS;
	protected KDFrame  KDFrame = null;
	protected String title = null;
	protected KDLabel kDLabel1 = null;
	protected KDLabel kDLabel2 = null;
	protected KDProgressBar kDProgressBar = null;
	protected KDButton btnBack = null;
	protected String tipsOne = null;
	protected String tipsTwo = null;
	protected boolean isSucceed = true;
	protected String errorStr = null;
	public LDProgress(){

	}
	public LDProgress(String title){
		this.title = title;
	}
	public LDProgress(String title, String tipsOne, String tipsTwo){
		this.title = title;
		this.tipsOne = tipsOne;
		this.tipsTwo = tipsTwo;
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		runnaleMethod();
	}
	/**
	 * Building a KDFrame, To display the schedule
	 * 
	 * @author HeMei Department
	 * @date 2021-4-19 下午03:57:39
	 * <p>Copyright: Copyright (c) 2021HeMei Group</p>
	 */
	public void runnaleMethod() {
		// TODO Auto-generated method stub
		KDFrame = new KDFrame(StringUtils.isEmpty(title) ? "进度提示" : title);
		KDFrame.setBounds(new Rectangle(10, 10, 420, 250));
		KDFrame.setLayout(null);
		KDFrame.setLocationRelativeTo(null);
		KDFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		// 创建一个进度条
		this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
		this.kDLabel2 = new com.kingdee.bos.ctrl.swing.KDLabel();
		this.kDProgressBar = new com.kingdee.bos.ctrl.swing.KDProgressBar();
		this.btnBack = new com.kingdee.bos.ctrl.swing.KDButton();
		this.kDLabel1.setName("kDLabel1");
		this.kDLabel2.setName("kDLabel2");
		this.kDProgressBar.setName("kDProgressBar");
		this.btnBack.setName("btnBack");
		this.kDLabel1.setText(StringUtils.isEmpty(tipsOne) ? "运行中···" : tipsOne);
		// kDLabel2        
		this.kDLabel2.setText(StringUtils.isEmpty(tipsTwo) ? "进度:" : tipsTwo);
		// kDProgressBar1
		// btnBack        
		this.btnBack.setText("后台进行");


		// 设置进度的 最小值 和 最大值
		kDProgressBar.setMinimum(MIN_PROGRESS);
		kDProgressBar.setMaximum(MAX_PROGRESS);

		// 设置当前进度值
		kDProgressBar.setValue(currentProgress);

		// 绘制百分比文本（进度条中间显示的百分数）
		kDProgressBar.setStringPainted(true);

		// 添加进度改变通知
		kDProgressBar.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent paramChangeEvent) {
				// TODO Auto-generated method stub
				System.out.println("当前进度值: " + kDProgressBar.getValue() + "; " +
						"进度百分比: " + kDProgressBar.getPercentComplete());
			}
		});
		this.btnBack.addMouseListener(new MouseAdapter(){

			public void mouseClicked(MouseEvent paramMouseEvent) {
				// TODO Auto-generated method stub
				super.mouseClicked(paramMouseEvent);
				KDFrame.toBack();
			}});

		kDLabel1.setBounds(new Rectangle(27, 23, 300, 19));
		KDFrame.add(kDLabel1, null);
		kDLabel2.setBounds(new Rectangle(25, 56, 300, 19));
		KDFrame.add(kDLabel2, null);
		kDProgressBar.setBounds(new Rectangle(25, 99, 350, 31));
		KDFrame.add(kDProgressBar, null);
		btnBack.setBounds(new Rectangle(225, 149, 145, 27));
		KDFrame.add(btnBack, null);
		KDFrame.setVisible(true);
		execute();
		afterExecute();
	}
	/**
	 * Inherit method in your code,so u can implement your logic code
	 * 
	 * @author HeMei Department
	 * @date 2021-4-19 下午03:55:30
	 * <p>Copyright: Copyright (c) 2021HeMei Group</p>
	 */
	public void execute() {
		// TODO Auto-generated method stub
	}
	/**
	 * After, Give tips to User
	 * 
	 * @author HeMei Department
	 * @date 2021-4-19 下午03:54:24
	 * <p>Copyright: Copyright (c) 2021HeMei Group</p>
	 */
	public void afterExecute() {
		// TODO Auto-generated method stub
		if(isSucceed){
			MsgBox.showInfo("执行完成");
			KDFrame.dispose();
		}else{
			MsgBox.showInfo(StringUtils.isEmpty(errorStr) ? "运行完成但有异常" : ("运行完成但有异常,异常信息：" + errorStr) );
			KDFrame.dispose();
		}
	}
	public int getCurrentProgress() {
		return currentProgress;
	}
	public void setCurrentProgress(int currentProgress) {
		this.currentProgress = currentProgress;
	}
	public KDFrame getKDFrame() {
		return KDFrame;
	}
	public void setKDFrame(KDFrame frame) {
		KDFrame = frame;
	}
	public KDLabel getKDLabel1() {
		return kDLabel1;
	}
	public void setKDLabel1(KDLabel label1) {
		kDLabel1 = label1;
	}
	public KDLabel getKDLabel2() {
		return kDLabel2;
	}
	public void setKDLabel2(KDLabel label2) {
		kDLabel2 = label2;
	}
	public KDProgressBar getKDProgressBar() {
		return kDProgressBar;
	}
	public void setKDProgressBar(KDProgressBar progressBar) {
		kDProgressBar = progressBar;
	}
	public KDButton getBtnBack() {
		return btnBack;
	}
	public void setBtnBack(KDButton btnBack) {
		this.btnBack = btnBack;
	}
	public int getMIN_PROGRESS() {
		return MIN_PROGRESS;
	}
	public int getMAX_PROGRESS() {
		return MAX_PROGRESS;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTipsOne() {
		return tipsOne;
	}
	public void setTipsOne(String tipsOne) {
		this.tipsOne = tipsOne;
		this.kDLabel1.setText(tipsOne);
	}
	public String getTipsTwo() {
		return tipsTwo;
	}
	public void setTipsTwo(String tipsTwo) {
		this.tipsTwo = tipsTwo;
		this.kDLabel2.setText(tipsTwo);
	}
	public boolean isSucceed() {
		return isSucceed;
	}
	public void setSucceed(boolean isSucceed) {
		this.isSucceed = isSucceed;
	}
	public String getErrorStr() {
		return errorStr;
	}
	public void setErrorStr(String errorStr) {
		this.errorStr = errorStr;
	}
	//Instance**
	//	LDProgress progress = new LDProgress(){
	//        @Override
	//        public void execute() {
	//            // TODO Auto-generated method stub
	//            for(int i = 0 ,size = 10; i <= size ;i++){
	//                kDProgressBar.setValue(i * 10);
	//                setTipsTwo(String.valueOf(i * 10));
	//                try {
	//                    Thread.sleep(500);
	//                } catch (InterruptedException e) {
	//                    // TODO Auto-generated catch block
	//                    e.printStackTrace();
	//                }
	//            }
	//            super.execute();
	//        }};
	//    new Thread(progress).start();
}
