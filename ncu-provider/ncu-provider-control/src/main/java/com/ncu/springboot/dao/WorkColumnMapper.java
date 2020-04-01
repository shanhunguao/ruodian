package com.ncu.springboot.dao;

import java.util.List;
import java.util.Map;

import com.ncu.springboot.api.control.entity.WorkColumn;
import com.ncu.springboot.mybatis.mapper.BaseMapper;

import io.lettuce.core.dynamic.annotation.Param;

public interface WorkColumnMapper extends BaseMapper<WorkColumn>{
	
	Integer getTotal(@Param("name")String name);
	
	List<Map<String, String>> queryList(@Param("name")String name,@Param("size")Integer size,@Param("num")Integer num);
	
}
