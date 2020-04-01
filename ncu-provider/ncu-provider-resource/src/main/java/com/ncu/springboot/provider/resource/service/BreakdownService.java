package com.ncu.springboot.provider.resource.service;

import java.util.List;
import java.util.Map;

import com.ncu.springboot.api.resource.entity.Breakdown;

public interface BreakdownService {

	Integer getTotal(String breakdownCode,Integer[] campusIds,String breakdownName,Integer status,Integer fibercableId,Integer breakdownType);
	
	List<Map<String,Object>> queryList(String breakdownCode,Integer[] campusIds,String breakdownName,Integer status,Integer fibercableId,Integer breakdownType,Integer size,Integer num);
	
	Integer addBreakdown(Breakdown breakdown);
	
	Integer addBreakdowns(List<Breakdown> breakdowns);
	
	Integer delBreakdown(Integer[] breakdownIds);
	
	Integer editBreakdown(Breakdown breakdown);
	
	List<Map<String,Object>> location(Integer breakdownId,Integer campusId);
	
	Map<String, Object> query(Integer breakdownId,String breakdownCode);
	
	List<Map<String,Object>> export(String breakdownCode,Integer[] campusIds,String breakdownName,Integer status,Integer fibercableId,Integer breakdownType);
}
