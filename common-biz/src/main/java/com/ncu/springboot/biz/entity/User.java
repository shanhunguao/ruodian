package com.ncu.springboot.biz.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Table(name = "tb_user")
public class User implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public static final int USERSTATE_ONE = 0; // 启用

    public static final int USERSTATE_TWO = 1; // 禁用

    @Column(name = "id")
    @Id
    private int id;

    @Column(name = "user_code")
    private String usercode; // 用户编码

    @Column(name = "password")
    private String password; // 密码

    @Column(name = "mobile")
    private String mobile; // 手机号

    @Column(name = "email")
    private String email; // 邮箱

    private String avatar; // 用户头像

    @Column(name = "id_card")
    private String idCard; // 身份证号

    @Column(name = "role_id")
    private String roleId; // 角色id


    @Column(name = "user_state")
    private int userState; // 状态 0：禁用 1：正常

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "update_time")
    private Date updateTime; // 更新时间

    @Column(name = "wx_open_id")
    private String wxOpenId; // 微信唯一标识id（平台不同会变）

    @Column(name = "qq_open_id")
    private String qqOpenId; // qq唯一标识id

    private List<Role> roles;

    private Employee employee;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
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

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", usercode='" + usercode + '\'' +
                ", password='" + password + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", avatar='" + avatar + '\'' +
                ", idCard='" + idCard + '\'' +
                ", roleId='" + roleId + '\'' +
                ", userState=" + userState +
                ", updateTime=" + updateTime +
                ", wxOpenId='" + wxOpenId + '\'' +
                ", qqOpenId='" + qqOpenId + '\'' +
                ", roles=" + roles +
                ", employee=" + employee +
                '}';
    }
}
