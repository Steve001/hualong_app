package com.hl.model;

public class Customer {

	private int cusId;
	private String cusName;
	private String cusSex;
	private String cusPhone;
	private String cusDate;
	private int cusArea;
	private int cusUserId;
	private String cusStat;
	private String cusTjTime;
	private String cusUser;
	private String cusGuwen;
	private String cusUserJigou;
	private String cusUserTjname;


	

	public String getCusUser() {
		return cusUser;
	}

	public void setCusUser(String cusUser) {
		this.cusUser = cusUser;
	}

	public String getCusTjTime() {
		return cusTjTime;
	}

	public void setCusTjTime(String cusTjTime) {
		this.cusTjTime = cusTjTime;
	}

	public int getCusId() {
		return cusId;
	}

	public void setCusId(int cusId) {
		this.cusId = cusId;
	}

	public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public String getCusSex() {
		return cusSex;
	}

	public void setCusSex(String cusSex) {
		this.cusSex = cusSex;
	}

	public String getCusPhone() {
		return cusPhone;
	}

	public void setCusPhone(String cusPhone) {
		this.cusPhone = cusPhone;
	}

	public String getCusDate() {
		return cusDate;
	}

	public void setCusDate(String cusDate) {
		this.cusDate = cusDate;
	}

	public int getCusArea() {
		return cusArea;
	}

	public void setCusArea(int cusArea) {
		this.cusArea = cusArea;
	}

	public int getCusUserId() {
		return cusUserId;
	}

	public void setCusUserId(int cusUserId) {
		this.cusUserId = cusUserId;
	}

	public String getCusStat() {
		return cusStat;
	}

	public void setCusStat(String cusStat) {
		this.cusStat = cusStat;
	}
	
	public String getCusGuwen() {
		return cusGuwen;
	}
	
	public void setCusGuwen(String cusGuwen) {
		this.cusGuwen = cusGuwen;
	}
	
	public String getCusUserJigou() {
		return cusUserJigou;
	}
	
	public void setCusUserJigou(String cusUserJigou) {
		this.cusUserJigou = cusUserJigou;
	}
	
	public String getCusUserTjname() {
		return cusUserTjname;
	}
	
	public void setCusUserTjname(String cusUserTjname) {
		this.cusUserTjname = cusUserTjname;
	}

	@Override
	public String toString() {
		return "Customer [cusId=" + cusId + ", cusName=" + cusName + ", cusSex=" + cusSex + ", cusPhone=" + cusPhone
				+ ", cusDate=" + cusDate + ", cusArea=" + cusArea + ", cusUserId=" + cusUserId + ", cusGuwen=" + cusGuwen + ", cusStat=" + cusStat
				+ ", cusTjTime=" + cusTjTime + ", cusUser=" + cusUser + ", UserJigou=" + cusUserJigou + ", UserTjname=" + cusUserTjname + "]";
	}



}