package com.ncu.springboot.entity;

import java.io.Serializable;

/**
 * @Created by zhoufan
 * @Date 2020/3/4 17:39
 */
public class LoginUser implements Serializable {

    private String account;
    private String ssoAccount;
    private String deptName;
    private String idCard;
    private String studentNo;
    private String remark;
    private String localAccount;
    private String tel;
    private String dicOrgId;
    private String nick;
    private String email;
    private String staffNo;
    private String name;
    private String deptCode;
    private String dn;
    private String mobile;
    private String typeCode;
    private String type;
    private String typeName;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getSsoAccount() {
        return ssoAccount;
    }

    public void setSsoAccount(String ssoAccount) {
        this.ssoAccount = ssoAccount;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLocalAccount() {
        return localAccount;
    }

    public void setLocalAccount(String localAccount) {
        this.localAccount = localAccount;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getDicOrgId() {
        return dicOrgId;
    }

    public void setDicOrgId(String dicOrgId) {
        this.dicOrgId = dicOrgId;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStaffNo() {
        return staffNo;
    }

    public void setStaffNo(String staffNo) {
        this.staffNo = staffNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDn() {
        return dn;
    }

    public void setDn(String dn) {
        this.dn = dn;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "LoginUser{" +
                "account='" + account + '\'' +
                ", ssoAccount='" + ssoAccount + '\'' +
                ", deptName='" + deptName + '\'' +
                ", idCard='" + idCard + '\'' +
                ", studentNo='" + studentNo + '\'' +
                ", remark='" + remark + '\'' +
                ", localAccount='" + localAccount + '\'' +
                ", tel='" + tel + '\'' +
                ", dicOrgId='" + dicOrgId + '\'' +
                ", nick='" + nick + '\'' +
                ", email='" + email + '\'' +
                ", staffNo='" + staffNo + '\'' +
                ", name='" + name + '\'' +
                ", deptCode='" + deptCode + '\'' +
                ", dn='" + dn + '\'' +
                ", mobile='" + mobile + '\'' +
                ", typeCode='" + typeCode + '\'' +
                ", type='" + type + '\'' +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}