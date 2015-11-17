package com.nan.view;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.nan.Server.Server;
import com.nan.model.ClientData;

//点滴速度控制窗口
public class ControlSpeedView extends JFrame {
	private ClientData mClientData;
	private Socket mSocket;
	private JPanel contentPane; // 主面板
	private JTextField speedStr;
	private JButton bt;// 确定按钮
	private String speed;
	OutputStream os = null;

	public ControlSpeedView(int row) {
		this.mClientData = Server.mClientDatas.get(row);
		this.mSocket = Server.mySocketList.get(row);
		init();
		setSize(380, 90);
		setTitle("点滴速度控制窗口");
		setLocationRelativeTo(null);// 设置居中显示
		this.setResizable(false);// 禁用最大化
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);// 关闭则销毁窗口
	}

	private void init() {
		contentPane = new JPanel(); // 主面板
		contentPane.setLayout(new FlowLayout());

		JLabel str1 = new JLabel("修改点滴速度值为：");
		speedStr = new JTextField(5);
		JLabel str2 = new JLabel("(滴/分钟)");
		bt = new JButton("确定");

		str1.setFont(ServerView.myFont2);
		str2.setFont(ServerView.myFont2);
		speedStr.setFont(ServerView.myFont2);
		bt.setFont(ServerView.myFont2);

		contentPane.add(str1);
		contentPane.add(speedStr);
		contentPane.add(str2);
		contentPane.add(bt);
		setContentPane(contentPane);
		setlisteners();
	}

	private void setlisteners() {
		bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (judgeSpeedStr()) {
					int i = JOptionPane.showConfirmDialog(null, "是否将病房号"
							+ mClientData.getClientIp() + "的速度修改为:" + speed
							+ "(滴/分钟)", "提示:", JOptionPane.YES_NO_OPTION);
					if (i == JOptionPane.OK_OPTION) {
						try {
							os = mSocket.getOutputStream();
							os.write(speed.length());
							os.write(0x11);
							for (int j = 0; j < speed.length(); j++) {
								int s = Integer.parseInt(speed.charAt(j)+"");
								os.write(s);
							}
							
							speedStr.setText("");
						} catch (IOException e) {
							e.printStackTrace();
						}

						ServerView.serverView.addMessage("已将病房号"
								+ mClientData.getClientIp() + "的速度修改为:" + speed
								+ "(滴/分钟)-------------->>>");
					}
				}
			}
		});
	}

	private Boolean judgeSpeedStr() {
		speed = speedStr.getText().toString().trim();
		if (speed.equals("")) {
			ServerView.serverView
					.addMessage("点滴速度(滴/分钟)不能为空-------------------->>>");
			return false;
		}
		for (int i = 0; i < speedStr.getText().trim().length(); i++) {
			if (speed.charAt(i) < 48 || speed.charAt(i) > 58) {
				ServerView.serverView
						.addMessage("点滴速度(滴/分钟)应为整数-------------------->>>");
				return false;
			}
		}
		return true;
	}

}
