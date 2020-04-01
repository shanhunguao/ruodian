package com.ncu.springboot.api.resource.bizservice;

import java.util.List;
import java.util.Map;

import com.ncu.springboot.api.resource.entity.ResourceFile;

public interface FileBizService {
	
	List<Map<String, Object>> download(Integer objectId,Integer type);
	
	Integer upload(ResourceFile resourceFile);
}
