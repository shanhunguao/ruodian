package com.ncu.springboot.api.system.entity;

/**
 * @Created by zhoufan
 * @Date 2020/3/13 10:00
 */
public class Ticket {

    private String errcode;
    private String errmsg;
    private String ticket;
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

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "errcode='" + errcode + '\'' +
                ", errmsg='" + errmsg + '\'' +
                ", ticket='" + ticket + '\'' +
                ", expires_in='" + expires_in + '\'' +
                '}';
    }
}
