package com.ncu.springboot.dao;

import java.util.List;
import java.util.Map;

import com.ncu.springboot.api.gate.entity.SpecialLog;
import com.ncu.springboot.mybatis.mapper.BaseMapper;

import io.lettuce.core.dynamic.annotation.Param;

public interface SpecialLogMapper extends BaseMapper<SpecialLog> {

	List<Map<String, Object>> querySpecialLog(@Param("date")String date,@Param("userId")String userId,@Param("size")Integer size,@Param("num")Integer num);
}
