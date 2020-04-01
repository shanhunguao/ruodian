package com.ncu.springboot.api.resource.bizservice;

import java.util.List;
import java.util.Map;

import com.ncu.springboot.api.resource.entity.Cabinet;
import com.ncu.springboot.biz.rs.Res;


public interface CabinetBizService {

	Integer getTotal(String cabinetCode,Integer[] campusIds,String roomName,String cabinetName,Integer status);
	
	List<Map<String,Object>> queryList(String cabinetCode,Integer[] campusIds,String roomName,String cabinetName,Integer status,Integer size,Integer num);
	
	Integer addCabinet(Cabinet cabinet);
	
	Integer delCabinet(Integer[] cabinetIds);
	
	Integer editCabinet(Cabinet cabinet);
	
	Map<String, Object> query(Integer cabinetId,String cabinetCode);
	
	List<Map<String,Object>> export(String cabinetCode,Integer[] campusIds,String roomName,String cabinetName,Integer status);
	
	Res importExcel(List<Map<String, String>> datas,Integer userId,Integer status,Integer type);
}
