package com.nan.Server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;

import com.nan.model.ClientData;
import com.nan.view.ServerView;

//用来取得客户端发来的数据
public class ConnectThread extends Thread {
	private Socket mysocket;
	public ClientData mClientData;
	private String clientIp;// 客戶端发来的病房号
	private String clientSpeed;// 客戶端发来的初始速度
	private int bottle_num;// 瓶数
	private int[] volume;// 点滴体积

	public ConnectThread(Socket mysocket) {
		this.mysocket = mysocket;
	}

	public void run() {
		try {
			if (readData(mysocket)) {// 判断是否病房号已录入

				mClientData = new ClientData(clientIp,
						Integer.parseInt(clientSpeed));
				setClientData();// 设置瓶数及容量数据

				Server.mClientDatas.add(mClientData);// 添加ClientData对象
				ServerView.tableModel.addRow(mClientData.getClientInfo());

				ServerView.serverView.addMessage("病房号" + clientIp
						+ "点滴已连接上监控系统-------------------->>>");
				ServerView.serverView.infoPanel.updateUI();// 刷新
				// 每来一个客户端就新开一个线程去处理，然后服务器继续阻塞，等待其他客户端来访问

				new ServerThread(mysocket, mClientData, Server.clientRow++)
						.start();

				Thread.sleep(1000);
				sendToClient(mysocket);// *0x12：电脑向stm32发送瓶数和体积
			}
		} catch (IOException e) {

			e.printStackTrace();
		} catch (InterruptedException e) {

			e.printStackTrace();
		}

		Save(clientIp);// 存储此病房号的数据，方便查找记录
	}

	private void sendToClient(Socket mysocket) throws IOException {
		int volume_len = 0;
		int[] volume = mClientData.getVolume();
		int bottle_num = mClientData.getBottle_num();
		for (int i = 0; i < volume.length; i++)

		{
			volume_len = volume_len + (volume[i] + "").length();
		}
		int len = (bottle_num + "").length() + volume_len;
		mysocket.getOutputStream().write(len);// 发送数据的长度
		mysocket.getOutputStream().write(0x12);// 发送字头
		mysocket.getOutputStream().write(bottle_num);// 发送瓶数
		for (int i = 0; i < volume.length; i++)

		{
			for (int j = 0; j < (volume[i] + "").length(); j++) {
				int v = Integer.parseInt((volume[i] + "").charAt(j) + "");
				mysocket.getOutputStream().write(v);// 发送数据的长度
			}
		}
		mysocket.getOutputStream().write(0);

	}

	private boolean readData(Socket mysocket) throws IOException {
		int len, temp;
		do

		// 读取数据
		{
			len = mysocket.getInputStream().read();// 数据个数
			System.out.println("个数：" + len);
			// while(mysocket.getInputStream().read()<10)
			temp = mysocket.getInputStream().read();// 字头
			System.out.println("字头：" + temp);
		} while (temp != 21);

		// if (temp == 0x15) {
		String ipString = "";
		String speedString = "";
		for (int i = 0; i < 3; i++) {
			int data = mysocket.getInputStream().read();
			ipString = ipString + data;
		}
		System.out.println("病房号：" + ipString);
		for (int i = 0; i < 3; i++) {
			int data = mysocket.getInputStream().read();
			speedString = speedString + data;
		}
		System.out.println("速度：" + speedString);
		System.out.print("已读到数据");
		// 判断病房ip是否已录入，否则警报
		String filePath = "E:" + File.separator + "temp";
		File file = new File(filePath);
		String ipfile[];
		ipfile = file.list();

		for (int i = 0; i < ipfile.length; i++) {
			if (ipString.equals(ipfile[i]))// 如果找到文件名和病房号匹配
			{
				clientIp = ipString;
				clientSpeed = speedString;
				ServerView.serverView.addMessage("找到匹配病房号" + clientIp
						+ ",正在初始化数据-------------------->>>");
				return true;
			}
		}

		// }
		ServerView.serverView
				.addMessage("病房号未录入，找不到匹配信息-------------------->>>");
		return false;

	}

	private void setClientData() throws NumberFormatException, IOException {
		// 从录入的文件中读取此病房号的瓶数以及各瓶的容量
		String filePath = "E:" + File.separator + "temp";
		File file = new File(filePath + File.separator + clientIp);// 找到此病房号文件
		BufferedReader br = new BufferedReader(new FileReader(file));

		bottle_num = Integer.parseInt(br.readLine());// 读取此病房号的瓶数
		volume = new int[bottle_num];

		for (int i = 0; i < bottle_num; i++) {
			volume[i] = Integer.parseInt(br.readLine());// 读取此病房号各瓶的容量,并存储在数组中
			System.out.println(volume[i]);
		}
		br.close();
		mClientData.setBottle_num(bottle_num);// 设置瓶数
		mClientData.setVolume(volume);// 设置各瓶的容量
		mClientData.setAllowance(volume[0]);
		mClientData.calAvaitime();
		System.out.println("已设置好数据" + mClientData.getVolume()[0]);
		ServerView.serverView.addMessage("已经导入病房号" + clientIp
				+ "的瓶数及容量信息-------------------->>>");
	}

	private void Save(String clientIp) {

		SimpleDateFormat time = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
		Server.filePath = "E:" + File.separator + "ClientData" + File.separator
				+ clientIp;
		File file = new File(Server.filePath);
		if (!file.exists())
			file.mkdirs();
		try {
			PrintWriter pw = new PrintWriter(Server.filePath + File.separator
					+ time.format(new java.util.Date()));
			pw.write("点滴瓶数:" + "\r\n" + bottle_num);
			pw.write("各瓶容量分別为(单位:ml):" + "\r\n");
			for (int i = 0; i < bottle_num; i++) {
				pw.write(volume[i] + "\r\n");
			}
			pw.flush();
			pw.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
