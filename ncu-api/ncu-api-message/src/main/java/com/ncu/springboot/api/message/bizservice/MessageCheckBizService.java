package com.ncu.springboot.api.message.bizservice;


import java.util.List;

import com.ncu.springboot.api.message.entity.MessageCheck;
import com.ncu.springboot.api.message.entity.MessageSendResult;
import com.ncu.springboot.biz.rs.Res;

public interface MessageCheckBizService {

	List<MessageCheck> queryList(String employeeId,Integer deptId);
	
	Res addMessageCheck(List<MessageCheck> messageChecks);
	
	Integer delMessageCheck(List<Integer> ids);
	
	MessageSendResult check(Integer messageId,String employeeId,boolean check);
}
