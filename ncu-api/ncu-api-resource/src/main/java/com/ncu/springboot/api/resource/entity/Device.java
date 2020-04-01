package com.ncu.springboot.api.resource.entity;
import javax.persistence.Column;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.ncu.springboot.core.annotation.Mark;

import java.io.Serializable;
import javax.persistence.Id;
import java.lang.String;
import java.lang.Integer;
import java.sql.Timestamp;
import java.util.Date;

@Table(name = "tb_device")
public class Device implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Mark(name = "设备id")
	@Column(name = "DEVICE_ID")
	private Integer deviceId;

	@Mark(name = "机柜id")
	@Column(name = "CABINET_ID")
	private Integer cabinetId;

	@Mark(name = "设备名称")
	@Column(name = "DEVICE_NAME")
	private String deviceName;

	@Mark(name = "安置机架")
	@Column(name = "PUT_RACK")
	private String putRack;

	@Mark(name = "负责人")
	@Column(name = "PRINCIPAL")
	private Integer principal;

	@Mark(name = "维护人")
	@Column(name = "MAINTAIN_PERSON")
	private Integer maintainPerson;

	@Mark(name = "创建人")
	@Column(name = "CREATE_PERSON_ID")
	private Integer createPersonId;

	@Mark(name = "创建时间")
	@Column(name = "CREATE_TIME")
	private Timestamp createTime;

	@Mark(name = "管理单位")
	@Column(name = "MANAGE_DEPT")
	private Integer manageDept;

	@Mark(name = "状态")
	@Column(name = "STATUS")
	private Integer status;

	@Mark(name = "备注")
	@Column(name = "REMARK")
	private String remark;

	@Mark(name = "设备厂家")
	@Column(name = "MANUFACTOR")
	private Integer manufactor;

	@Mark(name = "设备型号")
	@Column(name = "DEVICE_MODEL")
	private String deviceModel;

	@Mark(name = "修改时间")
	@Column(name = "UPDATE_TIME")
	private Timestamp updateTime;

	@Mark(name = "面积")
	@Column(name = "AREA")
	private String area;

	@Mark(name = "简介")
	@Column(name = "FUNCTION")
	private String function;

	@Mark(name = "厂商序列号")
	@Column(name = "SERIES")
	private String series;

	@Mark(name = "资产号")
	@Column(name = "PROPERTY_SERIES")
	private String propertySeries;

	@Mark(name = "设备类型")
	@Column(name = "DEVICE_TYPE")
	private Integer deviceType;

	@Mark(name = "功率")
	@Column(name = "POWER")
	private String power;

	@Mark(name = "领用人")
	@Column(name = "RECEIVE")
	private Integer receive;

	@Mark(name = "占用层数")
	@Column(name = "USER_TIER")
	private String userTier;

	@Mark(name = "层数")
	@Column(name = "TIER")
	private String tier;

	@Mark(name = "高度")
	@Column(name = "HEIGHT")
	private String height;

	@Mark(name = "长度")
	@Column(name = "LENGTH")
	private String length;

	@Mark(name = "宽度")
	@Column(name = "WIDTH")
	private String width;

	@Mark(name = "修改人")
	@Column(name = "UPDATE_PERSON_ID")
	private Integer updatePersonId;

	@Mark(name = "设备编号")
	@Column(name = "DEVICE_CODE")
	private String deviceCode;

	@Mark(name = "安置时间")
	@Column(name = "LAYOUT_TIME")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date layoutTime;

	public Integer getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	public Integer getCabinetId() {
		return cabinetId;
	}

	public void setCabinetId(Integer cabinetId) {
		this.cabinetId = cabinetId;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getPutRack() {
		return putRack;
	}

	public void setPutRack(String putRack) {
		this.putRack = putRack;
	}

	public Integer getPrincipal() {
		return principal;
	}

	public void setPrincipal(Integer principal) {
		this.principal = principal;
	}

	public Integer getCreatePersonId() {
		return createPersonId;
	}

	public void setCreatePersonId(Integer createPersonId) {
		this.createPersonId = createPersonId;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Integer getManageDept() {
		return manageDept;
	}

	public void setManageDept(Integer manageDept) {
		this.manageDept = manageDept;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public String getPropertySeries() {
		return propertySeries;
	}

	public void setPropertySeries(String propertySeries) {
		this.propertySeries = propertySeries;
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

	public Integer getReceive() {
		return receive;
	}

	public void setReceive(Integer receive) {
		this.receive = receive;
	}

	public String getUserTier() {
		return userTier;
	}

	public void setUserTier(String userTier) {
		this.userTier = userTier;
	}

	public String getTier() {
		return tier;
	}

	public void setTier(String tier) {
		this.tier = tier;
	}

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

	public Integer getUpdatePersonId() {
		return updatePersonId;
	}

	public void setUpdatePersonId(Integer updatePersonId) {
		this.updatePersonId = updatePersonId;
	}

	public String getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}

	public Date getLayoutTime() {
		return layoutTime;
	}

	public void setLayoutTime(Date layoutTime) {
		this.layoutTime = layoutTime;
	}

	public Integer getMaintainPerson() {
		return maintainPerson;
	}

	public void setMaintainPerson(Integer maintainPerson) {
		this.maintainPerson = maintainPerson;
	}

	@Override
	public String toString() {
		return "Device [deviceId=" + deviceId + ", cabinetId=" + cabinetId + ", deviceName=" + deviceName + ", putRack="
				+ putRack + ", principal=" + principal + ", maintainPerson=" + maintainPerson + ", createPersonId="
				+ createPersonId + ", createTime=" + createTime + ", manageDept=" + manageDept + ", status=" + status
				+ ", remark=" + remark + ", manufactor=" + manufactor + ", deviceModel=" + deviceModel + ", updateTime="
				+ updateTime + ", area=" + area + ", function=" + function + ", series=" + series + ", propertySeries="
				+ propertySeries + ", deviceType=" + deviceType + ", power=" + power + ", receive=" + receive
				+ ", userTier=" + userTier + ", tier=" + tier + ", height=" + height + ", length=" + length + ", width="
				+ width + ", updatePersonId=" + updatePersonId + ", deviceCode=" + deviceCode + ", layoutTime="
				+ layoutTime + "]";
	}

}