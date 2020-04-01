package com.ncu.springboot.api.cache.bizservice;

public interface CommonCacheBizService {
	
	void clearAllData();
	
	boolean existKey(String key);
	
}
