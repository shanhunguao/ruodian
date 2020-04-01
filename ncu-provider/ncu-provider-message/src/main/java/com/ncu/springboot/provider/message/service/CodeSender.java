package com.ncu.springboot.provider.message.service;

import com.ncu.springboot.api.message.entity.MessageSendResult;

public interface CodeSender {
	
	MessageSendResult sendCode(String target, String code);
	
}
