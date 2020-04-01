package com.ncu.springboot.provider.message.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ncu.springboot.api.message.entity.MessageSendResult;
import com.ncu.springboot.api.oauth2.bizservice.UserBizService;
import com.ncu.springboot.biz.error.LoginErrorCode;
import com.ncu.springboot.provider.message.entity.validatecode.SMSCodeGenerator;
import com.ncu.springboot.provider.message.entity.validatecode.ValidateCode;
import com.ncu.springboot.provider.message.queue.Message;
import com.ncu.springboot.provider.message.queue.MessageType;
import com.ncu.springboot.provider.message.queue.SmsMessageBody;
import com.ncu.springboot.provider.message.service.MessageQueueService;
import com.ncu.springboot.provider.message.service.SMSService;

@Service
public class SMSServiceImpl implements SMSService {

	@Autowired
	private SMSCodeGenerator smsCodeGenerator;

	@Reference
	private UserBizService userBizService;

	@Resource
	private MessageQueueService messageQueueService;

	@Override
	public MessageSendResult sendSMSCode(String mobile) {

		MessageSendResult result = new MessageSendResult();

		if (userBizService.checkMobileExist(mobile)) {
			ValidateCode code = smsCodeGenerator.generate();

			Message<SmsMessageBody> smsMessage = new Message<SmsMessageBody>();
			smsMessage.setWhat(MessageType.SMS_MESSAGE);
			smsMessage.setWhen(System.currentTimeMillis());
			SmsMessageBody smsMessageBody = new SmsMessageBody();
			smsMessageBody.setMobile(mobile);
			smsMessageBody.setContent(code.getCode());
			smsMessage.setData(smsMessageBody);
			if (messageQueueService.enqueueMessage(smsMessage)) {
				return result.setCode("0").setMsg("短信发送成功!");
			} else {
				return result.setCode("0").setMsg("短信发送成功!");
			}

		} else {
			return new MessageSendResult(String.valueOf(LoginErrorCode.MOBILE_ERROR.getErrorCode()),
					LoginErrorCode.MOBILE_ERROR.getErrorMsg());
		}
	}

	@Override
	public MessageSendResult sendSmsMessage(String mobile, String content, long when) {
		MessageSendResult result = new MessageSendResult();

		if (userBizService.checkMobileExist(mobile)) {
			Message<SmsMessageBody> smsMessage = new Message<SmsMessageBody>();
			smsMessage.setWhat(MessageType.SMS_MESSAGE);
			if (when == 0) {
				smsMessage.setWhen(System.currentTimeMillis());
			} else {
				smsMessage.setWhen(when);
			}
			SmsMessageBody smsMessageBody = new SmsMessageBody();
			smsMessageBody.setMobile(mobile);
			smsMessageBody.setContent(content);
			smsMessage.setData(smsMessageBody);
			if (messageQueueService.enqueueMessage(smsMessage)) {
				return result.setCode("0").setMsg("短信发送成功!");
			} else {
				return result.setCode("0").setMsg("短信发送成功!");
			}

		} else {
			return new MessageSendResult(String.valueOf(LoginErrorCode.MOBILE_ERROR.getErrorCode()),
					LoginErrorCode.MOBILE_ERROR.getErrorMsg());
		}
	}

}
