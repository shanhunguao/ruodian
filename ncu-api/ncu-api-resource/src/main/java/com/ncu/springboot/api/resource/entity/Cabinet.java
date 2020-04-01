package com.ncu.springboot.api.resource.entity;
import javax.persistence.Column;
import javax.persistence.Table;

import com.ncu.springboot.core.annotation.Mark;

import java.io.Serializable;
import javax.persistence.Id;
import java.lang.String;
import java.lang.Integer;
import java.sql.Timestamp;

@Table(name = "tb_cabinet")
public class Cabinet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Mark(name = "机柜id")
	@Column(name = "CABINET_ID")
	private Integer cabinetId;

	@Mark(name = "房间id")
	@Column(name = "ROOM_ID")
	private Integer roomId;

	@Mark(name = "高度")
	@Column(name = "HEIGHT")
	private String height;

	@Mark(name = "负责人")
	@Column(name = "PRINCIPAL")
	private Integer principal;

	@Mark(name = "创建人id")
	@Column(name = "CREATE_PERSON_ID")
	private Integer createPersonId;

	@Mark(name = "维修人")
	@Column(name = "MAINTAIN_PERSON")
	private Integer maintainPerson;

	@Mark(name = "管理单位")
	@Column(name = "MANAGE_DEPT")
	private Integer manageDept;

	@Mark(name = "状态")
	@Column(name = "STATUS")
	private Integer status;

	@Mark(name = "备注")
	@Column(name = "REMARK")
	private String remark;

	@Mark(name = "使用单位")
	@Column(name = "USE_DEPT")
	private Integer useDept;

	@Mark(name = "厂商")
	@Column(name = "MANUFACTOR")
	private Integer manufactor;

	@Mark(name = "机柜型号")
	@Column(name = "CABINET_MODEL")
	private String cabinetModel;

	@Mark(name = "机柜名称")
	@Column(name = "CABINET_NAME")
	private String cabinetName;

	@Mark(name = "修改时间")
	@Column(name = "UPDATE_TIME")
	private Timestamp updateTime;

	@Mark(name = "创建时间")
	@Column(name = "CREATE_TIME")
	private Timestamp createTime;

	@Mark(name = "长度")
	@Column(name = "LENGTH")
	private String length;

	@Mark(name = "宽度")
	@Column(name = "WIDTH")
	private String width;

	@Mark(name = "简介")
	@Column(name = "FUNCTION")
	private String function;

	@Mark(name = "面积")
	@Column(name = "AREA")
	private String area;

	@Mark(name = "定位")
	@Column(name = "LOCATION")
	private String location;

	@Mark(name = "领用人")
	@Column(name = "RECEIVE")
	private Integer receive;

	@Mark(name = "厂商序列号")
	@Column(name = "SERIES")
	private String series;

	@Mark(name = "资产号")
	@Column(name = "PROPERTY_SERIES")
	private String propertySeries;

	@Mark(name = "层数")
	@Column(name = "TIER")
	private Integer tier;

	@Mark(name = "修改人id")
	@Column(name = "UPDATE_PERSON_ID")
	private Integer updatePersonId;
	
	@Mark(name = "机柜编号")
	@Column(name = "CABINET_CODE")
	private String cabinetCode;
	
	@Mark(name = "机柜类型")
	@Column(name = "CABINET_TYPE")
	private Integer cabinetType;
	
	public Integer getCabinetType() {
		return cabinetType;
	}

	public void setCabinetType(Integer cabinetType) {
		this.cabinetType = cabinetType;
	}

	public String getCabinetCode() {
		return cabinetCode;
	}

	public void setCabinetCode(String cabinetCode) {
		this.cabinetCode = cabinetCode;
	}

	public Integer getCabinetId() {
		return cabinetId;
	}

	public void setCabinetId(Integer cabinetId) {
		this.cabinetId = cabinetId;
	}

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
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

	public Integer getMaintainPerson() {
		return maintainPerson;
	}

	public void setMaintainPerson(Integer maintainPerson) {
		this.maintainPerson = maintainPerson;
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

	public Integer getUseDept() {
		return useDept;
	}

	public void setUseDept(Integer useDept) {
		this.useDept = useDept;
	}

	public Integer getManufactor() {
		return manufactor;
	}

	public void setManufactor(Integer manufactor) {
		this.manufactor = manufactor;
	}

	public String getCabinetModel() {
		return cabinetModel;
	}

	public void setCabinetModel(String cabinetModel) {
		this.cabinetModel = cabinetModel;
	}

	public String getCabinetName() {
		return cabinetName;
	}

	public void setCabinetName(String cabinetName) {
		this.cabinetName = cabinetName;
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

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Integer getReceive() {
		return receive;
	}

	public void setReceive(Integer receive) {
		this.receive = receive;
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

	public Integer getTier() {
		return tier;
	}

	public void setTier(Integer tier) {
		this.tier = tier;
	}

	public Integer getUpdatePersonId() {
		return updatePersonId;
	}

	public void setUpdatePersonId(Integer updatePersonId) {
		this.updatePersonId = updatePersonId;
	}

	@Override
	public String toString() {
		return "Cabinet [cabinetId=" + cabinetId + ", roomId=" + roomId + ", height=" + height + ", principal="
				+ principal + ", createPersonId=" + createPersonId + ", maintainPerson=" + maintainPerson
				+ ", manageDept=" + manageDept + ", status=" + status + ", remark=" + remark + ", useDept=" + useDept
				+ ", manufactor=" + manufactor + ", cabinetModel=" + cabinetModel + ", cabinetName=" + cabinetName
				+ ", updateTime=" + updateTime + ", createTime=" + createTime + ", length=" + length + ", width="
				+ width + ", function=" + function + ", area=" + area + ", location=" + location + ", receive="
				+ receive + ", series=" + series + ", propertySeries=" + propertySeries + ", tier=" + tier
				+ ", updatePersonId=" + updatePersonId + ", cabinetCode=" + cabinetCode + ", cabinetType=" + cabinetType
				+ "]";
	}

}