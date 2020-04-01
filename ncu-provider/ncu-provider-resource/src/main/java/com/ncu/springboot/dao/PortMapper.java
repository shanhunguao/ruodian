package com.ncu.springboot.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ncu.springboot.api.resource.entity.Port;
import com.ncu.springboot.mybatis.mapper.BaseMapper;

public interface PortMapper extends BaseMapper<Port> {

	Integer getTotal(@Param("portCode")String portCode,@Param("campusIds")Integer[] campusIds,@Param("portName")String portName,@Param("deviceId")Integer deviceId,@Param("portType")Integer portType,@Param("status")Integer status,@Param("buildingId")Integer buildingId,@Param("roomId")Integer roomId);
	
	List<Map<String,Object>> queryList(@Param("portCode")String portCode,@Param("campusIds")Integer[] campusIds,@Param("portName")String portName,@Param("deviceId")Integer deviceId,@Param("portType")Integer portType,@Param("status")Integer status,@Param("buildingId")Integer buildingId,@Param("roomId")Integer roomId,@Param("size")Integer size,@Param("num")Integer num);
	
	List<Map<String, Object>> export(@Param("portCode")String portCode,@Param("campusIds")Integer[] campusIds,@Param("portName")String portName,@Param("deviceId")Integer deviceId,@Param("portType")Integer portType,@Param("status")Integer status,@Param("buildingId")Integer buildingId,@Param("roomId")Integer roomId);
	
	Map<String, Object> query(@Param("portId")Integer portId,@Param("portCode")String portCode);
	
	List<Map<String, Object>> selectIdByCode(@Param("portCodes")List<String> portCodes);
	
}
