package com.ncu.springboot.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ncu.springboot.AOP.Permission;
import com.ncu.springboot.api.cache.bizservice.UserCacheBizService;
import com.ncu.springboot.api.resource.bizservice.CodeBizService;
import com.ncu.springboot.api.resource.bizservice.EquipmentRoomBizService;
import com.ncu.springboot.api.resource.constant.EquipmentRoomConstant;
import com.ncu.springboot.api.resource.entity.EquipmentRoom;
import com.ncu.springboot.common.util.ExcelUtil;
import com.ncu.springboot.common.util.Utils;
import com.ncu.springboot.log.LogUtil;
import com.ncu.springboot.biz.rs.Res;

@RestController
@RequestMapping("/equipmentRoom")
public class EquipmentRoomController {
	
	@Reference
	private EquipmentRoomBizService equipmentRoomBizService;
	
	@Reference
	private CodeBizService codeBizService;
	
	@Reference
	private UserCacheBizService userCacheBizService;
	
	@Autowired
	private LogUtil<EquipmentRoom> logUtil;
	
	@RequestMapping("/getTotal")
	@Permission
	public Res getTotal(String roomCode,Integer[] campusIds,Integer roomType,String roomName,Integer status,Integer buildingId) {
		Integer data = equipmentRoomBizService.getTotal(roomCode,campusIds, roomType, roomName, status, buildingId);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/queryList")
	@Permission
	public Res queryList(String roomCode,Integer[] campusIds,Integer roomType,String roomName,Integer status,Integer buildingId,Integer floor,Integer size,Integer num){
		List<Map<String, Object>> data = equipmentRoomBizService.queryList(roomCode,campusIds, roomType, roomName, status, buildingId,floor, size, num);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/addEquipmentRoom")
	@Permission
	public Res addEquipmentRoom(EquipmentRoom equipmentRoom) {
		equipmentRoom.setCreateTime(Utils.getTimeStamp());
		equipmentRoom.setRoomCode("SBJ"+Utils.getCodeByUUId());
		Integer data = equipmentRoomBizService.addEquipmentRoom(equipmentRoom);
		logUtil.saveLog("add", "添加设备间", equipmentRoom,null);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/delEquipmentRoom")
	@Permission
	public Res delEquipmentRoom(Integer[] roomIds) {
		if(roomIds == null || roomIds.length<1) {
			return Res.ERROR("1", "未选择对象！");
		}
		Integer data = equipmentRoomBizService.delEquipmentRoom(roomIds);
		logUtil.saveLog("delete", "删除设备间",null ,roomIds);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/editEquipmentRoom")
	@Permission
	public Res editEquipmentRoom(EquipmentRoom equipmentRoom) {
		equipmentRoom.setUpdateTime(Utils.getTimeStamp());
		Integer data = equipmentRoomBizService.editEquipmentRoom(equipmentRoom);
		logUtil.saveLog("edit", "编辑设备间", equipmentRoom,null);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/location")
	@Permission
	public Res location(Integer roomId,String roomCode,Integer buildingId,Integer floor,Integer[] campusIds){
		List<Map<String, Object>> data = equipmentRoomBizService.location(roomId,roomCode,buildingId,floor,campusIds);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/query")
	@Permission
	public Res query(Integer roomId,String roomCode) {
		Map<String, Object> data = equipmentRoomBizService.query(roomId,roomCode);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/importExample")
	public void importExample(HttpServletResponse response) {
		List<Map<String, Object>> datas = new ArrayList<Map<String,Object>>();
		Map<String, Object> data = new HashMap<String, Object>();
		
		//状态
		List<Map<String, Object>> status = codeBizService.query(EquipmentRoomConstant.STATUS, null);
		StringBuffer statusValue = new StringBuffer();
		for (Map<String, Object> map : status) {
			statusValue.append(map.get("option").toString());
		}
		data.put("STATUS", statusValue);
		
		//类型
		List<Map<String, Object>> type = codeBizService.query(EquipmentRoomConstant.TYPE, null);
		StringBuffer typeValue = new StringBuffer();
		for (Map<String, Object> map : type) {
			typeValue.append(map.get("option").toString());
		}
		data.put("ROOM_TYPE", typeValue);
		datas.add(data);
		
		List<String> columns = new ArrayList<String>();
		columns.add("设备间名称");
		columns.add("设备间类型");
		columns.add("状态");
		columns.add("面积");
		columns.add("抱杆数");
		columns.add("简介");
		columns.add("备注");
		columns.add("经度");
		columns.add("纬度");
		columns.add("是否租赁");
		columns.add("所在层数");
		columns.add("负责人");
		columns.add("使用单位");
		columns.add("管理单位");
		columns.add("楼栋编号");
		columns.add("园区编号");
		List<String> keys = new ArrayList<String>();
		keys.add("ROOM_NAME");
		keys.add("ROOM_TYPE");
		keys.add("STATUS");
		keys.add("AREA");
		keys.add("HOLD_BAR_NUM");
		keys.add("FUNCTION");
		keys.add("REMARK");
		keys.add("LONGITUDE");
		keys.add("LATITUDE");
		keys.add("IS_RENT");
		keys.add("FLOOR");
		keys.add("PRINCIPAL_NAME");
		keys.add("USE_DEPT_NAME");
		keys.add("MANAGE_DEPT_NAME");
		keys.add("BUILDING_CODE");
		keys.add("CAMPUS_CODE");
		try {
			ExcelUtil.export(datas, "设备间导入模板", columns,response,keys,"equipmentExample");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/export")
	public void export(HttpServletResponse response,String roomCode,Integer[] campusIds,Integer roomType,String roomName,Integer status,Integer buildingId) {
		List<Map<String, Object>> datas = equipmentRoomBizService.export(roomCode,campusIds, roomType, roomName, status, buildingId);
		List<String> columns = new ArrayList<String>();
		columns.add("设备间编号");
		columns.add("设备间名称");
		columns.add("设备间类型");
		columns.add("面积");
		columns.add("抱杆数");
		columns.add("简介");
		columns.add("备注");
		columns.add("经度");
		columns.add("纬度");
		columns.add("修改时间");
		columns.add("是否租赁");
		columns.add("创建时间");
		columns.add("所在层数");
		columns.add("负责人");
		columns.add("负责人电话");
		columns.add("创建人");
		columns.add("修改人");
		columns.add("使用单位");
		columns.add("管理单位");
		columns.add("楼栋编号");
		columns.add("园区名称");
		columns.add("园区编号");
		List<String> keys = new ArrayList<String>();
		keys.add("ROOM_CODE");
		keys.add("ROOM_NAME");
		keys.add("ROOM_TYPE");
		keys.add("AREA");
		keys.add("HOLD_BAR_NUM");
		keys.add("FUNCTION");
		keys.add("REMARK");
		keys.add("LONGITUDE");
		keys.add("LATITUDE");
		keys.add("UPDATE_TIME");
		keys.add("IS_RENT");
		keys.add("CREATE_TIME");
		keys.add("FLOOR");
		keys.add("PRINCIPAL_NAME");
		keys.add("PRINCIPAL_MOBILE");
		keys.add("CREATE_PERSON_NAME");
		keys.add("UPDATE_PERSON_NAME");
		keys.add("USE_DEPT_NAME");
		keys.add("MANAGE_DEPT_NAME");
		keys.add("BUILDING_CODE");
		keys.add("CAMPUS_NAME");
		keys.add("CAMPUS_CODE");
		try {
			ExcelUtil.export(datas, "设备间", columns,response,keys,"equipment");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/importExcel")
	public Res importExcel(MultipartFile excelFile,String userCode) {
		String name = excelFile.getOriginalFilename();
		
        if (name.length() < 5 || !name.substring(name.length() - 4).equals(".xls")) {
        	return Res.ERROR("文件格式错误");
        }
        List<String> keys = new ArrayList<String>();
        keys.add("ROOM_NAME");
		keys.add("ROOM_TYPE");
		keys.add("AREA");
		keys.add("STATUS");
		keys.add("HOLD_BAR_NUM");
		keys.add("FUNCTION");
		keys.add("REMARK");
		keys.add("LONGITUDE");
		keys.add("LATITUDE");
		keys.add("IS_RENT");
		keys.add("FLOOR");
		keys.add("PRINCIPAL_NAME");
		keys.add("USE_DEPT_NAME");
		keys.add("MANAGE_DEPT_NAME");
		keys.add("BUILDING_CODE");
		keys.add("CAMPUS_CODE");
        // 业务逻辑，通过excelFile.getInputStream()，处理Excel文件
		List<Map<String, String>> datas = new ArrayList<Map<String,String>>();
		try {
			datas = ExcelUtil.excelToData(excelFile.getInputStream(), keys);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Integer employeeId = null;
		try {
			employeeId = Integer.parseInt(userCacheBizService.getUserInfoByUsercode(userCode).getEmployee().getEmployeeId());
		} catch (Exception e) {
			e.printStackTrace();
			return Res.ERROR("用户信息出错，请尝试重新登录");
		}
		
		Res result = equipmentRoomBizService.importExcel(datas,employeeId,EquipmentRoomConstant.STATUS,EquipmentRoomConstant.TYPE);
		return result;
	}
}
