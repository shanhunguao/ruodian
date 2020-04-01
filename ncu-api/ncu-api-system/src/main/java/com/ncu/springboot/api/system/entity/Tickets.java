package com.ncu.springboot.api.system.entity;

import java.io.Serializable;

/**
 * @Created by zhoufan
 * @Date 2020/3/13 10:41
 * 万文清依赖的企业微信返回值
 */
public class Tickets implements Serializable {

    private String appId;
    private Long timestamp;
    private String nonceStr;
    private String signature;

    public Tickets() {
    }

    public Tickets(String appId, Long timestamp, String nonceStr, String signature) {
        this.appId = appId;
        this.timestamp = timestamp;
        this.nonceStr = nonceStr;
        this.signature = signature;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Override
    public String toString() {
        return "Tickets{" +
                "appId='" + appId + '\'' +
                ", timestamp=" + timestamp +
                ", nonceStr='" + nonceStr + '\'' +
                ", signature='" + signature + '\'' +
                '}';
    }
}
