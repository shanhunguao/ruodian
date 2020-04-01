package com.ncu.springboot.dao;

import java.util.List;
import java.util.Map;

import com.ncu.springboot.api.message.entity.MessageDeptNumber;
import com.ncu.springboot.mybatis.mapper.BaseMapper;

import io.lettuce.core.dynamic.annotation.Param;

public interface MessageDeptNumberMapper extends BaseMapper<MessageDeptNumber> {

	Integer getTotal(@Param("deptId")Integer deptId,@Param("deptName")String deptName);
	
	List<Map<String, Object>> queryList(@Param("deptId")Integer deptId,@Param("deptName")String deptName,@Param("size")Integer size,@Param("num")Integer num);
	
	Integer addNumber(@Param("id")Integer id,@Param("number")Integer number);
}
