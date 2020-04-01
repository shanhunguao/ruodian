package com.ncu.springboot.api.message.bizservice;

import java.util.List;
import java.util.Map;

import com.ncu.springboot.api.message.entity.MessageDeptNumber;

public interface MessageDeptNumberBizService {
	
	Integer getTotal(Integer deptId,String deptName);
	
	List<Map<String, Object>> queryList(Integer deptId,String deptName,Integer size,Integer num);
	
	Integer delDeptNumber(List<Integer> ids);
	
	Integer addDeptNumber(MessageDeptNumber messageDeptNumber);
	
	Integer editDeptNumber(MessageDeptNumber messageDeptNumber);
	
	MessageDeptNumber query(Integer id);
	
	Integer addNumber(Integer id,Integer number);

}
