package com.ncu.springboot.biz.entity;

import java.io.Serializable;

import javax.persistence.Table;

@Table(name = "tb_user_role")
public class UserRole implements Serializable {

	private int id;
	private String userCode;
	private String roleId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Override
	public String toString() {
		return "UserRole{" +
				"id=" + id +
				", userCode='" + userCode + '\'' +
				", roleId='" + roleId + '\'' +
				'}';
	}
}
