package com.ncu.springboot.provider.resource.service;

import java.util.List;
import java.util.Map;

import com.ncu.springboot.api.resource.entity.Pipeline;

public interface PipelineService {
	
	Integer getTotal(String pipelineCode,Integer[] campusIds,Integer pipelineType,String pipelineName,Integer status,Integer wellheadId);
	
	List<Map<String,Object>> queryList(String pipelineCode,Integer[] campusIds,Integer pipelineType,String pipelineName,Integer status,Integer wellheadId,Integer size,Integer num);
	
	Integer addPipeline(Pipeline pipeline);
	
	Integer addPipelines(List<Pipeline> pipelines);
	
	Integer delPipeline(List<Pipeline> pipelines,Pipeline example);
	
	Integer editPipeline(Pipeline pipeline);
	
	Map<String, Object> query(Integer pipelineId,String pipelineCode);
	
	List<Map<String,Object>> location(Integer pipelineId,Integer[] campusIds,Integer wellheadId,String pipelineCode);
	
	List<Map<String,Object>> export(String pipelineCode,Integer[] campusIds,Integer pipelineType,String pipelineName,Integer status,Integer wellheadId);
	
	List<Pipeline> selectPipelines(Pipeline pipeline);
	
	List<Map<String, Object>> selectIdByCode(List<String> pipelineCodes);
}
