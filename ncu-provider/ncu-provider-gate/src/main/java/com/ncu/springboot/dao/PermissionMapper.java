package com.ncu.springboot.dao;

import java.util.List;
import java.util.Map;

import com.ncu.springboot.api.gate.entity.Permission;
import com.ncu.springboot.mybatis.mapper.BaseMapper;

import io.lettuce.core.dynamic.annotation.Param;

public interface PermissionMapper extends BaseMapper<Permission> {

	Integer getTotal(@Param("userId")String userId);
	
	List<Map<String, Object>> queryList(@Param("userId")String userId,@Param("size")Integer size,@Param("num")Integer num);
}
