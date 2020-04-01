package com.ncu.springboot.api.resource.entity;
import javax.persistence.Column;
import javax.persistence.Table;

import com.ncu.springboot.core.annotation.Mark;

import java.io.Serializable;
import javax.persistence.Id;
import java.lang.String;
import java.lang.Integer;
import java.sql.Timestamp;

@Table(name = "tb_port")
public class Port implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Mark(name = "端口id")
	@Column(name = "PORT_ID")
	private Integer portId;

	@Mark(name = "设备id")
	@Column(name = "DEVICE_ID")
	private Integer deviceId;

	@Mark(name = "端口名称")
	@Column(name = "PORT_NAME")
	private String portName;

	@Mark(name = "创建时间")
	@Column(name = "CREATE_TIME")
	private Timestamp createTime;

	@Mark(name = "状态")
	@Column(name = "STATUS")
	private Integer status;

	@Mark(name = "端口类型")
	@Column(name = "PORT_TYPE")
	private Integer portType;

	@Mark(name = "修改时间")
	@Column(name = "UPDATE_TIME")
	private Timestamp updateTime;

	@Mark(name = "备注")
	@Column(name = "REMARK")
	private String remark;

	@Mark(name = "修改人")
	@Column(name = "UPDATE_PERSON_ID")
	private Integer updatePersonId;

	@Mark(name = "端口编号")
	@Column(name = "PORT_CODE")
	private String portCode;

	public String getPortCode() {
		return portCode;
	}

	public void setPortCode(String portCode) {
		this.portCode = portCode;
	}

	public Integer getPortId() {
		return portId;
	}

	public void setPortId(Integer portId) {
		this.portId = portId;
	}

	public Integer getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	public String getPortName() {
		return portName;
	}

	public void setPortName(String portName) {
		this.portName = portName;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getUpdatePersonId() {
		return updatePersonId;
	}

	public void setUpdatePersonId(Integer updatePersonId) {
		this.updatePersonId = updatePersonId;
	}

	@Override
	public String toString() {
		return "Port [portId=" + portId + ", deviceId=" + deviceId + ", portName=" + portName + ", createTime="
				+ createTime + ", status=" + status + ", portType=" + portType + ", updateTime=" + updateTime
				+ ", remark=" + remark + ", updatePersonId=" + updatePersonId + ", portCode=" + portCode + "]";
	}
	
}