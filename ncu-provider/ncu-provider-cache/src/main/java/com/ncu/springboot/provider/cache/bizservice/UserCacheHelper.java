package com.ncu.springboot.provider.cache.bizservice;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.ncu.springboot.api.cache.bizservice.UserCacheBizService;
import com.ncu.springboot.api.cache.constants.CacheConstants;
import com.ncu.springboot.api.cache.keys.UserCacheIdKeys;
import com.ncu.springboot.biz.entity.User;
import com.ncu.springboot.utils.RedisUtil;

@Service
public class UserCacheHelper implements UserCacheBizService {
	
	@Autowired
	private RedisUtil redisUtil;

	@Override
	public void setUserInfoByToken(String token, User user) {
		redisUtil.set(UserCacheIdKeys.getUserInfoByTokenKey(token), user);
	}

	@Override
	public User getUserInfoByToken(String token) {
		return (User) redisUtil.get(UserCacheIdKeys.getUserInfoByTokenKey(token));
	}

	@Override
	public boolean deleteUserInfoByToken(String token) {
		if (redisUtil.del(UserCacheIdKeys.getUserInfoByTokenKey(token)) != 0) {
			return true;
		}
		
		return false;
	}

	@Override
	public boolean existsToken(String token) {
		return redisUtil.hasKey(UserCacheIdKeys.getUserInfoByTokenKey(token));
	}

	@Override
	public void delUsercodeByToken(String token) {
		redisUtil.del(UserCacheIdKeys.getUsercodeByTokenKey(token));
	}

	@Override
	public void setTokenByUsercode(String usercode, String token) {
		redisUtil.set(UserCacheIdKeys.getTokenByUsercodeKey(usercode), token);
	}

	@Override
	public String getTokenByUsercode(String usercode) {
		return (String) redisUtil.get(UserCacheIdKeys.getTokenByUsercodeKey(usercode));
	}

	@Override
	public void setUsercodeByToken(String token, String usercode) {
		redisUtil.set(UserCacheIdKeys.getUsercodeByTokenKey(token), usercode);
		redisUtil.expire(UserCacheIdKeys.getUsercodeByTokenKey(token), CacheConstants.TOKEN_VALIDATE_TIME);
	}

	@Override
	public String getUsercodeByToken(String token) {
		return (String) redisUtil.get(UserCacheIdKeys.getUsercodeByTokenKey(token));
	}

	@Override
	public void setUserInfoByUsercode(String usercode, User user) {
		redisUtil.set(UserCacheIdKeys.getUserInfoByUsercode(usercode), user);
	}

	@Override
	public User getUserInfoByUsercode(String usercode) {
		return (User) redisUtil.get(UserCacheIdKeys.getUserInfoByUsercode(usercode));
	}

	@Override
	public void delUserInfoByUsercode(String usercode) {
		redisUtil.del(UserCacheIdKeys.getUserInfoByUsercode(usercode));
	}

}
