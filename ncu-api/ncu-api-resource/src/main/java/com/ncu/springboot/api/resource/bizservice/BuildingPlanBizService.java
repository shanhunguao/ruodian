package com.ncu.springboot.api.resource.bizservice;

import java.util.List;
import java.util.Map;

import com.ncu.springboot.api.resource.entity.BuildingPlan;

public interface BuildingPlanBizService {
	
	Integer addPlan(BuildingPlan buildingPlan);
	
	Integer delPlan (Integer[] planIds);
	
	Integer editPlan (BuildingPlan buildingPlan);
	
	List<Map<String, Object>> queryList(Integer buildingId,Integer campusId,Integer floorNum);

}
