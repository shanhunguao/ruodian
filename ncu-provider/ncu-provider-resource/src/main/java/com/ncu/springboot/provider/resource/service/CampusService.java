package com.ncu.springboot.provider.resource.service;

import java.util.List;
import java.util.Map;

import com.ncu.springboot.api.resource.entity.Campus;

public interface CampusService {

	Integer getTotal(String campusCode,Integer manageDept,String campusName,Integer status);
	
	List<Map<String, Object>> queryList(String campusCode,Integer manageDept,String campusName,Integer status,Integer num,Integer size);
	
	Integer addCampus(Campus campus);
	
	Integer addCampuss(List<Campus> campuss);
	
	Integer delCampus(List<Campus> campuss,Campus example);
	
	Integer editCampus(Campus campus);
	
	Map<String,Object> location(Integer campusId,String campusCode,Integer manageDept);
	
	Map<String, Object> query(Integer campusId,String campusCode);
	
	List<Map<String,Object>> export(String campusCode,Integer manageDept,String campusName,Integer status);
	
	List<Campus> selectCampus(Campus campus);
	
	List<Map<String, Object>> selectIdByCode(List<String> campusCodes);
	
	List<Map<String, Object>> selectIdByName(List<String> campusNames);
}
