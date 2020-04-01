package com.ncu.springboot.provider.cache.bizservice;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.ncu.springboot.api.cache.bizservice.CommonCacheBizService;
import com.ncu.springboot.utils.RedisUtil;

@Service
public class CommonCacheHelper implements CommonCacheBizService {
	
	@Autowired
	private RedisUtil redisUtil;

	@Override
	public void clearAllData() {
		
	}

	@Override
	public boolean existKey(String key) {
		return redisUtil.hasKey(key);
	}

}
