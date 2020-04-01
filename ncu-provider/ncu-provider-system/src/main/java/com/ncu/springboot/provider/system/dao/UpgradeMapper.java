package com.ncu.springboot.provider.system.dao;

import org.apache.ibatis.annotations.Param;

import com.ncu.springboot.biz.entity.Upgrade;
import com.ncu.springboot.mybatis.mapper.BaseMapper;

public interface UpgradeMapper extends BaseMapper<Upgrade> {
	
	Upgrade checkUpgrade(@Param("old_version") String old_version);

}
