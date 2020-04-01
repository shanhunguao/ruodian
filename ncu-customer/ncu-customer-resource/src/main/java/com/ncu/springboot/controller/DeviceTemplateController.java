package com.ncu.springboot.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ncu.springboot.AOP.Permission;
import com.ncu.springboot.api.resource.DTO.DevicePortTemplateDTO;
import com.ncu.springboot.api.resource.bizservice.DeviceTemplateBizService;
import com.ncu.springboot.api.resource.entity.DeviceTemplate;
import com.ncu.springboot.api.resource.entity.PortTemplate;
import com.ncu.springboot.common.util.Utils;
import com.ncu.springboot.log.LogUtil;
import com.ncu.springboot.biz.rs.Res;

@RestController
@RequestMapping("/deviceTemplate")
public class DeviceTemplateController {

	@Reference
	private DeviceTemplateBizService deviceTemplateBizService;
	
	@Autowired
	private LogUtil<DeviceTemplate> logUtil;
	
	@Autowired
	private LogUtil<PortTemplate> logUtilPort;
	
	@RequestMapping("/getTotal")
	public Res getTotal(String templateName,Integer status,Integer manufactor,String deviceModel) {
		Integer data = deviceTemplateBizService.getTotal(templateName, status, manufactor, deviceModel);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/queryList")
	public Res queryList(String templateName,Integer status,Integer manufactor,String deviceModel,Integer size,Integer num) {
		List<Map<String, Object>> data = deviceTemplateBizService.queryList(templateName, status, manufactor, deviceModel, size, num);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/addDeviceTemplate")
	@Permission
	public Res addDeviceTemplate(@RequestBody(required = false)DevicePortTemplateDTO devicePortTemplateDTO) {
		DeviceTemplate deviceTemplate = devicePortTemplateDTO.getDeviceTemplate();
		deviceTemplate.setCreateTime(Utils.getTimeStamp());
		PortTemplate[] portTemplates = devicePortTemplateDTO.getPortTemplates();
		deviceTemplate.setCreateTime(Utils.getTimeStamp());
		for (int i = 0;i<portTemplates.length;i++) {
			portTemplates[i].setCreateTime(Utils.getTimeStamp());
		}
		Integer data = deviceTemplateBizService.addDeviceTemplate(deviceTemplate,portTemplates);
		logUtil.saveLog("add", "添加设备模板", deviceTemplate, null);
		for (int i = 0; i < portTemplates.length; i++) {
			logUtilPort.saveLog("add", "添加端口模板", portTemplates[i], null);
		}
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/delDeviceTemplate")
	@Permission
	public Res delDeviceTemplate(Integer[] templateIds) {
		if(templateIds == null || templateIds.length<1) {
			return Res.ERROR("1", "未选择对象！");
		}
		Integer data = deviceTemplateBizService.delDeviceTemplate(templateIds);
		logUtil.saveLog("delete", "删除设备模板", null, templateIds);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/editDeviceTemplate")
	@Permission
	public Res editDeviceTemplate(@RequestBody(required = false)DevicePortTemplateDTO devicePortTemplateDTO) {
		DeviceTemplate deviceTemplate = devicePortTemplateDTO.getDeviceTemplate();
		deviceTemplate.setUpdateTime(Utils.getTimeStamp());
		PortTemplate[] portTemplates = devicePortTemplateDTO.getPortTemplates();
		for (int i = 0; i < portTemplates.length; i++) {
			portTemplates[i].setUpdateTime(Utils.getTimeStamp());
		}
		Integer data = deviceTemplateBizService.editDeviceTemplate(deviceTemplate,portTemplates);
		logUtil.saveLog("edit", "编辑设备模板", deviceTemplate, null);
		for (int i = 0; i < portTemplates.length; i++) {
			logUtilPort.saveLog("edit", "编辑端口模板", portTemplates[i], null);
		}
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/query")
	public Res query(Integer templateId) {
		Map<String, Object> data = deviceTemplateBizService.query(templateId);
		return Res.SUCCESS(data);
	}
}
