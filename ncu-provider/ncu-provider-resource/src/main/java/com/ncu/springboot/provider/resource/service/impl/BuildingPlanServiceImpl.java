package com.ncu.springboot.provider.resource.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.ncu.springboot.api.resource.entity.BuildingPlan;
import com.ncu.springboot.dao.BuildingPlanMapper;
import com.ncu.springboot.provider.resource.service.BuildingPlanService;

@Service
public class BuildingPlanServiceImpl implements BuildingPlanService {

	@Resource
	private BuildingPlanMapper buildingPlanMapper;
	
	public Integer addPlan(BuildingPlan buildingPlan) {
		buildingPlanMapper.insertUseGeneratedKeys(buildingPlan);
		return buildingPlan.getPlanId();
	}

	public Integer delPlan(Integer[] planIds) {
		Integer count = 0;
		BuildingPlan buildingPlan = new BuildingPlan();
		for (Integer planId : planIds) {
			buildingPlan.setPlanId(planId);
			count += buildingPlanMapper.delete(buildingPlan);
		}
		return count;
	}

	public Integer editPlan(BuildingPlan buildingPlan) {
		return buildingPlanMapper.updateByPrimaryKeySelective(buildingPlan);
	}

	public List<Map<String, Object>> queryList(Integer buildingId, Integer campusId, Integer floorNum) {
		return buildingPlanMapper.queryList(buildingId, campusId, floorNum);
	}

}
