package com.ncu.springboot.dao;

import java.util.List;
import java.util.Map;

import com.ncu.springboot.api.resource.entity.BuildingPlan;
import com.ncu.springboot.mybatis.mapper.BaseMapper;

import io.lettuce.core.dynamic.annotation.Param;

public interface BuildingPlanMapper extends BaseMapper<BuildingPlan> {

	List<Map<String, Object>> queryList(@Param("buildingId")Integer buildingId,@Param("campusId")Integer campusId,@Param("floorNum")Integer floorNum);
}
