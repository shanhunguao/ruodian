package com.ncu.springboot.api.gate.bizservice;


import java.util.List;
import java.util.Map;

import com.ncu.springboot.api.gate.entity.Gate;
import com.ncu.springboot.api.gate.entity.Log;
import com.ncu.springboot.api.gate.entity.SpecialLog;
import com.ncu.springboot.biz.rs.Res;

public interface GuardBizService {
	
	Res check(Log log,String userId);
	
	Res getInfo(String cardId,String userId);
	
	Res queryLog(String date,String userId,Integer size,Integer num);
	
	Res queryLogByRole(String date,String userId,Integer size,Integer num);
	
	Integer addGate(Gate gate);
	
	Integer delGate(Integer id);
	
	Integer editGate(Gate gate);
	
	List<Map<String, Object>> queryList(Integer id, Integer campusId,Integer size,Integer num);
	
	Integer getTotal(Integer id,Integer campusId);
	
	Res specialCheck(SpecialLog specialLog,String userId);
	
	Res querySpecialLog(String date,String userId,Integer size,Integer num);
}
