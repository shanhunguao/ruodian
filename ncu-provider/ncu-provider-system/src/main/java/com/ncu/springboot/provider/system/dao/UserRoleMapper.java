package com.ncu.springboot.provider.system.dao;

import com.ncu.springboot.biz.entity.UserRole;
import com.ncu.springboot.mybatis.mapper.BaseMapper;

/**
 * @Created by zhoufan
 * @Date 2019/9/27 14:33
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {
    String findRoleId(String userName);
}
