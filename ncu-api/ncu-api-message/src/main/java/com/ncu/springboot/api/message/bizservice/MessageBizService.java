package com.ncu.springboot.api.message.bizservice;

import java.util.List;
import java.util.Map;

public interface MessageBizService {
	
	Integer sendMessage(Integer messageType,String subject,String content,String sendTime,String sendPersonId,List<String> departments,List<String> persons,List<String> employees,List<String> imagePath,String url);

	List<Map<String, Object>> querylist(String endTime,String startTime,String sendPerson,String to,Integer deptId,Integer status,String subject,String content);

}
