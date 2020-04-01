package com.ncu.springboot.api.cache.bizservice;

import java.util.List;
import java.util.Set;

import com.ncu.springboot.biz.entity.Permission;
import com.ncu.springboot.biz.entity.Role;

public interface RoleCacheBizService {

	List<Role> getUserRolesByToken(String token);

	void setUserRolesByToken(String token, List<Role> roles);

	void setRoleByRoleId(Role role);

	Role getRoleByRoleId(String role_id);
	
	void setPermissionByPermissionId(Permission permission);
	
	Permission getPermissionByPermissionId(String permission_id);

	boolean existsRole(String role_id);

	List<Object> getRoleIdsByToken(String token);

	void addRoleIdsByToken(String token, String role_ids);
	
	void addRoleIdByUsercode(String usercode, String role_id);
	
	List<Object> getRoleIdsByUsercode(String usercode);
	
	void addPermissionToRoleByRoleId(String role_id, String permission_id);
	
	Set<Object> getPermissionIdsByRoleId(String role_id);
	public void setRoleIds(String roleId);
	void delRoleIdByUsercode(String usercode);
	
	String getRoleIdByUsercode(String usercode);
	void setRoleIdByUsercode(String usercode, String role_id);
}
