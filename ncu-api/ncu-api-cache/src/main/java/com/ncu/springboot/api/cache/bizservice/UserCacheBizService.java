package com.ncu.springboot.api.cache.bizservice;

import com.ncu.springboot.biz.entity.User;

public interface UserCacheBizService {

	void setTokenByUsercode(String usercode, String token);
	String getTokenByUsercode(String usercode);
	
	void setUsercodeByToken(String token, String usercode);
	String getUsercodeByToken(String token);
	
	void setUserInfoByUsercode(String usercode, User user);
	User getUserInfoByUsercode(String usercode);

	void setUserInfoByToken(String token, User user);
	User getUserInfoByToken(String token);

	boolean deleteUserInfoByToken(String token);

	boolean existsToken(String token);

	void delUsercodeByToken(String token);
	void delUserInfoByUsercode(String usercode);
}
