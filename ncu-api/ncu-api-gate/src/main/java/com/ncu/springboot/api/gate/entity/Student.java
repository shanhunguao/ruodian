package com.ncu.springboot.api.gate.entity;
import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import javax.persistence.Id;
import com.ncu.springboot.core.annotation.Mark;
import java.lang.String;
import java.lang.Integer;

@Table(name = "tb_student")
public class Student implements Serializable {

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
	@Column(name = "campus")
	private String campus;

	@Mark(name = "")
	@Column(name = "college")
	private String college;

	@Mark(name = "")
	@Column(name = "major")
	private String major;

	@Mark(name = "")
	@Column(name = "grade")
	private String grade;

	@Mark(name = "")
	@Column(name = "class")
	private String classs;

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

	public String getCampus() {
		return campus;
	}

	public void setCampus(String campus) {
		this.campus = campus;
	}

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getClasss() {
		return classs;
	}

	public void setClasss(String classs) {
		this.classs = classs;
	}

	public String getOriginPlace() {
		return originPlace;
	}

	public void setOriginPlace(String originPlace) {
		this.originPlace = originPlace;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", userId=" + userId + ", userName=" + userName + ", sex=" + sex + ", idCard="
				+ idCard + ", mobile=" + mobile + ", campus=" + campus + ", college=" + college + ", major=" + major
				+ ", grade=" + grade + ", classs=" + classs + ", originPlace=" + originPlace + "]";
	}
}