package com.ncu.springboot.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ncu.springboot.api.resource.entity.Line;
import com.ncu.springboot.mybatis.mapper.BaseMapper;

public interface LineMapper extends BaseMapper<Line> {
	
	Integer getTotal(@Param("portId")Integer portId,@Param("campusIds")Integer[] campusIds,@Param("lineCode")String lineCode,@Param("lineName")String lineName,@Param("lineType")Integer lineType,@Param("status")Integer status,@Param("fibercableId")Integer fibercableId);
	
	List<Map<String,Object>> queryList(@Param("portId")Integer portId,@Param("campusIds")Integer[] campusIds,@Param("lineCode")String lineCode,@Param("lineName")String lineName,@Param("lineType")Integer lineType,@Param("status")Integer status,@Param("fibercableId")Integer fibercableId,@Param("size")Integer size,@Param("num")Integer num);

	List<Map<String,Object>> location(@Param("lineId")Integer lineId,@Param("lineType")Integer lineType);
	
	List<Map<String,Object>> export(@Param("portId")Integer portId,@Param("campusIds")Integer[] campusIds,@Param("lineCode")String lineCode,@Param("lineName")String lineName,@Param("lineType")Integer lineType,@Param("status")Integer status,@Param("fibercableId")Integer fibercableId);
	
	Map<String, Object> query(@Param("lineId")Integer lineId,@Param("lineCode")String lineCode);
}
