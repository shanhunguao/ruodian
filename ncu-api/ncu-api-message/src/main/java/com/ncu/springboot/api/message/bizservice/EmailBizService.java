package com.ncu.springboot.api.message.bizservice;

import com.ncu.springboot.api.message.entity.MessageSendResult;

public interface EmailBizService {

	// 发送邮箱验证码
	MessageSendResult sendEmailCode(String email);
	
	// 发送邮件
	MessageSendResult sendEmailMessage(String to, String subject, String content, long when);

}
