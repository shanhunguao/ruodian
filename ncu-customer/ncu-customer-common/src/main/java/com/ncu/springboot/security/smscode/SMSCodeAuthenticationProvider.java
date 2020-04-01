package com.ncu.springboot.security.smscode;

import java.util.Objects;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import com.ncu.springboot.security.UserDetailsServiceImpl;

public class SMSCodeAuthenticationProvider implements AuthenticationProvider {

	private UserDetailsServiceImpl userDetailsService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		System.out.println("eeeeeeeeeeeeeeeeeeeee");

		SMSCodeAuthenticationToken authenticationToken = (SMSCodeAuthenticationToken) authentication;

		UserDetails userDetails = userDetailsService.loadUserByMobile((String) authenticationToken.getPrincipal(), authenticationToken.getSmscode());
		
		System.out.println("fffffffffffffffffffffffffff");

		if (Objects.isNull(userDetails)) {
			throw new InternalAuthenticationServiceException("无法获取用户信息");
		}
		
		System.out.println("gggggggggggggggggggggggggg");

		SMSCodeAuthenticationToken authenticationResult = new SMSCodeAuthenticationToken(userDetails,
				userDetails.getAuthorities());
		authenticationResult.setDetails(authenticationToken.getDetails());
		return authenticationResult;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return SMSCodeAuthenticationToken.class.isAssignableFrom(authentication);
	}

	public UserDetailsServiceImpl getUserDetailsService() {
		return userDetailsService;
	}

	public void setUserDetailsService(UserDetailsServiceImpl userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

}
