package com.ncu.springboot.provider.message.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.ncu.springboot.api.message.bizservice.MessageLogBizService;
import com.ncu.springboot.api.message.entity.MessageFIle;
import com.ncu.springboot.api.message.entity.MessageSendResult;
import com.ncu.springboot.api.message.entity.TbMessage;
import com.ncu.springboot.api.message.entity.TbMessageObject;
import com.ncu.springboot.api.message.entity.TbMessageTo;
import com.ncu.springboot.common.util.Utils;
import com.ncu.springboot.api.message.entity.MessageLog;
import com.ncu.springboot.dao.MessageFileMapper;
import com.ncu.springboot.dao.TbMessageMapper;
import com.ncu.springboot.dao.TbMessageObjectMapper;
import com.ncu.springboot.dao.TbMessageToMapper;
import com.ncu.springboot.provider.message.queue.EmailMessageBody;
import com.ncu.springboot.provider.message.queue.Message;
import com.ncu.springboot.provider.message.queue.MessageStatus;
import com.ncu.springboot.provider.message.queue.MessageType;
import com.ncu.springboot.provider.message.queue.SmsMessageBody;
import com.ncu.springboot.provider.message.queue.WXMessageBody;
import com.ncu.springboot.provider.message.service.MessageQueueService;
import com.ncu.springboot.provider.message.service.MessageService;

import tk.mybatis.mapper.entity.Example;

@Service
public class MessageServiceImpl implements MessageService {

	@Resource
	private TbMessageMapper tbMessageMapper;

	@Resource
	private TbMessageObjectMapper tbMessageObjectMapper;

	@Resource 
	private TbMessageToMapper  tbMessageToMapper;

	@Resource
	private MessageQueueService messageQueueService;
	
	@Resource
	private MessageFileMapper messageFileMapper;

	public Integer addMessage(TbMessage tbMessage, List<TbMessageObject> tbMessageObjects,List<MessageFIle> fIles) {
		Integer count = 0;
		tbMessageMapper.insertUseGeneratedKeys(tbMessage);
		for (TbMessageObject tbMessageObject : tbMessageObjects) {
			tbMessageObject.setMessageId(tbMessage.getMessageId());
			tbMessageObjectMapper.insertUseGeneratedKeys(tbMessageObject);
			if (tbMessageObject.getTbMessageTos()!=null) {
				for (TbMessageTo tbMessageTo : tbMessageObject.getTbMessageTos()) {
					tbMessageTo.setMessageId(tbMessage.getMessageId());
					tbMessageTo.setObjectId(tbMessageObject.getObjectId());
					count += tbMessageToMapper.insertUseGeneratedKeys(tbMessageTo);
				}
			}
		}
		for (MessageFIle messageFIle : fIles) {
			messageFIle.setMessageId(tbMessage.getMessageId());
			messageFileMapper.insert(messageFIle);
		}
		//添加消息记录
		MessageLog messageLog = new MessageLog();
		messageLog.setMessageId(tbMessage.getMessageId());
		messageLog.setOperate("发送消息");
		messageLog.setOperatePersionId(tbMessage.getSendPersonId());
		messageLog.setOperateTime(Utils.getTimeStamp());
		return count;
	}

	public MessageSendResult sendMessage(List<TbMessageTo> to, long when,List<String> filePath,TbMessage tbMessage) {
		MessageSendResult result = new MessageSendResult();

		if (to==null || to.size()<1) {
			return result.setCode("1").setMsg("该条信息没有发送对象！!");
		}
		
		Example example = new Example(TbMessageTo.class);
		example.createCriteria().andEqualTo("messageId", to.get(0).getMessageId());
		TbMessageTo messageTo = new TbMessageTo();
		messageTo.setStatus(MessageStatus.WAIT_SEND);
		tbMessageToMapper.updateByExampleSelective(messageTo, example);
		
		
		boolean flag = true;
		
		switch (tbMessage.getMessageType()) {
		case MessageType.EMAIL_MESSAGE:
			for (TbMessageTo tbMessageTo : to) {
				//链表
				Message<EmailMessageBody> emailMessage = new Message<EmailMessageBody>();
				emailMessage.setWhat(MessageType.EMAIL_MESSAGE);
				emailMessage.setWhen(when);
				emailMessage.setMessageId(tbMessage.getMessageId());
				EmailMessageBody emailMessageBody = new EmailMessageBody();
				emailMessageBody.setTo(tbMessageTo.getReceiveTo());
				emailMessageBody.setSubject(tbMessage.getSubject());
				emailMessageBody.setContent(tbMessage.getContent());
				emailMessageBody.setId(tbMessageTo.getId());

				//插入链表
				emailMessage.setData(emailMessageBody);
				if (!messageQueueService.enqueueMessage(emailMessage)) {
					flag = false;
					break;
				}
			}
			break;
		case MessageType.SMS_MESSAGE:
			for (TbMessageTo tbMessageTo : to) {
				Message<SmsMessageBody> smsMessage = new Message<SmsMessageBody>();
				smsMessage.setWhat(MessageType.SMS_MESSAGE);
				smsMessage.setWhen(when);
				smsMessage.setMessageId(tbMessage.getMessageId());
				SmsMessageBody smsMessageBody = new SmsMessageBody();
				smsMessageBody.setMobile(tbMessageTo.getReceiveTo());
				smsMessageBody.setContent(tbMessage.getContent());
				
				smsMessage.setData(smsMessageBody);
				if (!messageQueueService.enqueueMessage(smsMessage)) {
					flag = false;
					break;
				}
			}
			break;
		case MessageType.WX_MESSAGE:
			List<String> touser = new ArrayList<String>();
			for (TbMessageTo tbMessageTo : to) {
				touser.add(tbMessageTo.getReceiveTo());
			}
			Message<WXMessageBody> wxMessage = new Message<WXMessageBody>();
			wxMessage.setWhen(when);
			wxMessage.setWhat(MessageType.WX_MESSAGE);
			wxMessage.setMessageId(tbMessage.getMessageId());
			WXMessageBody wxMessageBody = new WXMessageBody();
			wxMessageBody.setMsg(tbMessage.getContent());
			wxMessageBody.setUser(touser);
			wxMessageBody.setImage(filePath);
			wxMessageBody.setTitle(tbMessage.getSubject());
			wxMessageBody.setUrl(tbMessage.getUrl());
			wxMessage.setData(wxMessageBody);
			if (!messageQueueService.enqueueMessage(wxMessage)) {
				flag = false;
				break;
			}
			break;
		case MessageType.SYSTEM_MESSAGE:
			TbMessageTo tbMessageTo = new TbMessageTo();
			tbMessageTo.setStatus(MessageStatus.SENT);
			tbMessageToMapper.updateByExampleSelective(tbMessageTo, example);
			break;
		default:
			break;
		}
		

		if (flag) {
			return result.setCode("0").setMsg("发送成功!");
		}else {

			return result.setCode("1").setMsg("部分发送失败!");
		}

	}

	public List<Map<String, Object>> queryList(Integer messageId,String endTime,String startTime,String from,String to,Integer deptId,Integer status,String subject,String content){
		return tbMessageMapper.queryList(messageId,endTime, startTime, from, to, deptId,status,subject,content);
	}

	public Integer editTbMessageTo(TbMessageTo tbMessageTo) {
		return tbMessageToMapper.updateByPrimaryKeySelective(tbMessageTo);
	}

	@Override
	public Integer editTbMessage(TbMessage tbMessage) {
		return tbMessageMapper.updateByPrimaryKeySelective(tbMessage);
	}

}
