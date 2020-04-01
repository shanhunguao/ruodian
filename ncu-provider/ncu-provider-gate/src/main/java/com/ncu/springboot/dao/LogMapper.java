package com.ncu.springboot.dao;


import java.util.List;
import java.util.Map;

import com.ncu.springboot.api.gate.entity.Log;
import com.ncu.springboot.mybatis.mapper.BaseMapper;

import io.lettuce.core.dynamic.annotation.Param;

public interface LogMapper extends BaseMapper<Log>{
	
	List<Map<String,Object>> queryLog(@Param("date")String date,@Param("userId")String userId,@Param("size")Integer size,@Param("num")Integer num);

	List<Map<String, Object>> queryLogByRole(@Param("date")String date,@Param("userId")String userId,@Param("size")Integer size,@Param("num")Integer num);

}
