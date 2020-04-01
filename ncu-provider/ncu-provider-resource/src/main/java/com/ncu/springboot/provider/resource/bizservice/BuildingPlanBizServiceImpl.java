package com.ncu.springboot.provider.resource.bizservice;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.osgi.service.component.annotations.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.ncu.springboot.api.resource.bizservice.BuildingPlanBizService;
import com.ncu.springboot.api.resource.entity.BuildingPlan;
import com.ncu.springboot.provider.resource.service.BuildingPlanService;

@Component
@Service
public class BuildingPlanBizServiceImpl implements BuildingPlanBizService {

	@Resource
	private BuildingPlanService buildingPlanService;
	
	public Integer addPlan(BuildingPlan buildingPlan) {
		return buildingPlanService.addPlan(buildingPlan);
	}

	public Integer delPlan(Integer[] planIds) {
		return buildingPlanService.delPlan(planIds);
	}

	public Integer editPlan(BuildingPlan buildingPlan) {
		return buildingPlanService.editPlan(buildingPlan);
	}

	public List<Map<String, Object>> queryList(Integer buildingId, Integer campusId, Integer floorNum) {
		return buildingPlanService.queryList(buildingId, campusId, floorNum);
	}

}
