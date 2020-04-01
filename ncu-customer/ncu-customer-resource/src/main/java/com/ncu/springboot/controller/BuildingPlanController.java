package com.ncu.springboot.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ncu.springboot.AOP.Permission;
import com.ncu.springboot.api.resource.bizservice.BuildingPlanBizService;
import com.ncu.springboot.api.resource.entity.BuildingPlan;
import com.ncu.springboot.common.util.Utils;
import com.ncu.springboot.log.LogUtil;
import com.ncu.springboot.biz.rs.Res;

@RestController
@RequestMapping("/building/plan")
public class BuildingPlanController {

	@Reference
	private BuildingPlanBizService buildingPlanBizService;
	
	@Autowired
	private LogUtil<BuildingPlan> logUtil;
	
	@RequestMapping("/addPlan")
	@Permission
	public Res addPlan(BuildingPlan buildingPlan) {
		buildingPlan.setCreateTime(Utils.getTimeStamp());
		Integer data = buildingPlanBizService.addPlan(buildingPlan);
		logUtil.saveLog("add", "楼栋平面图添加", buildingPlan, null);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/delPlan")
	@Permission
	public Res delPlan (Integer[] planIds) {
		if(planIds == null || planIds.length<1) {
			return Res.ERROR("1", "未选择对象！");
		}
		Integer result = buildingPlanBizService.delPlan(planIds);
		logUtil.saveLog("delete", "楼栋平面图删除", null, planIds);
		return Res.SUCCESS(result);
	}
	
	@RequestMapping("/editPlan")
	@Permission
	public Res editPlan (BuildingPlan buildingPlan) {
		buildingPlan.setUpdateTime(Utils.getTimeStamp());
		Integer result = buildingPlanBizService.editPlan(buildingPlan);
		logUtil.saveLog("edit", "楼栋平面图编辑", buildingPlan, null);
		return Res.SUCCESS(result);
	}
	
	@RequestMapping("/queryList")
	@Permission
	public Res queryList(Integer buildingId,Integer campusId,Integer floorNum){
		List<Map<String,Object>> result = buildingPlanBizService.queryList(buildingId, campusId, floorNum);
		return Res.SUCCESS(result);
	}
	
	
}
