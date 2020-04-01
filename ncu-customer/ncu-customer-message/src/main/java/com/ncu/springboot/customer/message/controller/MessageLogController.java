package com.ncu.springboot.customer.message.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ncu.springboot.api.message.bizservice.MessageLogBizService;
import com.ncu.springboot.biz.rs.Res;

@RestController
@RequestMapping("/messageLog")
public class MessageLogController {

	@Reference
	private MessageLogBizService messageLogBizService;

	public Res getTotal(String employeeId,String employeeName,String messageId,String subject,String content,String operate,String endTime,String startTime,Integer deptId) {
		Integer data = messageLogBizService.getTotal(employeeId, employeeName, messageId, subject, content, operate, endTime, startTime,deptId);
		return Res.SUCCESS(data);
	}

	public Res queryList(String employeeId,String employeeName,String messageId,String subject,String content,String operate,String endTime,String startTime,Integer deptId,Integer size,Integer num) {
		List<Map<String, Object>> data = messageLogBizService.queryList(employeeId, employeeName, messageId, subject, content, operate, endTime, startTime,deptId,size,num);
		return Res.SUCCESS(data);
	}

}
