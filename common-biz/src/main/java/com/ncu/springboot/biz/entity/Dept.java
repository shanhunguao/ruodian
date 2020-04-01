package com.ncu.springboot.biz.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;


/**
 * 
 * @ClassName: Dept
 * @Description: 部门实体类
 * @author: czy
 * @date: 2019年9月6日 下午4:12:27
 */
@Table(name = "tb_dept")
public class Dept implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "id")
	@Id
	private String id;
	
	@Column(name = "parent_id")
	private String parentId; // 父级id
	
	@Column(name = "name")
	private String name; // 部门名
	

	@Column(name = "update_time")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime; // 更新时间

	private List<Posts> postList; // 职位

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}


	public List<Posts> getPostList() {
		return postList;
	}

	public void setPostList(List<Posts> postList) {
		this.postList = postList;
	}

	@Override
	public String toString() {
		return "Dept{" +
				"id='" + id + '\'' +
				", parentId='" + parentId + '\'' +
				", name='" + name + '\'' +
				", updateTime=" + updateTime +
				", postList=" + postList +
				'}';
	}
}
