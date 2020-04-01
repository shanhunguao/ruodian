package com.ncu.springboot.provider.system.service.impl;

import javax.annotation.Resource;

import com.alibaba.dubbo.config.annotation.Service;

import com.ncu.springboot.biz.entity.Upgrade;
import com.ncu.springboot.provider.system.dao.UpgradeMapper;
import com.ncu.springboot.provider.system.service.UpgradeService;

@Service
public class UpgradeServiceImpl implements UpgradeService {
	
	@Resource
	private UpgradeMapper upgradeMapper;

	@Override
	public Upgrade checkUpgrade(String old_version) {
		return upgradeMapper.checkUpgrade(old_version);
	}

}
