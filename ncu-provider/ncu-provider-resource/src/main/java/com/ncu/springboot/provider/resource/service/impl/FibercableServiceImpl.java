package com.ncu.springboot.provider.resource.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;
import com.ncu.springboot.api.resource.entity.Fibercable;
import com.ncu.springboot.api.resource.entity.FibercablePipeline;
import com.ncu.springboot.dao.FibercableMapper;
import com.ncu.springboot.dao.FibercablePipelineMapper;
import com.ncu.springboot.provider.resource.service.FibercableService;

import tk.mybatis.mapper.entity.Example;

@Service
public class FibercableServiceImpl implements FibercableService {

	@Resource
	private FibercableMapper fibercableMapper;

	@Resource
	private FibercablePipelineMapper fibercablePipelineMapper;

	public Integer getTotal(Integer[] campusIds,Integer originWellhead,Integer endWellhead,String fibercableName,Integer status,Integer fibercableType,String fibercableCode) {
		return fibercableMapper.getTotal(campusIds, originWellhead, endWellhead, fibercableName,status,fibercableType,fibercableCode);
	}

	public List<Map<String, Object>> queryList(Integer[] campusIds,Integer originWellhead,Integer endWellhead,String fibercableName,Integer status,Integer fibercableType,String fibercableCode, Integer size, Integer num) {
		return fibercableMapper.queryList(campusIds, originWellhead, endWellhead, fibercableName,status,fibercableType,fibercableCode,size,num);
	}


	public Integer addFibercable(Fibercable fibercable,Integer[] pipelineIds) {
		fibercableMapper.insertUseGeneratedKeys(fibercable);
		Integer data = fibercable.getFibercableId();
		Integer fibercableId = fibercable.getFibercableId();
		FibercablePipeline fibercablePipeline = new FibercablePipeline();
		if(pipelineIds != null) {
			for(Integer pipelineId:pipelineIds) {
				fibercablePipeline.setFibercableId(fibercableId);
				fibercablePipeline.setPipelineId(pipelineId);
				fibercablePipelineMapper.insert(fibercablePipeline);
			}
		}
		return data;
	}

	public Integer delFibercable(List<Fibercable> fibercables,Fibercable FibercableExample) {
		Integer count = 0;
		for (Fibercable fibercable : fibercables) {
			Example example = new Example(Fibercable.class);
			example.createCriteria().andEqualTo(fibercable);
			count += fibercableMapper.updateByExampleSelective(FibercableExample,example);
		}
		return count;
	}

	public Integer editFibercable(Fibercable fibercable,Integer[] pipelineIds) {
		FibercablePipeline fibercablePipeline = new FibercablePipeline();
		fibercablePipeline.setFibercableId(fibercable.getFibercableId());
		fibercablePipelineMapper.delete(fibercablePipeline);
		if(pipelineIds != null) {
			for(Integer pipelineId:pipelineIds) {
				fibercablePipeline.setPipelineId(pipelineId);
				fibercablePipelineMapper.insert(fibercablePipeline);
			}
		}
		return fibercableMapper.updateByPrimaryKeySelective(fibercable);
	}

	public Map<String, Object> query(Integer fibercableId,String fibercableCode) {
		return fibercableMapper.query(fibercableId, fibercableCode);
	}

	@Override
	public List<Map<String, Object>> export(Integer[] campusIds,Integer originWellhead,Integer endWellhead,String fibercableName,Integer status,Integer fibercableType,String fibercableCode) {
		return fibercableMapper.export(campusIds, originWellhead, endWellhead, fibercableName,status,fibercableType,fibercableCode);
	}

	@Override
	public List<Map<String, Object>> location(Integer fibercableId,String fibercableCode,Integer[] campusIds) {
		return fibercableMapper.location(fibercableId,fibercableCode,campusIds);
	}

	@Override
	public Integer delByPipeLine(List<Integer> pipelineIds,Integer status) {
		return fibercableMapper.delByPipeLine(pipelineIds,status);
	}

	//单表查询
	public List<Fibercable> selectFibercables(Fibercable fibercable) {
		return fibercableMapper.select(fibercable);
	}

	//根据管道id查询
	public List<Fibercable> selectByPipelineId(List<Integer> pipelineIds) {
		return fibercableMapper.selectByPipelineId(pipelineIds);
	}

	@Override
	public Integer addFibercables(List<Fibercable> fibercables) {
		return fibercableMapper.insertList(fibercables);
	}

	@Override
	public List<Map<String, Object>> selectIdByCode(List<String> fibercableCodes) {
		return fibercableMapper.selectIdByCode(fibercableCodes);
	}
}
