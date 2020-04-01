package com.ncu.springboot.biz.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 员工岗位信息
 * @author matebook
 *
 */
@Table(name = "tb_posts")
public class Posts implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "id")
	@Id
	private Integer id;
	
	@Column(name = "post_id")
	private String postId; // 职位编码
	
	@Column(name = "post_name")
	private String postName; // 职位名称
	

	@Column(name = "remark")
	private String remark; // 备注
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "update_time")
	private Date updateTime; // 更新时间

	private Employee employee; //岗位对应的员工

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@Override
	public String toString() {
		return "Posts{" +
				"id=" + id +
				", postId='" + postId + '\'' +
				", postName='" + postName + '\'' +
				", remark='" + remark + '\'' +
				", updateTime=" + updateTime +
				", employee=" + employee +
				'}';
	}
}
