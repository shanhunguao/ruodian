package com.ncu.springboot.api.gate.entity;
import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import javax.persistence.Id;
import com.ncu.springboot.core.annotation.Mark;
import java.lang.String;
import java.lang.Integer;

@Table(name = "tb_teacher")
public class Teacher implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Mark(name = "")
	@Column(name = "id")
	private Integer id;

	@Mark(name = "")
	@Column(name = "user_id")
	private String userId;

	@Mark(name = "")
	@Column(name = "user_name")
	private String userName;

	@Mark(name = "")
	@Column(name = "sex")
	private String sex;

	@Mark(name = "")
	@Column(name = "id_card")
	private String idCard;

	@Mark(name = "")
	@Column(name = "mobile")
	private String mobile;

	@Mark(name = "")
	@Column(name = "email")
	private String email;

	@Mark(name = "")
	@Column(name = "dept_id")
	private String deptId;

	@Mark(name = "")
	@Column(name = "origin_place")
	private String originPlace;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getOriginPlace() {
		return originPlace;
	}

	public void setOriginPlace(String originPlace) {
		this.originPlace = originPlace;
	}

	@Override
	public String toString() {
		return "Teacher [id=" + id + ", userId=" + userId + ", userName=" + userName + ", sex=" + sex + ", idCard="
				+ idCard + ", mobile=" + mobile + ", email=" + email + ", deptId=" + deptId + ", originPlace="
				+ originPlace + "]";
	}

}