package com.ncu.springboot.api.system.bizservice;


import com.ncu.springboot.api.system.util.Tree;
import com.ncu.springboot.biz.entity.Permission;

import java.util.List;

/**
 * @Created by zhoufan
 * @Date 2019/9/20 17:28
 */
public interface MenuService {
    Tree<Permission> getUserMenu(String roleId);
    Tree<Permission> getMenuButtonTree();
    Permission findById(String menuId);
    Tree<Permission> getPortTree();
    Tree<Permission> getAllTree();
    int addMenu(Permission permission);
    void deleteMeuns(String permissionId);
    void  updateMenu(Permission permission);
    boolean findPark(String userCode, String url,List<String> permission);
}