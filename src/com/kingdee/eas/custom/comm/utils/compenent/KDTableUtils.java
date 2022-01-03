package com.kingdee.eas.custom.comm.utils.compenent;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import org.apache.commons.lang.StringUtils;

import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditListener;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.eas.framework.client.multiDetail.DetailPanel;

public class KDTableUtils {
	/**
	 * 模拟表格修改字段值事件
	 * @param table
	 * @param oldValue
	 * @param newValue
	 * @param rowIndex
	 * @param colIndex
	 * @author HeMei XinXiBu
	 * @date 2020-8-13 上午08:34:12
	 * <p>Copyright: Copyright (c) 2020HeMeiJiTuan</p>
	 */
	public static void robotKdTableEditStopEvent(KDTable table, Object oldValue, Object newValue, int rowIndex, int colIndex){
		table.getCell(rowIndex, colIndex).setValue(newValue);
		KDTEditEvent kdte = new KDTEditEvent(table, oldValue, newValue, rowIndex, colIndex, false, 1);  
		Object[] listeners = table.getListenerList().getListenerList();  
		for (int i = listeners.length - 2; i >= 0; i -= 2) {  
			if (listeners[i] == KDTEditListener.class) {  
				((KDTEditListener) listeners[(i + 1)]).editStopped(kdte);  
			}  
		}  
	}
	/**
	 * 增加分录按钮控件
	 */
	public static void addTableWorkButtonPanel(final KDTable table, final KDWorkButton btn) {
		btn.setBounds(152,0,80, 27);
		JPanel controlPanel = (JPanel) table.getParent().getParent();
		if(controlPanel instanceof DetailPanel ){
			for(int index=0;index<controlPanel.getComponentCount();index++) {
				if(controlPanel.getComponent(index).getName().equalsIgnoreCase("controlPanel")) {
					JPanel  d = (JPanel )controlPanel.getComponent(index);
					Rectangle rect = table.getBounds();
					int x = rect.width - (btn.getWidth() + 86 + 30);
					d.add(btn,new com.kingdee.bos.ctrl.swing.KDLayout.Constraints(x, 5, btn.getWidth(), 19, 9));
					break;
				}
			}
		}
	}

	/**
	 * 增加分录查询*按钮*控件
	 * 不接收enter键事件
	 */
	public static void addTableQuerySearchPanel(final KDTable table) {
		final KDTextField jt=new KDTextField();
		jt.setBounds(0,0, 150, 27);
		KDWorkButton jb = new KDWorkButton("查询");
		jb.setBounds(152,0,48, 27);
		jb.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				findTableRowValue(table,jt.getText());
			}});

		JPanel controlPanel = (JPanel) table.getParent().getParent();
		if(controlPanel instanceof DetailPanel ){
			for(int index=0;index<controlPanel.getComponentCount();index++) {
				if(controlPanel.getComponent(index).getName().equalsIgnoreCase("controlPanel")) {
					JPanel  d = (JPanel )controlPanel.getComponent(index);
					Rectangle rect = table.getBounds();
					int x = rect.width - (jb.getWidth() + 86 + 30);
					d.add(jb,new com.kingdee.bos.ctrl.swing.KDLayout.Constraints(x, 5, jb.getWidth(), 19, 9));
					d.add(jt,new com.kingdee.bos.ctrl.swing.KDLayout.Constraints(x-152, 5, jt.getWidth(), 19, 9));
					break;
				}
			}
		}
	}

	/**
	 * 增加分录查询*按钮*控件
	 * 接收Enter键事件
	 */
	@SuppressWarnings("serial")
	public static void addTableWorkButtonAndTxtFieldPanel(final KDTable table, final KDWorkButton btn) {
		btn.setBounds(152,0,80, 27);
		final KDTextField jt = new KDTextField();
		jt.setBounds(0,0, 150, 27);
		final KDWorkButton jb = new KDWorkButton("查询");
		jb.setBounds(152,0,48, 27);
		jb.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				findTableRowValue(table,jt.getText());
			}});

		KeyStroke theEnter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0);       
		ActionMap contentActionMap = jt.getActionMap();
		InputMap  contentInputMap  = jt.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);       
		contentInputMap.put(theEnter,"Kind_of_Input");
		contentActionMap.put("Kind_of_Input",new AbstractAction(){
			public void actionPerformed(ActionEvent e){
				findTableRowValue(table,jt.getText());
			}
		});
		jt.setFocusTraversalKeysEnabled(false);

		JPanel controlPanel = (JPanel) table.getParent().getParent();
		if(controlPanel instanceof DetailPanel ){
			for(int index=0;index<controlPanel.getComponentCount();index++) {
				if(controlPanel.getComponent(index).getName().equalsIgnoreCase("controlPanel")) {
					JPanel  d = (JPanel )controlPanel.getComponent(index);
					Rectangle rect = table.getBounds();
					int x = rect.width - (btn.getWidth() + 86 + 30);
					d.add(btn,new com.kingdee.bos.ctrl.swing.KDLayout.Constraints(x, 5, btn.getWidth(), 19, 9));
					d.add(jb,new com.kingdee.bos.ctrl.swing.KDLayout.Constraints(x - btn.getWidth(), 5, jb.getWidth(), 19, 9));
					d.add(jt,new com.kingdee.bos.ctrl.swing.KDLayout.Constraints(x - btn.getWidth()-152, 5 , jt.getWidth(), 19, 9));
					break;
				}
			}
		}
	}

	/**
	 * 增加分录按钮控件（多个按钮，一个查询）
	 * 分录按钮任意
	 * 含一个查询框，查询框可以使用Enter键
	 * @param wbtnWidth 按钮宽度，为0则默认90
	 */
	@SuppressWarnings("serial")
	public static void addTableWorkButtonAndTxtFieldPanel(final KDTable table, final ArrayList<KDWorkButton> btns, int wbtnWidth) {
		int btnWidth = (wbtnWidth == 0 ? 90 : wbtnWidth);
		for (int i = 0, size = btns.size(); i < size; i++) {
			btns.get(i).setBounds(152, 0, btnWidth, 27);
		}
		final KDTextField jt = new KDTextField();
		jt.setBounds(0,0, 150, 27);
		final KDWorkButton jb = new KDWorkButton("查询");
		jb.setBounds(152,0, 48, 27);
		jb.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				findTableRowValue(table,jt.getText());
			}});

		KeyStroke theEnter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0);       
		ActionMap contentActionMap = jt.getActionMap();
		InputMap  contentInputMap  = jt.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);       
		contentInputMap.put(theEnter,"Kind_of_Input");
		contentActionMap.put("Kind_of_Input",new AbstractAction(){
			public void actionPerformed(ActionEvent e){
				findTableRowValue(table,jt.getText());
			}
		});
		jt.setFocusTraversalKeysEnabled(false);

		JPanel controlPanel = (JPanel) table.getParent().getParent();
		if(controlPanel instanceof DetailPanel ){
			for(int index=0;index<controlPanel.getComponentCount();index++) {
				if(controlPanel.getComponent(index).getName().equalsIgnoreCase("controlPanel")) {
					JPanel  d = (JPanel )controlPanel.getComponent(index);
					Rectangle rect = table.getBounds();
					int x = rect.width - (80 + 86 + 30);
					for (int i = 0, size = btns.size(); i < size; i++) {
						d.add(btns.get(i), new com.kingdee.bos.ctrl.swing.KDLayout.Constraints(x - (btnWidth+5)*i, 5, btnWidth, 19, 9));
					}
					d.add(jb, new com.kingdee.bos.ctrl.swing.KDLayout.Constraints(x - btnWidth*btns.size(), 5, jb.getWidth(), 19, 9));
					d.add(jt, new com.kingdee.bos.ctrl.swing.KDLayout.Constraints(x - btnWidth*btns.size() - 152, 5 , jt.getWidth(), 19, 9));
					break;
				}
			}
		}
	}
	/**
	 * 查找分录值--行隐藏
	 * @param table
	 * @param valueStr
	 */
	public static void findTableRowValue(KDTable table,String valueStr) {
		boolean isClear = false;
		if(StringUtils.isBlank(valueStr)) {
			isClear = true;
		}
		Object value;
		String tempValue;
		KDBizPromptBox tempPrmt;
		for(int rowIndex = 0; rowIndex < table.getRowCount(); rowIndex++) {
			table.getRow(rowIndex).getStyleAttributes().setHided(true);
			if(!isClear){
				for(int colIndex = 0; colIndex < table.getColumnCount(); colIndex++) {
					value = table.getValueAt(rowIndex, colIndex);
					if(value != null) {
						if(value instanceof IObjectValue) {
							tempPrmt = null;
							if(table.getCell(rowIndex, colIndex).getEditor() != null) {
								if(table.getCell(rowIndex, colIndex).getEditor().getComponent() instanceof KDBizPromptBox) {
									tempPrmt=(KDBizPromptBox) table.getCell(rowIndex, colIndex).getEditor().getComponent();
								}
							}else {
								if(table.getColumn(colIndex).getEditor().getComponent() instanceof KDBizPromptBox) {
									tempPrmt=(KDBizPromptBox) table.getColumn(colIndex).getEditor().getComponent();
								}
							}
							if(tempPrmt == null) {
								continue;
							}
							tempValue = "";
							if(((IObjectValue) value).containsKey("number")) {
								if(((IObjectValue) value).get("number") != null) {
									tempValue += ((IObjectValue) value).get("number").toString();
								}
							}
							if(((IObjectValue) value).containsKey("name")) {
								if(((IObjectValue) value).get("name")!=null) {
									tempValue+="|"+((IObjectValue) value).get("name").toString();
								}
							}
						}else if(value instanceof com.kingdee.util.enums.Enum) {
							value=((com.kingdee.util.enums.Enum)value).getAlias();
						}
						if(value.toString().contains(valueStr)){
							table.getRow(rowIndex).getStyleAttributes().setHided(false);
							//							table.getRow(rowIndex).getStyleAttributes().setBackground(new Color(200,191,231));
							break;
						}
					}
				}
			}else{
				table.getRow(rowIndex).getStyleAttributes().setHided(false);
			}
		}
	}

	/**
	 * KDTable EXCEL SumProduct  获取总数
	 * @param table
	 * @param colKeys
	 * @param colkey
	 * @param beginRowIndex
	 * @param endRowIndex
	 * @return
	 * @author HeMei Department
	 * @date 2020-12-23 上午11:31:41
	 * <p>Copyright: Copyright (c) 2020HeMei Group</p>
	 */
	public static BigDecimal[] excelFuncSumProduct(KDTable table,String[] colKeys, String colkey, int beginRowIndex, int endRowIndex){
		BigDecimal[] result = new BigDecimal[colKeys.length];
		for(int rowIndex = beginRowIndex; rowIndex < endRowIndex; rowIndex++){
			for(int i = 0, size = colKeys.length; i < size; i++){
				result[i] = result[i].add(UIRuleUtil.getBigDecimal(table.getCell(rowIndex, colKeys[i]).getValue()).multiply(UIRuleUtil.getBigDecimal(table.getCell(rowIndex, colkey).getValue())));
			}
		}
		return result;
	}

	/**
	 * KDTable EXCEL SumProduct 然后除以总数获得加权平均
	 * @param table
	 * @param colKeys
	 * @param colkey
	 * @param beginRowIndex
	 * @param endRowIndex
	 * @return
	 * @author HeMei Department
	 * @date 2020-12-23 上午11:31:41
	 * <p>Copyright: Copyright (c) 2020HeMei Group</p>
	 */
	public static BigDecimal[] excelFuncSumProductDividor(KDTable table,String[] colKeys, String colkey, int beginRowIndex, int endRowIndex, int precious){
		BigDecimal[] result = new BigDecimal[colKeys.length];
		BigDecimal colkeyQty = BigDecimal.ZERO;
		if(table.getRowCount() == 0){
			return null;
		}
		for(int rowIndex = beginRowIndex; rowIndex < endRowIndex; rowIndex++){
			for(int i = 0, size = colKeys.length; i < size; i++){
				result[i] = UIRuleUtil.getBigDecimal(result[i]).add(UIRuleUtil.getBigDecimal(table.getCell(rowIndex, colKeys[i]).getValue()).multiply(UIRuleUtil.getBigDecimal(table.getCell(rowIndex, colkey).getValue())));
			}
			colkeyQty = colkeyQty.add(UIRuleUtil.getBigDecimal(table.getCell(rowIndex, colkey).getValue()));
		}
		for(int i = 0, size = colKeys.length; i < size; i++){
			result[i] = UIRuleUtil.getBigDecimal(table.getCell(i, colKeys[i]).getValue()).divide(UIRuleUtil.getBigDecimal(table.getCell(i, colkey).getValue()), precious, 4);
		}
		return result;
	}
	/**
	 * 计算加权平均然后表格赋值
	 * @param table
	 * @param colKeys
	 * @param colkey
	 * @param beginRowIndex
	 * @param endRowIndex
	 * @param precious
	 * @param row
	 * @author HeMei Department
	 * @date 2020-12-23 上午11:39:09
	 * <p>Copyright: Copyright (c) 2020HeMei Group</p>
	 */
	public static void excelFuncSumProductDividorAndFillTable(KDTable table,String[] colKeys, String colkey, int beginRowIndex, int endRowIndex, int precious, IRow row){
		BigDecimal[] result = new BigDecimal[colKeys.length];
		BigDecimal colkeyQty = BigDecimal.ZERO;
		if(table.getRowCount() == 0){
			return;
		}
		for(int rowIndex = beginRowIndex; rowIndex <= endRowIndex; rowIndex++){
			for(int i = 0, size = colKeys.length; i < size; i++){
				result[i] = UIRuleUtil.getBigDecimal(result[i]).add(UIRuleUtil.getBigDecimal(table.getCell(rowIndex, colKeys[i]).getValue()).multiply(UIRuleUtil.getBigDecimal(table.getCell(rowIndex, colkey).getValue())));
			}
			colkeyQty = colkeyQty.add(UIRuleUtil.getBigDecimal(table.getCell(rowIndex, colkey).getValue()));
		}
		for(int i = 0, size = colKeys.length; i < size; i++){
			result[i] = colkeyQty.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : UIRuleUtil.getBigDecimal(result[i]).divide(colkeyQty, precious, 4);
			row.getCell(colKeys[i]).setValue(result[i]);
		}
	}

}
