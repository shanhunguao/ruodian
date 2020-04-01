package com.ncu.springboot.provider.resource.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.ncu.springboot.api.resource.entity.Port;
import com.ncu.springboot.dao.PortMapper;
import com.ncu.springboot.provider.resource.service.PortService;

import tk.mybatis.mapper.entity.Example;

@Service
public class PortServiceImpl implements PortService{

	@Resource
	private PortMapper portMapper;
	
	@Override
	public Integer getTotal(String portCode,Integer[] campusIds,String portName,Integer deviceId,Integer portType,Integer status,Integer buildingId,Integer roomId) {
		return portMapper.getTotal(portCode,campusIds, portName, deviceId, portType, status, buildingId, roomId);
	}

	@Override
	public List<Map<String, Object>> queryList(String portCode,Integer[] campusIds,String portName,Integer deviceId,Integer portType,Integer status,Integer buildingId,Integer roomId, Integer size, Integer num) {
		return portMapper.queryList(portCode,campusIds, portName, deviceId, portType, status, buildingId, roomId,size,num);
	}

	
	public Integer addPort(Port[] ports) {
		Integer count = 0;
		for (Port port : ports) {
			count += portMapper.insert(port);
		}
		return count;
	}

	@Override
	public Integer delPort(List<Port> ports,Port portExample) {
		Integer count = 0;
		for (Port port : ports) {
			Example example = new Example(Port.class);
			example.createCriteria().andEqualTo(port);
			count += portMapper.updateByExampleSelective(portExample,example);
		}
		return count;
	}

	@Override
	public Integer editPort(Port[] ports) {
		int count = 0;
		for (Port port : ports) {
			count += portMapper.updateByPrimaryKeySelective(port);
		}
		return count;
	}

	@Override
	public Map<String, Object> query(Integer portId,String portCode) {
		return portMapper.query(portId,portCode);
	}

	@Override
	public List<Map<String, Object>> export(String portCode,Integer[] campusIds,String portName,Integer deviceId,Integer portType,Integer status,Integer buildingId,Integer roomId) {
		return portMapper.export(portCode,campusIds, portName, deviceId, portType, status, buildingId, roomId);
	}

	//单表查询
	public List<Port> selectPorts(Port port) {
		return portMapper.select(port);
	}

	@Override
	public Integer addPorts(List<Port> ports) {
		return portMapper.insertList(ports);
	}

	@Override
	public List<Map<String, Object>> selectIdByCode(List<String> portCodes) {
		return portMapper.selectIdByCode(portCodes);
	}

}
