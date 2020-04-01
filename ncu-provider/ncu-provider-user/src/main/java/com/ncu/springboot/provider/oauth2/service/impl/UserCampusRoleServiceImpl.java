package com.ncu.springboot.provider.oauth2.service.impl;

import java.util.List;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ncu.springboot.biz.entity.UserCampusRole;
import com.ncu.springboot.dao.UserCampusRoleMapper;


@Service
public class UserCampusRoleServiceImpl implements UserCampusRoleService {

	@Resource
	private UserCampusRoleMapper userCampusRoleMapper;

	@Override
	public List<UserCampusRole> userCampusRoleList(Map<String, Object> map) {
		return userCampusRoleMapper.userCampusRoleList(map);
	}

	@Override
	public int insert(UserCampusRole userCampusRole) {
		return userCampusRoleMapper.insertSelective(userCampusRole);
	}

	@Override
	public int remove(int id) {
		return userCampusRoleMapper.remove(id);
	}

	@Override
	public int updateUserCampusRole(UserCampusRole userCampusRole) {
		return userCampusRoleMapper.updateByPrimaryKeySelective(userCampusRole);
	}

}
