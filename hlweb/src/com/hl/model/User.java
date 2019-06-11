package com.hl.model;

public class User {
	
	private int userId;

	private String userName;
	
	private String userPhone;
	
	private String userPassword;
	
	private String userJigou;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserJigou() {
		return userJigou;
	}

	public void setUserJigou(String userJigou) {
		this.userJigou = userJigou;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", userPhone=" + userPhone + ", userPassword="
				+ userPassword + ", userJigou=" + userJigou + "]";
	}

}
