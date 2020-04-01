package com.ncu.springboot.provider.system.dao;

import com.ncu.springboot.biz.entity.Role;
import com.ncu.springboot.mybatis.mapper.BaseMapper;

public interface RoleMapper extends BaseMapper<Role> {
    Role selectRoleId(String roleId);
    Integer updateRoles(Role role);

}
