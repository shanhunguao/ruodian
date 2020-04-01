package com.ncu.springboot.api.resource.bizservice;

import java.util.List;
import java.util.Map;

import com.ncu.springboot.api.resource.entity.Line;
import com.ncu.springboot.biz.rs.Res;

public interface LineBizService {

	Integer getTotal(Integer portId,Integer[] campusIds,String lineCode,String lineName,Integer lineType,Integer status,Integer fibercableId);
	
	List<Map<String,Object>> queryList(Integer portId,Integer[] campusIds,String lineCode,String lineName,Integer lineType,Integer status,Integer fibercableId,Integer size,Integer num);
	
	Integer addLines(List<Line> lines);
	
	Integer delLine(Integer[] lineIds);
	
	Integer editLine(Line line);
	
	Map<String, Object> query(Integer lineId,String lineCode);
	
	List<Map<String,Object>> location(Integer lineId,Integer lineType);
	
	List<Map<String,Object>> export(Integer portId,Integer[] campusIds,String lineCode,String lineName,Integer lineType,Integer status,Integer fibercableId);

	Res importExcel(List<Map<String, String>> datas, Integer userId, Integer status, Integer type);
}
