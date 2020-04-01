package com.ncu.springboot.api.oauth2.entity;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Created by zhoufan
 * @Date 2020/3/30 17:14
 */
@Table(name = "tb_permission")
public class TbPermission implements Serializable {

    private int id;
    private String permissionId;
    private String permissionName;
    private String permissionEnname;
    private String url;
    private String remark;
    private String parentId;
    private String permissionTypeId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getPermissionEnname() {
        return permissionEnname;
    }

    public void setPermissionEnname(String permissionEnname) {
        this.permissionEnname = permissionEnname;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getPermissionTypeId() {
        return permissionTypeId;
    }

    public void setPermissionTypeId(String permissionTypeId) {
        this.permissionTypeId = permissionTypeId;
    }

    @Override
    public String toString() {
        return "TbPermission{" +
                "id=" + id +
                ", permissionId='" + permissionId + '\'' +
                ", permissionName='" + permissionName + '\'' +
                ", permissionEnname='" + permissionEnname + '\'' +
                ", url='" + url + '\'' +
                ", remark='" + remark + '\'' +
                ", parentId='" + parentId + '\'' +
                ", permissionTypeId='" + permissionTypeId + '\'' +
                '}';
    }
}