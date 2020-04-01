package com.ncu.springboot.api.system.bizservice;

import com.ncu.springboot.biz.entity.Upgrade;

public interface UpgradeBizService {
	
	Upgrade checkUpgrade(String old_version);

}
