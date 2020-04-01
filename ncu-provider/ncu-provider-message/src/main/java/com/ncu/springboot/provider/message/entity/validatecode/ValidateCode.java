package com.ncu.springboot.provider.message.entity.validatecode;

import java.time.LocalDateTime;

public class ValidateCode {

	private String code;
	private LocalDateTime expireTime;
	
	public ValidateCode(String code, LocalDateTime expireTime) {
		this.code = code;
		this.expireTime = expireTime;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	

	public LocalDateTime getExpireTime() {
		return expireTime;
	}

	public boolean isExpired() {
		return LocalDateTime.now().isAfter(getExpireTime());
	}

	public void setExpireTime(LocalDateTime expireTime) {
		this.expireTime = expireTime;
	}

}
