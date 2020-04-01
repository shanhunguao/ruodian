package com.ncu.springboot.biz.entity;

import java.io.Serializable;

/**
 * @Created by zhoufan
 * @Date 2020/2/26 9:33
 */
public class Teacher extends ControlUser implements Serializable {
    private static final long serialVersionUID = -7265703976373157896L;

    private String userId;
    private String userName;
    private String sex; // 性别
    private String idCard;
    private String mobile; // 手机号码
    private String email;
    private String deptId;
    private String deptName;
//    地址
    private String originPlace;

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
    

    public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getOriginPlace() {
        return originPlace;
    }

    public void setOriginPlace(String originPlace) {
        this.originPlace = originPlace;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", sex='" + sex + '\'' +
                ", idCard='" + idCard + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", deptId='" + deptId + '\'' +
                ", originPlace='" + originPlace + '\'' +
                '}';
    }
}
