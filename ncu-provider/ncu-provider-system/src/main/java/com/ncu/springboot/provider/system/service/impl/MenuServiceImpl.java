package com.ncu.springboot.provider.system.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ncu.springboot.api.cache.keys.RolePermissionCacheIdKeys;
import com.ncu.springboot.api.system.bizservice.MenuService;
import com.ncu.springboot.api.system.util.Tree;
import com.ncu.springboot.api.system.util.TreeUtils;
import com.ncu.springboot.biz.entity.Permission;
import com.ncu.springboot.biz.entity.RolePermission;
import com.ncu.springboot.provider.system.dao.PermissionMapper;
import com.ncu.springboot.provider.system.dao.RolePermissionMapper;
import com.ncu.springboot.provider.system.dao.UserRoleMapper;
import com.ncu.springboot.utils.RedisUtil;
import org.osgi.service.component.annotations.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Created by zhoufan
 * @Date 2019/9/20 18:00
 */
@Service
@Component
public class MenuServiceImpl implements MenuService {
    @Resource
    private PermissionMapper permissionMapper;
    @Resource
    private RolePermissionMapper rolePermissionMapper;
    @Resource
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * Cache Aside Pattern缓存+数据库读写模式的分析
     */
    @Override
    public Tree<Permission> getUserMenu(String roleId) {
        List<Permission> menus = null;
        if (redisUtil.hasKey(roleId)) {
            //从缓存中读取该用户对应角色Id拥有的权限集合
            menus = getPermissions(roleId);
            //过滤掉内存中非菜单元素的权限集合
            for (int i = 0; i < menus.size(); i++) {
                if ("2".equals(menus.get(i).getPermissionTypeId())) {
                    menus.remove(i);
                }
            }
        } else {
            //从数据库中获取角色对应的菜单权限集合
            menus = permissionMapper.selectUserMenu(roleId);
            if (!menus.isEmpty()) {
            //添加到内存中
                addredis(menus, roleId);
            }
        }
        List<Tree<Permission>> trees = getTrees(menus);
        return TreeUtils.build(trees);
    }

    /**
     * 基于角色Id查询用户的所有权限集合
     */
    private List<Permission> getPermissions(String roleId) {
        List<Permission> menus = new ArrayList<>();
        Set<Object> permiss = redisUtil.sGet(RolePermissionCacheIdKeys.getPermissionIdsByRoleIdKey(roleId));
        for (Object o : permiss) {
            Permission o1 = (Permission) redisUtil.get(RolePermissionCacheIdKeys.getPermissionByPermissionIdKey((String) o));
            if (o1 != null) {
                menus.add(o1);
            }
        }
        return menus;
    }

    /**
     * 将权限集合封装成tree型结构
     */
    private List<Tree<Permission>> getTrees(List<Permission> menus) {
        List<Tree<Permission>> trees = new ArrayList<>();
        menus.forEach(menu -> {
            Tree<Permission> tree = new Tree<>();
            tree.setId(menu.getPermissionId());
            tree.setParentId(menu.getParentId());
            tree.setText(menu.getPermissionName());
            trees.add(tree);
        });
        return trees;
    }

    /**
     * 将数据添加到内存中
     */
    private void addredis(List<Permission> menus, String roleId) {
        List<RolePermission> rolePermissions = rolePermissionMapper.selectRolePermission(roleId);
        for (RolePermission rolePermission : rolePermissions) {
            redisUtil.sSet(RolePermissionCacheIdKeys.getPermissionIdsByRoleIdKey(roleId), rolePermission.getPermissionId());
        }
        for (Permission menu : menus) {
            redisUtil.set(RolePermissionCacheIdKeys.getPermissionByPermissionIdKey(menu.getPermissionId()), menu);
        }
    }

    @Override
    public Tree<Permission> getMenuButtonTree() {
        List<Permission> menus = getList(RolePermissionCacheIdKeys.getPermissionIds());
        for (int i = 0; i < menus.size(); i++) {
            if ("2".equals(menus.get(i).getPermissionTypeId())) {
                menus.remove(i);
            }
        }
        Tree<Permission> tree = getTree(menus, RolePermissionCacheIdKeys.getPermissionIds(), "permission_type_id <>", 2);
        return tree;
    }


    @Override
    public Tree<Permission> getPortTree() {
        List<Permission> menu = new ArrayList<>();
        List<Permission> menus = getList(RolePermissionCacheIdKeys.getPermissionIds());
        for (int i = 0; i < menus.size(); i++) {
            if ("2".equals(menus.get(i).getPermissionTypeId())) {
                menu.add(menus.get(i));
            }
        }
        Tree<Permission> tree = getTree(menu, RolePermissionCacheIdKeys.getPermissionIds(), "permission_type_id =", 2);
        return tree;
    }

    @Override
    public Tree<Permission> getAllTree() {
        List<Permission> menus = new ArrayList<>();
        Set<Object> permiss = redisUtil.sGet(RolePermissionCacheIdKeys.getPermissionIds());
        for (Object o : permiss) {
            Permission o1 = (Permission) redisUtil.get(RolePermissionCacheIdKeys.getPermissionByPermissionIdKey((String) o));
            if (o1 != null) {
                menus.add(o1);
            }
        }
        if (menus.isEmpty()) {
            menus = permissionMapper.selectAll();
            for (Permission permission : menus) {
                redisUtil.set(RolePermissionCacheIdKeys.getPermissionByPermissionIdKey(permission.getPermissionId()), permission);
                redisUtil.sSet(RolePermissionCacheIdKeys.getPermissionIds(), permission.getPermissionId());
            }
        }
        List<Tree<Permission>> trees = getTrees(menus);
        return TreeUtils.build(trees);
    }

    @Override
    @Transactional
    public int addMenu(Permission permission) {
        int num = getNum(permission);
        permission.setPermissionId(String.valueOf(num));
        int insert = permissionMapper.insert(permission);
        if (insert > 0) {
            Set<Object> permissions = redisUtil.sGet(RolePermissionCacheIdKeys.getPermissionIds());
            permissions.add(permission.getPermissionId());
            for (Object o : permissions) {
                redisUtil.sSet(RolePermissionCacheIdKeys.getPermissionIds(), o);
            }
            redisUtil.set(RolePermissionCacheIdKeys.getPermissionByPermissionIdKey(permission.getPermissionId()), permission);
        }
        return insert;
    }

    private int getNum(Permission permission) {
        int num=0;
        String parentId = permission.getParentId();
        Integer childrenLength = permissionMapper.findParentId(parentId);
        int length = parentId.length();
        if (length==2 ||length==4) {
            int i = Integer.parseInt(parentId);
             num=(i*100)+childrenLength+1;
        }else if(length==1){
            int i = Integer.parseInt(parentId);
            num=(i*10)+childrenLength+1;
        }
        return num;
    }

    @Override
    @Transactional
    public void deleteMeuns(String permissionId) {
        Example example = new Example(Permission.class);
        example.createCriteria().andCondition("permission_id=", permissionId);
        int i = permissionMapper.deleteByExample(example);
        if (i > 0) {
            redisUtil.del(RolePermissionCacheIdKeys.getPermissionIds());
            redisUtil.del(RolePermissionCacheIdKeys.getPermissionByPermissionIdKey(permissionId));
        }
    }

    @Override
    @Transactional
    public void updateMenu(Permission permission) {
        redisUtil.del(RolePermissionCacheIdKeys.getPermissionIds());
        redisUtil.del(RolePermissionCacheIdKeys.getPermissionByPermissionIdKey(permission.getPermissionId()));
        Example example = new Example(Permission.class);
        example.createCriteria().andCondition("permission_id=", permission.getPermissionId());
        permissionMapper.updateByExample(permission, example);
    }


    @Override
    public Permission findById(String menuId) {
        Permission permission = (Permission) redisUtil.get(RolePermissionCacheIdKeys.getPermissionByPermissionIdKey(menuId));
        if (permission == null) {
            permission = permissionMapper.findById(menuId);
            redisUtil.set(RolePermissionCacheIdKeys.getPermissionByPermissionIdKey(menuId), permission);
        }
        return permission;
    }


    /**
     * 根据参数动态获取不同类型的权限
     *
     * @Parmeter key 缓存中的key值,permissionType 权限判断，num 权限类型
     * @retrun tree权限树
     */
    public Tree<Permission> getTree(List<Permission> menus, String key, String permissionType, int num) {
        if (menus.isEmpty()) {
            Example example = new Example(Permission.class);
            example.createCriteria().andCondition(permissionType, num);
            example.setOrderByClause("update_time");
            menus = permissionMapper.selectByExample(example);
            for (Permission permission : menus) {
                redisUtil.sSet(key, permission.getPermissionId());
            }
        }
        List<Tree<Permission>> trees = getTrees(menus);
        return TreeUtils.build(trees);
    }

    private List<Permission> getList(String key) {
        List<Permission> menus = new ArrayList<>();
        Set<Object> permiss = redisUtil.sGet(key);
        if (permiss != null && !permiss.isEmpty()) {
            for (Object o : permiss) {
                Permission o1 = (Permission) redisUtil.get(RolePermissionCacheIdKeys.getPermissionByPermissionIdKey((String) o));
                if (o1 != null) {
                    menus.add(o1);
                }
            }
        }
        return menus;
    }

    /**
     * 判断接口园区权限
     *
     * @Parmeter userCode 用户名 parentId 接口url permission园区集合
     * @retrun
     */
    public boolean findPark(String userCode, String url, List<String> permission) {
        return true;
    }


}