package com.ncu.springboot.provider.resource.service.impl;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.ncu.springboot.api.resource.entity.PipeWellhead;
import com.ncu.springboot.dao.PipeWellheadMapper;
import com.ncu.springboot.provider.resource.service.PipeWellheadService;

import tk.mybatis.mapper.entity.Example;

@Service
public class PipeWellheadServiceImpl implements PipeWellheadService {
	
	@Resource
	private PipeWellheadMapper pipeWellheadMapper;
	
	public Integer getTotal(String wellheadCode,Integer[] campusIds,String wellheadType,String wellheadName,Integer status) {
		return pipeWellheadMapper.getTotal(wellheadCode,campusIds, wellheadType, wellheadName, status);
	}

	public List<Map<String, Object>> queryList(String wellheadCode,Integer[] campusIds,String wellheadType,String wellheadName,Integer status,Integer size,Integer num) {
		return pipeWellheadMapper.queryList(wellheadCode,campusIds, wellheadType, wellheadName, status, size, num);
	}
	
	public Integer delWellhead(List<PipeWellhead> pipeWellheads,PipeWellhead wellheadExample) {
		Integer count = 0;
		for (PipeWellhead pipeWellhead : pipeWellheads) {
			Example example = new Example(PipeWellhead.class);
			example.createCriteria().andEqualTo(pipeWellhead);
			count += pipeWellheadMapper.updateByExampleSelective(wellheadExample,example);
		}
		return count;
	}

	@Override
	public Integer addWellhead(PipeWellhead pipeWellhead) {
		return pipeWellheadMapper.insert(pipeWellhead);
	}

	@Override
	public Integer editWellhead(PipeWellhead pipeWellhead) {
		return pipeWellheadMapper.updateByPrimaryKeySelective(pipeWellhead);
	}

	@Override
	public List<Map<String, Object>> location(Integer wellheadId,String wellheadCode,Integer[] campusIds) {
		return pipeWellheadMapper.location(wellheadId,wellheadCode,campusIds);
	}

	@Override
	public Map<String, Object> query(Integer wellheadId,String wellheadCode) {
		return pipeWellheadMapper.query(wellheadId,wellheadCode);
	}

	@Override
	public List<Map<String, Object>> export(String wellheadCode,Integer[] campusIds,String wellheadType,String wellheadName,Integer status) {
		return pipeWellheadMapper.export(wellheadCode,campusIds, wellheadType, wellheadName, status);
	}

	//单表查询
	public List<PipeWellhead> selectWellheads(PipeWellhead wellhead) {
		return pipeWellheadMapper.select(wellhead);
	}

	@Override
	public Integer addWellheads(List<PipeWellhead> pipeWellheads) {
		return pipeWellheadMapper.insertList(pipeWellheads);
	}

	@Override
	public List<Map<String, Object>> selectIdByCode(List<String> wellheadCodes) {
		return pipeWellheadMapper.selectIdByCode(wellheadCodes);
	}
}
