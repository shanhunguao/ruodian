package com.ncu.springboot.api.resource.bizservice;

import java.util.List;
import java.util.Map;

import com.ncu.springboot.api.resource.entity.Pipeline;
import com.ncu.springboot.biz.rs.Res;


public interface PipelineBizService {
	
	Integer getTotal(String pipelineCode,Integer[] campusIds,Integer pipelineType,String pipelineName,Integer status,Integer wellheadId);
	
	List<Map<String,Object>> queryList(String pipelineCode,Integer[] campusIds,Integer pipelineType,String pipelineName,Integer status,Integer wellheadId,Integer size,Integer num);
	
	Integer addPipeline(Pipeline pipeline);
	
	Integer delPipeline(Integer[] pipelineIds);
	
	Integer editPipeline(Pipeline pipeline);
	
	Map<String, Object> query(Integer pipelineId,String pipelineCode);
	
	List<Map<String,Object>> location(Integer pipelineId,Integer[] campusIds,Integer wellheadId,String pipelineCode);
	
	List<Map<String,Object>> export(String pipelineCode,Integer[] campusIds,Integer pipelineType,String pipelineName,Integer status,Integer wellheadId);

	Res importExcel(List<Map<String, String>> datas, Integer userId, Integer status, Integer type);
}
