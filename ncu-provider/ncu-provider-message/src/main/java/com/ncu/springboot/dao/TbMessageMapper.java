package com.ncu.springboot.dao;

import java.util.List;
import java.util.Map;

import com.ncu.springboot.api.message.entity.TbMessage;
import com.ncu.springboot.mybatis.mapper.BaseMapper;

import io.lettuce.core.dynamic.annotation.Param;

public interface TbMessageMapper extends BaseMapper<TbMessage> {

	List<Map<String, Object>> queryList(@Param("messageId")Integer messageId,@Param("endTime")String endTime,@Param("startTime")String startTime,@Param("sendPerson")String sendPerson,@Param("to")String to,@Param("deptId")Integer deptId,@Param("status")Integer status,@Param("subject")String subject,@Param("content")String content);
}
