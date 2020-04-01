package com.ncu.springboot.provider.message.bizservice;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.ncu.springboot.api.message.bizservice.MessageTemplateBizService;
import com.ncu.springboot.api.message.entity.TbMessageTemplate;
import com.ncu.springboot.provider.message.service.MessageTemplateService;

@Component
@Service
public class MessageTemplateBizServiceImpl implements MessageTemplateBizService{
	
	@Resource
	private MessageTemplateService messageTemplateService;

	public List<Map<String, Object>> queryList(String subject, Integer size, Integer num) {
		if(size != null && num !=null) {
			num = (num-1)*size;
			}
		return messageTemplateService.queryList(subject, size, num);
	}

	public Integer getTotal(String subject) {
		return messageTemplateService.getTotal(subject);
	}

	public Integer addMessageTemplate(TbMessageTemplate messageTemplate) {
		return messageTemplateService.addMessageTemplate(messageTemplate);
	}

	@Transactional
	public Integer delMessageTemplate(List<Integer> ids) {
		return messageTemplateService.delMessageTemplate(ids);
	}

	public Integer editMessageTemplate(TbMessageTemplate messageTemplate) {
		return messageTemplateService.editMessageTemplate(messageTemplate);
	}

}
