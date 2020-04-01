package com.ncu.springboot.provider.message.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.ncu.springboot.api.message.entity.MessageCheck;
import com.ncu.springboot.dao.MessageCheckMapper;
import com.ncu.springboot.provider.message.service.MessageCheckService;

@Service
public class MessageCheckServiceImpl implements MessageCheckService{

	@Resource
	private MessageCheckMapper messageCheckMapper;
	
	public List<MessageCheck> queryList(String employeeId, Integer deptId) {
		MessageCheck messageCheck = new MessageCheck();
		messageCheck.setEmployeeId(employeeId);
		messageCheck.setDeptId(deptId);
		return messageCheckMapper.select(messageCheck);
	}

	@Override
	public Integer addMessageCheck(List<MessageCheck> messageCheck) {
		return messageCheckMapper.insertList(messageCheck);
	}

	@Override
	public Integer delMessageCheck(List<Integer> ids) {
		return messageCheckMapper.deleteByPrimaryKey(ids);
	}

}
