package com.ncu.springboot.provider.cache.bizservice;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.ncu.springboot.api.cache.bizservice.RoleCacheBizService;
import com.ncu.springboot.api.cache.keys.RolePermissionCacheIdKeys;
import com.ncu.springboot.biz.entity.Permission;
import com.ncu.springboot.biz.entity.Role;
import com.ncu.springboot.utils.RedisUtil;

@Service
public class RoleCacheHelper implements RoleCacheBizService {

	@Autowired
	private RedisUtil redisUtil;

	@Override
	public List<Role> getUserRolesByToken(String token) {
		return (List<Role>) redisUtil.get(RolePermissionCacheIdKeys.getRolesByTokenKey(token));
	}

	@Override
	public void setUserRolesByToken(String token, List<Role> roles) {
		redisUtil.set(RolePermissionCacheIdKeys.getRolesByTokenKey(token), roles);
	}

	@Override
	public void setRoleByRoleId(Role role) {
		redisUtil.set(RolePermissionCacheIdKeys.getRoleByRoleIdKey(role.getRoleId()), role);
	}

	@Override
	public Role getRoleByRoleId(String role_id) {
		return (Role) redisUtil.get(RolePermissionCacheIdKeys.getRoleByRoleIdKey(role_id));
	}

	@Override
	public boolean existsRole(String role_id) {
		return redisUtil.hasKey(RolePermissionCacheIdKeys.getRoleByRoleIdKey(role_id));
	}

	@Override
	public List<Object> getRoleIdsByToken(String token) {
		return redisUtil.lGet(RolePermissionCacheIdKeys.getRoleIdsByTokenKey(token), 0, -1);
	}

	@Override
	public void addRoleIdsByToken(String token, String role_id) {
		redisUtil.lSet(RolePermissionCacheIdKeys.getRoleIdsByTokenKey(token), role_id);
	}

	@Override
	public void addRoleIdByUsercode(String usercode, String role_id) {
		redisUtil.lSet(RolePermissionCacheIdKeys.getRoleIdsByUsercodeKey(usercode), role_id);
	}

	@Override
	public List<Object> getRoleIdsByUsercode(String usercode) {
		return redisUtil.lGet(RolePermissionCacheIdKeys.getRoleIdsByUsercodeKey(usercode), 0, -1);
	}

	@Override
	public void setPermissionByPermissionId(Permission permission) {
		redisUtil.set(RolePermissionCacheIdKeys.getPermissionByPermissionIdKey(permission.getPermissionId()), permission);
		redisUtil.sSet(RolePermissionCacheIdKeys.getPermissionIds(),permission.getPermissionId());
	}

	@Override
	public Permission getPermissionByPermissionId(String permission_id) {
		return (Permission) redisUtil.get(RolePermissionCacheIdKeys.getPermissionByPermissionIdKey(permission_id));
	}

	@Override
	public void addPermissionToRoleByRoleId(String role_id, String permission_id) {
		redisUtil.sSet(RolePermissionCacheIdKeys.getPermissionIdsByRoleIdKey(role_id), permission_id);
	}

	@Override
	public Set<Object> getPermissionIdsByRoleId(String role_id) {
		return redisUtil.sGet(RolePermissionCacheIdKeys.getPermissionIdsByRoleIdKey(role_id));
	}

	@Override
	public void setRoleIds(String roleId) {
		redisUtil.sSet(RolePermissionCacheIdKeys.getRoleByRoleIdsKey(), roleId);
	}
	
	@Override
	public void delRoleIdByUsercode(String usercode) {
		redisUtil.del(RolePermissionCacheIdKeys.getRoleIdsByUsercodeKey(usercode));
	}

	@Override
	public String getRoleIdByUsercode(String usercode) {
		return (String) redisUtil.get(RolePermissionCacheIdKeys.getRoleIdsByUsercodeKey(usercode));
	}

	@Override
	public void setRoleIdByUsercode(String usercode, String role_id) {
		redisUtil.set(RolePermissionCacheIdKeys.getRoleIdsByUsercodeKey(usercode), role_id);
	}
	
	

}
