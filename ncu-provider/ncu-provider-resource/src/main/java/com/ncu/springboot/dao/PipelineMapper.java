package com.ncu.springboot.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ncu.springboot.api.resource.entity.Pipeline;
import com.ncu.springboot.mybatis.mapper.BaseMapper;

public interface PipelineMapper extends BaseMapper<Pipeline>{
	
	Integer getTotal(@Param("pipelineCode")String pipelineCode,@Param("campusIds")Integer[] campusIds,@Param("pipelineType")Integer pipelineType,@Param("pipelineName")String pipelineName,@Param("status")Integer status,@Param("wellheadId")Integer wellheadId);
	
	List<Map<String,Object>> queryList(@Param("pipelineCode")String pipelineCode,@Param("campusIds")Integer[] campusIds,@Param("pipelineType")Integer pipelineType,@Param("pipelineName")String pipelineName,@Param("status")Integer status,@Param("wellheadId")Integer wellheadId,@Param("size")Integer size,@Param("num")Integer num);
	
	List<Map<String,Object>> location(@Param("pipelineId")Integer pipelineId,@Param("campusIds")Integer[] campusIds,@Param("wellheadId")Integer wellheadId,@Param("pipelineCode")String pipelineCode);
	
	List<Map<String,Object>> export(@Param("pipelineCode")String pipelineCode,@Param("campusIds")Integer[] campusIds,@Param("pipelineType")Integer pipelineType,@Param("pipelineName")String pipelineName,@Param("status")Integer status,@Param("wellheadId")Integer wellheadId);
	
	Map<String, Object> query(@Param("pipelineId")Integer pipelineId,@Param("pipelineCode")String pipelineCode);
	
	List<Map<String, Object>> selectIdByCode(@Param("pipelineCodes")List<String> pipelineCodes);
}
