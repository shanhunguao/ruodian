package com.ncu.springboot.provider.message.service;

import com.ncu.springboot.api.message.entity.MessageSendResult;

public interface EmailService {
	
	MessageSendResult sendEmailCode(String email);
	
	MessageSendResult sendEmailMessage(String to, String subject, String content, long when);
	
}
