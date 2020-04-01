package com.ncu.springboot.provider.resource.bizservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.osgi.service.component.annotations.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.ncu.springboot.api.resource.bizservice.EquipmentRoomBizService;
import com.ncu.springboot.api.resource.constant.EquipmentRoomConstant;
import com.ncu.springboot.api.resource.entity.Building;
import com.ncu.springboot.api.resource.entity.EquipmentRoom;
import com.ncu.springboot.common.util.Utils;
import com.ncu.springboot.biz.rs.Res;
import com.ncu.springboot.provider.resource.service.BuildingService;
import com.ncu.springboot.provider.resource.service.CampusService;
import com.ncu.springboot.provider.resource.service.EquipmentRoomService;
import com.ncu.springboot.util.UserUtil;

@Component
@Service
public class EquipmentRoomBizServiceImpl implements EquipmentRoomBizService {
	
	@Resource
	private EquipmentRoomService equipmentRoomService;
	
	@Resource
	private CabinetBizServiceImpl cabinetBizServiceImpl;
	
	@Resource
	private CodeBizServiceImpl codeBizServiceImpl;
	
	@Resource
	private CampusService campusService;
	
	@Resource
	private BuildingService buildingServer;
	
	@Autowired
	private UserUtil userUtil;
	
	@Override
	public Integer getTotal(String roomCode,Integer[] campusIds,Integer roomType,String roomName,Integer status,Integer buildingId) {
		return equipmentRoomService.getTotal(roomCode,campusIds, roomType, roomName, status, buildingId);
	}

	@Override
	public List<Map<String, Object>> queryList(String roomCode,Integer[] campusIds,Integer roomType,String roomName,Integer status,Integer buildingId,Integer floor, Integer size, Integer num) {
		if(size != null && num !=null) {
			num = (num-1)*size;
			}
		return equipmentRoomService.queryList(roomCode,campusIds, roomType, roomName, status, buildingId, floor,size, num);
	}

	@Override
	public Integer addEquipmentRoom(EquipmentRoom equipmentRoom) {
		return equipmentRoomService.addEquipmentRoom(equipmentRoom);
	}

	@Transactional
	public Integer delEquipmentRoom(Integer[] roomIds) {
		List<EquipmentRoom> rooms = new ArrayList<EquipmentRoom>();
		for (Integer roomId : roomIds) {
			EquipmentRoom room = new EquipmentRoom();
			room.setRoomId(roomId);
			rooms.add(room);
		}
		//创建设备间模板
		EquipmentRoom roomExample = new EquipmentRoom();
		roomExample.setStatus(EquipmentRoomConstant.DISCARD_STATU);//废弃
		//关联删除机柜
		cabinetBizServiceImpl.delByRoom(rooms);
		return equipmentRoomService.delEquipmentRoom(rooms,roomExample);
	}
	
	@Transactional
	public Integer delByBuilding(List<Building> buildings) {
		List<EquipmentRoom> rooms = new ArrayList<EquipmentRoom>();
		for (Building building : buildings) {
			EquipmentRoom room = new EquipmentRoom();
			room.setBuildingId(building.getBuildingId());
			rooms.add(room);
		}
		//创建设备间模板
		EquipmentRoom roomExample = new EquipmentRoom();
		roomExample.setStatus(EquipmentRoomConstant.DISCARD_STATU);//废弃
		//关联删除机柜
		cabinetBizServiceImpl.delByRoom(rooms);
		return equipmentRoomService.delEquipmentRoom(rooms, roomExample);
	}

	@Override
	public Integer editEquipmentRoom(EquipmentRoom equipmentRoom) {
		return equipmentRoomService.editEquipmentRoom(equipmentRoom);
	}

	@Override
	public List<Map<String, Object>> location(Integer roomId,String roomCode,Integer buildingId,Integer floor,Integer[] campusIds) {
		return equipmentRoomService.location(roomId,roomCode,buildingId,floor,campusIds);
	}

	@Override
	public Map<String, Object> query(Integer roomId,String roomCode) {
		return equipmentRoomService.query(roomId,roomCode);
	}

	@Override
	public List<Map<String, Object>> export(String roomCode,Integer[] campusIds,Integer roomType,String roomName,Integer status,Integer buildingId) {
		return equipmentRoomService.export(roomCode,campusIds, roomType, roomName, status, buildingId);
	}

	@Override
	public Res importExcel(List<Map<String, String>> datas, Integer userId, Integer status, Integer type) {
		List<EquipmentRoom> equipmentRooms = new ArrayList<EquipmentRoom>();
		List<String> campusCodes = new ArrayList<String>();
		List<String> buildingCodes = new ArrayList<String>();
		List<String> deptNames = new ArrayList<String>();
		List<String> employeeNames = new ArrayList<String>();
		
		String msg = null;
		
		for (int i = 0; i < datas.size(); i++) {
			campusCodes.add(datas.get(i).get("CAMPUS_CODE"));
			buildingCodes.add(datas.get(i).get("BUILDING_CODE"));
			deptNames.add(datas.get(i).get("MANAGE_DEPT_NAME"));
			deptNames.add(datas.get(i).get("USE_DEPT_NAME"));
			employeeNames.add(datas.get(i).get("PRINCIPAL_NAME"));
		}
		Map<String, String> deptIds = Utils.listToMap(userUtil.getDeptMapByDeptName(deptNames), "name", "id");
		Map<String, String> employeeIds = Utils.listToMap(userUtil.getEmployeeMapByEmployeeName(employeeNames), "employeeName", "employeeId");
		
		Map<String, String> campusIds = Utils.listToMap(campusService.selectIdByCode(campusCodes), "CAMPUS_CODE", "CAMPUS_ID");
		Map<String, String> buildingIds = Utils.listToMap(buildingServer.selectIdByCode(buildingCodes), "BUILDING_CODE", "BUILDING_ID");
		
		Map<String, String> roomStatus =Utils.listToMap(codeBizServiceImpl.query(status, null), "option", "codeId");
		Map<String, String> roomType =Utils.listToMap(codeBizServiceImpl.query(type, null), "option", "codeId");
		
		for (int i = 0; i < datas.size(); i++) {
			EquipmentRoom equipmentRoom = new EquipmentRoom();
			try {
				equipmentRoom.setRoomName(datas.get(i).get("ROOM_NAME"));
				equipmentRoom.setArea(datas.get(i).get("AREA"));
				equipmentRoom.setHoldBarNum(datas.get(i).get("HOLD_BAR_NUM"));
				equipmentRoom.setFloor(Integer.parseInt(datas.get(i).get("FLOOR")));
				equipmentRoom.setFunction(datas.get(i).get("FUNCTION"));
				equipmentRoom.setRemark(datas.get(i).get("REMARK"));
				equipmentRoom.setLongitude(datas.get(i).get("LONGITUDE"));
				equipmentRoom.setLatitude(datas.get(i).get("LATITUDE"));
				if (datas.get(i).get("IS_RENT")=="是") {
					equipmentRoom.setIsRent(100001);
				}else if (datas.get(i).get("IS_RENT")=="否") {
					equipmentRoom.setIsRent(100002);
				}else {
					throw new Exception();
				}
				equipmentRoom.setPrincipal(Integer.parseInt(employeeIds.get(datas.get(i).get("PRINCIPAL_NAME"))));
				equipmentRoom.setUseDept(Integer.parseInt(deptIds.get(datas.get(i).get("USE_DEPT_NAME"))));
				equipmentRoom.setManageDept(Integer.parseInt(deptIds.get(datas.get(i).get("MANAGE_DEPT_NAME"))));
				equipmentRoom.setBuildingId(Integer.parseInt(buildingIds.get(datas.get(i).get("BUILDING_CODE"))));
				equipmentRoom.setCampusId(Integer.parseInt(campusIds.get(datas.get(i).get("CAMPUS_CODE"))));
				equipmentRoom.setStatus(Integer.parseInt(roomStatus.get(datas.get(i).get("STATUS"))));
				equipmentRoom.setRoomType(Integer.parseInt(roomType.get(datas.get(i).get("ROOM_TYPE"))));
				
				equipmentRoom.setRoomCode("SBJ"+Utils.getCodeByUUId());
			} catch (Exception e) {
				msg = "该条数据出错，请检查！\n"+
					  "园区名称："+datas.get(i).get("CAMPUS_NAME")+"\n"+
					  "状态："+datas.get(i).get("STATUS")+"\n"+
					  "备注："+datas.get(i).get("REMARK")+"\n"+
					  "简介："+datas.get(i).get("FUNCTION")+"\n"+
					  "面积："+datas.get(i).get("AREA")+"\n"+
					  "经度："+datas.get(i).get("LONGITUDE")+"\n"+
					  "纬度："+datas.get(i).get("LATITUDE")+"\n"+
					  "负责人："+datas.get(i).get("PRINCIPAL_NAME")+"\n"+
					  "管理单位："+datas.get(i).get("MANAGE_DEPT_NAME")+"\n"+
					  "使用单位："+datas.get(i).get("USE_DEPT_NAME");
				e.printStackTrace();
			}
			
			equipmentRoom.setCreateTime(Utils.getTimeStamp());
			equipmentRoom.setCreatePersonId(userId);
			
			equipmentRooms.add(equipmentRoom);
		}
		
		Res res = Res.Res("0", "", null);
		if (msg != null) {
			res.setCode("1");
			res.setMessage(msg);
		} 
		
		Integer count = equipmentRoomService.addEquipmentRooms(equipmentRooms);
		if (count == 0) {
			res.setCode("1");
			res.setMessage("无数据！");
		} 
		
		return res;
	}

}
