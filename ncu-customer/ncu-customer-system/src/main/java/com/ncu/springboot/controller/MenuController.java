package com.ncu.springboot.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ncu.springboot.annotation.LogAnno;
import com.ncu.springboot.api.system.bizservice.MenuService;
import com.ncu.springboot.api.system.util.Tree;
import com.ncu.springboot.biz.entity.Permission;
import com.ncu.springboot.biz.rs.Res;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Created by zhoufan
 * @Date 2019/9/20 17:28
 */
@RestController
public class MenuController {
    @Reference
    private MenuService menuService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 获取用户菜单列表
     *
     * @Parmeter userName 用户名
     * @retrun Tree<Permission> userMenu 用户菜单树形列表
     */
    @RequestMapping("/menu/getUserMenu")
    public Res getUserMenu(String roleId) {
        try {

            Tree<Permission> userMenu = menuService.getUserMenu(roleId);
            return Res.SUCCESS(userMenu);
        } catch (Exception e) {
            logger.error("获取用户菜单失败", e);
            return Res.ERROR("获取用户菜单失败");
        }
    }
    /**
     * 获取菜单和元素权限树
     */
    @RequestMapping("/menu/menuButtonTree")
    public Res getMenuButtonTree() {
        try {
            Tree<Permission> tree = this.menuService.getMenuButtonTree();
            return Res.SUCCESS(tree);
        } catch (Exception e) {
            logger.error("获取菜单和按钮权限列表失败", e);
            return Res.ERROR("获取菜单和按钮权限列表失败！");
        }
    }
    /**
     * 获取接口权限树
     */
    @RequestMapping("/menu/portTree")
    public Res getPortTree() {
        try {
            Tree<Permission> tree = this.menuService.getPortTree();
            return Res.SUCCESS(tree);
        } catch (Exception e) {
            logger.error("获取接口权限列表失败", e);
            return Res.ERROR("获取接口权限列表失败！");
        }
    }
    /**
     * 获取所有权限树
     */
    @RequestMapping("/menu/AllTree")
    public Res getAllTree() {
        try {
            Tree<Permission> tree = this.menuService.getAllTree();
            return Res.SUCCESS(tree);
        } catch (Exception e) {
            logger.error("获取所有权限列表失败", e);
            return Res.ERROR("获取所有权限列表失败！");
        }
    }
    /**
     * 获取permissionId对应的对象信息
     *
     * @Parmeter menuId等于permission_id字段的值
     * @retrun Permission对象
     */
    @RequestMapping("/menu/getPermissionId")
    public Res getMenu(String menuId) {
        try {
            Permission permission = menuService.findById(menuId);
            return Res.SUCCESS(permission);
        } catch (Exception e) {
            logger.error("获取菜单信息失败", e);
            return Res.ERROR("获取信息失败");
        }
    }
    @RequestMapping("/menu/add")
    @LogAnno(operateType = "添加权限")
    public Res addMenu(Permission permission) {
        try {
            if (menuService.addMenu(permission)>0) {
                return Res.SUCCESS("新增权限成功!");
            }
            return Res.ERROR("无法添加按钮以下权限");
        } catch (Exception e) {
            logger.error("新增权限失败", e);
            return Res.ERROR("新增权限失败");
        }
    }
    @RequestMapping("/menu/delete")
    @LogAnno(operateType = "删除权限")
    public Res deleteMenus(String permissionId) {
        try {
            menuService.deleteMeuns(permissionId);
            return Res.SUCCESS("删除权限成功");
        } catch (Exception e) {
            logger.error("删除权限失败", e);
            return Res.ERROR("删除权限失败");
        }
    }
    @RequestMapping("/menu/update")
    @LogAnno(operateType = "修改权限")
    public Res updateMenu(Permission permission) {
        try {
            menuService.updateMenu(permission);
            return Res.SUCCESS("修改权限成功");
        } catch (Exception e) {
            logger.error("修改权限失败", e);
            return Res.ERROR("修改权限失败");
        }
    }

    @RequestMapping("/nginxUploadResult")
    public String findPark(){
        return "";
    }
}