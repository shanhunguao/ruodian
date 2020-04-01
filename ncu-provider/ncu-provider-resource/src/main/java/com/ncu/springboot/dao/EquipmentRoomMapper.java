package com.ncu.springboot.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ncu.springboot.api.resource.entity.EquipmentRoom;
import com.ncu.springboot.mybatis.mapper.BaseMapper;

public interface EquipmentRoomMapper extends BaseMapper<EquipmentRoom> {
	
	Integer getTotal(@Param("roomCode")String roomCode,@Param("campusIds")Integer[] campusIds,@Param("roomType")Integer roomType,@Param("roomName")String roomName,@Param("status")Integer status,@Param("buildingId")Integer buildingId);
	
	List<Map<String,Object>> queryList(@Param("roomCode")String roomCode,@Param("campusIds")Integer[] campusIds,@Param("roomType")Integer roomType,@Param("roomName")String roomName,@Param("status")Integer status,@Param("buildingId")Integer buildingId,@Param("floor")Integer floor,@Param("size")Integer size,@Param("num")Integer num);
	
	List<Map<String,Object>> location(@Param("roomId")Integer roomId,@Param("roomCode")String roomCode,@Param("buildingId")Integer buildingId,@Param("floor")Integer floor,@Param("campusIds")Integer[] campusIds);
	
	List<Map<String,Object>> export(@Param("roomCode")String roomCode,@Param("campusIds")Integer[] campusIds,@Param("roomType")Integer roomType,@Param("roomName")String roomName,@Param("status")Integer status,@Param("buildingId")Integer buildingId);
	
	Map<String, Object> query(@Param("roomId")Integer roomId,@Param("roomCode")String roomCode);
	
	List<Map<String, Object>> selectIdByCode(@Param("roomCodes")List<String> roomCodes);
}
