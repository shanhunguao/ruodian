package com.ncu.springboot.api.resource.bizservice;

import java.util.List;
import java.util.Map;

import com.ncu.springboot.api.resource.entity.EquipmentRoom;
import com.ncu.springboot.biz.rs.Res;


public interface EquipmentRoomBizService {
	
	Integer getTotal(String roomCode,Integer[] campusIds,Integer roomType,String roomName,Integer status,Integer buildingId);
	
	List<Map<String,Object>> queryList(String roomCode,Integer[] campusIds,Integer roomType,String roomName,Integer status,Integer buildingId,Integer floor,Integer size,Integer num);
	
	Integer addEquipmentRoom(EquipmentRoom equipmentRoom);
	
	Integer delEquipmentRoom(Integer[] roomIds);
	
	Integer editEquipmentRoom(EquipmentRoom equipmentRoom);
	
	List<Map<String,Object>> location(Integer roomId,String roomCode,Integer buildingId,Integer floor,Integer[] campusIds);
	
	Map<String, Object> query(Integer roomId,String roomCode);
	
	List<Map<String,Object>> export(String roomCode,Integer[] campusIds,Integer roomType,String roomName,Integer status,Integer buildingId);
	
	Res importExcel(List<Map<String, String>> datas,Integer userId,Integer status,Integer type);
}
