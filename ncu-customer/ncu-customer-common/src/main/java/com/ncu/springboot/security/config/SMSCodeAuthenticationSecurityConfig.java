package com.ncu.springboot.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.ncu.springboot.security.AjaxAuthenticationFailureHandler;
import com.ncu.springboot.security.AjaxAuthenticationSuccessHandler;
import com.ncu.springboot.security.UserDetailsServiceImpl;
import com.ncu.springboot.security.smscode.SMSCodeAuthenticationFilter;
import com.ncu.springboot.security.smscode.SMSCodeAuthenticationProvider;

@Component
public class SMSCodeAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
	
	@Autowired
    AjaxAuthenticationSuccessHandler authenticationSuccessHandler;
    
    @Autowired
    AjaxAuthenticationFailureHandler authenticationFailureHandler;
    
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    
	@Override
	public void configure(HttpSecurity http) throws Exception {
		
		SMSCodeAuthenticationFilter smsCodeAuthenticationFilter = new SMSCodeAuthenticationFilter();
		smsCodeAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
		smsCodeAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
		smsCodeAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
		
		SMSCodeAuthenticationProvider smsCodeAuthenticationProvider = new SMSCodeAuthenticationProvider();
		smsCodeAuthenticationProvider.setUserDetailsService(userDetailsService);
		
		http.authenticationProvider(smsCodeAuthenticationProvider).addFilterAfter(smsCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		
	}
	
}
