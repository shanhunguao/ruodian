package com.ncu.springboot.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ncu.springboot.api.resource.entity.ResourceFile;
import com.ncu.springboot.mybatis.mapper.BaseMapper;

public interface FileMapper extends BaseMapper<ResourceFile> {

	List<Map<String, Object>> download(@Param("objectId")Integer objectId,@Param("type")Integer type);
	
}
