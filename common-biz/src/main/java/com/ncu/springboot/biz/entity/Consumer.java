package com.ncu.springboot.biz.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Created by zhoufan
 * @Date 2019/12/13 14:43 专为web登录和app登录定制的用户信息返回值
 */
public class Consumer implements Serializable {

    private static final long serialVersionUID = -7265703976373157895L;
    private int id;
    private String userCode;
    private String avatar;
    private String token;
    private String wxOpenId;
    private String qqOpenId;
    private String userType;
    private int userState;
    @Column(name = "mobile")
    private String mobile; // 手机号

    @Column(name = "email")
    private String email; // 邮箱

    @Column(name = "id_card")
    private String idCard; // 身份证号

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "update_time")
    private Date updateTime; // 更新时间

    private List<Role> role;
    private Employee employee;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getWxOpenId() {
        return wxOpenId;
    }

    public void setWxOpenId(String wxOpenId) {
        this.wxOpenId = wxOpenId;
    }

    public String getQqOpenId() {
        return qqOpenId;
    }

    public void setQqOpenId(String qqOpenId) {
        this.qqOpenId = qqOpenId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public int getUserState() {
        return userState;
    }

    public void setUserState(int userState) {
        this.userState = userState;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<Role> getRole() {
        return role;
    }

    public void setRole(List<Role> role) {
        this.role = role;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
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

    @Override
    public String toString() {
        return "Consumer{" +
                "id=" + id +
                ", userCode='" + userCode + '\'' +
                ", avatar='" + avatar + '\'' +
                ", token='" + token + '\'' +
                ", wxOpenId='" + wxOpenId + '\'' +
                ", qqOpenId='" + qqOpenId + '\'' +
                ", userType='" + userType + '\'' +
                ", userState=" + userState +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", avatar='" + avatar + '\'' +
                ", idCard='" + idCard + '\'' +
                ", userState=" + userState +
                ", updateTime=" + updateTime +
                ", role=" + role +
                ", employee=" + employee +
                '}';
    }
}
