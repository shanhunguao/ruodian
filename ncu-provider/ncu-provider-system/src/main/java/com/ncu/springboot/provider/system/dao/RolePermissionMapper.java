package com.ncu.springboot.provider.system.dao;

import com.ncu.springboot.biz.entity.RolePermission;
import com.ncu.springboot.mybatis.mapper.BaseMapper;

import java.util.List;

public interface RolePermissionMapper extends BaseMapper<RolePermission> {
    List<String> selectRoleId(String roleId);
    List<RolePermission> selectRolePermission(String roleId);
    Integer deleteRoleId(String roleId);

}
