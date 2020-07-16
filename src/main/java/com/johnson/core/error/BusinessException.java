package com.johnson.core.error;

/**
 * 此類別的功能描述：BusinessException物件，繼承Exception
 * 企業邏輯錯誤，例如isgroupaware帶錯、或者無權限分享等等
 */
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 6125399297356470562L;
	
	private int status = 999;
	private String errorMsg;

	public BusinessException(Throwable e) {
		super(e);
		this.errorMsg = e.getMessage();
	}

	public BusinessException(int status, String message, Throwable cause) {
		this(cause);
		this.status = status;
		this.errorMsg = message;
	}

	public BusinessException(int status, String message) {
		this.status = status;
		this.errorMsg = message;
	}
	
	public int getStatus() {
		return this.status;
	}
	
	@Override
	public String getMessage() {
		if (this.errorMsg == null) {
			return getCause().getMessage();
		}
		return this.errorMsg;
	}

}
