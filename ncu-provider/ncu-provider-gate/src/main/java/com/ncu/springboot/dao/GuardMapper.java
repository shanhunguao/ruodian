package com.ncu.springboot.dao;


import java.util.List;
import java.util.Map;

import com.ncu.springboot.api.gate.entity.Gate;
import com.ncu.springboot.mybatis.mapper.BaseMapper;

import io.lettuce.core.dynamic.annotation.Param;


public interface GuardMapper extends BaseMapper<Gate> {

	List<Map<String, Object>> queryApply(@Param("userId")String userId);
	
	List<Map<String, Object>> queryList(@Param("id")Integer id, @Param("campusId")Integer campusId,@Param("size")Integer size,@Param("num")Integer num);
	
	Integer getTotal(@Param("id")Integer id,@Param("campusId")Integer campusId);
	
}
