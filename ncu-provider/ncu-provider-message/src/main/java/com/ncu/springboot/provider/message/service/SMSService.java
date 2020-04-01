package com.ncu.springboot.provider.message.service;

import com.ncu.springboot.api.message.entity.MessageSendResult;

public interface SMSService {
	
	MessageSendResult sendSMSCode(String mobile);
	
	MessageSendResult sendSmsMessage(String mobile, String content, long when);
	
}
