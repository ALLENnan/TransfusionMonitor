package com.nan.view;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.nan.view.ClientDataView.myListener;

public class RecordInfoView extends JFrame {

	private JPanel contentPane;
	private JTextField ip;
	private JTextField bottle_num;
	private JTextArea volumes;
	private JScrollPane mTextAreaScroll;
	private JLabel showRecordNum;
	private JButton mButton;

	public RecordInfoView() {
		init();
		setSize(350, 360);
		setTitle("病人点滴信息录入");
		setLocationRelativeTo(null);// 设置居中显示
		this.setResizable(false);// 禁用最大化
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);// 关闭则销毁窗口
	}

	private void init() {
		// TODO Auto-generated method stub
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new FlowLayout(FlowLayout.RIGHT, 60, 20));
		setContentPane(contentPane);

		initPanel();
	}

	private void initPanel() {
		// TODO Auto-generated method stub
		JPanel mJPanel1 = new JPanel();
		JLabel mJLabel1 = new JLabel("病房号：  ");
		ip = new JTextField(14);
		mJLabel1.setFont(ServerView.myFont1);
		ip.setFont(ServerView.myFont3);
		mJPanel1.add(mJLabel1, BorderLayout.WEST);
		mJPanel1.add(ip, BorderLayout.CENTER);

		JPanel mJPanel2 = new JPanel();
		JLabel mJLabel2 = new JLabel("瓶数：    ");
		bottle_num = new JTextField(14);
		mJLabel2.setFont(ServerView.myFont1);
		bottle_num.setFont(ServerView.myFont3);
		mJPanel2.add(mJLabel2, BorderLayout.WEST);
		mJPanel2.add(bottle_num, BorderLayout.CENTER);

		JPanel mJPanel3 = new JPanel();
		JLabel mJLabel3 = new JLabel(("<html>每瓶容量:<br>(以空格隔开)</html>"));
		volumes = new JTextArea(5, 0);
		mTextAreaScroll = new JScrollPane(volumes);
		mTextAreaScroll
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);// 设置垂直滚动条
		volumes.setLineWrap(true);// 设置自动换行
		mJLabel3.setFont(ServerView.myFont1);
		volumes.setFont(ServerView.myFont3);
		mJPanel3.add(mJLabel3, BorderLayout.WEST);
		mJPanel3.add(mTextAreaScroll, BorderLayout.CENTER);

		int num = ServerView.record_num;// 取出静态数据
		showRecordNum = new JLabel("已录入：" + num);

		mButton = new JButton("录入");
		mButton.setFont(ServerView.myFont3);

		contentPane.add(mJPanel1);
		contentPane.add(mJPanel2);
		contentPane.add(mJPanel3);
		contentPane.add(showRecordNum);
		contentPane.add(mButton);

		setListeners();

	}

	private void setListeners() {

		mButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				// 从view中取出数据
				String ipString = ip.getText().toString().trim();
				String bottle_numString = bottle_num.getText().toString()
						.trim();
				String volumesString = volumes.getText().toString().replaceAll(" ","\r\n");;
				// 存储数据
				String filePath = "E:" + File.separator + "temp";
				File file = new File(filePath);
				// 文件夹不存在则新建
				if (!file.exists()) {
					file.mkdirs();
				}
				try {
					PrintWriter pw = new PrintWriter(filePath + File.separator
							+ ipString);// 根据病房号ip新建txt文件
					pw.write(bottle_numString + "\r\n");
					 
					pw.write(volumesString);
					pw.flush();
					pw.close();

				} catch (IOException e1) {
					e1.printStackTrace();
				}

				// 已录入病房号数目加1
				ServerView.record_num++;
				showRecordNum.setText("已录入：" + ServerView.record_num);
				ip.setText("");
				bottle_num.setText("");
				volumes.setText("");

			}
		});
	}

}
