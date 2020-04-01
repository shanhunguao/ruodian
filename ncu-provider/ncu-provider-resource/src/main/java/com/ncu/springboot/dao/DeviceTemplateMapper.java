package com.ncu.springboot.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ncu.springboot.api.resource.entity.DeviceTemplate;
import com.ncu.springboot.mybatis.mapper.BaseMapper;

public interface DeviceTemplateMapper extends BaseMapper<DeviceTemplate> {

	Integer getTotal(@Param("templateName")String templateName,@Param("status")Integer status,@Param("manufactor")Integer manufactor,@Param("deviceModel")String deviceModel);
	
	List<Map<String,Object>> queryList(@Param("templateName")String templateName,@Param("status")Integer status,@Param("manufactor")Integer manufactor,@Param("deviceModel")String deviceModel,@Param("size")Integer size,@Param("num")Integer num);
	
	Map<String, Object> query(@Param("templateId")Integer templateId);
	
}
