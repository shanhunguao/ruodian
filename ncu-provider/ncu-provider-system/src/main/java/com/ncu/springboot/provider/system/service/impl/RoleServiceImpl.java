package com.ncu.springboot.provider.system.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ncu.springboot.api.cache.keys.RolePermissionCacheIdKeys;
import com.ncu.springboot.api.system.bizservice.RoleService;
import com.ncu.springboot.api.system.entity.RoleWithMenu;
import com.ncu.springboot.biz.entity.Role;
import com.ncu.springboot.biz.entity.RolePermission;
import com.ncu.springboot.provider.system.dao.PermissionMapper;
import com.ncu.springboot.provider.system.dao.RoleMapper;
import com.ncu.springboot.provider.system.dao.RolePermissionMapper;
import com.ncu.springboot.utils.RedisUtil;
import org.osgi.service.component.annotations.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Created by zhoufan
 * @Date 2019/9/24 13:37
 */
@Service
@Component
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RolePermissionMapper rolePermissionMapper;
    @Resource
    private PermissionMapper permissionMapper;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public List<Role> findAllRole() {
        List<Role> roles = new ArrayList<>();
//        角色Id集合
        Set<Object> roleIds = redisUtil.sGet(RolePermissionCacheIdKeys.getRoleByRoleIdsKey());
        for (Object o : roleIds) {
//        所有角色Id对应的对象集合
            Role role = (Role) redisUtil.get(RolePermissionCacheIdKeys.getRoleByRoleIdKey((String) o));
            if (role==null) {
                role = roleMapper.selectRoleId((String) o);
            }
            roles.add(role);
        }
        if (roles.isEmpty()) {
            roles = roleMapper.selectAll();
            for (Role role : roles) {
                redisUtil.sSet(RolePermissionCacheIdKeys.getRoleByRoleIdsKey(), role.getRoleId());
                redisUtil.set(RolePermissionCacheIdKeys.getRoleByRoleIdKey(role.getRoleId()), role);
            }
        }
        return roles;
    }


    @Override
    public RoleWithMenu findRoleId(String roleId) {
        List<String> menu = new ArrayList<>();
//        基于角色Id对应的对象
        Role role = (Role) redisUtil.get(RolePermissionCacheIdKeys.getRoleByRoleIdKey(roleId));
//        基于角色Id对应的权限
        Set<Object> permiss = redisUtil.sGet(RolePermissionCacheIdKeys.getPermissionIdsByRoleIdKey(roleId));
        for (Object o : permiss) {
            if (o != null) {
                menu.add((String) o);
            }
        }
        if (role == null || menu.isEmpty()) {
            role = roleMapper.selectRoleId(roleId);
            redisUtil.set(RolePermissionCacheIdKeys.getRoleByRoleIdKey(roleId), role);
            redisUtil.set(RolePermissionCacheIdKeys.getRoleByRoleIdKey(roleId), role);
            menu = rolePermissionMapper.selectRoleId(roleId);
            for (String permissionId : menu) {
                redisUtil.sSet(RolePermissionCacheIdKeys.getPermissionIdsByRoleIdKey(roleId), permissionId);
            }
        }
//        过滤掉拥有子节点的权限ID
        Iterator<String> it = menu.iterator();
        while (it.hasNext()) {
            String x = it.next();
            Integer childrenLength = permissionMapper.findParentId(x);
            if (childrenLength > 0) {
                it.remove();
            }
        }
        RoleWithMenu roleWithMenu = new RoleWithMenu();
        roleWithMenu.setRole(role);
        roleWithMenu.setMenuIds(menu);
        return roleWithMenu;
    }

    @Override
    @Transactional
    public void updateRole(Role role, String[] menuIds) {
//        先删除缓存，然后在更新数据库
        deleteRoleId(role.getRoleId());
        roleMapper.updateRoles(role);
        Example example = new Example(RolePermission.class);
        example.createCriteria().andCondition("role_id=", role.getRoleId());
        rolePermissionMapper.deleteByExample(example);
        addRolePermission(role.getRoleId(), menuIds);
    }

    @Override
    @Transactional
    public void addRole(Role role) {
        int insert = roleMapper.insert(role);
        if (insert>0) {
            Set<Object> roleIds = redisUtil.sGet(RolePermissionCacheIdKeys.getRoleByRoleIdsKey());
            roleIds.add(role.getRoleId());
//        添加到角色Id集合
            for (Object roleId : roleIds) {
                redisUtil.sSet(RolePermissionCacheIdKeys.getRoleByRoleIdsKey(), roleId);
            }
//        添加角色
            redisUtil.set(RolePermissionCacheIdKeys.getRoleByRoleIdKey(role.getRoleId()), role);
        }

    }

    /**
     * 添加缓存
     */
    private void addRoleId(Role role, String[] menuId) {
        Set<Object> roleIds = redisUtil.sGet(RolePermissionCacheIdKeys.getRoleByRoleIdsKey());
        roleIds.add(role.getRoleId());
//        添加到角色Id集合
        for (Object roleId : roleIds) {
            redisUtil.sSet(RolePermissionCacheIdKeys.getRoleByRoleIdsKey(), roleId);
        }
//        添加角色
        redisUtil.set(RolePermissionCacheIdKeys.getRoleByRoleIdKey(role.getRoleId()), role);
//        添加角色对应的权限
        for (String permissionId : menuId) {
            redisUtil.sSet(RolePermissionCacheIdKeys.getPermissionIdsByRoleIdKey(role.getRoleId()), permissionId);
        }
    }

    @Override
    @Transactional
    public void deleteRoles(String roleId) {
        redisUtil.setRemove(RolePermissionCacheIdKeys.getRoleByRoleIdsKey(),roleId);
        deleteRoleId(roleId);
        Example example = new Example(RolePermission.class);
        example.createCriteria().andCondition("role_id=", roleId);
        roleMapper.deleteByExample(example);
        rolePermissionMapper.deleteByExample(example);
    }

    /**
     * 删除缓存
     */
    private void deleteRoleId(String roleId) {
        // 基于value删除角色集合中角色id
        redisUtil.del(RolePermissionCacheIdKeys.getRoleByRoleIdKey(roleId));
        redisUtil.del(RolePermissionCacheIdKeys.getPermissionIdsByRoleIdKey(roleId));
    }

    private void addRolePermission(String roleId, String[] menuIds) {
        Arrays.stream(menuIds).forEach(menuId -> {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(menuId);
            rolePermissionMapper.insert(rolePermission);
        });
    }


}
