package com.ncu.springboot.security.exception;

import org.springframework.security.core.AuthenticationException;

public class MobileNotFoundException extends AuthenticationException {
	
	public MobileNotFoundException(String msg) {
		super(msg);
	}

	/**
	 * Constructs a {@code UsernameNotFoundException} with the specified message and root
	 * cause.
	 *
	 * @param msg the detail message.
	 * @param t root cause
	 */
	public MobileNotFoundException(String msg, Throwable t) {
		super(msg, t);
	}
	
}
