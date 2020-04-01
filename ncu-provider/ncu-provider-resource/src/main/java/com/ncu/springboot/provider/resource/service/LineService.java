package com.ncu.springboot.provider.resource.service;

import java.util.List;
import java.util.Map;

import com.ncu.springboot.api.resource.entity.Line;

public interface LineService {

	Integer getTotal(Integer portId,Integer[] campusIds,String lineCode,String lineName,Integer lineType,Integer status,Integer fibercableId);
	
	List<Map<String,Object>> queryList(Integer portId,Integer[] campusIds,String lineCode,String lineName,Integer lineType,Integer status,Integer fibercableId,Integer size,Integer num);
	
	Integer addLines(List<Line> lines);
	
	Integer delLine(List<Line> lines,Line example);
	
	Integer editLine(Line line);
	
	Map<String, Object> query(Integer lineId,String lineCode);
	
	List<Map<String,Object>> location(Integer lineId,Integer lineType);
	
	List<Map<String,Object>> export(Integer portId,Integer[] campusIds,String lineCode,String lineName,Integer lineType,Integer status,Integer fibercableId);
	
	List<Line> selectLines(Line line);
}
