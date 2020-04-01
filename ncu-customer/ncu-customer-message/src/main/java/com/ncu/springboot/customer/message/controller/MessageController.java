package com.ncu.springboot.customer.message.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ncu.springboot.api.message.bizservice.MessageBizService;
import com.ncu.springboot.biz.rs.Res;
import com.ncu.springboot.customer.message.form.MessageForm;

@RestController
@RequestMapping("/message")
public class MessageController {
	
	@Reference
	private MessageBizService messageBizService;
	
	@RequestMapping("/sendMessage")
	public Res sendMessage(MessageForm messageForm) {
		Integer result = messageBizService.sendMessage(messageForm.getMessageType(),messageForm.getSubject(),messageForm.getContent(),messageForm.getSendTime(),messageForm.getSendPersonId(),messageForm.getDepartments(),messageForm.getPersons(),messageForm.getEmployees(),
				messageForm.getImagePath(),messageForm.getUrl());
		if (result > 0) {
			return Res.SUCCESS("消息添加成功，等待审核");
		} else {
			return Res.ERROR("数据错误，消息发送失败");
		}
	}
	
	@RequestMapping("/queryList")
	public Res queryList(String endTime,String startTime,String sendPerson,String to,Integer deptId,Integer status,String subject,String content) {
		List<Map<String, Object>> data = messageBizService.querylist(endTime,startTime,sendPerson,to,deptId,status,subject,content);
		return Res.SUCCESS(data);
	}

}
