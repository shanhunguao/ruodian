package com.ncu.springboot.provider.message.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ncu.springboot.api.message.entity.TbMessage;
import com.ncu.springboot.api.message.entity.TbMessageObject;
import com.ncu.springboot.api.message.entity.TbMessageTo;
import com.ncu.springboot.api.oauth2.bizservice.EmployeeBizService;
import com.ncu.springboot.biz.entity.UserInfos;
import com.ncu.springboot.common.util.Utils;
import com.ncu.springboot.provider.message.queue.MessageStatus;
import com.ncu.springboot.provider.message.queue.MessageType;
import com.ncu.springboot.provider.message.queue.ReceviceType;

@Component
public class MessageUtil {
	
	@Reference
	private EmployeeBizService employeeBizService;

	public List<TbMessageObject> getTbMessageObjects(List<String> departments, List<String> persons, List<String> employees,Integer messageType) {
		List<TbMessageObject> tbMessageObjects = new ArrayList<TbMessageObject>();
		if (departments != null) {
			for (String department : departments) {
				TbMessageObject tbMessageObject = new TbMessageObject();
				tbMessageObject.setReceiveObject(department);
				tbMessageObject.setReceiveType(ReceviceType.DEPT);
				//获取接收对象
				tbMessageObject.setTbMessageTos(getSendPerson(department,ReceviceType.DEPT,messageType));
				tbMessageObjects.add(tbMessageObject);
			}
		}
		if (persons != null && MessageType.SYSTEM_MESSAGE != messageType) {
			for (String person : persons) {
				TbMessageObject tbMessageObject = new TbMessageObject();
				tbMessageObject.setReceiveObject(person);
				tbMessageObject.setReceiveType(ReceviceType.PERSON);
				List<TbMessageTo> tbMessageTos = new ArrayList<TbMessageTo>();
				TbMessageTo tbMessageTo = new TbMessageTo();
				tbMessageTo.setReceiveTo(person);
				tbMessageTo.setStatus(MessageStatus.WAIT_CHECK);
				tbMessageTos.add(tbMessageTo);
				tbMessageObject.setTbMessageTos(tbMessageTos);
				tbMessageObjects.add(tbMessageObject);
			}
		}
		if (employees != null) {
			for (String employee : employees) {
				TbMessageObject tbMessageObject = new TbMessageObject();
				tbMessageObject.setReceiveObject(employee);
				tbMessageObject.setReceiveType(ReceviceType.EMPLOYEE);
				tbMessageObject.setTbMessageTos(getSendPerson(employee,ReceviceType.EMPLOYEE,messageType));
				tbMessageObjects.add(tbMessageObject);
			}
		}
		return tbMessageObjects;
	}

	public List<TbMessageTo> getSendPerson(String object , int receviceType,Integer messageType) {
		List<TbMessageTo> tbMessageTos = new ArrayList<TbMessageTo>();
		List<String> tos = new ArrayList<String>();
		List<UserInfos> userInfos = new ArrayList<UserInfos>();
		//根据对象类型通过不同方式获取数据
		switch (receviceType) {
		case ReceviceType.DEPT:
			UserInfos deptExample = new UserInfos();
			deptExample.setDeptId(object);
			userInfos = employeeBizService.find(deptExample);
			break;
		case ReceviceType.EMPLOYEE:
			UserInfos employeeExample = new UserInfos();
			employeeExample.setEmployeeId(object);
			userInfos = employeeBizService.find(employeeExample);
			break;
		default:
			break;
		}

		//根据消息类型获取邮箱或者手机
		switch (messageType) {
		case MessageType.EMAIL_MESSAGE:
			for (UserInfos userInfo : userInfos) {
				tos.add(userInfo.getEmail());
			}
			break;
		case MessageType.SMS_MESSAGE:
			for (UserInfos userInfo : userInfos) {
				tos.add(userInfo.getMobile());
			}
			break;
		case MessageType.SYSTEM_MESSAGE:
			for (UserInfos userInfo : userInfos) {
				tos.add(userInfo.getEmployeeId());
			}
			break;
		case MessageType.WX_MESSAGE:
			for (UserInfos userInfo : userInfos) {
				tos.add(userInfo.getEmployeeId());
			}
			break;
		default:
			break;
		}
		
		//去重
		List<String> newList = tos.stream().distinct().collect(Collectors.toList()); 
		
		for (String to : newList) {
			TbMessageTo tbMessageTo = new TbMessageTo();
			tbMessageTo.setReceiveTo(to);
			tbMessageTo.setStatus(MessageStatus.WAIT_CHECK);
			tbMessageTos.add(tbMessageTo);
		}

		return tbMessageTos;
	}

	public TbMessage getTbMessage(Integer messageType, String subject, String content, String sendTime,
			String sendPersonId,String url) {
		TbMessage tbMessage = new TbMessage();
		tbMessage.setContent(content);
		tbMessage.setCreateTime(Utils.getTimeStamp());
		tbMessage.setMessageType(messageType);
		tbMessage.setSendPersonId(sendPersonId);
		tbMessage.setSendTime(Utils.getTimesStamp(sendTime));
		tbMessage.setStatus(MessageStatus.WAIT_CHECK);
		tbMessage.setSubject(subject);
		tbMessage.setUrl(url);
		return tbMessage;
	}
}
