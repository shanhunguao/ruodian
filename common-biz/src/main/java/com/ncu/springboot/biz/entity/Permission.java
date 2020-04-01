package com.ncu.springboot.biz.entity;

import java.io.Serializable;

import javax.persistence.Table;

@Table(name = "tb_permission")
public class Permission implements Serializable {

	private int id;
	private String permissionId;
	private String permissionName;
	private String url;
	private String remark;
	private String parentId;
	private String permissionTypeId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getPermissionTypeId() {
		return permissionTypeId;
	}

	public void setPermissionTypeId(String permissionTypeId) {
		this.permissionTypeId = permissionTypeId;
	}

	@Override
	public String toString() {
		return "Permission{" +
				"id=" + id +
				", permissionId='" + permissionId + '\'' +
				", permissionName='" + permissionName + '\'' +
				", url='" + url + '\'' +
				", remark='" + remark + '\'' +
				", parentId='" + parentId + '\'' +
				", permissionTypeId='" + permissionTypeId + '\'' +
				'}';
	}
}
