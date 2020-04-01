package com.ncu.springboot.provider.message.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ncu.springboot.api.message.entity.MessageSendResult;
import com.ncu.springboot.api.message.entity.TbMessage;
import com.ncu.springboot.api.message.entity.TbMessageTo;
import com.ncu.springboot.common.util.Utils;
import com.ncu.springboot.core.util.DateUtil;
import com.ncu.springboot.provider.message.queue.Looper;
import com.ncu.springboot.provider.message.queue.Message;
import com.ncu.springboot.provider.message.queue.MessageHandler;
import com.ncu.springboot.provider.message.queue.MessageStatus;
import com.ncu.springboot.provider.message.service.MessageQueueService;
import com.ncu.springboot.provider.message.service.MessageService;

@Service
public class MessageQueueServiceImpl implements MessageQueueService {
	
	@Autowired
	private MessageHandler handler;
	
	private Looper looper;

	@Resource
	private MessageService messageService;
	
	@Override
	public void startService() {
		looper = new Looper(handler);
		looper.loop();
		initMessageQueue();
	}

	@Override
	public boolean enqueueMessage(Message msg) {
		return handler.enqueueMessage(msg);
	}

	@Transactional
	private void initMessageQueue() {
		List<Map<String, Object>> waitsendMessages = messageService.queryList( null,null,Utils.getTimeStamp().toString(), null, null, null, MessageStatus.WAIT_SEND,null,null);
		MessageSendResult result = new MessageSendResult();
		List<TbMessageTo> to = new ArrayList<TbMessageTo>();
		List<String> filePath = new ArrayList<String>();
		if(waitsendMessages==null||waitsendMessages.size()<1) {
			return;
		}
		if (waitsendMessages.get(0).get("filePath")!=null) {
			for (String path : waitsendMessages.get(0).get("filePath").toString().split(",")) {
				filePath.add(path);
			}
		}
		for (Map<String, Object> message : waitsendMessages) {
			TbMessageTo tbMessageTo = new TbMessageTo();
			tbMessageTo.setId(Integer.parseInt(message.get("toId").toString()));
			tbMessageTo.setReceiveTo(message.get("receiveTo").toString());
			TbMessage tbMessage = new TbMessage();
			if (message.get("content")!=null) {
				tbMessage.setContent(message.get("content").toString());
			}else if (message.get("url")!=null) {
				tbMessage.setUrl(message.get("url").toString());
			}else if (message.get("subject")!=null) {
				tbMessage.setSubject(message.get("subject").toString());
			}
			tbMessage.setMessageType(Integer.parseInt(message.get("messageType").toString()));
			to.add(tbMessageTo);
			result = messageService.sendMessage(to, DateUtil.strToLong(message.get("sendTime").toString(), DateUtil.YYYYMMDDHHMMSS),filePath,tbMessage);
			to.clear();
		}
		//超过时间
		List<Map<String, Object>> failMessages = messageService.queryList(null,Utils.getTimeStamp().toString(),null, null, null, null, MessageStatus.WAIT_SEND,null,null);
		for (Map<String, Object> message : failMessages) {
			TbMessageTo tbMessageTo = new TbMessageTo();
			tbMessageTo.setId(Integer.parseInt(message.get("toId").toString()));
			tbMessageTo.setStatus(MessageStatus.FAIL);
			messageService.editTbMessageTo(tbMessageTo);
		}
	}
	
}
