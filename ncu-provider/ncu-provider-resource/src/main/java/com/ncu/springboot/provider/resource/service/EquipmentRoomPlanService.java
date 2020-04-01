package com.ncu.springboot.provider.resource.service;

import java.util.List;
import java.util.Map;

import com.ncu.springboot.api.resource.entity.EquipmentRoomPlan;

public interface EquipmentRoomPlanService {

	Integer addPlan(EquipmentRoomPlan equipmentRoomPlan);
	
	Integer delPlan (Integer[] planIds);
	
	Integer editPlan (EquipmentRoomPlan equipmentRoomPlan);
	
	List<Map<String, Object>> queryList(Integer roomId,Integer buildingId,Integer campusId,Integer floorNum);
}
