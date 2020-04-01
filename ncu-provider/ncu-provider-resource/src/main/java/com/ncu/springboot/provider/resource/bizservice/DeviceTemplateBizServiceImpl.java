package com.ncu.springboot.provider.resource.bizservice;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.osgi.service.component.annotations.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.ncu.springboot.api.resource.bizservice.DeviceTemplateBizService;
import com.ncu.springboot.api.resource.entity.DeviceTemplate;
import com.ncu.springboot.api.resource.entity.PortTemplate;
import com.ncu.springboot.provider.resource.service.DeviceTemplateService;

@Component
@Service
public class DeviceTemplateBizServiceImpl implements DeviceTemplateBizService {

	@Resource
	private DeviceTemplateService deviceTemplateService;
	
	@Override
	public Integer getTotal(String templateName, Integer status, Integer manufactor,
			String deviceModel) {
		return deviceTemplateService.getTotal(templateName, status, manufactor, deviceModel);
	}

	@Override
	public List<Map<String, Object>> queryList(String templateName, Integer status,
			Integer manufactor, String deviceModel, Integer size, Integer num) {
		if(size != null && num !=null) {
			num = (num-1)*size;
			}
		return deviceTemplateService.queryList(templateName, status, manufactor, deviceModel, size, num);
	}

	@Override
	public Integer addDeviceTemplate(DeviceTemplate deviceTemplate,PortTemplate[] portTemplates) {
		return deviceTemplateService.addDeviceTemplate(deviceTemplate,portTemplates);
	}

	@Override
	public Integer delDeviceTemplate(Integer[] templateIds) {
		return deviceTemplateService.delDeviceTemplate(templateIds);
	}

	@Override
	public Integer editDeviceTemplate(DeviceTemplate deviceTemplate,PortTemplate[] portTemplates) {
		return deviceTemplateService.editDeviceTemplate(deviceTemplate,portTemplates);
	}

	@Override
	public Map<String, Object> query(Integer templateId) {
		return deviceTemplateService.query(templateId);
	}

}
