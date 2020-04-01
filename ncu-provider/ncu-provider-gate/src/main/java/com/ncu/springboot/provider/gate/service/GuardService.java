package com.ncu.springboot.provider.gate.service;

import java.util.List;
import java.util.Map;

import com.ncu.springboot.api.gate.entity.Gate;
import com.ncu.springboot.api.gate.entity.Log;
import com.ncu.springboot.api.gate.entity.SpecialLog;

public interface GuardService {

	List<Map<String, Object>> queryApply(String userId);

	Integer addLog(Log log);

	List<Map<String, Object>> queryLogByRole(String date,String userId,Integer size,Integer num);
	
	List<Map<String, Object>> queryLog(String date,String userId,Integer size,Integer num);

	Integer addGate(Gate gate);
	
	Integer delGate(Integer id);
	
	Integer editGate(Gate gate);
	
	List<Map<String, Object>> queryList(Integer id, Integer campusId,Integer size,Integer num);
	
	Integer getTotal(Integer id,Integer campusId);
	
	Integer addSpecialLog(SpecialLog specialLog);
	
	List<Map<String, Object>> querySpecialLog(String date,String userId,Integer size,Integer num);
}
