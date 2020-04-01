package com.ncu.springboot.provider.resource.service;

import java.util.List;
import java.util.Map;


import com.ncu.springboot.api.resource.entity.ResourceFile;

public interface FileService {

	List<Map<String, Object>> download(Integer objectId,Integer type);
	
	Integer upload(ResourceFile file);
}
