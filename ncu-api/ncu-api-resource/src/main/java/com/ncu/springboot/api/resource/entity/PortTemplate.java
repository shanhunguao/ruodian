package com.ncu.springboot.api.resource.entity;
import javax.persistence.Column;
import javax.persistence.Table;

import com.ncu.springboot.core.annotation.Mark;

import java.io.Serializable;
import javax.persistence.Id;
import java.lang.Integer;
import java.sql.Timestamp;

@Table(name = "tb_port_template")
public class PortTemplate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id

	@Mark(name = "模板id")
	@Column(name = "TEMPLATE_ID")
	private Integer templateId;

	@Mark(name = "设备模板id")
	@Column(name = "DEVICE_TEMPLATE_ID")
	private Integer deviceTemplateId;

	@Mark(name = "端口数量")
	@Column(name = "NUM")
	private Integer num;

	@Mark(name = "端口类型")
	@Column(name = "PORT_TYPE")
	private Integer portType;

	@Mark(name = "修改时间")
	@Column(name = "UPDATE_TIME")
	private Timestamp updateTime;

	@Mark(name = "创建时间")
	@Column(name = "CREATE_TIME")
	private Timestamp createTime;

	@Mark(name = "创建人")
	@Column(name = "CREATE_PERSON_ID")
	private Integer createPersonId;

	@Mark(name = "修改人")
	@Column(name = "UPDATE_PERSON_ID")
	private Integer updatePersonId;

	public Integer getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	public Integer getDeviceTemplateId() {
		return deviceTemplateId;
	}

	public void setDeviceTemplateId(Integer deviceTemplateId) {
		this.deviceTemplateId = deviceTemplateId;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getPortType() {
		return portType;
	}

	public void setPortType(Integer portType) {
		this.portType = portType;
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

	public Integer getUpdatePersonId() {
		return updatePersonId;
	}

	public void setUpdatePersonId(Integer updatePersonId) {
		this.updatePersonId = updatePersonId;
	}

	@Override
	public String toString() {
		return "PortTemplate [templateId=" + templateId + ", deviceTemplateId=" + deviceTemplateId + ", num=" + num
				+ ", portType=" + portType + ", updateTime=" + updateTime + ", createTime=" + createTime
				+ ", createPersonId=" + createPersonId + ", updatePersonId=" + updatePersonId + "]";
	}

}