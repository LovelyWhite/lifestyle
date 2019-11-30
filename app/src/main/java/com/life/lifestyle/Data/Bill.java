package com.life.lifestyle.Data;


public class Bill {//账单
	String billID;//账单编号
	String userID;//用户id
	String time;//记录日期.数据库date型
	double price;
	boolean bit;

	public String getBillID() {
		return billID;
	}

	public void setBillID(String billID) {
		this.billID = billID;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public boolean isBit() {
		return bit;
	}

	public void setBit(boolean bit) {
		this.bit = bit;
	}
}
