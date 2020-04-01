package com.ncu.springboot.provider.message.bizservice;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.osgi.service.component.annotations.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.ncu.springboot.api.message.entity.MessageLog;
import com.ncu.springboot.provider.message.service.MessageLogService;

@Component
@Service
public class MessageLogBizserverImpl implements MessageLogService{
	
	@Resource
	private MessageLogService messageLogService;
	
	public Integer getTotal(String employeeId,String employeeName,String messageId,String subject,String content,String operate,String endTime,String startTime,Integer deptId) {
		return messageLogService.getTotal(employeeId, employeeName, messageId, subject, content, operate, endTime, startTime,deptId);
	}
	
	public List<Map<String, Object>> queryList(String employeeId,String employeeName,String messageId,String subject,String content,String operate,String endTime,String startTime,Integer deptId,Integer size,Integer num) {
		if(size != null && num !=null) {
			num = (num-1)*size;
			}
		return messageLogService.queryList(employeeId, employeeName, messageId, subject, content, operate, endTime, startTime,deptId, size, num);
	}

	public Integer addMessageLog(MessageLog messageLog) {
		return messageLogService.addMessageLog(messageLog);
	}

}
