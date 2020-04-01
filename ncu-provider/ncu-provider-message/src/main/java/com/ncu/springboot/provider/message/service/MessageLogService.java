package com.ncu.springboot.provider.message.service;

import java.util.List;
import java.util.Map;

import com.ncu.springboot.api.message.entity.MessageLog;

public interface MessageLogService {

	Integer getTotal(String employeeId,String employeeName,String messageId,String subject,String content,String operate,String endTime,String startTime,Integer deptId);
	
	List<Map<String, Object>> queryList(String employeeId,String employeeName,String messageId,String subject,String content,String operate,String endTime,String startTime,Integer deptId,Integer size,Integer num);
	
	Integer addMessageLog(MessageLog messageLog);
}
