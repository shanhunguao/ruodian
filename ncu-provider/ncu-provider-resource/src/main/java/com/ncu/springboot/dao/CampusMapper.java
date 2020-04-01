package com.ncu.springboot.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ncu.springboot.api.resource.entity.Campus;
import com.ncu.springboot.mybatis.mapper.BaseMapper;

public interface CampusMapper extends BaseMapper<Campus> {

	Integer getTotal(@Param("campusCode")String campusCode,@Param("manageDept")Integer manageDept,@Param("campusName")String campusName,@Param("status")Integer status);
	
	List<Map<String, Object>> queryList(@Param("campusCode")String campusCode,@Param("manageDept")Integer manageDept,@Param("campusName")String campusName,@Param("status")Integer status,@Param("num")Integer num,@Param("size")Integer size);
	
	Map<String,Object> location(@Param("campusId")Integer campusId,@Param("campusCode")String campusCode,@Param("manageDept")Integer manageDept);
	
	List<Map<String,Object>> export(@Param("campusCode")String campusCode,@Param("manageDept")Integer manageDept,@Param("campusName")String campusName,@Param("status")Integer status);
	
	Map<String, Object> query(@Param("campusId")Integer campusId,@Param("campusCode")String campusCode);
	
	List<Map<String, Object>> selectIdByCode(@Param("campusCodes")List<String> campusCodes);
	
	List<Map<String, Object>> selectIdByName(@Param("campusNames")List<String> campusNames);
	
}
