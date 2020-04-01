package com.ncu.springboot.provider.resource.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;
import com.ncu.springboot.api.resource.entity.Building;
import com.ncu.springboot.dao.BuildingMapper;
import com.ncu.springboot.provider.resource.service.BuildingService;

import tk.mybatis.mapper.entity.Example;

@Service
public class BuildingServiceImpl implements BuildingService {

	@Resource
	private BuildingMapper buildingMapper;
	@Override
	public Integer getTotal(String buildingCode,Integer[] campusIds,String buildingName,Integer status) {
		return buildingMapper.getTotal(buildingCode,campusIds, buildingName, status);
	}

	@Override
	public List<Map<String, Object>> queryList(String buildingCode,Integer[] campusIds,String buildingName,Integer status, Integer size, Integer num) {
		return buildingMapper.queryList(buildingCode,campusIds, buildingName, status, size, num);
	}

	@Override
	public Integer addBuilding(Building building) {
		return buildingMapper.insert(building);
	}

	public Integer delBuilding(List<Building> buildings,Building buildingExample) {
		Integer count = 0;
		for (Building building : buildings) {
			Example example = new Example(Building.class);
			example.createCriteria().andEqualTo(building);
			count += buildingMapper.updateByExampleSelective(buildingExample,example);
		}
		return count;
	}

	@Override
	public Integer editBuilding(Building building) {
		return buildingMapper.updateByPrimaryKeySelective(building);
	}

	@Override
	public List<Map<String, Object>> location(Integer buildingId,String buildingCode,Integer[] campusIds) {
		return buildingMapper.location(buildingId,buildingCode,campusIds);
	}

	@Override
	public Map<String, Object> query(Integer buildingId,String buildingCode) {
		return buildingMapper.query(buildingId,buildingCode);
	}

	@Override
	public List<Map<String, Object>> export(String buildingCode,Integer[] campusIds,String buildingName,Integer status) {
		return buildingMapper.export(buildingCode,campusIds, buildingName, status);
	}

	//查找楼栋，单表查询
	public List<Building> selectBuildings(Building building) {
		return buildingMapper.select(building);
	}

	@Override
	public Integer addBuildings(List<Building> buildings) {
		return buildingMapper.insertList(buildings);
	}

	@Override
	public List<Map<String, Object>> selectIdByCode(List<String> buildingCodes) {
		return buildingMapper.selectIdByCode(buildingCodes);
	}

}
