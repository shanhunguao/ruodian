package com.ncu.springboot.api.system.bizservice;

import com.ncu.springboot.api.system.entity.RoleWithMenu;
import com.ncu.springboot.biz.entity.Role;

import java.util.List;

/**
 * @Created by zhoufan
 * @Date 2019/9/24 11:25
 */
public interface RoleService {
    List<Role> findAllRole();
    RoleWithMenu findRoleId(String roleId);
    void updateRole(Role role,String[] menuId);
    void addRole(Role role);
    void deleteRoles(String roleId);

}
