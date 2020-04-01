package com.ncu.springboot.provider.system.init;

import java.util.List;

import javax.annotation.Resource;

import com.ncu.springboot.provider.system.dao.PermissionMapper;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ncu.springboot.api.cache.bizservice.RoleCacheBizService;
import com.ncu.springboot.biz.entity.Permission;
import com.ncu.springboot.biz.entity.Role;
import com.ncu.springboot.biz.entity.RolePermission;
import com.ncu.springboot.provider.system.dao.RoleMapper;
import com.ncu.springboot.provider.system.dao.RolePermissionMapper;

@Component
public class RolePermissionInitializer implements ApplicationListener<ContextRefreshedEvent> {
	
	@Resource
	private RoleMapper roleMapper;
	
	@Resource
	private PermissionMapper permissionMapper;

	@Resource
	private RolePermissionMapper rolePermissionMapper;
	
	@Reference
	private RoleCacheBizService roleCacheHelper;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		System.out.println("-------------------RolePermissionInitializer init start");
		
		List<Role> roles = roleMapper.selectAll();
		for (Role role : roles) {
			roleCacheHelper.setRoleByRoleId(role);
			roleCacheHelper.setRoleIds(role.getRoleId());
		}
		
		List<Permission> permissions = permissionMapper.selectAll();
		for (Permission permission : permissions) {
			roleCacheHelper.setPermissionByPermissionId(permission);
		}
		
		List<RolePermission> rolePermissions = rolePermissionMapper.selectAll();
		for (RolePermission rolePermission : rolePermissions) {
			roleCacheHelper.addPermissionToRoleByRoleId(rolePermission.getRoleId(), rolePermission.getPermissionId());
		}
		
		System.out.println("-------------------RolePermissionInitializer init end");
		
	}

}
