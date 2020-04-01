package com.ncu.springboot.provider.message.service;

import java.util.List;

import com.ncu.springboot.api.message.entity.MessageCheck;

public interface MessageCheckService {

	List<MessageCheck> queryList(String employeeId,Integer deptId);

	Integer addMessageCheck(List<MessageCheck> messageCheck);

	Integer delMessageCheck(List<Integer> ids);
}
