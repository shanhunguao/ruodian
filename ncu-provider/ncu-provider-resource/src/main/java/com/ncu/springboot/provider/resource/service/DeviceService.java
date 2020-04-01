package com.ncu.springboot.provider.resource.service;

import java.util.List;
import java.util.Map;

import com.ncu.springboot.api.resource.entity.Device;

public interface DeviceService {

	Integer getTotal(String deviceCode,Integer[] campusIds,Integer cabinetId,String deviceName,Integer status,Integer deviceType,Integer buildingId,Integer roomId);
	
	List<Map<String,Object>> queryList(String deviceCode,Integer[] campusIds,Integer cabinetId,String deviceName,Integer status,Integer deviceType,Integer buildingId,Integer roomId,Integer size,Integer num);
	
	Integer addDevice(Device device);
	
	Integer addDevices(List<Device> devices);
	
	Integer delDevice(List<Device> devices,Device example);
	
	Integer editDevice(Device device);
	
	Map<String, Object> query(Integer deviceId,String deviceCode);
	
	List<Map<String,Object>> export(String deviceCode,Integer[] campusIds,Integer cabinetId,String deviceName,Integer status,Integer deviceType,Integer buildingId,Integer roomId);
	
	List<Device> selectDevices(Device device);
	
	List<Map<String, Object>> selectIdByCode(List<String> deviceCodes);
}
