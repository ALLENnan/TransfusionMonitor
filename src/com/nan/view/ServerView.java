package com.nan.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.nan.Server.Server;

//主窗口（服务器可视化界面）
public class ServerView extends JFrame {
	private JPanel contentPane; // 主面板
	public JPanel infoPanel;// 点滴信息面板
	public static JTextArea mTextArea;// 打印信息区
	public static JScrollPane mTextAreaScroll;
	public static JPanel mButtonPane;// 按钮面板
	public static JTable mTable;// 点滴表格
	private JButton start_bt;// 启动监控按钮
	private JButton stop_bt;// 关闭监控按钮
	private JMenuBar menuBar;// 菜单栏
	private JMenu menu, help; // 菜单组件
	private JMenuItem record, refresh, clear, exit, about;// 菜单子项
	public static Font myFont1;// 字体样式
	public static Font myFont2;
	public static Font myFont3;
	public static Border border;// 边框
	public Border title1;// 标题
	public Border title2;// 标题
	public static DefaultTableModel tableModel;// 表格模型对象
	public Server myServer;
	public static ServerView serverView;// ServerView实例
	public static int record_num;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					serverView = new ServerView();
					serverView.setVisible(true);// 设置可见
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ServerView() {
		init();// 初始化界面
		setSize(850, 600);
		setTitle("智能输液监控系统");
		setLocationRelativeTo(null);// 设置居中显示
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 关闭则结束程序
	}

	private void init() {
		contentPane = new JPanel(); // 主面板
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));// 设置边界
		contentPane.setLayout(new BorderLayout());
		dress();// 美化
		initMenubar();

		setJMenuBar(menuBar); // 添加菜单栏到Frame

		initInfoPanel();
		initButtonPanel();

		JLabel label = new JLabel("智能输液监控系统——广外创新实验室", JLabel.CENTER);
		label.setFont(myFont1);

		contentPane.add(label, BorderLayout.NORTH);
		contentPane.add(infoPanel, BorderLayout.CENTER);
		contentPane.add(mButtonPane, BorderLayout.SOUTH);

		setContentPane(contentPane);
		setListeners();
	}

	private void initMenubar() {
		// TODO Auto-generated method stub
		// 菜单功能
		menuBar = new JMenuBar(); // 创建菜单栏对象

		menu = new JMenu("菜单");
		help = new JMenu("帮助");
		record = new JMenuItem("录入");
		refresh = new JMenuItem("刷新");
		clear = new JMenuItem("清除打印信息");
		exit = new JMenuItem("退出");
		about = new JMenuItem("关于");

		menu.add(record);
		menu.addSeparator();
		menu.add(refresh);
		menu.addSeparator();
		menu.add(clear);
		menu.addSeparator();
		menu.add(exit);

		help.add(about);
		menuBar.add(menu);
		menuBar.add(help);

		menu.setFont(myFont3);
		help.setFont(myFont3);
		record.setFont(myFont3);
		refresh.setFont(myFont3);
		clear.setFont(myFont3);
		exit.setFont(myFont3);
		about.setFont(myFont3);
	}

	private void dress() {

		myFont1 = new Font("微软雅黑", Font.PLAIN, 18);// 设置字体样式
		myFont2 = new Font("微软雅黑", Font.PLAIN, 16);// 设置字体样式
		myFont3 = new Font("黑体", Font.PLAIN, 15);
		border = BorderFactory.createEtchedBorder(Color.GRAY, Color.LIGHT_GRAY);
		title1 = BorderFactory.createTitledBorder(border, "信息打印区",
				TitledBorder.LEFT, TitledBorder.TOP, new Font("微软雅黑",
						Font.ITALIC, 16), Color.black);
		title2 = BorderFactory.createTitledBorder(border, "实时监控区",
				TitledBorder.LEFT, TitledBorder.TOP, new Font("微软雅黑",
						Font.ITALIC, 16), Color.black);
	}

	private void initButtonPanel() {
		mButtonPane = new JPanel();
		start_bt = new JButton("启动监控");
		stop_bt = new JButton("关闭监控");
		start_bt.setFont(myFont1);
		stop_bt.setFont(myFont1);
		stop_bt.setEnabled(false);
		mButtonPane.add(start_bt, BorderLayout.WEST);
		mButtonPane.add(stop_bt, BorderLayout.EAST);
	}

	private void initInfoPanel() {
		infoPanel = new JPanel();// 消息面板
		infoPanel.setLayout(new BorderLayout());

		mTextArea = new JTextArea(8, 0);
		mTextAreaScroll = new JScrollPane(mTextArea);
		mTextAreaScroll
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);// 设置垂直滚动条
		mTextArea.setLineWrap(true);// 设置自动换行
		mTextArea.setFont(myFont3);

		mTextAreaScroll.setBorder(border);
		mTextAreaScroll.setBorder(title1);

		String[] colStrings = { "病房号", "点滴速度(滴/分钟)", "余量(ml)", "剩余时间(分钟)",
				"容量规格(ml)", "瓶数" };// 列名
		String[][] tableVales = {};// 数据
		tableModel = new DefaultTableModel(tableVales, colStrings) {
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false; // 设置双击无法修改单元格
			};
		};

		mTable = new JTable(tableModel);// 初始化表格
		mTable.setRowHeight(30);
		mTable.getTableHeader().setFont(myFont2);
		mTable.setFont(myFont2);
		JScrollPane mJScrollTable = new JScrollPane(mTable); // 支持滚动
		mJScrollTable.setBorder(border);
		mJScrollTable.setBorder(title2);

		infoPanel.add(mTextAreaScroll, BorderLayout.SOUTH);

		infoPanel.add(mJScrollTable, BorderLayout.CENTER);

	}

	private void setListeners() {
		// TODO Auto-generated method stub
		// 其他窗口
		start_bt.addActionListener(new myListener());
		stop_bt.addActionListener(new myListener());
		record.addActionListener(new myListener());
		refresh.addActionListener(new myListener());
		clear.addActionListener(new myListener());
		exit.addActionListener(new myListener());
		about.addActionListener(new myListener());
		mTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {// 单击鼠标左键
					if (e.getClickCount() == 2) {
						int row = ((JTable) e.getSource()).rowAtPoint(e
								.getPoint());// 获得行位置
						int column = ((JTable) e.getSource()).columnAtPoint(e
								.getPoint());// 获得列位置
						if (column == 0) {
							ClientDataView mClientDataView = new ClientDataView(
									Server.mClientDatas.get(row));
							mClientDataView.setVisible(true);// 设置可见
						}
						if (column == 1) {
							ControlSpeedView mControlSpeedView = new ControlSpeedView(
									row);
							mControlSpeedView.setVisible(true);// 设置可见
						}
						if (column == 4) {
							VolumeInfoView mVolumeInfoView = new VolumeInfoView(
									Server.mClientDatas.get(row));
							mVolumeInfoView.setVisible(true);// 设置可见
						}
					}
				}
			}
		});
	}

	class myListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == start_bt) {
				myServer = new Server();
				myServer.start();
				start_bt.setEnabled(false);
				stop_bt.setEnabled(true);
			}
			if (e.getSource() == stop_bt) {
				System.exit(0);
			}
			if (e.getSource() == record) {
				RecordInfoView mRecordInfoView = new RecordInfoView();
				mRecordInfoView.setVisible(true);// 设置可见
			}
			if (e.getSource() == refresh) {
				ServerView.serverView.contentPane.updateUI();// 更新面板
			}
			if (e.getSource() == clear) {
				mTextArea.setText("");
			}
			if (e.getSource() == exit) {
				System.exit(0);
			}
			if (e.getSource() == about) {
				AboutView mAboutView = new AboutView();
				mAboutView.setVisible(true);// 设置可见
			}
		}
	}

	public void addMessage(String message) {
		mTextArea.append(message + "\n");
	}
}
