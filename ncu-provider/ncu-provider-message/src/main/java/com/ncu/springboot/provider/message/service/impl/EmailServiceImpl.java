package com.ncu.springboot.provider.message.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ncu.springboot.api.message.entity.MessageSendResult;
import com.ncu.springboot.provider.message.entity.validatecode.EmailCodeGenerator;
import com.ncu.springboot.provider.message.queue.EmailMessageBody;
import com.ncu.springboot.provider.message.queue.Message;
import com.ncu.springboot.provider.message.queue.MessageType;
import com.ncu.springboot.provider.message.service.CodeSender;
import com.ncu.springboot.provider.message.service.EmailService;
import com.ncu.springboot.provider.message.service.MessageQueueService;

@Service
public class EmailServiceImpl implements EmailService, CodeSender {

	@Autowired
	private EmailCodeGenerator emailCodeGenerator;

	@Resource
	private MessageQueueService messageQueueService;

	@Override
	public MessageSendResult sendEmailCode(String email) {
		return sendCode(email, emailCodeGenerator.generate().getCode());
	}

	@Override
	public MessageSendResult sendCode(String target, String code) {

		MessageSendResult result = new MessageSendResult();

		Message<EmailMessageBody> emailMessage = new Message<EmailMessageBody>();
		emailMessage.setWhat(MessageType.EMAIL_MESSAGE);
		emailMessage.setWhen(System.currentTimeMillis());
		EmailMessageBody emailMessageBody = new EmailMessageBody();
		emailMessageBody.setTo(target);
		emailMessageBody.setSubject("【南昌大学】验证码");
		emailMessageBody.setContent(code);

		emailMessage.setData(emailMessageBody);
		if (messageQueueService.enqueueMessage(emailMessage)) {
			return result.setCode("0").setMsg("邮件发送成功!");
		} else {
			return result.setCode("1").setMsg("邮件发送失败!");
		}

	}

	@Override
	public MessageSendResult sendEmailMessage(String to, String subject, String content, long when) {
		MessageSendResult result = new MessageSendResult();
		
		//链表
		Message<EmailMessageBody> emailMessage = new Message<EmailMessageBody>();
		emailMessage.setWhat(MessageType.EMAIL_MESSAGE);
		if (when == 0) {
			emailMessage.setWhen(System.currentTimeMillis());
		} else {
			emailMessage.setWhen(when);
		}
		EmailMessageBody emailMessageBody = new EmailMessageBody();
		emailMessageBody.setTo(to);
		emailMessageBody.setSubject(subject);
		emailMessageBody.setContent(content);
		
		//插入链表
		emailMessage.setData(emailMessageBody);
		if (messageQueueService.enqueueMessage(emailMessage)) {
			return result.setCode("0").setMsg("邮件发送成功!");
		} else {
			return result.setCode("1").setMsg("邮件发送失败!");
		}
	}

}
