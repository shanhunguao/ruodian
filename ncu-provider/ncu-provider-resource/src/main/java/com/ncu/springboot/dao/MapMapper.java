package com.ncu.springboot.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface MapMapper {

	List<Map<String, Object>> queryList(@Param("name")String name);
	
}
