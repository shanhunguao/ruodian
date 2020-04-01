package com.ncu.springboot.provider.gate.bizservice;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.osgi.service.component.annotations.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.ncu.springboot.api.gate.bizservice.PermissionBizService;
import com.ncu.springboot.api.gate.entity.Permission;
import com.ncu.springboot.provider.gate.service.PermissionService;

@Component
@Service
public class PermissionBizServiceImpl implements PermissionBizService {

	@Resource
	private PermissionService permissionService;
	
	public Integer addPermission(Permission permission) {
		return permissionService.addPermission(permission);
	}

	public Integer editPermission(Permission permission) {
		return permissionService.editPermission(permission);
	}

	public Integer delPermission(Integer id) {
		return permissionService.delPermission(id);
	}

	public Integer getTotal(String userId) {
		return permissionService.getTotal(userId);
	}

	public List<Map<String, Object>> queryList(String userId, Integer size, Integer num) {
		if(size != null && num !=null) {
			num = (num-1)*size;
			}
		return permissionService.queryList(userId, size, num);
	}

}
