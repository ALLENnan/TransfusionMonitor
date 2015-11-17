package com.nan.view;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

//为那些已用完点滴的一行标志颜色
public class MyTableCellRenderrer extends DefaultTableCellRenderer {

	private int clientRaw;
	private boolean color;

	public MyTableCellRenderrer(int clientRaw, boolean color) {
		this.clientRaw = clientRaw;
		this.color = color;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		Component comp = super.getTableCellRendererComponent(table, value,
				isSelected, hasFocus, row, column);
		// 隔行换色
		if (row == clientRaw) {
			if (color == false)
				comp.setBackground(Color.ORANGE);// 将要用完橙色
			else
				comp.setBackground(Color.RED);// 已用完设为红色
		}
		return comp;
	}
}
