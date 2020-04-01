package com.ncu.springboot.provider.gate.service;

import java.util.List;
import java.util.Map;

import com.ncu.springboot.api.gate.entity.Apply;
import com.ncu.springboot.biz.rs.Res;


public interface ApplyService {

	Res addApply(List<Apply> applys);

	Integer editApply(Apply apply);

	Integer delApply(Integer id);

	Apply query(Integer id);
	
	Map<String, Object> queryMap(Integer id);

	Integer getTotal(String userId,Integer isCheck);
	
	List<Map<String, Object>> queryList(String userId,Integer isCheck,Integer size,Integer num);
	
	List<Map<String, Object>> queryListByRole(List<String> deptIds,List<String> userIds,String checkPersion,String status,String startTime,String endTime,Integer size,Integer num);
	
	List<Map<String, Object>> getTotalByRole(List<String> deptIds,List<String> userIds,String checkPersion,String status,String startTime,String endTime);
	
	List<Map<String, Object>> getDeptByUserId(List<String> userIds); 
	
	Map<String,Object> getCheckPersion(String userId);
	
	Integer check(List<Integer> ids,String status,String userId,String remark) ;
	
	boolean isSafe(String userId);
	
	boolean isSafeByMobile(String mobile);
}
