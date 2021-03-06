package com.ncu.springboot.api.message.bizservice;

import java.util.List;
import java.util.Map;

import com.ncu.springboot.api.message.entity.TbMessageTemplate;

public interface MessageTemplateBizService{

	List<Map<String, Object>> queryList(String subject,Integer size,Integer num);
	
	Integer getTotal(String subject);
	
	Integer addMessageTemplate(TbMessageTemplate messageTemplate);
	
	Integer delMessageTemplate(List<Integer> ids);
	
	Integer editMessageTemplate(TbMessageTemplate messageTemplate);
}
