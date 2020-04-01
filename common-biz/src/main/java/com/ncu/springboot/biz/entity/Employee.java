package com.ncu.springboot.biz.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "tb_employee")
public class Employee implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "id")
	@Id
	private Integer id;

	@Column(name = "employee_id")
	private String employeeId; // 员工工号

	@Column(name = "employee_name")
	private String employeeName; // 员工姓名

	@Column(name = "sex")
	private String sex; // 性别

	@Column(name = "mobile")
	private String mobile; // 手机号码
	@Column(name = "email")
	private String email;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "update_time")
	private Date updateTime; // 更新时间

	/*辅助字段*/
	private String DeptId;
	private String DeptName;
	private String postsId;
	private String postName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDeptId() {
		return DeptId;
	}

	public void setDeptId(String deptId) {
		DeptId = deptId;
	}

	public String getDeptName() {
		return DeptName;
	}

	public void setDeptName(String deptName) {
		DeptName = deptName;
	}

	public String getPostsId() {
		return postsId;
	}

	public void setPostsId(String postsId) {
		this.postsId = postsId;
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "Employee{" +
				"id=" + id +
				", employeeId='" + employeeId + '\'' +
				", employeeName='" + employeeName + '\'' +
				", sex='" + sex + '\'' +
				", mobile='" + mobile + '\'' +
				", email='" + email + '\'' +
				", updateTime=" + updateTime +
				", DeptId='" + DeptId + '\'' +
				", DeptName='" + DeptName + '\'' +
				", postsId='" + postsId + '\'' +
				", postName='" + postName + '\'' +
				'}';
	}
}
