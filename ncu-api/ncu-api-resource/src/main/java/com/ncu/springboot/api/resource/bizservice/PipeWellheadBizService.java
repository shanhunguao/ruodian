package com.ncu.springboot.api.resource.bizservice;

import java.util.List;
import java.util.Map;

import com.ncu.springboot.api.resource.entity.PipeWellhead;
import com.ncu.springboot.biz.rs.Res;

public interface PipeWellheadBizService {
	
	Integer getTotal(String wellheadCode,Integer[] campusIds,String wellheadType,String wellheadName,Integer status);
	
	List<Map<String,Object>> queryList(String wellheadCode,Integer[] campusIds,String wellheadType,String wellheadName,Integer status,Integer size,Integer num);
	
	Integer addWellhead(PipeWellhead pipeWellhead);
	
	Integer delWellhead(Integer[] wellheadIds);
	
	Integer editWellhead(PipeWellhead pipeWellhead);
	
	List<Map<String,Object>> location(Integer wellheadId,String wellheadCode,Integer[] campusIds);
	
	Map<String, Object> query(Integer wellheadId,String wellheadCode);
	
	List<Map<String, Object>> export(String wellheadCode,Integer[] campusIds,String wellheadType,String wellheadName,Integer status);

	Res importExcel(List<Map<String, String>> datas, Integer userId, Integer status, Integer type);
}
