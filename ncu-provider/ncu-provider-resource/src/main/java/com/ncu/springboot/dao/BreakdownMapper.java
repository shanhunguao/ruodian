package com.ncu.springboot.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ncu.springboot.api.resource.entity.Breakdown;
import com.ncu.springboot.mybatis.mapper.BaseMapper;

public interface BreakdownMapper extends BaseMapper<Breakdown> {

	Integer getTotal(@Param("breakdownCode")String breakdownCode,@Param("campusIds")Integer[] campusIds,@Param("breakdownName")String breakdownName,@Param("status")Integer status,@Param("fibercableId")Integer fibercableId,@Param("breakdownType")Integer breakdownType);
	
	List<Map<String,Object>> queryList(@Param("breakdownCode")String breakdownCode,@Param("campusIds")Integer[] campusIds,@Param("breakdownName")String breakdownName,@Param("status")Integer status,@Param("fibercableId")Integer fibercableId,@Param("breakdownType")Integer breakdownType,@Param("size")Integer size,@Param("num")Integer num);
	
	List<Map<String,Object>> location(@Param("breakdownId")Integer breakdownId,@Param("campusId")Integer campusId);
	
	List<Map<String,Object>> export(@Param("breakdownCode")String breakdownCode,@Param("campusIds")Integer[] campusIds,@Param("breakdownName")String breakdownName,@Param("status")Integer status,@Param("fibercableId")Integer fibercableId,@Param("breakdownType")Integer breakdownType);
	
	Map<String, Object> query(@Param("breakdownId")Integer breakdownId,@Param("breakdownCode")String breakdownCode);
}
