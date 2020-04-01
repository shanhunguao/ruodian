package com.ncu.springboot.provider.resource.bizservice;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.osgi.service.component.annotations.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.ncu.springboot.api.resource.bizservice.FileBizService;
import com.ncu.springboot.api.resource.entity.ResourceFile;
import com.ncu.springboot.provider.resource.service.FileService;


@Service
@Component
public class FileBizServiceImpl implements FileBizService {

	@Resource
	private FileService fileService;

	public List<Map<String, Object>> download(Integer objectId, Integer type) {
		return fileService.download(objectId, type);
	}

	public Integer upload(ResourceFile resourceFile) {
		return fileService.upload(resourceFile);
	}

}
