package com.ncu.springboot.provider.resource.service;

import java.util.List;
import java.util.Map;

import com.ncu.springboot.api.resource.entity.EquipmentRoom;

public interface EquipmentRoomService {

	Integer getTotal(String roomCode,Integer[] campusIds,Integer roomType,String roomName,Integer status,Integer buildingId);
	
	List<Map<String,Object>> queryList(String roomCode,Integer[] campusIds,Integer roomType,String roomName,Integer status,Integer buildingId,Integer floor,Integer size,Integer num);
	
	Integer addEquipmentRoom(EquipmentRoom equipmentRoom);
	
	Integer addEquipmentRooms(List<EquipmentRoom> equipmentRooms);
	
	Integer delEquipmentRoom(List<EquipmentRoom> rooms,EquipmentRoom example);
	
	Integer editEquipmentRoom(EquipmentRoom equipmentRoom);
	
	List<Map<String,Object>> location(Integer roomId,String roomCode,Integer buildingId,Integer floor,Integer[] campusIds);
	
	Map<String, Object> query(Integer roomId,String roomCode);
	
	List<Map<String,Object>> export(String roomCode,Integer[] campusIds,Integer roomType,String roomName,Integer status,Integer buildingId);
	
	List<EquipmentRoom> selectRooms(EquipmentRoom room);
	
	List<Map<String, Object>> selectIdByCode(List<String> roomCodes);
}
