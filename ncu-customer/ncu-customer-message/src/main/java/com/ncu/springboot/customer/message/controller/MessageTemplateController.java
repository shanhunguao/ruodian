package com.ncu.springboot.customer.message.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ncu.springboot.api.message.bizservice.MessageTemplateBizService;
import com.ncu.springboot.api.message.entity.TbMessageTemplate;
import com.ncu.springboot.biz.rs.Res;
import com.ncu.springboot.common.util.Utils;

@RestController
@RequestMapping("/template")
public class MessageTemplateController {

	@Reference
	private MessageTemplateBizService messageTemplateBizService;
	
	@RequestMapping("/queryList")
	public Res queryList(String subject,Integer size,Integer num) {
		List<Map<String, Object>> data = messageTemplateBizService.queryList(subject, size, num);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/getTotal")
	public Res getTotal(String subject) {
		Integer data = messageTemplateBizService.getTotal(subject);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/delMessageTemplate")
	public Res delMessageTemplate(List<Integer> ids) {
		if(ids == null || ids.size()<1) {
			return Res.ERROR("1", "未选择对象！");
		}
		Integer data = messageTemplateBizService.delMessageTemplate(ids);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/addMessageTemplate")
	public Res addMessageTemplate(TbMessageTemplate messageTemplate) {
		messageTemplate.setCreateTime(Utils.getTimeStamp());
		Integer data = messageTemplateBizService.addMessageTemplate(messageTemplate);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/editMessageTemplate")
	public Res editMessageTemplate(TbMessageTemplate messageTemplate) {
		messageTemplate.setUpdateTime(Utils.getTimeStamp());
		Integer data = messageTemplateBizService.editMessageTemplate(messageTemplate);
		return Res.SUCCESS(data);
	}
}
