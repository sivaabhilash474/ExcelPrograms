package com.automation.implementations;

public class ResponseObject {

	private String message;
	
	public String getMessage() {
		return this.message;
	}
	
	public ResponseObject(String message) {
		this.message = message;
	}
	
	
	public ResponseObject() {
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
