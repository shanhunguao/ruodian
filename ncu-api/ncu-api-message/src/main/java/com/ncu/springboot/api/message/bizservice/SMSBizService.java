package com.ncu.springboot.api.message.bizservice;

import com.ncu.springboot.api.message.entity.MessageSendResult;

public interface SMSBizService {

	// 发送短信验证码
	MessageSendResult sendSmsCode(String mobile);

	// 发送短消息
	MessageSendResult sendSmsMessage(String mobile, String content, long when);

}
