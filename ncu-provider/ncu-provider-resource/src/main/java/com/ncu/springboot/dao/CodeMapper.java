package com.ncu.springboot.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ncu.springboot.api.resource.entity.Code;
import com.ncu.springboot.mybatis.mapper.BaseMapper;

public interface CodeMapper extends BaseMapper<Code> {

	List<Map<String, Object>> query(@Param("codeId")Integer codeId,@Param("option")String option);
}
