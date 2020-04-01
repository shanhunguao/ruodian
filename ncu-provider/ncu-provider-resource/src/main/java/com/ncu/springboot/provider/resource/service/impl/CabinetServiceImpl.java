package com.ncu.springboot.provider.resource.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;
import com.ncu.springboot.api.resource.entity.Cabinet;
import com.ncu.springboot.dao.CabinetMapper;
import com.ncu.springboot.provider.resource.service.CabinetService;

import tk.mybatis.mapper.entity.Example;

@Service
public class CabinetServiceImpl implements CabinetService {
	
	@Resource
	private CabinetMapper cabinetMapper;
	
	@Override
	public Integer getTotal(String cabinetCode,Integer[] campusIds,String roomName,String cabinetName,Integer status) {
		return cabinetMapper.getTotal(cabinetCode,campusIds, roomName, cabinetName, status);
	}

	@Override
	public List<Map<String, Object>> queryList(String cabinetCode,Integer[] campusIds,String roomName,String cabinetName,Integer status,Integer size,Integer num) {
		return cabinetMapper.queryList(cabinetCode,campusIds, roomName, cabinetName, status, size, num);
	}

	@Override
	public Integer addCabinet(Cabinet cabinet) {
		return cabinetMapper.insert(cabinet);
	}

	public Integer delCabinet(List<Cabinet> cabinets,Cabinet CabinetExample) {
		Integer count = 0;
		for (Cabinet cabinet : cabinets) {
			Example example = new Example(Cabinet.class);
			example.createCriteria().andEqualTo(cabinet);
			count += cabinetMapper.updateByExampleSelective(CabinetExample,example);
		}
		return count;
	}

	@Override
	public Integer editCabinet(Cabinet cabinet) {
		return cabinetMapper.updateByPrimaryKeySelective(cabinet);
	}

	@Override
	public Map<String, Object> query(Integer cabinetId,String cabinetCode) {
		return cabinetMapper.query(cabinetId,cabinetCode);
	}

	@Override
	public List<Map<String, Object>> export(String cabinetCode,Integer[] campusIds,String roomName,String cabinetName,Integer status) {
		return cabinetMapper.export(cabinetCode,campusIds, roomName, cabinetName, status);
	}

	//单表查询
	public List<Cabinet> selectCabinets(Cabinet cabinet) {
		return cabinetMapper.select(cabinet);
	}

	@Override
	public Integer addCabinets(List<Cabinet> cabinets) {
		return cabinetMapper.insertList(cabinets);
	}

	@Override
	public List<Map<String, Object>> selectIdByCode(List<String> cabinetCodes) {
		return cabinetMapper.selectIdByCode(cabinetCodes);
	}

}
