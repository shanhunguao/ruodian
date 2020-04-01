package com.ncu.springboot.provider.gate.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.ncu.springboot.api.gate.entity.Permission;
import com.ncu.springboot.dao.PermissionMapper;
import com.ncu.springboot.provider.gate.service.PermissionService;

@Service
public class PermissionServiceImpl implements PermissionService {

	@Resource
	private PermissionMapper permissionMapper;
	
	public Integer addPermission(Permission permission) {
		return permissionMapper.insert(permission);
	}

	public Integer editPermission(Permission permission) {
		return permissionMapper.updateByPrimaryKeySelective(permission);
	}

	public Integer delPermission(Integer id) {
		return permissionMapper.deleteByPrimaryKey(id);
	}

	public Integer getTotal(String userId) {
		return permissionMapper.getTotal(userId);
	}

	public List<Map<String, Object>> queryList(String userId, Integer size, Integer num) {
		return permissionMapper.queryList(userId, size, num);
	}

}
