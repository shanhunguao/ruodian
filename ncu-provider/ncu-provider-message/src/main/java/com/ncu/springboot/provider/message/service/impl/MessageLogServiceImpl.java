package com.ncu.springboot.provider.message.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.ncu.springboot.api.message.entity.MessageLog;
import com.ncu.springboot.dao.MessageLogMapper;
import com.ncu.springboot.provider.message.service.MessageLogService;

@Service
public class MessageLogServiceImpl implements MessageLogService {

	@Resource
	private MessageLogMapper messageLogMapper;
	
	public Integer getTotal(String employeeId, String employeeName, String messageId, String subject, String content,
			String operate, String endTime, String startTime,Integer deptId) {
		return messageLogMapper.getTotal(employeeId, employeeName, messageId, subject, content, operate, endTime, startTime,deptId);
	}

	public List<Map<String, Object>> queryList(String employeeId, String employeeName, String messageId, String subject,
			String content, String operate, String endTime, String startTime,Integer deptId, Integer size, Integer num) {
		return messageLogMapper.queryList(employeeId, employeeName, messageId, subject, content, operate, endTime, startTime, deptId,size, num);
	}

	public Integer addMessageLog(MessageLog messageLog) {
		return messageLogMapper.insert(messageLog);
	}

}
