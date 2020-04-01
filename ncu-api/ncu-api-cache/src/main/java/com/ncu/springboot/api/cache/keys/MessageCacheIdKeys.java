package com.ncu.springboot.api.cache.keys;

public interface MessageCacheIdKeys extends CacheIdKeys {

	String NAMESPACE = CacheIdKeys.NAMESPACE + "message.";

	static String getSMSCodeByMobileKey(String mobile) {
		return NAMESPACE + "mobile." + mobile + ".smscode";
	}

}
