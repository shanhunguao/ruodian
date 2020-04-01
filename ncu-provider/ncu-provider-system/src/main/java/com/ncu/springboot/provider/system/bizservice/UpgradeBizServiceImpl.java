package com.ncu.springboot.provider.system.bizservice;

import javax.annotation.Resource;

import org.osgi.service.component.annotations.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.ncu.springboot.api.system.bizservice.UpgradeBizService;
import com.ncu.springboot.biz.entity.Upgrade;
import com.ncu.springboot.provider.system.service.UpgradeService;

@Component
@Service
public class UpgradeBizServiceImpl implements UpgradeBizService {
	
	@Resource
	private UpgradeService upgradeService;

	@Override
	public Upgrade checkUpgrade(String old_version) {
		
		return upgradeService.checkUpgrade(old_version);
	}

}
