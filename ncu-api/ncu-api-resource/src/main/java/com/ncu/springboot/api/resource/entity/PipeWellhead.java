package com.ncu.springboot.api.resource.entity;
import javax.persistence.Column;
import javax.persistence.Table;

import com.ncu.springboot.core.annotation.Mark;

import java.io.Serializable;
import javax.persistence.Id;
import java.lang.String;
import java.lang.Integer;
import java.sql.Timestamp;

@Table(name = "tb_pipe_wellhead")
public class PipeWellhead implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Mark(name = "井口id")
	@Column(name = "WELLHEAD_ID")
	private Integer wellheadId;

	@Mark(name = "简介")
	@Column(name = "FUNCTION")
	private String function;

	@Mark(name = "功率")
	@Column(name = "POWER")
	private String power;

	@Mark(name = "直径")
	@Column(name = "DIAMETER")
	private String diameter;

	@Mark(name = "负责人")
	@Column(name = "PRINCIPAL")
	private Integer principal;

	@Mark(name = "创建时间")
	@Column(name = "CREATE_TIME")
	private Timestamp createTime;

	@Mark(name = "创建人")
	@Column(name = "CREATE_PERSON_ID")
	private Integer createPersonId;

	@Mark(name = "经度")
	@Column(name = "LONGITUDE")
	private String longitude;

	@Mark(name = "纬度")
	@Column(name = "LATITUDE")
	private String latitude;

	@Mark(name = "井口名称")
	@Column(name = "WELLHEAD_NAME")
	private String wellheadName;

	@Mark(name = "管理单位")
	@Column(name = "MANAGE_DEPT")
	private Integer manageDept;

	@Mark(name = "备注")
	@Column(name = "REMARK")
	private String remark;

	@Mark(name = "状态")
	@Column(name = "STATUS")
	private Integer status;

	@Mark(name = "修改时间")
	@Column(name = "UPDATE_TIME")
	private Timestamp updateTime;

	@Mark(name = "井口类型")
	@Column(name = "WELLHEAD_TYPE")
	private Integer wellheadType;

	@Mark(name = "园区id")
	@Column(name = "CAMPUS_ID")
	private Integer campusId;

	@Mark(name = "深度")
	@Column(name = "DEPTH")
	private String depth;

	@Mark(name = "使用单位")
	@Column(name = "USE_DEPT")
	private Integer useDept;

	@Mark(name = "房间id")
	@Column(name = "ROOM_ID")
	private Integer roomId;

	@Mark(name = "修改人")
	@Column(name = "UPDATE_PERSON_ID")
	private Integer updatePersonId;

	@Mark(name = "井口编号")
	@Column(name = "WELLHEAD_CODE")
	private String wellheadCode;

	public String getWellheadCode() {
		return wellheadCode;
	}

	public void setWellheadCode(String wellheadCode) {
		this.wellheadCode = wellheadCode;
	}

	public Integer getWellheadId() {
		return wellheadId;
	}

	public void setWellheadId(Integer wellheadId) {
		this.wellheadId = wellheadId;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public String getPower() {
		return power;
	}

	public void setPower(String power) {
		this.power = power;
	}

	public String getDiameter() {
		return diameter;
	}

	public void setDiameter(String diameter) {
		this.diameter = diameter;
	}

	public Integer getPrincipal() {
		return principal;
	}

	public void setPrincipal(Integer principal) {
		this.principal = principal;
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

	public String getWellheadName() {
		return wellheadName;
	}

	public void setWellheadName(String wellheadName) {
		this.wellheadName = wellheadName;
	}

	public Integer getManageDept() {
		return manageDept;
	}

	public void setManageDept(Integer manageDept) {
		this.manageDept = manageDept;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getWellheadType() {
		return wellheadType;
	}

	public void setWellheadType(Integer wellheadType) {
		this.wellheadType = wellheadType;
	}

	public Integer getCampusId() {
		return campusId;
	}

	public void setCampusId(Integer campusId) {
		this.campusId = campusId;
	}

	public String getDepth() {
		return depth;
	}

	public void setDepth(String depth) {
		this.depth = depth;
	}

	public Integer getUseDept() {
		return useDept;
	}

	public void setUseDept(Integer useDept) {
		this.useDept = useDept;
	}

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public Integer getUpdatePersonId() {
		return updatePersonId;
	}

	public void setUpdatePersonId(Integer updatePersonId) {
		this.updatePersonId = updatePersonId;
	}

	@Override
	public String toString() {
		return "PipeWellhead [wellheadId=" + wellheadId + ", function=" + function + ", power=" + power + ", diameter="
				+ diameter + ", principal=" + principal + ", createTime=" + createTime + ", createPersonId="
				+ createPersonId + ", longitude=" + longitude + ", latitude=" + latitude + ", wellheadName="
				+ wellheadName + ", manageDept=" + manageDept + ", remark=" + remark + ", status=" + status
				+ ", updateTime=" + updateTime + ", wellheadType=" + wellheadType + ", campusId=" + campusId
				+ ", depth=" + depth + ", useDept=" + useDept + ", roomId=" + roomId + ", updatePersonId="
				+ updatePersonId + ", wellheadCode=" + wellheadCode + "]";
	}

}