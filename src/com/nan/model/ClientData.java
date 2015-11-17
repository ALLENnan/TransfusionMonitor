package com.nan.model;


//客户端数据
public class ClientData {
	private String clientIp;// 点滴用户病房号
	private int[] volume;// 点滴体积
	private static final float glob_volume = 0.0667f;// 一滴的体积（假设15滴为一毫升）
	private int speed;// 点滴速度(一分钟多少滴)
	private float allowance; // 液体余量
	private float avaitime;// 可用时间
	private int bottle_num;// 瓶数
	private  int bottle_num_which;//第几瓶
	public ClientData(String clientIp, int speed) {
		this.clientIp = clientIp;
		this.speed = speed;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setVolume(int[] volume) {
	this.volume=volume;
		
	}
	public int[] getVolume() {
		return volume;
	}

	public void setBottle_num(int bottle_num) {
		this.bottle_num = bottle_num;
	}
	public int getBottle_num() {
		return bottle_num;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public float getSpeed() {
		return speed;
	}

	public void setAllowance(float allowance) {
		this.allowance = allowance;
	}

	public void setAvaitime(float avaitime) {
		this.avaitime = avaitime;
	}

	public void setNum(int bottle_num) {
		this.bottle_num = bottle_num;
	}

	public int getNum() {
		return bottle_num;
	}
	public void bottle_num_which_add_one()
	{
		bottle_num_which++;
	}
	public int getBottle_num_which()
	{
		return bottle_num_which;
	}
	public String[] getClientInfo() {
		String[] ClientInfo = { clientIp, speed + "", allowance + "",
				avaitime + "", volume[bottle_num_which]+"" , bottle_num+""};
		return ClientInfo;

	}

	// 计算余量
	public void calAllowance() {
		float temp = allowance - (speed / 60f) * glob_volume;
		setAllowance(temp);
	}

	public float getAllowance() {
		return allowance;
	}

	// 计算可用时间
	public void calAvaitime() {
		float temp = (allowance / glob_volume) / speed;
		setAvaitime(temp);
	}

}
