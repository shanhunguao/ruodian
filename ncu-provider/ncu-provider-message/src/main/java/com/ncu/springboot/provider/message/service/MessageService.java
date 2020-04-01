package com.ncu.springboot.provider.message.service;

import java.util.List;
import java.util.Map;

import com.ncu.springboot.api.message.entity.MessageFIle;
import com.ncu.springboot.api.message.entity.MessageSendResult;
import com.ncu.springboot.api.message.entity.TbMessage;
import com.ncu.springboot.api.message.entity.TbMessageObject;
import com.ncu.springboot.api.message.entity.TbMessageTo;

public interface MessageService {
	
	Integer addMessage(TbMessage tbMessage,List<TbMessageObject> tbMessageObjects,List<MessageFIle> files);
	
	MessageSendResult sendMessage(List<TbMessageTo> to, long when,List<String> filePath,TbMessage tbMessage);
	
	List<Map<String, Object>> queryList(Integer messageId,String endTime,String startTime,String sendPerson,String to,Integer deptId,Integer status,String subject,String content);
	
	Integer editTbMessageTo(TbMessageTo tbMessageTo);
	
	Integer editTbMessage(TbMessage tbMessage);

}
