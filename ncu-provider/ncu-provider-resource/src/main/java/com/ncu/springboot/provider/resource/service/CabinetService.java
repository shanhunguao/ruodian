package com.ncu.springboot.provider.resource.service;

import java.util.List;
import java.util.Map;

import com.ncu.springboot.api.resource.entity.Cabinet;

public interface CabinetService {
	
	Integer getTotal(String cabinetCode,Integer[] campusIds,String roomName,String cabinetName,Integer status);
	
	List<Map<String,Object>> queryList(String cabinetCode,Integer[] campusIds,String roomName,String cabinetName,Integer status,Integer size,Integer num);
	
	Integer addCabinet(Cabinet cabinet);
	
	Integer addCabinets(List<Cabinet> cabinets);
	
	Integer delCabinet(List<Cabinet> cabinets,Cabinet example);
	
	Integer editCabinet(Cabinet cabinet);
	
	Map<String, Object> query(Integer cabinetId,String cabinetCode);
	
	List<Map<String,Object>> export(String cabinetCode,Integer[] campusIds,String roomName,String cabinetName,Integer status);
	
	List<Cabinet> selectCabinets(Cabinet cabinet);
	
	List<Map<String, Object>> selectIdByCode(List<String> cabinetCodes);
}
