package com.ncu.springboot.provider.resource.bizservice;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.osgi.service.component.annotations.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.ncu.springboot.api.resource.bizservice.EquipmentRoomPlanBizService;
import com.ncu.springboot.api.resource.entity.EquipmentRoomPlan;
import com.ncu.springboot.provider.resource.service.EquipmentRoomPlanService;

@Component
@Service
public class EquipmentRoomPlanBizServiceImpl implements EquipmentRoomPlanBizService {
	
	@Resource
	private EquipmentRoomPlanService equipmentRoomPlanService;

	public Integer addPlan(EquipmentRoomPlan equipmentRoomPlan) {
		return equipmentRoomPlanService.addPlan(equipmentRoomPlan);
	}

	public Integer delPlan(Integer[] planIds) {
		return equipmentRoomPlanService.delPlan(planIds);
	}

	public Integer editPlan(EquipmentRoomPlan equipmentRoomPlan) {
		return equipmentRoomPlanService.editPlan(equipmentRoomPlan);
	}

	public List<Map<String, Object>> queryList(Integer roomId, Integer buildingId, Integer campusId, Integer floorNum) {
		return equipmentRoomPlanService.queryList(roomId, buildingId, campusId, floorNum);
	}

}
