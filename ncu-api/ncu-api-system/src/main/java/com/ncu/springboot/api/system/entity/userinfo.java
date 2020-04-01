package com.ncu.springboot.api.system.entity;

/**
 * @Created by zhoufan
 * @Date 2020/2/25 17:17
 */
public class userinfo {
    private String errcode;
    private String errmsg;
    private String UserId;
    private String DeviceId;
    private String user_ticket;
    private String expires_in;

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getDeviceId() {
        return DeviceId;
    }

    public void setDeviceId(String deviceId) {
        DeviceId = deviceId;
    }

    public String getUser_ticket() {
        return user_ticket;
    }

    public void setUser_ticket(String user_ticket) {
        this.user_ticket = user_ticket;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    @Override
    public String toString() {
        return "userinfo{" +
                "errcode='" + errcode + '\'' +
                ", errmsg='" + errmsg + '\'' +
                ", UserId='" + UserId + '\'' +
                ", DeviceId='" + DeviceId + '\'' +
                ", user_ticket='" + user_ticket + '\'' +
                ", expires_in='" + expires_in + '\'' +
                '}';
    }
}
