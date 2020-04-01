package com.ncu.springboot.provider.message.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.ncu.springboot.api.message.entity.MessageDeptNumber;
import com.ncu.springboot.dao.MessageDeptNumberMapper;
import com.ncu.springboot.provider.message.service.MessageDeptNumberService;

@Service
public class MessageDeptNumberServiceImpl implements MessageDeptNumberService {

	@Resource
	private MessageDeptNumberMapper messageDeptNumberMapper;
	
	public Integer getTotal(Integer deptId, String deptName) {
		return messageDeptNumberMapper.getTotal(deptId, deptName);
	}

	public List<Map<String, Object>> queryList(Integer deptId, String deptName, Integer size, Integer num) {
		return messageDeptNumberMapper.queryList(deptId, deptName, size, num);
	}

	public Integer delDeptNumber(List<Integer> ids) {
		return messageDeptNumberMapper.deleteByPrimaryKey(ids);
	}

	public Integer addDeptNumber(MessageDeptNumber messageDeptNumber) {
		return messageDeptNumberMapper.insert(messageDeptNumber);
	}

	public Integer editDeptNumber(MessageDeptNumber messageDeptNumber) {
		return messageDeptNumberMapper.updateByPrimaryKeySelective(messageDeptNumber);
	}

	public MessageDeptNumber query(Integer id) {
		return messageDeptNumberMapper.selectByPrimaryKey(id);
	}

	public Integer addNumber(Integer id, Integer number) {
		return messageDeptNumberMapper.addNumber(id, number);
	}

}
