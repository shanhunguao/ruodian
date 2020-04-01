package com.ncu.springboot.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ncu.springboot.api.resource.entity.Building;
import com.ncu.springboot.mybatis.mapper.BaseMapper;

public interface BuildingMapper extends BaseMapper<Building>{
	
	Integer getTotal(@Param("buildingCode")String buildingCode,@Param("campusIds")Integer[] campusIds,@Param("buildingName")String buildingName,@Param("status")Integer status);
	
	List<Map<String,Object>> queryList(@Param("buildingCode")String buildingCode,@Param("campusIds")Integer[] campusIds,@Param("buildingName")String buildingName,@Param("status")Integer status,@Param("size")Integer size,@Param("num")Integer num);
	
	List<Map<String,Object>> location(@Param("buildingId")Integer buildingId,@Param("buildingCode")String buildingCode,@Param("campusIds")Integer[] campusIds);
	
	List<Map<String,Object>> export(@Param("buildingCode")String buildingCode,@Param("campusIds")Integer[] campusIds,@Param("buildingName")String buildingName,@Param("status")Integer status);
	
	Map<String, Object> query(@Param("buildingId")Integer buildingId,@Param("buildingCode")String buildingCode);
	
	List<Map<String, Object>> selectIdByCode(@Param("buildingCodes")List<String> buildingCodes);
}
