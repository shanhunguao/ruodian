package com.ncu.springboot.api.resource.entity;
import javax.persistence.Column;
import javax.persistence.Table;

import com.ncu.springboot.core.annotation.Mark;

import java.io.Serializable;
import javax.persistence.Id;
import java.lang.String;
import java.lang.Integer;
import java.sql.Timestamp;

@Table(name = "tb_building")
public class Building implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Mark(name = "楼栋主键id")
	@Column(name = "BUILDING_ID")
	private Integer buildingId;

	@Mark(name = "楼栋名称")
	@Column(name = "BUILDING_NAME")
	private String buildingName;

	@Mark(name = "状态")
	@Column(name = "STATUS")
	private Integer status;

	@Mark(name = "备注")
	@Column(name = "REMARK")
	private String remark;

	@Mark(name = "经度")
	@Column(name = "LONGITUDE")
	private String longitude;

	@Mark(name = "纬度")
	@Column(name = "LATITUDE")
	private String latitude;

	@Mark(name = "修改时间")
	@Column(name = "UPDATE_TIME")
	private Timestamp updateTime;

	@Mark(name = "管理单位")
	@Column(name = "MANAGE_DEPT")
	private Integer manageDept;

	@Mark(name = "使用单位")
	@Column(name = "USE_DEPT")
	private Integer useDept;

	@Mark(name = "创建时间")
	@Column(name = "CREATE_TIME")
	private Timestamp createTime;

	@Mark(name = "负责人")
	@Column(name = "PRINCIPAL")
	private Integer principal;

	@Mark(name = "创建人id")
	@Column(name = "CREATE_PERSON_ID")
	private Integer createPersonId;

	@Mark(name = "简介")
	@Column(name = "FUNCTION")
	private String function;

	@Mark(name = "面积")
	@Column(name = "AERA")
	private String aera;

	@Mark(name = "楼层数")
	@Column(name = "FLOOR_NUM")
	private String floorNum;

	@Mark(name = "园区id")
	@Column(name = "CAMPUS_ID")
	private Integer campusId;

	@Mark(name = "修改人id")
	@Column(name = "UPDATE_PERSON_ID")
	private Integer updatePersonId;
	
	@Mark(name = "楼栋编号")
	@Column(name = "BUILDING_CODE")
	private String buildingCode;
	
	public String getBuildingCode() {
		return buildingCode;
	}

	public void setBuildingCode(String buildingCode) {
		this.buildingCode = buildingCode;
	}

	public Integer getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(Integer buildingId) {
		this.buildingId = buildingId;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
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

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getManageDept() {
		return manageDept;
	}

	public void setManageDept(Integer manageDept) {
		this.manageDept = manageDept;
	}

	public Integer getUseDept() {
		return useDept;
	}

	public void setUseDept(Integer useDept) {
		this.useDept = useDept;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
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

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public String getAera() {
		return aera;
	}

	public void setAera(String aera) {
		this.aera = aera;
	}

	public String getFloorNum() {
		return floorNum;
	}

	public void setFloorNum(String floorNum) {
		this.floorNum = floorNum;
	}

	public Integer getCampusId() {
		return campusId;
	}

	public void setCampusId(Integer campusId) {
		this.campusId = campusId;
	}

	public Integer getUpdatePersonId() {
		return updatePersonId;
	}

	public void setUpdatePersonId(Integer updatePersonId) {
		this.updatePersonId = updatePersonId;
	}

	@Override
	public String toString() {
		return "Building [buildingId=" + buildingId + ", buildingName=" + buildingName + ", status=" + status
				+ ", remark=" + remark + ", longitude=" + longitude + ", latitude=" + latitude + ", updateTime="
				+ updateTime + ", manageDept=" + manageDept + ", useDept=" + useDept + ", createTime=" + createTime
				+ ", principal=" + principal + ", createPersonId=" + createPersonId + ", function=" + function
				+ ", aera=" + aera + ", floorNum=" + floorNum + ", campusId=" + campusId + ", updatePersonId="
				+ updatePersonId + ", buildingCode=" + buildingCode + "]";
	}

}