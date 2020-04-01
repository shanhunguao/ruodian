package com.ncu.springboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ncu.springboot.api.system.bizservice.UpgradeBizService;
import com.ncu.springboot.biz.entity.Upgrade;
import com.ncu.springboot.biz.rs.Res;

@RestController
@RequestMapping("/upgrade")
public class UpgradeController {
	
	@Reference
	private UpgradeBizService upgradeBizService;
	
	@RequestMapping("/checkUpgrade")
	public Res checkUpgrade(String old_version) {
		Upgrade upgrade = upgradeBizService.checkUpgrade(old_version);
		
		return Res.SUCCESS("当前已是最新版本", upgrade);
	}

}
