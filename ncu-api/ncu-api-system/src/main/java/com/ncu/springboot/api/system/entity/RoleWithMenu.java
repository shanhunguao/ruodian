package com.ncu.springboot.api.system.entity;

import com.ncu.springboot.biz.entity.Role;

import java.io.Serializable;
import java.util.List;

/**
 * @Created by zhoufan
 * @Date 2019/9/24 16:37
 */
public class RoleWithMenu implements Serializable {
    private static final Long serialVersionUID = 2013847071068967187L;

    private Role role;
    private List<String> menuIds;

    public List<String> getMenuIds() {
        return menuIds;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setMenuIds(List<String> menuIds) {
        this.menuIds = menuIds;
    }
}
