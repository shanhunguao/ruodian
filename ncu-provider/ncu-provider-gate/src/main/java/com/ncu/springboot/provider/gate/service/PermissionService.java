package com.ncu.springboot.provider.gate.service;

import java.util.List;
import java.util.Map;

import com.ncu.springboot.api.gate.entity.Permission;

public interface PermissionService {

	Integer addPermission(Permission permission);
	
	Integer editPermission(Permission permission);
	
	Integer delPermission(Integer id);
	
	Integer getTotal(String userId);
	
	List<Map<String, Object>> queryList(String userId,Integer size,Integer num);
}
