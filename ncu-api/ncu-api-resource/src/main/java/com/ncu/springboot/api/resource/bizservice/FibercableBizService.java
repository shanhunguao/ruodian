package com.ncu.springboot.api.resource.bizservice;

import java.util.List;
import java.util.Map;

import com.ncu.springboot.api.resource.entity.Fibercable;
import com.ncu.springboot.biz.rs.Res;

public interface FibercableBizService {

	Integer getTotal(Integer[] campusIds,Integer originWellhead,Integer endWellhead,String fibercableName,Integer status,Integer fibercableType,String fibercableCode);
	
	List<Map<String,Object>> queryList(Integer[] campusIds,Integer originWellhead,Integer endWellhead,String fibercableName,Integer status,Integer fibercableType,String fibercableCode,Integer size,Integer num);
	
	Integer addFibercable(Fibercable fibercable,Integer[] pipelineIds);
	
	Integer delFibercable(Integer[] fibercableIds);
	
	Integer editFibercable(Fibercable fibercableInte,Integer[] pipelineIds );
	
	Map<String, Object> query(Integer fibercableId,String fibercableCode);
	
	List<Map<String, Object>> location(Integer fibercableId,String fibercableCode,Integer[] campusIds);
	
	List<Map<String,Object>> export(Integer[] campusIds,Integer originWellhead,Integer endWellhead,String fibercableName,Integer status,Integer fibercableType,String fibercableCode);

	Res importExcel(List<Map<String, String>> datas, Integer userId, Integer status, Integer type);
}
