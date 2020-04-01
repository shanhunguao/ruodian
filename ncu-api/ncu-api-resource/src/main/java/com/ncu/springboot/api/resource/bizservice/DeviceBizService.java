package com.ncu.springboot.api.resource.bizservice;

import java.util.List;
import java.util.Map;

import com.ncu.springboot.api.resource.entity.Device;
import com.ncu.springboot.biz.rs.Res;

public interface DeviceBizService {

	Integer getTotal(String deviceCode,Integer[] campusIds,Integer cabinetId,String deviceName,Integer status,Integer deviceType,Integer buildingId,Integer roomId);
	
	List<Map<String,Object>> queryList(String deviceCode,Integer[] campusIds,Integer cabinetId,String deviceName,Integer status,Integer deviceType,Integer buildingId,Integer roomId,Integer size,Integer num);
	
	Integer addDevice(Device device);
	
	Integer delDevice(Integer[] deviceIds);
	
	Integer editDevice(Device device);
	
	Map<String, Object> query(Integer deviceId,String deviceCode);
	
	List<Map<String,Object>> export(String deviceCode,Integer[] campusIds,Integer cabinetId,String deviceName,Integer status,Integer deviceType,Integer buildingId,Integer roomId);
	
	Res importExcel(List<Map<String, String>> datas,Integer userId,Integer status,Integer type);
}
