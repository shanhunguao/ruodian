package com.ncu.springboot.api.oauth2.bizservice;

import java.util.List;

import com.ncu.springboot.biz.entity.Permission;
import com.ncu.springboot.biz.entity.PermissionType;
import com.ncu.springboot.biz.entity.Role;

public interface RoleBizService {
	
	/**
	  *    新增角色
	 * @param role
	 */
	void addRole(Role role);
	
	/**
	  *    删除角色
	 * @param role_id
	 */
	void deleteRole(String role_id);
	
	/**
	  *    修改角色信息
	 * @param role
	 */
	void updateRole(Role role);
	
	/**
	  *    新增权限
	 * @param permission
	 */
	void addPermission(Permission permission);
	
	/**
	  *    删除权限
	 * @param permission_id
	 */
	void deletePermission(String permission_id);
	
	/**
	  *    修改权限信息
	 * @param permission
	 */
	void updatePermission(Permission permission);
	
	/**
	  *    为用户新增角色
	 * @param usercode
	 * @param role_id
	 */
	void addUserRoles(String usercode, List<String> role_ids);
	
	/**
	  *    删除用户角色
	 * @param usercode
	 * @param role_id
	 */
	void deleteUserRoles(String usercode, List<String> role_ids);
	
	/**
	  *    为角色添加权限
	 * @param role_id
	 * @param permission_ids
	 */
	void addRolePermissions(String role_id, List<String> permission_ids);
	
	/**
	  *    删除角色中的权限
	 * @param role_id
	 * @param permission_ids
	 */
	void deleteRolePermissions(String role_id, List<String> permission_ids);
	
	/**
	  *    获取用户角色
	 * @param usercode
	 * @return
	 */
	List<String> getUserRoleIds(String usercode);
	
	/**
	  *    获取角色拥有的所有权限
	 * @param role_id
	 * @return
	 */
	List<Permission> getRolePermissions(String role_id);
	
	/**
	  *    新增权限类型
	 * @param permissionType
	 */
	void addPermissionType(PermissionType  permissionType);
	
	/**
	  *    删除权限类型
	 * @param permission_type_id
	 */
	void deletePermissionType(String permission_type_id);
	
	/**
	  *    获取所有角色
	 * @return
	 */
	List<Role> getAllRole();
	
	/**
	  *    获取所有权限
	 * @return
	 */
	List<Permission> getAllPermissions();
	
	/**
	  *    获取所有权限类型
	 * @return
	 */
	List<PermissionType> getAllPermissionTypes();
	
}
