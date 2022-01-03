package com.kingdee.eas.custom.wlhllicensemanager.util;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import org.apache.commons.lang.StringUtils;

import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.client.multiDetail.DetailPanel;

public class WlhlManagerClientUtils {
	/**
	 * 增加分录查询控件
	 */
	public static void addTableQuerySearchPanel(final KDTable table) {
		final KDTextField jt=new KDTextField();
		jt.setBounds(0,0, 150, 27);
		KDWorkButton jb=new KDWorkButton("查询");
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
	 * 查找分录值--行变色
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
		for(int rowIndex=0;rowIndex<table.getRowCount();rowIndex++) {
//			table.getRow(rowIndex).getStyleAttributes().setBackground(Color.white);
			table.getRow(rowIndex).getStyleAttributes().setHided(true);
			if(!isClear){
				for(int colIndex=0;colIndex<table.getColumnCount();colIndex++) {
					value=table.getValueAt(rowIndex, colIndex);
					if(value!=null) {
						if(value instanceof IObjectValue) {
							tempPrmt=null;
							if(table.getCell(rowIndex, colIndex).getEditor()!=null) {
								if(table.getCell(rowIndex, colIndex).getEditor().getComponent() instanceof KDBizPromptBox) {
									tempPrmt=(KDBizPromptBox) table.getCell(rowIndex, colIndex).getEditor().getComponent();
								}
							}else {
								if(table.getColumn(colIndex).getEditor().getComponent() instanceof KDBizPromptBox) {
									tempPrmt=(KDBizPromptBox) table.getColumn(colIndex).getEditor().getComponent();
								}
							}
							if(tempPrmt==null) {
								continue;
							}
							tempValue="";
							if(((IObjectValue) value).containsKey("number")) {
								if(((IObjectValue) value).get("number")!=null) {
									tempValue+=((IObjectValue) value).get("number").toString();
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
//							table.getRow(rowIndex).getStyleAttributes().setBackground(new Color(200,191,231));
							table.getRow(rowIndex).getStyleAttributes().setHided(false);
							break;
						}
					}
				}
			}else{
				table.getRow(rowIndex).getStyleAttributes().setHided(false);
			}
		}
	}
}
