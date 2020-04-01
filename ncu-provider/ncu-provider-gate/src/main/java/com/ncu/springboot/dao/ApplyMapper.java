package com.ncu.springboot.dao;

import java.util.List;
import java.util.Map;

import com.ncu.springboot.api.gate.entity.Apply;
import com.ncu.springboot.mybatis.mapper.BaseMapper;

import io.lettuce.core.dynamic.annotation.Param;

public interface ApplyMapper extends BaseMapper<Apply> {

	
	Integer getTotal(@Param("userId")String userId,@Param("isCheck")Integer isCheck);
	
	List<Map<String, Object>> queryList(@Param("userId")String userId,@Param("isCheck")Integer isCheck,@Param("size")Integer size,@Param("num")Integer num);
	
	List<Map<String, Object>> queryListByRole(@Param("deptIds")List<String> deptIds,@Param("userIds")List<String> userIds
			,@Param("checkPersion")String checkPersion,@Param("status")String status,@Param("startTime")String startTime,@Param("endTime")String endTime
			,@Param("size")Integer size,@Param("num")Integer num);
	
	List<Map<String, Object>> getTotalByRole(@Param("deptIds")List<String> deptIds,@Param("userIds")List<String> userIds
			,@Param("checkPersion")String checkPersion,@Param("status")String status,@Param("startTime")String startTime,@Param("endTime")String endTime);
	
	List<Map<String, Object>> getDeptByUserId(List<String> userIds); 
	
	Map<String,Object> getCheckPersion(@Param("userId")String userId);
	
	Map<String,Object> query(@Param("id")Integer id);
	
	Integer check(@Param("ids")List<Integer> ids,@Param("status")String status,@Param("userId")String userId,@Param("remark")String remark) ;
	
	Integer isSafe(@Param("userId")String userId);

	Integer isSafeByMobile(@Param("mobile")String mobile);
}
