package com.johnson.core.mvc.view.utils;

public class ResponseData {
	private int status;
	private Object data;
	private String message;
	public ResponseData() {
		super();
	}
	public ResponseData(int status, String message) {
		this();
		this.status = status;
		this.message = message;
	}
	public ResponseData(int status, Object data) {
		this();
		this.status = status;
		this.data = data;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
