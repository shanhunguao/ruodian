package com.ncu.springboot.provider.resource.service;

import java.util.List;
import java.util.Map;

import com.ncu.springboot.api.resource.entity.Fibercable;

public interface FibercableService {
	
Integer getTotal(Integer[] campusIds,Integer originWellhead,Integer endWellhead,String fibercableName,Integer status,Integer fibercableType,String fibercableCode);
	
	List<Map<String,Object>> queryList(Integer[] campusIds,Integer originWellhead,Integer endWellhead,String fibercableName,Integer status,Integer fibercableType,String fibercableCode,Integer size,Integer num);
	
	Integer addFibercable(Fibercable fibercable,Integer[] pipelineIds);
	
	Integer addFibercables(List<Fibercable> fibercables);
	
	Integer delFibercable(List<Fibercable> fibercables,Fibercable example);
	
	Integer editFibercable(Fibercable fibercable,Integer[] pipelineIds);
	
	Map<String, Object> query(Integer fibercableId,String fibercableCode);
	
	List<Map<String, Object>> location(Integer fibercableId,String fibercableCode,Integer[] campusIds);
	
	List<Map<String,Object>> export(Integer[] campusIds,Integer originWellhead,Integer endWellhead,String fibercableName,Integer status,Integer fibercableType,String fibercableCode);
	
	Integer delByPipeLine(List<Integer> pipelineIds, Integer status);
	
	List<Fibercable> selectFibercables(Fibercable fibercable);
	
	List<Fibercable> selectByPipelineId(List<Integer> pipelineIds);
	
	List<Map<String, Object>> selectIdByCode(List<String> fibercableCodes);
}
