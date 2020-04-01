package com.ncu.springboot.provider.message.bizservice;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.ncu.springboot.api.message.bizservice.SMSBizService;
import com.ncu.springboot.api.message.entity.MessageSendResult;
import com.ncu.springboot.provider.message.service.SMSService;

@Component
@Service
public class SMSBizServiceImpl implements SMSBizService {
	
	@Resource
	private SMSService smsService;

	@Override
	public MessageSendResult sendSmsCode(String mobile) {
		return smsService.sendSMSCode(mobile);
	}

	@Override
	public MessageSendResult sendSmsMessage(String mobile, String content, long when) {
		return smsService.sendSmsMessage(mobile, content, when);
	}

}
