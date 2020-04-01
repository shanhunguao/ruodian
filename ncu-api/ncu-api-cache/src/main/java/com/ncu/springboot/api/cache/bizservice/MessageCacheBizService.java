package com.ncu.springboot.api.cache.bizservice;

public interface MessageCacheBizService {
	
	void setSMSCodeByMobile(String mobile, String code);
	String getSMSCodeByMobile(String mobile);
	
}
