package com.ncu.springboot.provider.resource.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;
import com.ncu.springboot.api.resource.entity.Device;
import com.ncu.springboot.dao.DeviceMapper;
import com.ncu.springboot.provider.resource.service.DeviceService;

import tk.mybatis.mapper.entity.Example;

@Service
public class DeviceServiceImpl implements DeviceService {

	@Resource
	private DeviceMapper deviceMapper;
	
	@Override
	public Integer getTotal(String deviceCode,Integer[] campusIds,Integer cabinetId,String deviceName,Integer status,Integer deviceType,Integer buildingId,Integer roomId) {
		return deviceMapper.getTotal(deviceCode,campusIds, cabinetId, deviceName, status, deviceType,buildingId,roomId);
	}

	@Override
	public List<Map<String, Object>> queryList(String deviceCode,Integer[] campusIds,Integer cabinetId,String deviceName,Integer status,Integer deviceType,Integer buildingId,Integer roomId, Integer size, Integer num) {
		return deviceMapper.queryList(deviceCode,campusIds, cabinetId, deviceName, status, deviceType,buildingId,roomId, size, num);
	}

	@Override
	public Integer addDevice(Device device) {
		deviceMapper.insertUseGeneratedKeys(device);
		return device.getDeviceId();
	}

	public Integer delDevice(List<Device> devices,Device DeviceExample) {
		Integer count = 0;
		for (Device device : devices) {
			Example example = new Example(Device.class);
			example.createCriteria().andEqualTo(device);
			count += deviceMapper.updateByExampleSelective(DeviceExample,example);
		}
		return count;
	}

	@Override
	public Integer editDevice(Device device) {
		return deviceMapper.updateByPrimaryKeySelective(device);
	}

	@Override
	public Map<String, Object> query(Integer deviceId,String deviceCode) {
		return deviceMapper.query(deviceId, deviceCode);
	}

	@Override
	public List<Map<String, Object>> export(String deviceCode,Integer[] campusIds,Integer cabinetId,String deviceName,Integer status,Integer deviceType,Integer buildingId,Integer roomId) {
		return deviceMapper.export(deviceCode,campusIds, cabinetId, deviceName, status, deviceType,buildingId,roomId);
	}

	//单表查询
	public List<Device> selectDevices(Device device) {
		return deviceMapper.select(device);
	}

	@Override
	public Integer addDevices(List<Device> devices) {
		return deviceMapper.insertList(devices);
	}

	@Override
	public List<Map<String, Object>> selectIdByCode(List<String> deviceCodes) {
		return deviceMapper.selectIdByCode(deviceCodes);
	}

}
