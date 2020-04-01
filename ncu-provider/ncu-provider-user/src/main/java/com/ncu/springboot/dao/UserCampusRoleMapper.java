package com.ncu.springboot.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ncu.springboot.biz.entity.UserCampusRole;
import com.ncu.springboot.mybatis.mapper.BaseMapper;

public interface UserCampusRoleMapper extends BaseMapper<UserCampusRole>{

	List<UserCampusRole> userCampusRoleList(@Param("entity")Map<String , Object> map); // 查询用户园区权限
	
	int remove(int id); // 删除
}
