package com.ncu.springboot.customer.message.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ncu.springboot.api.message.bizservice.MessageDraftBizService;
import com.ncu.springboot.api.message.entity.MessageDraft;
import com.ncu.springboot.biz.rs.Res;
import com.ncu.springboot.common.util.Utils;

@RestController
@RequestMapping("/draft")
public class MessageDraftController {

	@Reference
	private MessageDraftBizService messageDraftBizService;
	
	@RequestMapping("/queryList")
	public Res queryList(Integer sendPerson,Integer size,Integer num) {
		List<Map<String, Object>> data = messageDraftBizService.queryList(sendPerson,size,num);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/getTotal")
	public Res getTotal(Integer sendPerson) {
		Integer data = messageDraftBizService.getTotal(sendPerson);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/delMessageDraft")
	public Res delMessageDraft(List<Integer> ids) {
		if(ids == null || ids.size()<1) {
			return Res.ERROR("1", "未选择对象！");
		}
		Integer data = messageDraftBizService.delMessageDraft(ids);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/addMessageDraft")
	public Res addMessageDraft(MessageDraft messageDraft) {
		messageDraft.setCreateTime(Utils.getTimeStamp());
		Integer data = messageDraftBizService.addMessageDraft(messageDraft);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/editMessageDraft")
	public Res editMessageDraft(MessageDraft messageDraft) {
		messageDraft.setUpdateTime(Utils.getTimeStamp());
		Integer data = messageDraftBizService.editMessageDraft(messageDraft);
		return Res.SUCCESS(data);
	}
}
