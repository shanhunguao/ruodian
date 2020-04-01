package com.ncu.springboot.biz.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @Created by zhoufan
 * @Date 2020/2/25 14:24
 * 专为人员管控开发的返回值
 */
public class ControlUser implements Serializable {
    private static final long serialVersionUID = -7265703976373157895L;
    private int id;
    private String userCode;
    private String avatar;
    private Role role;
    //    电子ID
    private String cardId;
    private String cardStatus;

    //    用户类型
    private String userType;
    private String token;
    private List<String> deptIds;

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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<String> getDeptIds() {
        return deptIds;
    }

    public void setDeptIds(List<String> deptIds) {
        this.deptIds = deptIds;
    }

    @Override
    public String toString() {
        return "ControlUser{" +
                "id=" + id +
                ", userCode='" + userCode + '\'' +
                ", avatar='" + avatar + '\'' +
                ", role=" + role +
                ", cardId='" + cardId + '\'' +
                ", cardStatus='" + cardStatus + '\'' +
                ", userType='" + userType + '\'' +
                ", token='" + token + '\'' +
                ", deptIds=" + deptIds +
                '}';
    }
}
