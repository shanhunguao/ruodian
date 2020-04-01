package com.ncu.springboot.provider.resource.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;
import com.ncu.springboot.api.resource.entity.EquipmentRoom;
import com.ncu.springboot.dao.EquipmentRoomMapper;
import com.ncu.springboot.provider.resource.service.EquipmentRoomService;

import tk.mybatis.mapper.entity.Example;

@Service
public class EquipmentRoomServiceImpl implements EquipmentRoomService{
	
	@Resource
	private EquipmentRoomMapper equipmentRoomMapper;
	
	@Override
	public Integer getTotal(String roomCode,Integer[] campusIds,Integer roomType,String roomName,Integer status,Integer buildingId) {
		return equipmentRoomMapper.getTotal(roomCode,campusIds, roomType, roomName, status, buildingId);
	}

	@Override
	public List<Map<String, Object>> queryList(String roomCode,Integer[] campusIds,Integer roomType,String roomName,Integer status,Integer buildingId, Integer floor,Integer size, Integer num) {
		return equipmentRoomMapper.queryList(roomCode,campusIds, roomType, roomName, status, buildingId, floor,size, num);
	}

	@Override
	public Integer addEquipmentRoom(EquipmentRoom equipmentRoom) {
		return equipmentRoomMapper.insert(equipmentRoom);
	}

	public Integer delEquipmentRoom(List<EquipmentRoom> rooms,EquipmentRoom roomExample) {
		Integer count = 0;
		for (EquipmentRoom room : rooms) {
			Example example = new Example(EquipmentRoom.class);
			example.createCriteria().andEqualTo(room);
			count += equipmentRoomMapper.updateByExampleSelective(roomExample,example);
		}
		return count;
	}

	public Integer editEquipmentRoom(EquipmentRoom equipmentRoom) {
		return equipmentRoomMapper.updateByPrimaryKeySelective(equipmentRoom);
	}

	@Override
	public List<Map<String, Object>> location(Integer roomId,String roomCode,Integer buildingId,Integer floor,Integer[] campusIds) {
		return equipmentRoomMapper.location(roomId,roomCode,buildingId,floor,campusIds);
	}

	public Map<String, Object> query(Integer roomId,String roomCode) {
		return equipmentRoomMapper.query(roomId, roomCode);
	}

	@Override
	public List<Map<String, Object>> export(String roomCode,Integer[] campusIds,Integer roomType,String roomName,Integer status,Integer buildingId) {
		return equipmentRoomMapper.export(roomCode,campusIds, roomType, roomName, status, buildingId);
	}

	//单表查询
	public List<EquipmentRoom> selectRooms(EquipmentRoom room) {
		return equipmentRoomMapper.select(room);
	}

	@Override
	public Integer addEquipmentRooms(List<EquipmentRoom> equipmentRooms) {
		return equipmentRoomMapper.insertList(equipmentRooms);
	}

	@Override
	public List<Map<String, Object>> selectIdByCode(List<String> roomCodes) {
		return equipmentRoomMapper.selectIdByCode(roomCodes);
	}

}
