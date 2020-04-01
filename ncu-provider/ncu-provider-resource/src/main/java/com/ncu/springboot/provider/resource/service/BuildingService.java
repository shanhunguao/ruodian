package com.ncu.springboot.provider.resource.service;

import java.util.List;
import java.util.Map;

import com.ncu.springboot.api.resource.entity.Building;

public interface BuildingService {
	
	Integer getTotal(String buildingCode,Integer[] campusIds,String buildingName,Integer status);
	
	List<Map<String,Object>> queryList(String buildingCode,Integer[] campusIds,String buildingName,Integer status,Integer size,Integer num);
	
	Integer addBuilding(Building building);
	
	Integer addBuildings(List<Building> buildings);
	
	Integer delBuilding(List<Building> buildings,Building example);
	
	Integer editBuilding(Building building);
	
	List<Map<String,Object>> location(Integer buildingId,String buildingCode,Integer[] campusIds);
	
	Map<String, Object> query(Integer buildingId,String buildingCode);
	
	List<Map<String,Object>> export(String buildingCode,Integer[] campusIds,String buildingName,Integer status);
	
	List<Building> selectBuildings(Building building);
	
	List<Map<String, Object>> selectIdByCode(List<String> buildingCodes);
}
