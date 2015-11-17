package com.nan.Server;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;
import com.nan.model.ClientData;
import com.nan.view.ServerView;

//每来一个客户端为此客户端新开一个线程,监听其发来的消息
public class ServerThread extends Thread {

	private Socket socket = null;
	private ClientData mClientData;// 一个线程有唯一的一个客户端数据
	public int clientRow;
	public int[] sign = { 0x11, 0x12, 0x13, 0x14, 0x15, 0x16 };
	public boolean sym = true;

	public ServerThread(Socket socket, ClientData mClientData, int clientRow)
			throws IOException {
		this.socket = socket;
		this.mClientData = mClientData;
		this.clientRow = clientRow;
		// myBufferedReader = new BufferedReader(new InputStreamReader(
		// socket.getInputStream()));
		// myBufferedReader = new BufferedInputStream(socket.getInputStream());
	}

	@Override
	public void run() {

		try {
			while (true) {
				init();

				mClientData.calAllowance();
				mClientData.calAvaitime();

				checkAllowance();// 检查容量是否将完
				ServerView.tableModel.removeRow(clientRow);
				ServerView.tableModel.insertRow(clientRow,
						mClientData.getClientInfo());
				// ServerView.tableModel.fireTableDataChanged();
				// ServerView.tableModel.fireTableStructureChanged();
				ServerView.mTextArea.setCaretPosition(ServerView.mTextArea
						.getText().length());
				ServerView.serverView.infoPanel.updateUI();// 更新信息面板

			}

		} catch (Exception e) {
			e.printStackTrace();
			try {
				socket.getInputStream().close();// 关闭流
				socket.close();
			} catch (IOException e1) {

				e1.printStackTrace();
			}
			ServerView.serverView.addMessage("病房号" + mClientData.getClientIp()
					+ "异常退出");
			ServerView.tableModel.removeRow(clientRow);
			Server.mySocketList.remove(socket);// 如果客户端异常退出，则删除此客户端socket

		}
		super.run();

	}

	private void init() throws IOException {
		int len = socket.getInputStream().read();// 数据个数
		System.out.println(len);

		int temp = socket.getInputStream().read();// 字头

		switch (temp) {// 判断字头执行相应动作
		//
		// *0x11：电脑向stm32发送远程修改速度信号
		// *0x12：电脑向stm32发送瓶数和体积
		// *0x13: stm32向电脑发送警报
		// *0x14：stm32向电脑发送当前的点滴速度
		// *0x15：stm32向电脑发送输入的病房号，输入的速度
		// *0x16: stm32向电脑发送减少瓶数的信号

		case 0x13:
			// TODO
			// 播放提示声音
			break;
		case 0x14:
			System.out.print("读到速度");
			readSpeed();

			break;
		// case 0x15:
		// break;
		case 0x16:
			mClientData.bottle_num_which_add_one();// 第几瓶加1
			ServerView.serverView.addMessage("病房号" + mClientData.getClientIp()
					+ "换下一瓶");
			mClientData.setAllowance(mClientData.getVolume()[mClientData
					.getBottle_num_which()]);
			break;
		default:
			System.out.print("无匹配字头");
			break;
		}

	}

	private void readSpeed() throws IOException {
		String speedString = "";
		for (int i = 0; i < 3; i++) {
			int data = socket.getInputStream().read();
			speedString = speedString + data;
		}
		mClientData.setSpeed(Integer.parseInt(speedString));// 接收到点滴速度转为int
		System.out.println("点滴速度" + Integer.parseInt(speedString));
	}

	private void checkAllowance() {
		// 即将用完
		if (mClientData.getAllowance() < 4 && sym == true) {
			sym = false;// 标志函数只能进入一次
			new SoundThread(mClientData.getClientIp(), false).start();// 播放提示声音
			ServerView.mTable.getColumn("病房号").setCellRenderer(
					new com.nan.view.MyTableCellRenderrer(clientRow, false));// 设置此行为橙色
			ServerView.serverView.addMessage(mClientData.getClientIp()
					+ "的药液即将用完，请立即换瓶");
			ServerView.mTextArea.setCaretPosition(ServerView.mTextArea
					.getText().length());
			ServerView.serverView.infoPanel.updateUI();// 更新信息面板
		}
		// 已经用完
		if (mClientData.getAllowance() < 0) {
			mClientData.setSpeed(0);
			mClientData.setAllowance(0);
			mClientData.setAvaitime(0);

			ServerView.tableModel.removeRow(clientRow);
			ServerView.tableModel.insertRow(clientRow,
					mClientData.getClientInfo());

			new SoundThread(mClientData.getClientIp(), true).start();// 播放提示声音
			ServerView.mTable.getColumn("病房号").setCellRenderer(
					new com.nan.view.MyTableCellRenderrer(clientRow, true));// 设置此行为红色

			ServerView.serverView.addMessage(mClientData.getClientIp()
					+ "已经停止输液，请立即换瓶");
			ServerView.mTextArea.setCaretPosition(ServerView.mTextArea
					.getText().length());

			ServerView.serverView.infoPanel.updateUI();// 更新信息面板
			// FIXME
			// 发送停止输液命令到wifi,wifi停止发速度直到换点滴
		}
	}

	// 给消息添加客户端的IP与发送时间
	// private String packMessage(String message) {
	//
	// SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
	// String myMessage = " " + socket.getInetAddress().getHostAddress()
	// + "     " + time.format(new java.util.Date()) + '\n' + "   "
	// + message;
	//
	// return myMessage;
	// }
}
