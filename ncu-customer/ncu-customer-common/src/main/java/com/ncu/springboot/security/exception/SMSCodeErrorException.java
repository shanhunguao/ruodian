package com.ncu.springboot.security.exception;

import org.springframework.security.core.AuthenticationException;

public class SMSCodeErrorException extends AuthenticationException {

	public SMSCodeErrorException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}
	
	public SMSCodeErrorException(String msg, Throwable t) {
		super(msg, t);
	}
	
}
