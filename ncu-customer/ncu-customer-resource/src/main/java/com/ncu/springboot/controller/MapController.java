package com.ncu.springboot.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ncu.springboot.AOP.Log;
import com.ncu.springboot.AOP.Permission;
import com.ncu.springboot.api.resource.bizservice.MapBizService;
import com.ncu.springboot.biz.rs.Res;

@RestController
@RequestMapping("/map")
public class MapController {

	@Reference
	private MapBizService mapBizService;
	
	@RequestMapping("/queryList")
	@Log(operateType = "地图资源查询")
	@Permission
	public Res queryList(String name) {
		List<Map<String, Object>> data = mapBizService.queryList(name);
		return Res.SUCCESS(data);
	}
	
}
