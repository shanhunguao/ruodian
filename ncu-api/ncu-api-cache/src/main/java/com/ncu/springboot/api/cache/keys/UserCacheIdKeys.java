package com.ncu.springboot.api.cache.keys;

public interface UserCacheIdKeys extends CacheIdKeys {

	String NAMESPACE = CacheIdKeys.NAMESPACE + "user.";

	static String getUserInfoByTokenKey(String token) {
		return NAMESPACE + "token." + token + ".userinfo";
	}

	static String getUserRoleByTokenKey(String token) {
		return NAMESPACE + "token." + token + ".roles";
	}

	static String getTokenByUsercodeKey(String usercode) {
		return NAMESPACE + "usercode." + usercode + ".token";
	}
	
	static String getUsercodeByTokenKey(String token) {
		return NAMESPACE + "token." + token + ".usercode";
	}
	
	static String getUserInfoByUsercode(String usercode) {
		return NAMESPACE + "usercode." + usercode + ".userinfo";
	}

}
