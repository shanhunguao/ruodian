package com.ncu.springboot.dao;

import java.util.List;
import java.util.Map;

import com.ncu.springboot.api.message.entity.TbMessageTemplate;
import com.ncu.springboot.mybatis.mapper.BaseMapper;

import io.lettuce.core.dynamic.annotation.Param;

public interface MessageTemplateMapper extends BaseMapper<TbMessageTemplate> {
	
	List<Map<String, Object>> queryList(@Param("subject")String subject,@Param("size")Integer size,@Param("num")Integer num);
	
	Integer getTotal(@Param("subject")String subject);
}
