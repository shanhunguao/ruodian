package com.ncu.springboot.biz.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Table;

@Table(name = "tb_role")
public class Role implements Serializable {

	private int id;
	private String roleId;
	private String roleName;
	private String remark;
	private Set<Permission> permissions;

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

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", roleId=" + roleId + ", roleName=" + roleName + ", remark=" + remark
				+ ", permissions=" + permissions + "]";
	}

}
