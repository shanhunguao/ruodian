package com.ncu.springboot.api.message.bizservice;

import java.util.List;
import java.util.Map;

import com.ncu.springboot.api.message.entity.MessageDraft;

public interface MessageDraftBizService {
	
	List<Map<String, Object>> queryList(Integer sendPerson,Integer size,Integer num);
	
	Integer getTotal(Integer sendPerson);
	
	Integer addMessageDraft(MessageDraft messageDraft);
	
	Integer delMessageDraft(List<Integer> ids);
	
	Integer editMessageDraft(MessageDraft messageDraft);
}
