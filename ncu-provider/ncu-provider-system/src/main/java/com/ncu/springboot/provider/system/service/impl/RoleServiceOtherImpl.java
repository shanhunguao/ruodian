package com.ncu.springboot.provider.system.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.alibaba.dubbo.config.annotation.Service;

import com.ncu.springboot.biz.entity.Role;
import com.ncu.springboot.provider.system.dao.RoleMapper;
import com.ncu.springboot.provider.system.service.RoleService;

@Service
public class RoleServiceOtherImpl implements RoleService {
	
	@Resource
	private RoleMapper roleMapper;

	@Override
	public List<Role> getAllRoles() {
		return roleMapper.selectAll();
	}

}
