package com.ncu.springboot.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ncu.springboot.AOP.Permission;
import com.ncu.springboot.api.resource.bizservice.EquipmentRoomPlanBizService;
import com.ncu.springboot.api.resource.entity.EquipmentRoomPlan;
import com.ncu.springboot.common.util.Utils;
import com.ncu.springboot.log.LogUtil;
import com.ncu.springboot.biz.rs.Res;

@RestController
@RequestMapping("/equipmentRoom/plan")
public class EquipmentRoomPlanController {

	@Reference
	private EquipmentRoomPlanBizService equipmentRoomPlanBizService;
	
	@Autowired
	private LogUtil<EquipmentRoomPlan> logUtil;
	
	@RequestMapping("/addPlan")
	@Permission
	public Res addPlan(EquipmentRoomPlan equipmentRoomPlan) {
		equipmentRoomPlan.setCreateTime(Utils.getTimeStamp());
		Integer result = equipmentRoomPlanBizService.addPlan(equipmentRoomPlan);
		logUtil.saveLog("add", "设备间平面图添加", equipmentRoomPlan, null);
		return Res.SUCCESS(result);
	}
	
	@RequestMapping("/delPlan")
	@Permission
	public Res delPlan (Integer[] planIds) {
		if(planIds == null || planIds.length<1) {
			return Res.ERROR("1", "未选择对象！");
		}
		Integer result = equipmentRoomPlanBizService.delPlan(planIds);
		logUtil.saveLog("delete", "设备间平面图删除", null, planIds);
		return Res.SUCCESS(result);
	}
	
	@RequestMapping("/editPlan")
	@Permission
	public Res editPlan (EquipmentRoomPlan equipmentRoomPlan) {
		equipmentRoomPlan.setUpdateTime(Utils.getTimeStamp());
		Integer result = equipmentRoomPlanBizService.editPlan(equipmentRoomPlan);
		logUtil.saveLog("edit", "编辑设备间平面图", equipmentRoomPlan, null);
		return Res.SUCCESS(result);
	}
	
	@RequestMapping("/queryList")
	@Permission
	public Res queryList(Integer roomId,Integer buildingId,Integer campusId,Integer floorNum){
		List<Map<String,Object>> result = equipmentRoomPlanBizService.queryList(roomId, buildingId, campusId, floorNum);
		return Res.SUCCESS(result);
	}
}
