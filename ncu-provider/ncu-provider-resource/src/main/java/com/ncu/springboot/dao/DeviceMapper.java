package com.ncu.springboot.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ncu.springboot.api.resource.entity.Device;
import com.ncu.springboot.mybatis.mapper.BaseMapper;

public interface DeviceMapper extends BaseMapper<Device> {
	
	Integer getTotal(@Param("deviceCode")String deviceCode,@Param("campusIds")Integer[] campusIds,@Param("cabinetId")Integer cabinetId,@Param("deviceName")String deviceName,@Param("status")Integer status,@Param("deviceType")Integer deviceType,@Param("buildingId")Integer buildingId,@Param("roomId")Integer roomId);
	
	List<Map<String,Object>> queryList(@Param("deviceCode")String deviceCode,@Param("campusIds")Integer[] campusIds,@Param("cabinetId")Integer cabinetId,@Param("deviceName")String deviceName,@Param("status")Integer status,@Param("deviceType")Integer deviceType,@Param("buildingId")Integer buildingId,@Param("roomId")Integer roomId,@Param("size")Integer size,@Param("num")Integer num);
	
	List<Map<String,Object>> export(@Param("deviceCode")String deviceCode,@Param("campusIds")Integer[] campusIds,@Param("cabinetId")Integer cabinetId,@Param("deviceName")String deviceName,@Param("status")Integer status,@Param("deviceType")Integer deviceType,@Param("buildingId")Integer buildingId,@Param("roomId")Integer roomId);
	
	Map<String, Object> query(@Param("deviceId")Integer deviceId,@Param("deviceCode")String deviceCode);
	
	List<Map<String, Object>> selectIdByCode(@Param("deviceCodes")List<String> deviceCodes);
}
