package com.ncu.springboot.biz.entity;

import java.io.Serializable;

import javax.persistence.Table;

@Table(name = "tb_permission_type")
public class PermissionType implements Serializable {

	private int id;
	private String permissionTypeId;
	private String permissionTypeName;
	private String remark;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPermissionTypeId() {
		return permissionTypeId;
	}

	public void setPermissionTypeId(String permissionTypeId) {
		this.permissionTypeId = permissionTypeId;
	}

	public String getPermissionTypeName() {
		return permissionTypeName;
	}

	public void setPermissionTypeName(String permissionTypeName) {
		this.permissionTypeName = permissionTypeName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
