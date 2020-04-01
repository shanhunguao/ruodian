package com.ncu.springboot.provider.system.service;

import com.ncu.springboot.biz.entity.Upgrade;

public interface UpgradeService {
	
	Upgrade checkUpgrade(String old_version);
	
}
