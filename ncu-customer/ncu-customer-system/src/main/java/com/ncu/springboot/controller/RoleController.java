package com.ncu.springboot.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ncu.springboot.annotation.LogAnno;
import com.ncu.springboot.api.system.bizservice.RoleService;
import com.ncu.springboot.api.system.entity.RoleWithMenu;
import com.ncu.springboot.biz.entity.Role;
import com.ncu.springboot.biz.rs.Res;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Created by zhoufan
 * @Date 2019/9/24 11:18
 */
@RestController
public class RoleController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Reference
    private RoleService roleService;

    /**
     * 查询角色表
     */
    @RequestMapping("/role/list")
    @LogAnno(operateType = "获取角色")
    public Res roleList() {
        List<Role> allRole = roleService.findAllRole();
        return Res.SUCCESS(allRole);
    }

    /**
     * 查询角色和拥有的权限集合
     * @Parmeter roleId 角色id
     * @retrun Res-roleId1 角色拥有权限的集合
     */
    @RequestMapping("/role/getRole")
    public Res getRole(String roleId) {
        try {
            RoleWithMenu roleId1 = roleService.findRoleId(roleId);
            return Res.SUCCESS(roleId1);
        } catch (Exception e) {
            log.error("获取角色信息失败", e);
            return Res.ERROR("获取角色权限失败");
        }
    }

    /**
     * 修改角色
     * @Parmeter Role对象{roleId,roleName,remark}不能为空, menuId 关于权限数组
     * @retrun Res
     */
    @RequestMapping("/role/update")
    @LogAnno(operateType = "修改角色")
    public Res updateRole(Role role, String[] menuId) {
        try {
            roleService.updateRole(role, menuId);
            return Res.SUCCESS("修改角色成功");
        } catch (Exception e) {
            log.error("修改角色失败", e);
            return Res.ERROR("修改角色失败");
        }
    }

    /**
     * 添加角色
     * @Parmeter Role对象{roleId,roleName,remark}不能为空, menuId 关于权限数组
     * @retrun Res
     */
    @RequestMapping("/role/add")
    @LogAnno(operateType = "添加角色")
    public Res addRole(Role role) {
        try {
            roleService.addRole(role);
            return Res.SUCCESS("新增角色成功！");
        } catch (Exception e) {
            log.error("新增角色失败", e);
            return Res.ERROR("新增角色失败");
        }
    }

    /**
     * 删除角色
     * @Parmeter roleId 角色role_id字段值
     * @retrun Res
     */
    @RequestMapping("/role/delete")
    @LogAnno(operateType = "删除角色")
    public Res deleteRoles(String roleId) {
        try {
            roleService.deleteRoles(roleId);
            return Res.SUCCESS("删除角色成功！");
        } catch (Exception e) {
            log.error("删除角色失败", e);
            return Res.ERROR("删除角色失败");
        }
    }


}
