package com.ncu.springboot.provider.message.bizservice;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.ncu.springboot.api.message.bizservice.EmailBizService;
import com.ncu.springboot.api.message.entity.MessageSendResult;
import com.ncu.springboot.provider.message.service.EmailService;

@Component
@Service
public class EmailBizServiceImpl implements EmailBizService {
	
	@Resource
	private EmailService emailService;

	@Override
	public MessageSendResult sendEmailCode(String email) {
		return emailService.sendEmailCode(email);
	}

	@Override
	public MessageSendResult sendEmailMessage(String to, String subject, String content, long when) {
		return emailService.sendEmailMessage(to, subject, content, when);
	}

}
