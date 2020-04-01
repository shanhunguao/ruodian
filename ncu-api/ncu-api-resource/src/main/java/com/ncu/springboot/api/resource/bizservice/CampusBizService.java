package com.ncu.springboot.api.resource.bizservice;

import java.util.List;
import java.util.Map;

import com.ncu.springboot.api.resource.entity.Campus;
import com.ncu.springboot.biz.rs.Res;

public interface CampusBizService {

	Integer getTotal(String campusCode,Integer manageDept,String campusName,Integer status);
	
	List<Map<String, Object>> queryList(String campusCode,Integer manageDept,String campusName,Integer status,Integer num,Integer size);
	
	Integer addCampus(Campus campus);
	
	Integer delCampus(Integer[] campusIds);
	
	Integer editCampus(Campus campus);
	
	Map<String,Object> location(Integer campusId,String campusCode,Integer manageDept);
	
	Map<String, Object> query(Integer campusId,String campusCode);
	
	List<Map<String,Object>> export(String campusCode,Integer manageDept,String campusName,Integer status);
	
	Res importExcel(List<Map<String, String>> datas,Integer userId,Integer status);
	
	List<Map<String, Object>> selectIdByName(List<String> campusNames);
}
