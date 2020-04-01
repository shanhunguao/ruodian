package com.ncu.springboot.biz.entity;

import java.io.Serializable;

import javax.persistence.Table;

@Table(name = "tb_department_posts")
public class DepartmentPosts implements Serializable {

	private int id;
	private String departmentId;
	private String postId;
	private String employeeId;

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

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

}
