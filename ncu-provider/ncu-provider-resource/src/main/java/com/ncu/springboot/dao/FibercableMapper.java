package com.ncu.springboot.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ncu.springboot.api.resource.entity.Fibercable;
import com.ncu.springboot.mybatis.mapper.BaseMapper;

public interface FibercableMapper extends BaseMapper<Fibercable> {
	
	Integer getTotal(@Param("campusIds")Integer[] campusIds,@Param("originWellhead")Integer originWellhead,@Param("endWellhead")Integer endWellhead,@Param("fibercableName")String fibercableName,@Param("status")Integer status,@Param("fibercableType")Integer fibercableType,@Param("fibercableCode")String fibercableCode);
	
	List<Map<String,Object>> queryList(@Param("campusIds")Integer[] campusIds,@Param("originWellhead")Integer originWellhead,@Param("endWellhead")Integer endWellhead,@Param("fibercableName")String fibercableName,@Param("status")Integer status,@Param("fibercableType")Integer fibercableType,@Param("fibercableCode")String fibercableCode,@Param("size")Integer size,@Param("num")Integer num);
	
	List<Map<String, Object>> location(@Param("fibercableId")Integer fibercableId,@Param("fibercableCode")String fibercableCode,@Param("campusIds")Integer[] campusIds);

	List<Map<String,Object>> export(@Param("campusIds")Integer[] campusIds,@Param("originWellhead")Integer originWellhead,@Param("endWellhead")Integer endWellhead,@Param("fibercableName")String fibercableName,@Param("status")Integer status,@Param("fibercableType")Integer fibercableType,@Param("fibercableCode")String fibercableCode);
	
	Integer delByPipeLine(@Param("pipelineIds")List<Integer> pipelineIds,@Param("status")Integer status);
	
	List<Fibercable> selectByPipelineId(@Param("pipelineIds")List<Integer> pipelineIds);
	
	Map<String, Object> query(@Param("fibercableId")Integer fibercableId,@Param("fibercableCode")String fibercableCode);
	
	List<Map<String, Object>> selectIdByCode(@Param("fibercableCodes")List<String> fibercableCodes);
}
