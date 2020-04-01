package com.ncu.springboot.biz.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Table;

@Table(name = "tb_department")
public class Department implements Serializable {

	private int id;
	private String departmentId;
	private String departmentName;
	private String parentId;
	private String remark;

	private List<Posts> posts;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<Posts> getPosts() {
		return posts;
	}

	public void setPosts(List<Posts> posts) {
		this.posts = posts;
	}

	@Override
	public String toString() {
		return "Department [id=" + id + ", departmentId=" + departmentId + ", departmentName=" + departmentName
				+ ", parentId=" + parentId + ", remark=" + remark + ", posts=" + posts + "]";
	}

}
