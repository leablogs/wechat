package com.leablogs.util;

import java.io.Serializable;

public class HttpClientResultUtil implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8861186337864333185L;

	private Integer code;
	private String message;
	private Object data;

	public HttpClientResultUtil(Integer code, String message, Object data) {
		super();
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public HttpClientResultUtil(Integer code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
