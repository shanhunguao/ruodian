package com.ncu.springboot.provider.message.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.ncu.springboot.api.message.entity.TbMessageTemplate;
import com.ncu.springboot.dao.MessageTemplateMapper;
import com.ncu.springboot.provider.message.service.MessageTemplateService;

@Service
public class MessageTemplateServiceImpl implements MessageTemplateService {
	
	@Resource
	private MessageTemplateMapper messageTemplateMapper;

	public List<Map<String, Object>> queryList(String subject, Integer size, Integer num) {
		return messageTemplateMapper.queryList(subject, size, num);
	}

	public Integer getTotal(String subject) {
		return messageTemplateMapper.getTotal(subject);
	}

	public Integer addMessageTemplate(TbMessageTemplate messageTemplate) {
		return messageTemplateMapper.insert(messageTemplate);
	}

	public Integer delMessageTemplate(List<Integer> ids) {
		Integer count = 0;
		for (Integer id : ids) {
			count += messageTemplateMapper.deleteByPrimaryKey(id);
		}
		return count;
	}

	public Integer editMessageTemplate(TbMessageTemplate messageTemplate) {
		return messageTemplateMapper.updateByPrimaryKeySelective(messageTemplate);
	}

}
