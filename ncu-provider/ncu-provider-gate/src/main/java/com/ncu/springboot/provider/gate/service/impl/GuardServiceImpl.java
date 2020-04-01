package com.ncu.springboot.provider.gate.service.impl;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.ncu.springboot.api.gate.entity.Gate;
import com.ncu.springboot.api.gate.entity.Log;
import com.ncu.springboot.api.gate.entity.SpecialLog;
import com.ncu.springboot.dao.GuardMapper;
import com.ncu.springboot.dao.LogMapper;
import com.ncu.springboot.dao.SpecialLogMapper;
import com.ncu.springboot.provider.gate.service.GuardService;

@Service
public class GuardServiceImpl implements GuardService {

	
	@Resource
	private GuardMapper guardMapper;
	
	@Resource
	private LogMapper logMapper;
	
	@Resource
	private SpecialLogMapper specialLogMapper;

	public List<Map<String, Object>> queryApply(String userId) {
		return guardMapper.queryApply(userId);
	}

	public Integer addLog(Log log) {
		return logMapper.insert(log);
	}

	public List<Map<String, Object>> queryLogByRole(String date, String userId,Integer size,Integer num) {
		return logMapper.queryLogByRole(date, userId,size,num);
	}

	public Integer addGate(Gate gate) {
		return guardMapper.insert(gate);
	}

	public Integer delGate(Integer id) {
		return guardMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Integer editGate(Gate gate) {
		return guardMapper.updateByPrimaryKeySelective(gate);
	}

	public List<Map<String, Object>> queryList(Integer id, Integer campusId, Integer size, Integer num) {
		return guardMapper.queryList(id, campusId, size, num);
	}

	public Integer getTotal(Integer id, Integer campusId) {
		return guardMapper.getTotal(id, campusId);
	}

	public List<Map<String, Object>> queryLog(String date, String userId, Integer size, Integer num) {
		return logMapper.queryLog(date, userId, size, num);
	}

	public Integer addSpecialLog(SpecialLog specialLog) {
		return specialLogMapper.insert(specialLog);
	}

	public List<Map<String, Object>> querySpecialLog(String date, String userId, Integer size, Integer num) {
		return specialLogMapper.querySpecialLog(date, userId, size, num);
	}

}
