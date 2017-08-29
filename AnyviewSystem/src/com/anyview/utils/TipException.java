package com.anyview.utils;

public class TipException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	
	public TipException(){
		
	}

	public TipException(String message) {
		super();
		this.message = message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

	@Override
	public void printStackTrace() {
		super.printStackTrace();
	}

}
