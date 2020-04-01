package com.ncu.springboot.provider.resource.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;
import com.ncu.springboot.api.resource.entity.Line;
import com.ncu.springboot.dao.LineMapper;
import com.ncu.springboot.provider.resource.service.LineService;

import tk.mybatis.mapper.entity.Example;

@Service
public class LineServiceImpl implements LineService {

	@Resource
	private LineMapper lineMapper;
	
	public Integer getTotal(Integer portId,Integer[] campusIds,String lineCode,String lineName,Integer lineType,Integer status,Integer fibercableId) {
		return lineMapper.getTotal(portId,campusIds, lineCode, lineName, lineType, status,fibercableId);
	}

	public List<Map<String, Object>> queryList(Integer portId,Integer[] campusIds,String lineCode,String lineName,Integer lineType,Integer status,Integer fibercableId, Integer size, Integer num) {
		return lineMapper.queryList(portId,campusIds, lineCode, lineName, lineType, status,fibercableId, size, num);
	}

	public Integer delLine(List<Line> lines,Line lineExample) {
		Integer count = 0;
		for (Line line : lines) {
			Example example = new Example(Line.class);
			example.createCriteria().andEqualTo(line);
			count += lineMapper.updateByExampleSelective(lineExample, example);
		}
		return count;
	}

	public Integer editLine(Line line) {
		return lineMapper.updateByPrimaryKeySelective(line);
	}

	public Map<String, Object> query(Integer lineId,String lineCode) {
		return lineMapper.query(lineId, lineCode);
	}
	
	public List<Map<String,Object>> location(Integer lineId,Integer portId){
		return lineMapper.location(lineId,portId);
	}

	public List<Map<String, Object>> export(Integer portId,Integer[] campusIds,String lineCode,String lineName,Integer lineType,Integer status,Integer fibercableId) {
		return lineMapper.export(portId,campusIds, lineCode, lineName, lineType, status,fibercableId);
	}

	//单表查询
	public List<Line> selectLines(Line line) {
		return lineMapper.select(line);
	}

	@Override
	public Integer addLines(List<Line> lines) {
		return lineMapper.insertList(lines);
	}

}
