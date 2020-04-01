package com.ncu.springboot.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ncu.springboot.api.resource.entity.Cabinet;
import com.ncu.springboot.mybatis.mapper.BaseMapper;

public interface CabinetMapper extends BaseMapper<Cabinet> {
	
	Integer getTotal(@Param("cabinetCode")String cabinetCode,@Param("campusIds")Integer[] campusIds,@Param("roomName")String roomName,@Param("cabinetName")String cabinetName,@Param("status")Integer status);
	
	List<Map<String,Object>> queryList(@Param("cabinetCode")String cabinetCode,@Param("campusIds")Integer[] campusIds,@Param("roomName")String roomName,@Param("cabinetName")String cabinetName,@Param("status")Integer status,@Param("size")Integer size,@Param("num")Integer num);
	
	List<Map<String,Object>> export(@Param("cabinetCode")String cabinetCode,@Param("campusIds")Integer[] campusIds,@Param("roomName")String roomName,@Param("cabinetName")String cabinetName,@Param("status")Integer status);
	
	Map<String, Object> query(@Param("cabinetId")Integer cabinetId,@Param("cabinetCode")String cabinetCode);
	
	List<Map<String, Object>> selectIdByCode(@Param("cabinetCodes")List<String> cabinetCodes);
}
