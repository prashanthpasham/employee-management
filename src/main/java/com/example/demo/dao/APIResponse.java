package com.example.demo.dao;

public class APIResponse {
	
	private Object data;
	
	public APIResponse() {
		
	}

	public APIResponse(Object data) {
		super();
		this.data = data;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "APIResponse [data=" + data + "]";
	}
	
	

}
