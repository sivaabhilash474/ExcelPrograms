package com.automation.customclasses;

public class Headers {
	private String header;
	
	public Headers(String header) {
		super();
		this.header = header;
	}

	

	@Override
	public String toString() {
		return header;
	}



	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}
	
	
}
