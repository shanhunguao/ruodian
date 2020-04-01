package com.ncu.springboot.dao;

import java.util.List;
import java.util.Map;

import com.ncu.springboot.api.message.entity.MessageLog;
import com.ncu.springboot.mybatis.mapper.BaseMapper;

import io.lettuce.core.dynamic.annotation.Param;

public interface MessageLogMapper extends BaseMapper<MessageLog> {

	Integer getTotal(@Param("employeeId")String employeeId,@Param("employeeName")String employeeName,@Param("messageId")String messageId,@Param("subject")String subject,@Param("content")String content,@Param("operate")String operate,@Param("endTime")String endTime,@Param("startTime")String startTime,@Param("deptId")Integer deptId);

	List<Map<String, Object>> queryList(@Param("employeeId")String employeeId,@Param("employeeName")String employeeName,@Param("messageId")String messageId,@Param("subject")String subject,@Param("content")String content,@Param("operate")String operate,@Param("endTime")String endTime,@Param("startTime")String startTime,@Param("deptId")Integer deptId,@Param("size")Integer size,@Param("num")Integer num);
}
