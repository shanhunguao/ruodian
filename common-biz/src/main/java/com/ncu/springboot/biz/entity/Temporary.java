package com.ncu.springboot.biz.entity;

import java.io.Serializable;

/**
 * @Created by zhoufan
 * @Date 2020/3/10 16:49
 * 临时人员
 */
public class Temporary extends ControlUser implements Serializable {
    private static final long serialVersionUID = -7265703976373157891L;
    private String userId;
    private String userName;
    private String sex;
    private String idCard;
    private String mobile;
    private String deptId;
    private String deptName;
    private String createPersion;

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

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getCreatePersion() {
        return createPersion;
    }

    public void setCreatePersion(String createPersion) {
        this.createPersion = createPersion;
    }

    @Override
    public String toString() {
        return "Temporary{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", sex='" + sex + '\'' +
                ", idCard='" + idCard + '\'' +
                ", mobile='" + mobile + '\'' +
                ", deptId='" + deptId + '\'' +
                ", deptName='" + deptName + '\'' +
                ", createPersion='" + createPersion + '\'' +
                '}';
    }
}
