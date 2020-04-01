package com.ncu.springboot.provider.message.bizservice;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.osgi.service.component.annotations.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.ncu.springboot.api.message.bizservice.MessageDraftBizService;
import com.ncu.springboot.api.message.entity.MessageDraft;
import com.ncu.springboot.provider.message.service.MessageDraftService;

@Component
@Service
public class MessageDraftBizServiceImpe implements MessageDraftBizService {

	@Resource
	private MessageDraftService messageDraftService;
	
	public List<Map<String, Object>> queryList(Integer sendPerson,Integer size,Integer num) {
		if(size != null && num !=null) {
			num = (num-1)*size;
			}
		return messageDraftService.queryList(sendPerson,size,num);
	}

	@Override
	public Integer getTotal(Integer sendPerson) {
		return messageDraftService.getTotal(sendPerson);
	}

	@Override
	public Integer addMessageDraft(MessageDraft messageDraft) {
		return messageDraftService.addMessageDraft(messageDraft);
	}

	@Transactional
	public Integer delMessageDraft(List<Integer> ids) {
		return messageDraftService.delMessageDraft(ids);
	}

	@Override
	public Integer editMessageDraft(MessageDraft messageDraft) {
		return messageDraftService.editMessageDraft(messageDraft);
	}

	
}
