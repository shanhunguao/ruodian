package com.ncu.springboot.biz.entity;

import java.io.Serializable;

import javax.persistence.Table;

@Table(name = "tb_role_permission")
public class RolePermission implements Serializable {

	private int id;
	private String roleId;
	private String permissionId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
	}

	@Override
	public String toString() {
		return "RolePermission{" +
				"id=" + id +
				", roleId='" + roleId + '\'' +
				", permissionId='" + permissionId + '\'' +
				'}';
	}
}
