package com.ncu.springboot.api.resource.entity;
import javax.persistence.Column;
import javax.persistence.Table;

import com.ncu.springboot.core.annotation.Mark;

import java.io.Serializable;
import javax.persistence.Id;
import java.lang.String;
import java.lang.Integer;
import java.sql.Timestamp;

@Table(name = "tb_device_template")
public class DeviceTemplate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Mark(name = "设备模板id")
	@Column(name = "TEMPLATE_ID")
	private Integer templateId;

	@Mark(name = "模板名称")
	@Column(name = "TEMPLATE_NAME")
	private String templateName;

	@Mark(name = "面积")
	@Column(name = "AREA")
	private String area;

	@Mark(name = "厂商")
	@Column(name = "MANUFACTOR")
	private Integer manufactor;

	@Mark(name = "设备型号")
	@Column(name = "DEVICE_MODEL")
	private String deviceModel;

	@Mark(name = "设备类型")
	@Column(name = "DEVICE_TYPE")
	private Integer deviceType;

	@Mark(name = "功率")
	@Column(name = "POWER")
	private String power;

	@Mark(name = "简介")
	@Column(name = "FUNCTION")
	private String function;

	@Mark(name = "修改时间")
	@Column(name = "UPDATE_TIME")
	private Timestamp updateTime;

	@Mark(name = "创建时间")
	@Column(name = "CREATE_TIME")
	private Timestamp createTime;

	@Mark(name = "创建人")
	@Column(name = "CREATE_PERSON_ID")
	private Integer createPersonId;

	@Mark(name = "状态")
	@Column(name = "STATUS")
	private Integer status;

	@Mark(name = "修改人")
	@Column(name = "UPDATE_PERSON_ID")
	private Integer updatePersonId;
	
	@Mark(name = "高度")
	@Column(name = "HEIGHT")
	private String height;

	@Mark(name = "长度")
	@Column(name = "LENGTH")
	private String length;
	
	@Mark(name = "宽度")
	@Column(name = "WIDTH")
	private String width;

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public Integer getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Integer getManufactor() {
		return manufactor;
	}

	public void setManufactor(Integer manufactor) {
		this.manufactor = manufactor;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public Integer getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}

	public String getPower() {
		return power;
	}

	public void setPower(String power) {
		this.power = power;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Integer getCreatePersonId() {
		return createPersonId;
	}

	public void setCreatePersonId(Integer createPersonId) {
		this.createPersonId = createPersonId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getUpdatePersonId() {
		return updatePersonId;
	}

	public void setUpdatePersonId(Integer updatePersonId) {
		this.updatePersonId = updatePersonId;
	}

	@Override
	public String toString() {
		return "DeviceTemplate [templateId=" + templateId + ", templateName=" + templateName + ", area=" + area
				+ ", manufactor=" + manufactor + ", deviceModel=" + deviceModel + ", deviceType=" + deviceType
				+ ", power=" + power + ", function=" + function + ", updateTime=" + updateTime + ", createTime="
				+ createTime + ", createPersonId=" + createPersonId + ", status=" + status + ", updatePersonId="
				+ updatePersonId + ", height=" + height + ", length=" + length + ", width=" + width + "]";
	}

}