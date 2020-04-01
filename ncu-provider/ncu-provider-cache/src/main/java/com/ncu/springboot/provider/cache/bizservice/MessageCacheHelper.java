package com.ncu.springboot.provider.cache.bizservice;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.ncu.springboot.api.cache.bizservice.MessageCacheBizService;
import com.ncu.springboot.api.cache.keys.MessageCacheIdKeys;
import com.ncu.springboot.utils.RedisUtil;

@Service
public class MessageCacheHelper implements MessageCacheBizService {
	
	@Autowired
	private RedisUtil redisUtil;

	@Override
	public void setSMSCodeByMobile(String mobile, String code) {
		redisUtil.set(MessageCacheIdKeys.getSMSCodeByMobileKey(mobile), code);
		redisUtil.expire(MessageCacheIdKeys.getSMSCodeByMobileKey(mobile), 300);
	}

	@Override
	public String getSMSCodeByMobile(String mobile) {
		return (String) redisUtil.get(MessageCacheIdKeys.getSMSCodeByMobileKey(mobile));
	}

}
