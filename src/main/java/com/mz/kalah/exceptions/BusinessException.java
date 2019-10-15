package com.mz.kalah.exceptions;

public class BusinessException extends Exception{
	private static final long serialVersionUID = 1L;
	String message;
	
	public BusinessException(String message) {
		super(message);
		this.message=message;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
