package com.ncu.springboot.provider.oauth2.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ncu.springboot.biz.entity.UserCampusRole;

public interface UserCampusRoleService {

	List<UserCampusRole> userCampusRoleList(@Param("entity")Map<String , Object> map); // 查询用户园区权限
	
	int insert(UserCampusRole userCampusRole); // 增加
	
	int remove(int id); // 删除
	
	int updateUserCampusRole(UserCampusRole userCampusRole); // 修改
}
