package com.ncu.springboot.api.resource.DTO;

import java.io.Serializable;
import java.util.Arrays;

import com.ncu.springboot.api.resource.entity.DeviceTemplate;
import com.ncu.springboot.api.resource.entity.PortTemplate;

public class DevicePortTemplateDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private DeviceTemplate deviceTemplate;
	
	private PortTemplate[] portTemplates;

	public DeviceTemplate getDeviceTemplate() {
		return deviceTemplate;
	}

	public void setDeviceTemplate(DeviceTemplate deviceTemplate) {
		this.deviceTemplate = deviceTemplate;
	}

	public PortTemplate[] getPortTemplates() {
		return portTemplates;
	}

	public void setPortTemplates(PortTemplate[] portTemplates) {
		this.portTemplates = portTemplates;
	}

	@Override
	public String toString() {
		return "DevicePortTemplateDTO [deviceTemplate=" + deviceTemplate + ", portTemplates="
				+ Arrays.toString(portTemplates) + "]";
	}

}
