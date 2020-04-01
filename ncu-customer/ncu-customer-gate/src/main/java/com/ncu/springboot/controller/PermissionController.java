package com.ncu.springboot.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ncu.springboot.api.gate.bizservice.PermissionBizService;
import com.ncu.springboot.api.gate.entity.Permission;
import com.ncu.springboot.biz.rs.Res;
import com.ncu.springboot.common.util.Utils;

@RestController
@RequestMapping("/permission")
public class PermissionController {
	
	@Reference
	private PermissionBizService permissionBizService;

	@RequestMapping("/addPermission")
	public Res addPermission(Permission permission) {
		permission.setCreateTime(Utils.getTimeStamp());
		Integer data = permissionBizService.addPermission(permission);
		return Res.SUCCESS(data);
	}

	@RequestMapping("/editPermission")
	public Res editPermission(Permission permission) {
		Integer data = permissionBizService.editPermission(permission);
		return Res.SUCCESS(data);
	}

	@RequestMapping("/delPermission")
	public Res delPermission(Integer id) {
		if (id==null) {
			return Res.ERROR("未选择对象");
		}
		Integer data = permissionBizService.delPermission(id);
		return Res.SUCCESS(data);
	}

	@RequestMapping("/getTotal")
	public Res getTotal(String userId) {
		Integer data = permissionBizService.getTotal(userId);
		return Res.SUCCESS(data);
	}

	@RequestMapping("/queryList")
	public Res queryList(String userId,Integer size,Integer num) {
		List<Map<String, Object>> data = permissionBizService.queryList(userId, size, num);
		return Res.SUCCESS(data);
	}

}
