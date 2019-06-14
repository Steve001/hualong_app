package com.hl.model;

public class Customer {

	private int cusId;
	private String cusName;
	private String cusSex;
	private String cusPhone;
	private String cusDate;
	//private int cusArea;
	private String cusArea;
	private int cusUserId;
	private String cusStat;
	private String cusBz;
	private String cusTjtime;
	private String cusGuwen;

	public String getCusTjtime() {
		return cusTjtime;
	}

	public void setCusTjtime(String cusTjtime) {
		this.cusTjtime = cusTjtime;
	}

	public String getCusBz() {
		return cusBz;
	}

	public void setCusBz(String cusBz) {
		this.cusBz = cusBz;
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

	public String getCusArea() {
		return cusArea;
	}

	public void setCusArea(String cusArea) {
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
	
	//@Override
	//public String toString() {
	//	return "Customer [cusId=" + cusId + ", cusName=" + cusName + ", cusSex=" + cusSex + ", cusPhone=" + cusPhone
	//			+ ", cusDate=" + cusDate + ", cusArea=" + cusArea + ", cusUserId=" + cusUserId + ", cusGuwen=" + cusGuwen + ", cusStat=" + cusStat
	//			+ ", cusBz=" + cusBz + "]";
	//}

	@Override
	public String toString() {
		return "Customer [cusId=" + cusId +", cusName=" + cusName +", cusSex=" + cusSex + ", cusPhone=" + cusPhone + ", cusArea=" + cusArea + 
				", cusDate=" + cusDate + ", cusUserId=" + cusUserId + ", cusGuwen=" + cusGuwen +", cusStat=" + cusStat + ", cusTjtime=" + cusTjtime + "]";
	}
}
