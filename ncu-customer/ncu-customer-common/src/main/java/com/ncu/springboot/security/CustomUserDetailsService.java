package com.ncu.springboot.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.ncu.springboot.security.exception.MobileNotFoundException;

public interface CustomUserDetailsService extends UserDetailsService {
	
	UserDetails loadUserByMobile(String mobile, String smsCode) throws MobileNotFoundException;
	
}
