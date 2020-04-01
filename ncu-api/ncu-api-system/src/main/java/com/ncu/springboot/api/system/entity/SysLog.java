package com.ncu.springboot.api.system.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @Created by zhoufan
 * @Date 2019/10/23 10:16
 * 日志表
 */
@Table(name="tb_log")
public class SysLog implements Serializable {
    private Long id;
    private String username;
    private String operation;
    private String method;
    private String params;
    private Long operationType;
    private String ip;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public Long getOperationType() {
        return operationType;
    }

    public void setOperationType(Long operationType) {
        this.operationType = operationType;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "SysLog{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", operation='" + operation + '\'' +
                ", method='" + method + '\'' +
                ", params='" + params + '\'' +
                ", operationType=" + operationType +
                ", ip='" + ip + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}