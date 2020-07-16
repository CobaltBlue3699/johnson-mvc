package com.johnson.core.error;

public class UnLoginException extends BusinessException {

    public UnLoginException(int status, String message) {
		super(status, message);
	}

	private static final long serialVersionUID = 8239925503745362671L;

}
