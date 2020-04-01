package com.ncu.springboot.api.gate.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "gate_temp_persion")
public class TempPersion implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id")
	private Integer id;
	
	@Id
	@Column(name = "user_id")
	private String userId;
	
	@Id
	@Column(name = "user_name")
	private String userName;
	
	@Id
	@Column(name = "sex")
	private String sex;
	
	@Id
	@Column(name = "id_card")
	private String idCard;
	
	@Id
	@Column(name = "mobile")
	private String mobile;
	
	@Id
	@Column(name = "dept_id")
	private String deptId;
	
	@Id
	@Column(name = "create_persion")
	private String createPersion;
	
	@Column(name = "remark")
	private String remark;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getCreatePersion() {
		return createPersion;
	}

	public void setCreatePersion(String createPersion) {
		this.createPersion = createPersion;
	}

	@Override
	public String toString() {
		return "TempPersion [id=" + id + ", userId=" + userId + ", userName=" + userName + ", sex=" + sex + ", idCard="
				+ idCard + ", mobile=" + mobile + ", deptId=" + deptId + ", createPersion=" + createPersion
				+ ", remark=" + remark + "]";
	}

}
