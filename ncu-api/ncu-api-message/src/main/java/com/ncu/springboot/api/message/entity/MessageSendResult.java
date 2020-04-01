package com.ncu.springboot.api.message.entity;

import java.io.Serializable;

public class MessageSendResult implements Serializable {

	private String code;
	private String msg;
	
	public MessageSendResult() {
		
	}
	
	public MessageSendResult(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public MessageSendResult setCode(String code) {
		this.code = code;
		return this;
	}

	public String getMsg() {
		return msg;
	}

	public MessageSendResult setMsg(String msg) {
		this.msg = msg;
		return this;
	}

}
