package com.nan.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class AboutView extends JFrame {

	private JPanel contentPane;

	public AboutView() {
		Init();
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);

		String str1 = "   此智能输液监控系统由创新实验室开发，" + "用于实时监测并控制远端点滴速度，"
				+ "点滴即将用完系统会发出警报提醒换瓶";
		String str2 = "1、点击“启动监控”，系统将会等待远端点滴系统的连接" + "\n"
				+ "2、通过点击“病房号”的单元格可查看和修改病患信息" + "\n"
				+ "3、通过点击“点滴速度”的单元格可修改点滴速度";
		JTextArea textArea = new JTextArea(str1);
		textArea.setFont(ServerView.myFont2);
		textArea.setLineWrap(true);// 设置自动换行
		tabbedPane.addTab("简介", null, textArea, null);

		JTextArea textArea2 = new JTextArea(str2);
		textArea2.setFont(ServerView.myFont2);
		textArea2.setLineWrap(true);// 设置自动换行
		tabbedPane.addTab("使用帮助", null, textArea2, null);
	}

	private void Init() {
		setSize(500, 350);
		setTitle("关于");
		setLocationRelativeTo(null);// 设置居中显示
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);// 关闭则销毁窗口
	}
}
