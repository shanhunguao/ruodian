package com.ncu.springboot.provider.resource.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Service;
import com.ncu.springboot.api.resource.entity.DeviceTemplate;
import com.ncu.springboot.api.resource.entity.PortTemplate;
import com.ncu.springboot.dao.DeviceTemplateMapper;
import com.ncu.springboot.dao.PortTemplateMapper;
import com.ncu.springboot.provider.resource.service.DeviceTemplateService;

@Service
public class DeviceTemplateServiceImpl implements DeviceTemplateService {

	@Resource
	private DeviceTemplateMapper deviceTemplateMapper;
	
	@Resource
	private PortTemplateMapper portTemplateMapper;
	
	@Override
	public Integer getTotal(String templateName, Integer status, Integer manufactor,
			String deviceModel) {
		return deviceTemplateMapper.getTotal(templateName, status, manufactor, deviceModel);
	}

	@Override
	public List<Map<String, Object>> queryList(String templateName, Integer status,
			Integer manufactor, String deviceModel, Integer size, Integer num) {
		return deviceTemplateMapper.queryList(templateName, status, manufactor, deviceModel, size, num);
	}

	@Transactional
	public Integer addDeviceTemplate(DeviceTemplate deviceTemplate,PortTemplate[] portTemplates) {
		Integer data = deviceTemplateMapper.insertUseGeneratedKeys(deviceTemplate);
		Integer deviceTemplateId = deviceTemplate.getTemplateId();
		for (PortTemplate portTemplate :portTemplates) {
			portTemplate.setDeviceTemplateId(deviceTemplateId);
			portTemplateMapper.insert(portTemplate);
		}
		return data;
	}

	@Transactional
	public Integer delDeviceTemplate(Integer[] templateIds) {
		int count = 0;
		PortTemplate portTemplate = new PortTemplate();
		DeviceTemplate deviceTemplate = new DeviceTemplate();
		for (Integer templateId:templateIds) {
			deviceTemplate.setTemplateId(templateId);
			portTemplate.setTemplateId(templateId);
			count += deviceTemplateMapper.delete(deviceTemplate);
			portTemplateMapper.delete(portTemplate);
		}
		return count;
	}

	@Transactional
	public Integer editDeviceTemplate(DeviceTemplate deviceTemplate,PortTemplate[] portTemplates) {
		for (PortTemplate portTemplate : portTemplates) {
			portTemplateMapper.updateByPrimaryKeySelective(portTemplate);
		}
		return deviceTemplateMapper.updateByPrimaryKeySelective(deviceTemplate);
	}

	@Override
	public Map<String, Object> query(Integer templateId) {
		Map<String, Object> result = new HashMap<String, Object>();
		result = deviceTemplateMapper.query(templateId);
		PortTemplate example = new PortTemplate();
		example.setDeviceTemplateId(templateId);
		List<PortTemplate> portTemplateList = portTemplateMapper.select(example);
		result.put("portTemplateList", portTemplateList);
		return result;
	}

}
