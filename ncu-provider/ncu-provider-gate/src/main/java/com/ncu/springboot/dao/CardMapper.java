package com.ncu.springboot.dao;

import java.util.List;
import java.util.Map;

import com.ncu.springboot.api.gate.entity.Card;
import com.ncu.springboot.mybatis.mapper.BaseMapper;

import io.lettuce.core.dynamic.annotation.Param;

public interface CardMapper extends BaseMapper<Card> {
	
	Map<String, Object> getInfoByStudent(@Param("userId")String userId,@Param("cardId")String cardId);

	Map<String, Object> getInfoByTeacher(@Param("userId")String userId,@Param("cardId")String cardId);
	
	Map<String, Object> getInfoByTempUser(@Param("userId")String userId);

	List<Map<String, Object>> getUserInfoListByStudent(@Param("userId")String userId, @Param("mobile")String mobile, @Param("userName")String userName,@Param("deptIds")List<String> deptIds);
	
	List<Map<String, Object>> getUserInfoListByTeacher(@Param("userId")String userId, @Param("mobile")String mobile, @Param("userName")String userName,@Param("deptIds")List<String> deptIds);

	List<Map<String, Object>> getUserInfoListByTeachers(@Param("deptId")String deptId,@Param("userId")String userId, @Param("mobile")String mobile, @Param("userName")String userName);

	Integer getUserInfoListByTeacherCount(@Param("deptId")String deptId,@Param("userId")String userId, @Param("mobile")String mobile, @Param("userName")String userName);
}
