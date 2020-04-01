package com.ncu.springboot.provider.resource.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;
import com.ncu.springboot.api.resource.entity.Pipeline;
import com.ncu.springboot.dao.PipelineMapper;
import com.ncu.springboot.provider.resource.service.PipelineService;

import tk.mybatis.mapper.entity.Example;

@Service
public class PipelineServiceImpl implements PipelineService {

	@Resource
	private PipelineMapper pipelineMapper;
	
	public Integer getTotal(String pipelineCode,Integer[] campusIds,Integer pipelineType,String pipelineName,Integer status,Integer wellheadId) {
		return pipelineMapper.getTotal(pipelineCode,campusIds, pipelineType, pipelineName, status, wellheadId);
	}

	public List<Map<String, Object>> queryList(String pipelineCode,Integer[] campusIds,Integer pipelineType,String pipelineName,Integer status,Integer wellheadId, Integer size,Integer num) {
		return pipelineMapper.queryList(pipelineCode,campusIds, pipelineType, pipelineName, status, wellheadId, size,num);
	}

	public Integer addPipeline(Pipeline pipeline) {
		return pipelineMapper.insert(pipeline);
	}

	public Integer delPipeline(List<Pipeline> pipelines,Pipeline PipelineExample) {
		Integer count = 0;
		for (Pipeline pipeline : pipelines) {
			Example example = new Example(Pipeline.class);
			example.createCriteria().andEqualTo(pipeline);
			count += pipelineMapper.updateByExampleSelective(PipelineExample,example);
		}
		return count;
	}

	public Integer editPipeline(Pipeline pipeline) {
		return pipelineMapper.updateByPrimaryKeySelective(pipeline);
	}

	public Map<String, Object> query(Integer pipelineId,String pipelineCode) {
		return pipelineMapper.query(pipelineId, pipelineCode);
	}
	
	public List<Map<String,Object>> location(Integer pipelineId,Integer[] campusIds,Integer wellheadId,String pipelineCode) {
		return pipelineMapper.location(pipelineId,campusIds,wellheadId,pipelineCode);
	}

	@Override
	public List<Map<String, Object>> export(String pipelineCode,Integer[] campusIds,Integer pipelineType,String pipelineName,Integer status,Integer wellheadId) {
		return pipelineMapper.export(pipelineCode,campusIds, pipelineType, pipelineName, status, wellheadId);
	}

	//单表查询
	public List<Pipeline> selectPipelines(Pipeline pipeline) {
		return pipelineMapper.select(pipeline);
	}

	@Override
	public Integer addPipelines(List<Pipeline> pipelines) {
		return pipelineMapper.insertList(pipelines);
	}

	@Override
	public List<Map<String, Object>> selectIdByCode(List<String> pipelineCodes) {
		return pipelineMapper.selectIdByCode(pipelineCodes);
	}

}
