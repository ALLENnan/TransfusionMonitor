package com.nan.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.nan.model.ClientData;
import com.nan.view.ServerView;

//服务器，用来接收客户端的访问
public class Server extends Thread {
	public static ArrayList<Socket> mySocketList = new ArrayList<Socket>();// 用来存放socket对象
	public static ServerSocket serverSocket;
	public static ArrayList<ClientData> mClientDatas = new ArrayList<ClientData>();

	public static String filePath;// 保存信息的路径
	public static int clientRow = 0;

	public static int record_num;// 用来保存已经录入的病房号数目

	public void run() {

		try {
			serverSocket = new ServerSocket(8888);
			System.out.println("智能输液监控系统已启动------------------->>>");
			ServerView.serverView
					.addMessage("智能输液监控系统已启动------------------->>>");
			while (true) {
				Socket mysocket = serverSocket.accept();// 等待客户端访问，无客户端则阻塞此方法
				mySocketList.add(mysocket);
				System.out.print("病房点滴正在尝试连接-------------------->>>");
				ServerView.serverView
						.addMessage("病房点滴正在尝试连接-------------------->>>");
				new ConnectThread(mysocket).start();
				System.out.println("开启cconnectThread");
			}
		} catch (IOException e) {
			System.out.println("Error");
			e.printStackTrace();
		}
	}

	// private Boolean judgeInputValue() {
	//
	// if (inputValue.equals("") || inputValue == null) {
	// ServerView.serverView
	// .addMessage("点滴规格(ml)不能为空-------------------->>>");
	// return true;
	// }
	// for (int i = 0; i < inputValue.length(); i++) {
	// if (inputValue.charAt(i) < 48 || inputValue.charAt(i) > 58) {
	// ServerView.serverView
	// .addMessage("点滴规格(ml)应为整数-------------------->>>");
	// return true;
	// }
	// }
	// return false;
	// }
}
