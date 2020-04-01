package com.ncu.springboot.biz.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @ClassName: UserCampusRole
 * @Description: 园区权限
 * @author: czy
 * @date: 2019年10月24日 下午4:14:37
 */
@Table(name = "tb_user_campus_role")
public class UserCampusRole implements Serializable{

	@Column(name = "id")
	@Id
	private int id;
	
	@Column(name = "user_id")
	private int userId; // 用户Id
	
	@Column(name = "campus_id")
	private int campusId; // 园区id
	
	@Column(name = "type")
	private String type; // 状态码(0-增1-删2-改3-查)
	
	/*辅助字段*/
	private String campusName; // 园区名称
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getCampusId() {
		return campusId;
	}
	public void setCampusId(int campusId) {
		this.campusId = campusId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCampusName() {
		return campusName;
	}
	public void setCampusName(String campusName) {
		this.campusName = campusName;
	}
}
