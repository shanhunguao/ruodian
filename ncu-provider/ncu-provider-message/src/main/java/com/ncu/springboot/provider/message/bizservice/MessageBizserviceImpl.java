package com.ncu.springboot.provider.message.bizservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.ncu.springboot.api.message.bizservice.EmailBizService;
import com.ncu.springboot.api.message.bizservice.MessageBizService;
import com.ncu.springboot.api.message.entity.MessageFIle;
import com.ncu.springboot.api.message.entity.TbMessage;
import com.ncu.springboot.api.message.entity.TbMessageObject;
import com.ncu.springboot.provider.message.service.MessageService;
import com.ncu.springboot.provider.message.service.SMSService;
import com.ncu.springboot.provider.message.util.MessageUtil;

@Component
@Service
public class MessageBizserviceImpl implements MessageBizService{

	@Resource
	private MessageService messageService;

	@Resource
	private EmailBizService emailBizService;

	@Resource
	private SMSService smsService;
	
	@Autowired
	private MessageUtil messageUtil;

	@Transactional
	public Integer sendMessage(Integer messageType, String subject, String content, String sendTime,
			String sendPersonId, List<String> departments, List<String> persons, List<String> employees,List<String> imagePaths,String url) {

		TbMessage tbMessage = messageUtil.getTbMessage(messageType, subject, content, sendTime, sendPersonId,url);
		List<MessageFIle> image = new ArrayList<MessageFIle>();
		if (imagePaths!=null) {
			for (String imagePath : imagePaths) {
				MessageFIle messageFIle = new MessageFIle();
				messageFIle.setFilePath(imagePath);
				messageFIle.setFileType("image");
				image.add(messageFIle);
			}
		}
		List<TbMessageObject> tbMessageObjects = messageUtil.getTbMessageObjects(departments, persons, employees,messageType);

		return messageService.addMessage(tbMessage, tbMessageObjects,image);
	}

	public List<Map<String, Object>> querylist(String endTime, String startTime, String sendPerson, String to,
			Integer deptId, Integer status,String subject,String content) {
		return messageService.queryList(null,endTime, startTime, sendPerson, to, deptId, status,subject,content);
	}

	




}
