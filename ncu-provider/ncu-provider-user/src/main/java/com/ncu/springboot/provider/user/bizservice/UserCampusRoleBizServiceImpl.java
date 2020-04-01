package com.ncu.springboot.provider.user.bizservice;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.ncu.springboot.api.oauth2.bizservice.UserCampusRoleBizService;
import com.ncu.springboot.biz.entity.UserCampusRole;
import com.ncu.springboot.provider.oauth2.service.impl.UserCampusRoleService;

@Component
@Service
public class UserCampusRoleBizServiceImpl implements UserCampusRoleBizService{

	@Resource
	private UserCampusRoleService userCampusRoleService;
	
	@Override
	public List<UserCampusRole> userCampusRoleList(Map<String, Object> map) {
		return userCampusRoleService.userCampusRoleList(map);
	}

	@Override
	public int insert(UserCampusRole userCampusRole) {
		return userCampusRoleService.insert(userCampusRole);
	}

	@Override
	public int remove(int id) {
		return userCampusRoleService.remove(id);
	}

	@Override
	public int updateUserCampusRole(UserCampusRole userCampusRole) {
		return userCampusRoleService.updateUserCampusRole(userCampusRole);
	}

}
