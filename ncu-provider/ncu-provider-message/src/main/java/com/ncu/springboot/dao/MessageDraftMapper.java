package com.ncu.springboot.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ncu.springboot.api.message.entity.MessageDraft;
import com.ncu.springboot.mybatis.mapper.BaseMapper;

public interface MessageDraftMapper extends BaseMapper<MessageDraft>{
	
	List<Map<String, Object>> queryList(@Param("sendPerson")Integer sendPerson,@Param("size")Integer size,@Param("num")Integer num);
	
	Integer getTotal(@Param("sendPerson")Integer sendPerson);
}
