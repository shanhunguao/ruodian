package com.ncu.springboot.dao;

import java.util.Map;

import com.ncu.springboot.api.gate.entity.TempPersion;
import com.ncu.springboot.mybatis.mapper.BaseMapper;

import io.lettuce.core.dynamic.annotation.Param;

public interface TempPersionMapper extends BaseMapper<TempPersion>{

	Map<String, Object> getTempPersionInfo(@Param("mobile")String mobile, @Param("idCard")String idCard,@Param("cardId")String cardId);

}
