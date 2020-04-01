package com.ncu.springboot.provider.message.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.ncu.springboot.api.message.entity.MessageDraft;
import com.ncu.springboot.dao.MessageDraftMapper;
import com.ncu.springboot.provider.message.service.MessageDraftService;

@Service
public class MessageDraftServiceImpl implements MessageDraftService{

	@Resource
	private MessageDraftMapper messageDraftMapper;

	public List<Map<String, Object>> queryList(Integer sendPerson,Integer size,Integer num) {
		return messageDraftMapper.queryList(sendPerson, size, num);
	}

	@Override
	public Integer getTotal(Integer sendPerson) {
		return messageDraftMapper.getTotal(sendPerson);
	}

	@Override
	public Integer addMessageDraft(MessageDraft messageDraft) {
		return messageDraftMapper.insert(messageDraft);
	}

	@Override
	public Integer delMessageDraft(List<Integer> ids) {
		return messageDraftMapper.deleteByPrimaryKey(ids);
	}

	@Override
	public Integer editMessageDraft(MessageDraft messageDraft) {
		return messageDraftMapper.updateByPrimaryKeySelective(messageDraft);
	}
	
}
