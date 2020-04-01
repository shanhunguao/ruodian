package com.ncu.springboot.provider.resource.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ncu.springboot.api.resource.entity.Breakdown;
import com.ncu.springboot.dao.BreakdownMapper;
import com.ncu.springboot.provider.resource.service.BreakdownService;

@Service
public class BreakdownServiceImpl implements BreakdownService {

	@Resource
	private BreakdownMapper breakdownMapper;
	
	public Integer getTotal(String breakdownCode, Integer[] campusIds, String breakdownName, Integer status,
			Integer fibercableId, Integer breakdownType) {
		return breakdownMapper.getTotal(breakdownCode, campusIds, breakdownName, status, fibercableId, breakdownType);
	}

	public List<Map<String, Object>> queryList(String breakdownCode, Integer[] campusIds, String breakdownName,
			Integer status, Integer fibercableId, Integer breakdownType, Integer size, Integer num) {
		return breakdownMapper.queryList(breakdownCode, campusIds, breakdownName, status, fibercableId, breakdownType, size, num);
	}

	public Integer addBreakdowns(List<Breakdown> breakdowns) {
		return breakdownMapper.insertList(breakdowns);
	}

	public Integer delBreakdown(Integer[] breakdownIds) {
		Integer count = 0;
		for (Integer breakdownId : breakdownIds) {
			count += breakdownMapper.deleteByPrimaryKey(breakdownId);
		}
		return count;
	}

	public Integer editBreakdown(Breakdown breakdown) {
		return breakdownMapper.updateByPrimaryKeySelective(breakdown);
	}

	public List<Map<String, Object>> location(Integer breakdownId, Integer campusId) {
		return breakdownMapper.location(breakdownId, campusId);
	}

	public Map<String, Object> query(Integer breakdownId,String breakdownCode) {
		return breakdownMapper.query(breakdownId,breakdownCode);
	}

	public List<Map<String, Object>> export(String breakdownCode, Integer[] campusIds, String breakdownName,
			Integer status, Integer fibercableId, Integer breakdownType) {
		return breakdownMapper.export(breakdownCode, campusIds, breakdownName, status, fibercableId, breakdownType);
	}

	public List<Breakdown> selectBreakdown(Breakdown breakdown) {
		return breakdownMapper.select(breakdown);
	}

	public Integer addBreakdown(Breakdown breakdown) {
		return breakdownMapper.insert(breakdown);
	}
}
