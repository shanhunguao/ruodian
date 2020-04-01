package com.ncu.springboot.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ncu.springboot.api.resource.entity.PipeWellhead;
import com.ncu.springboot.mybatis.mapper.BaseMapper;

public interface PipeWellheadMapper extends BaseMapper<PipeWellhead>{
	
	Integer getTotal(@Param("wellheadCode")String wellheadCode,@Param("campusIds")Integer[] campusIds,@Param("wellheadType")String wellheadType,@Param("wellheadName")String wellheadName,@Param("status")Integer status);
	
	List<Map<String,Object>> queryList(@Param("wellheadCode")String wellheadCode,@Param("campusIds")Integer[] campusIds,@Param("wellheadType")String wellheadType,@Param("wellheadName")String wellheadName,@Param("status")Integer status,@Param("size")Integer size,@Param("num")Integer num);
	
	List<Map<String,Object>> location(@Param("wellheadId")Integer wellheadId,@Param("wellheadCode")String wellheadCode,@Param("campusIds")Integer[] campusIds);
	
	List<Map<String,Object>> export(@Param("wellheadCode")String wellheadCode,@Param("campusIds")Integer[] campusIds,@Param("wellheadType")String wellheadType,@Param("wellheadName")String wellheadName,@Param("status")Integer status);
	
	Map<String, Object> query(@Param("wellheadId")Integer wellheadId,@Param("wellheadCode")String wellheadCode);
	
	List<Map<String, Object>> selectIdByCode(@Param("wellheadCodes")List<String> wellheadCodes);
}
