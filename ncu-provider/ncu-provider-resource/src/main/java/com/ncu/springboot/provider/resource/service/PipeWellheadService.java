package com.ncu.springboot.provider.resource.service;

import java.util.List;

import java.util.Map;

import com.ncu.springboot.api.resource.entity.PipeWellhead;

public interface PipeWellheadService {

	Integer getTotal(String wellheadCode,Integer[] campusIds,String wellheadType,String wellheadName,Integer status);
	
	List<Map<String,Object>> queryList(String wellheadCode,Integer[] campusIds,String wellheadType,String wellheadName,Integer status,Integer size,Integer num);
	
	Integer addWellhead(PipeWellhead pipeWellhead);
	
	Integer addWellheads(List<PipeWellhead> pipeWellheads);
	
	Integer delWellhead(List<PipeWellhead> pipeWellheads,PipeWellhead example);
	
	Integer editWellhead(PipeWellhead pipeWellhead);
	
	List<Map<String,Object>> location(Integer wellheadId,String wellheadCode,Integer[] campusIds);
	
	Map<String, Object> query(Integer wellheadId,String wellheadCode);
	
	List<Map<String, Object>> export(String wellheadCode,Integer[] campusIds,String wellheadType,String wellheadName,Integer status);
	
	List<PipeWellhead> selectWellheads(PipeWellhead wellhead);
	
	List<Map<String, Object>> selectIdByCode(List<String> wellheadCodes);
}
