package com.ncu.springboot.api.resource.bizservice;

import java.util.List;
import java.util.Map;

import com.ncu.springboot.api.resource.entity.Building;
import com.ncu.springboot.biz.rs.Res;


public interface BuildingBizService {
	
	Integer getTotal(String buildingCode,Integer[] campusIds,String buildingName,Integer status);
	
	List<Map<String,Object>> queryList(String campusCode,Integer[] campusIds,String buildingName,Integer status,Integer size,Integer num);
	
	Integer addBuilding(Building building);
	
	Integer delBuilding(Integer[] buildingIds);
	
	Integer editBuilding(Building building);
	
	List<Map<String,Object>> location(Integer buildingId,String buildingCode,Integer[] campusIds);
	
	Map<String, Object> query(Integer buildingId,String buildingCode);
	
	List<Map<String,Object>> export(String campusCode,Integer[] campusIds,String buildingName,Integer status);

	Res importExcel(List<Map<String, String>> datas,Integer userId,Integer status);
}
