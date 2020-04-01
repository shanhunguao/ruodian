package com.ncu.springboot.controller;

import java.util.List;
import java.util.Map;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ncu.springboot.AOP.Log;
import com.ncu.springboot.AOP.Permission;
import com.ncu.springboot.api.resource.bizservice.FileBizService;
import com.ncu.springboot.api.resource.entity.ResourceFile;
import com.ncu.springboot.biz.rs.Res;

@RestController
@RequestMapping("/resourceFile")
public class FileController {

	@Reference
	private FileBizService fileBizService;
	
	@RequestMapping("/download")
	@Log(operateType = "文件查看")
	@Permission
	public Res download(Integer objectId,Integer type) {
		List<Map<String, Object>> data = fileBizService.download(objectId, type);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping(value = "/upload",method = RequestMethod.POST)
	@Log(operateType = "文件上传")
	@Permission
	public Res uploadFile(ResourceFile resourceFile){
		Integer data = fileBizService.upload(resourceFile);
		return Res.SUCCESS(data);
	}
}
