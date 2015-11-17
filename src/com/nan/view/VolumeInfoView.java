package com.nan.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.nan.model.ClientData;

public class VolumeInfoView extends JFrame {

	private JPanel contentPane;
	private JTextArea mTextArea;
	private JScrollPane mTextAreaScroll;

	private ClientData mClientData;

	public VolumeInfoView(ClientData mClientData) {
		this.mClientData = mClientData;
		init();
		setSize(500, 350);
		setTitle("容量规格");
		setLocationRelativeTo(null);// 设置居中显示
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);// 关闭则销毁窗口
	}

	private void init() {

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		Border title = BorderFactory.createTitledBorder(ServerView.border,
				"点滴信息", TitledBorder.LEFT, TitledBorder.TOP, new Font("微软雅黑",
						Font.ITALIC, 16), Color.black);
		mTextArea = new JTextArea(8, 0);
		mTextAreaScroll = new JScrollPane(mTextArea);
		mTextAreaScroll
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);// 设置垂直滚动条
		mTextArea.setLineWrap(true);// 设置自动换行
		mTextArea.setFont(ServerView.myFont3);
		mTextAreaScroll.setBorder(ServerView.border);
		mTextAreaScroll.setBorder(title);

		contentPane.add(mTextAreaScroll);
		showMes();
	}

	private void showMes() {
		String ip = "病房号: " + mClientData.getClientIp();
		String mes1 = "瓶数： " + mClientData.getNum();
		int[] volume = mClientData.getVolume();
		String volumesString="";
		for(int i=0;i<volume.length;i++)
		{
			volumesString=volumesString+'\n'+volume[i];
		}
		String mes2 = "每瓶容量分别为: " +volumesString;
		mTextArea.setText(ip+'\n'+mes1+'\n'+mes2);
	}
}