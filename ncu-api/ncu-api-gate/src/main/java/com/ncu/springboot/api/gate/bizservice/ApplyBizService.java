package com.ncu.springboot.api.gate.bizservice;

import java.util.List;
import java.util.Map;

import com.ncu.springboot.api.gate.entity.Apply;
import com.ncu.springboot.biz.rs.Res;

public interface ApplyBizService {
	
	Res check(String userId, List<Integer> ids,String remark,boolean check);
	
	Res addApply(List<Apply> applys,String userId);
	
	Res editApply(Apply apply,String userId);
	
	Res delApply(Integer id,String userId);
	
	Map<String, Object> query(Integer id);
	
	Integer getTotal(String userId,Integer isCheck);
	
	List<Map<String, Object>> queryList(String userId,Integer isCheck,Integer size,Integer num);
	
	Res queryListByRole(String userId,String checkPersion,String status,String startTime,String endTime,Integer size,Integer num);
	
	Res getTotalByRole(String userId,String checkPersion,String status,String startTime,String endTime);
	
	Res revocation(Integer id,String userId);
	
	Res importExcel(List<Map<String, String>> datas,String userId);
	
	Res addApplyTest(String userId,Integer campusId,String admin);
	
	boolean isSafe(String userId);

}
