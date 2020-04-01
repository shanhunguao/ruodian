package com.ncu.springboot.api.resource.bizservice;

import java.util.List;
import java.util.Map;

import com.ncu.springboot.api.resource.entity.DeviceTemplate;
import com.ncu.springboot.api.resource.entity.PortTemplate;

public interface DeviceTemplateBizService {

	Integer getTotal(String templateName,Integer status,Integer manufactor,String deviceModel);
	
	List<Map<String,Object>> queryList(String templateName,Integer status,Integer manufactor,String deviceModel,Integer size,Integer num);
	
	Integer addDeviceTemplate(DeviceTemplate deviceTemplate,PortTemplate[] portTemplates);
	
	Integer delDeviceTemplate(Integer[] templateIds);
	
	Integer editDeviceTemplate(DeviceTemplate deviceTemplate,PortTemplate[] portTemplates);
	
	Map<String,Object> query(Integer templateId);
}
