package com.ncu.springboot.provider.resource.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.ncu.springboot.dao.MapMapper;
import com.ncu.springboot.provider.resource.service.MapService;

@Service
public class MapServiceImpl implements MapService{

	@Resource
	private MapMapper mapMapper;

	@Override
	public List<Map<String, Object>> queryList(String name) {
		return mapMapper.queryList(name);
	}
	
	
}
