package com.ncu.springboot.api.resource.bizservice;

import java.util.List;
import java.util.Map;

import com.ncu.springboot.api.resource.entity.Port;
import com.ncu.springboot.biz.rs.Res;

public interface PortBizService {

	Integer getTotal(String portCode,Integer[] campusIds,String portName,Integer deviceId,Integer portType,Integer status,Integer buildingId,Integer roomId);
	
	List<Map<String,Object>> queryList(String portCode,Integer[] campusIds,String portName,Integer deviceId,Integer portType,Integer status,Integer buildingId,Integer roomId,Integer size,Integer num);
	
	Integer addPorts(List<Port> ports);
	
	Integer delPort(Integer[] portIds);
	
	Integer editPort(Port[] ports);
	
	Map<String, Object> query(Integer portId,String portCode);
	
	List<Map<String, Object>> export(String portCode,Integer[] campusIds,String portName,Integer deviceId,Integer portType,Integer status,Integer buildingId,Integer roomId);
	
	Res importExcel(List<Map<String, String>> datas,Integer status,Integer type);
}
