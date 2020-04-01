package com.ncu.springboot.provider.resource.service;

import java.util.List;
import java.util.Map;

import com.ncu.springboot.api.resource.entity.Port;

public interface PortService {

	Integer getTotal(String portCode,Integer[] campusIds,String portName,Integer deviceId,Integer portType,Integer status,Integer buildingId,Integer roomId);
	
	List<Map<String,Object>> queryList(String portCode,Integer[] campusIds,String portName,Integer deviceId,Integer portType,Integer status,Integer buildingId,Integer roomId,Integer size,Integer num);
	
	Integer addPorts(List<Port> ports);
	
	Integer delPort(List<Port> ports,Port example);
	
	Integer editPort(Port[] ports);
	
	Map<String, Object> query(Integer portId,String portCode);
	
	List<Map<String, Object>> export(String portCode,Integer[] campusIds,String portName,Integer deviceId,Integer portType,Integer status,Integer buildingId,Integer roomId);
	
	List<Port> selectPorts(Port port);
	
	List<Map<String, Object>> selectIdByCode(List<String> portCodes);
}
