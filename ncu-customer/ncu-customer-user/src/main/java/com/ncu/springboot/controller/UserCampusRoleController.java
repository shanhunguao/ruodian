package com.ncu.springboot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ncu.springboot.api.oauth2.bizservice.UserCampusRoleBizService;
import com.ncu.springboot.biz.entity.UserCampusRole;
import com.ncu.springboot.biz.rs.Res;

@RestController
@RequestMapping("/userCampusRole")
public class UserCampusRoleController {

	
	@Reference
	private UserCampusRoleBizService userCampusRoleBizService;
	
	@RequestMapping("/userCampusRoleList")
	public Res userCampusRoleList(Map<String , Object> param) {
		List<UserCampusRole> list = userCampusRoleBizService.userCampusRoleList(param);
		Map<String , Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("rows", list);
		return Res.SUCCESS(jsonMap);
	}
	
	@RequestMapping("/save")
	public Res save(UserCampusRole userCampusRole) {
		if (userCampusRoleBizService.insert(userCampusRole) > 0) {
			return Res.SUCCESS();
		}
		return Res.ERROR();
		
	}
	
	@RequestMapping("/remove")
	public Res Remove(int id) {
		if (userCampusRoleBizService.remove(id) > 0) {
			return Res.SUCCESS("删除成功");
		}
		return Res.ERROR();
	}
	
	@RequestMapping("/updateUserCampusRole")
	public Res updateUserCampusRole(UserCampusRole userCampusRole) {
		if (userCampusRole.getType().contains("0")) {
			if (userCampusRoleBizService.updateUserCampusRole(userCampusRole) > 0) {
				return Res.SUCCESS("修改成功");
			}
		}
		return Res.ERROR();
		
	}
}
