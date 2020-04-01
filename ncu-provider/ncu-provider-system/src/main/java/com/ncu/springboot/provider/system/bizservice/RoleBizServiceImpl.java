package com.ncu.springboot.provider.system.bizservice;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.ncu.springboot.api.system.bizservice.RoleBizService;
import com.ncu.springboot.biz.entity.Role;
import com.ncu.springboot.provider.system.service.impl.RoleServiceOtherImpl;

@Component
@Service
public class RoleBizServiceImpl implements RoleBizService {
	
	@Resource
	private RoleServiceOtherImpl roleService;

	@Override
	public List<Role> getAllRoles() {
		return roleService.getAllRoles();
	}

}
