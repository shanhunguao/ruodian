package com.ncu.springboot.dao;

import org.apache.ibatis.annotations.Param;

import com.ncu.springboot.biz.entity.User;
import com.ncu.springboot.mybatis.mapper.BaseMapper;

public interface UserSecurityMapper extends BaseMapper<User> {

    public User getUserInfoAndRolesByUsercode(@Param("usercode") String usercode);
    
    public User getUserInfoAndRolesByMobile(@Param("mobile") String mobile);

}
