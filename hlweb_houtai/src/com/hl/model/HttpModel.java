package com.hl.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 网络数据公共模型
 * 
 * @author lzj
 * @param <E>
 *            主键的类型
 * 
 */
public class HttpModel {

	public static final String ERROR = "error";
	public static final String SUCCESS = "success";
	private String tag;
	private String status;
	private int code;
	private String message;
	private Exception error;
	private String version;
	
	// 业务数据
	private List<Object> appdata = new ArrayList<Object>();

	public Exception getError() {
		return error;
	}

	public void setError(Exception error) {
		this.error = error;
	}

	public List<Object> getAppdata() {
		return appdata;
	}

	public void setAppdata(List<Object> appdata) {
		this.appdata = appdata;
	}

	public HttpModel() {

	}

	public HttpModel(String tag) {
		this.setTag(tag);
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void addData(Object object) {
		if (appdata == null) {
			appdata = new ArrayList<Object>();
		}
		this.appdata.add(object);
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getVersion() {
		return version;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}

}