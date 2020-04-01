package com.ncu.springboot.provider.resource.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;
import com.ncu.springboot.api.resource.entity.ResourceFile;
import com.ncu.springboot.dao.FileMapper;
import com.ncu.springboot.provider.resource.service.FileService;

@Service
public class FileServiceImpl implements FileService {
	
	@Resource
	private FileMapper fileMapper;

	public List<Map<String, Object>> download(Integer objectId, Integer type) {
		return fileMapper.download(objectId, type);
	}

	@Override
	public Integer upload(ResourceFile file) {
		return fileMapper.insert(file);
	}

	

}
