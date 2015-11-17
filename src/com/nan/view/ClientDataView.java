package com.nan.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.nan.Server.Server;
import com.nan.model.ClientData;

//客户端信息窗口
public class ClientDataView extends JFrame {
	private ClientData mClientData;
	private JPanel contentPane; // 主面板

	public Border border;
	public Border title;

	private JPanel clientDataPanel;// 患者信息面板
	private JTextField ipStr;// 患者ip
	public JTextArea clientTextArea;// 信息文本区域
	private JButton save_bt;// 保存按钮
	private JButton del_bt;// 删除按钮

	private JPanel imageJPanel;

	public ClientDataView(ClientData mClientData) {
		this.mClientData = mClientData;
		init();
		setSize(500, 400);
		setTitle("患者信息");
		setLocationRelativeTo(null);// 设置居中显示
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);// 关闭则销毁窗口
	}

	private void init() {

		contentPane = new JPanel(); // 主面板
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));// 设置边界
		contentPane.setLayout(new BorderLayout());
		border = BorderFactory.createEtchedBorder(Color.GRAY, Color.LIGHT_GRAY);
		title = BorderFactory.createTitledBorder(border, "患者基本信息",
				TitledBorder.LEFT, TitledBorder.TOP, new Font("微软雅黑",
						Font.ITALIC, 16), Color.black);

		imageJPanel = new JPanel() {

			protected void paintComponent(Graphics g) {
				// FIXME
				// 图像闪？？？？？？？？？？？？？？？？
				ImageIcon icon = new ImageIcon("./image/client.gif");
				icon.setImage(icon.getImage().getScaledInstance(80, 80,
						Image.SCALE_AREA_AVERAGING));// 设置图片大小

				Image img = icon.getImage();
				g.drawImage(img, 10, 10, icon.getIconWidth(),
						icon.getIconHeight(), icon.getImageObserver());
				imageJPanel.setSize(icon.getIconWidth(), icon.getIconHeight());

			}
		};
		initclientDataPanel();

		contentPane.add(imageJPanel, BorderLayout.WEST);
		contentPane.add(clientDataPanel, BorderLayout.CENTER);
		setContentPane(contentPane);
		setListeners();
	}

	private void initclientDataPanel() {
		clientDataPanel = new JPanel();
		clientDataPanel.setBorder(new EmptyBorder(10, 80, 5, 5));// 设置边界
		clientDataPanel.setLayout(new BorderLayout());

		JLabel str = new JLabel("病房号:");
		str.setFont(ServerView.myFont2);
		JTextField ipStr = new JTextField();
		ipStr.setText(mClientData.getClientIp());
		JPanel ipStrPanel = new JPanel();
		ipStrPanel.add(str, BorderLayout.WEST);
		ipStrPanel.add(ipStr, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();
		save_bt = new JButton("保存");
		del_bt = new JButton("删除");
		save_bt.setFont(ServerView.myFont2);
		del_bt.setFont(ServerView.myFont2);
		buttonPanel.add(save_bt, BorderLayout.WEST);
		buttonPanel.add(del_bt, BorderLayout.CENTER);

		clientTextArea = new JTextArea(8, 0);
		JScrollPane mClientTextAreaScroll = new JScrollPane(clientTextArea);
		mClientTextAreaScroll
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);// 设置垂直滚动条
		clientTextArea.setLineWrap(true);// 设置自动换行
		clientTextArea.setFont(ServerView.myFont2);

		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(Server.filePath
					+ File.separator + "患者基本信息.txt"));
			String info, infos = "";
			while ((info = br.readLine()) != null) {
				infos = infos + info;

			}
			clientTextArea.setText(infos);
		} catch (IOException e) {
			
			e.printStackTrace();
		}

		mClientTextAreaScroll.setBorder(border);
		mClientTextAreaScroll.setBorder(title);

		ipStr.setFont(ServerView.myFont1);
		clientDataPanel.add(ipStrPanel, BorderLayout.NORTH);
		clientDataPanel.add(mClientTextAreaScroll, BorderLayout.CENTER);
		clientDataPanel.add(buttonPanel, BorderLayout.SOUTH);

	}

	private void setListeners() {

		save_bt.addActionListener(new myListener());
		del_bt.addActionListener(new myListener());
	}

	class myListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == save_bt) {
				try {
					PrintWriter pw = new PrintWriter(Server.filePath
							+ File.separator + "患者基本信息.txt");
					String str = clientTextArea.getText();
					pw.write(str);
					pw.flush();
					pw.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (e.getSource() == del_bt) {
				clientTextArea.setText("");
			}
		}
	}

}
