package com.ncu.springboot.DTO;

import java.io.Serializable;

public class DogetTeacherRegistrationListDTPO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String userCode;
	
	private String qwxq;

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getQwxq() {
		return qwxq;
	}

	public void setQwxq(String qwxq) {
		this.qwxq = qwxq;
	}

	@Override
	public String toString() {
		return "DogetTeacherRegistrationListDTPO [userCode=" + userCode + ", qwxq=" + qwxq + "]";
	}

}
