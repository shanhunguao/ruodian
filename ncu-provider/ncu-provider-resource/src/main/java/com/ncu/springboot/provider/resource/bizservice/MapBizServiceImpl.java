package com.ncu.springboot.provider.resource.bizservice;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.osgi.service.component.annotations.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.ncu.springboot.api.resource.bizservice.MapBizService;
import com.ncu.springboot.provider.resource.service.MapService;

@Component
@Service
public class MapBizServiceImpl implements MapBizService {

	@Resource
	private MapService mapService;
	
	@Override
	public List<Map<String, Object>> queryList(String name) {
		return mapService.queryList(name);
	}

}
