package com.ncu.springboot.biz.rs;

import java.io.Serializable;
import java.util.Arrays;

public class BaseRequestBody<T> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String token;
	
	private String userCode;
	
	private Integer[] campusIds;
	
	private T data;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public Integer[] getCampusIds() {
		return campusIds;
	}

	public void setCampusIds(Integer[] campusIds) {
		this.campusIds = campusIds;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "BaseRequestBody [token=" + token + ", userCode=" + userCode + ", campusIds="
				+ Arrays.toString(campusIds) + ", data=" + data + "]";
	}

}
