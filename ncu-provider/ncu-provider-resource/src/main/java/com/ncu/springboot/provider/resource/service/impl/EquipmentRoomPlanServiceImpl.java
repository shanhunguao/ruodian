package com.ncu.springboot.provider.resource.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.ncu.springboot.api.resource.entity.EquipmentRoomPlan;
import com.ncu.springboot.dao.EquipmentRoomPlanMapper;
import com.ncu.springboot.provider.resource.service.EquipmentRoomPlanService;

@Service
public class EquipmentRoomPlanServiceImpl implements EquipmentRoomPlanService {

	@Resource
	private EquipmentRoomPlanMapper equipmentRoomPlanMapper;
	
	public Integer addPlan(EquipmentRoomPlan equipmentRoomPlan) {
		equipmentRoomPlanMapper.insertUseGeneratedKeys(equipmentRoomPlan);
		return equipmentRoomPlan.getPlanId();
	}

	public Integer delPlan(Integer[] planIds) {
		Integer count = 0;
		EquipmentRoomPlan equipmentRoomPlan = new EquipmentRoomPlan();
		for (Integer planId : planIds) {
			equipmentRoomPlan.setPlanId(planId);
			count += equipmentRoomPlanMapper.delete(equipmentRoomPlan);
		}
		return count;
	}

	public Integer editPlan(EquipmentRoomPlan equipmentRoomPlan) {
		return equipmentRoomPlanMapper.updateByPrimaryKeySelective(equipmentRoomPlan);
	}

	public List<Map<String, Object>> queryList(Integer roomId, Integer buildingId, Integer campusId, Integer floorNum) {
		return equipmentRoomPlanMapper.queryList(roomId, buildingId, campusId, floorNum);
	}

}
