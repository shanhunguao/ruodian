package com.ncu.springboot.provider.message.queue;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Component;

import com.ncu.springboot.api.message.entity.MessageLog;
import com.ncu.springboot.provider.message.entity.MailInfo;
import com.ncu.springboot.provider.message.service.impl.UnicomSMSCodeSender;
import com.ncu.springboot.provider.message.util.MailUtil;
import com.ncu.springboot.provider.message.util.WechatUtil;

@Component
public class MessageHandler {

	private MessageQueue queue;
	
	@Autowired
	private MailUtil mailUtil;
	
	@Autowired
	private WechatUtil wechatUtil;
	
	@Autowired
	private UnicomSMSCodeSender unicomSMSCodeSender;

	public boolean enqueueMessage(Message msg) {
		return queue.enqueueMessage(msg, msg.when);
	}

	public void handleMessage(Message msg) {
		switch (msg.what) {
		case MessageType.SMS_MESSAGE:
			SmsMessageBody smsBody = (SmsMessageBody) msg.getData();
			unicomSMSCodeSender.sendCode(smsBody.getMobile(), smsBody.getContent());
			break;
		case MessageType.EMAIL_MESSAGE:
			try {
				EmailMessageBody emailBody = (EmailMessageBody) msg.getData();
				MailInfo mailInfo = new MailInfo();
				mailInfo.setTo(emailBody.getTo());
				mailInfo.setSubject(emailBody.getSubject());
				mailInfo.setContent(emailBody.getContent());
				mailUtil.sendSimpleMail(mailInfo,emailBody.getId());
			} catch (MailException e) {
				e.printStackTrace();
			}
			
			//TODO:将code和邮箱地址存入redis
		case MessageType.WX_MESSAGE:
			WXMessageBody wxMessageBody = (WXMessageBody) msg.getData();
			try {
				wechatUtil.sendMessage(wxMessageBody.getMsg(), wxMessageBody.getTitle(),wxMessageBody.getImage(), wxMessageBody.getUser(),wxMessageBody.getUrl());
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
		MessageLog messageLog = new MessageLog();
		messageLog.setMessageId(msg.getMessageId());
	}

	public MessageQueue getQueue() {
		return queue;
	}

	public void setQueue(MessageQueue queue) {
		this.queue = queue;
	}

}
