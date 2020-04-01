package com.ncu.springboot.api.resource.entity;
import javax.persistence.Column;
import javax.persistence.Table;

import com.ncu.springboot.core.annotation.Mark;

import java.io.Serializable;
import javax.persistence.Id;
import java.lang.String;
import java.lang.Integer;
import java.sql.Timestamp;

@Table(name = "tb_breakdown")
public class Breakdown implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Mark(name = "故障点id")
	@Column(name = "BREAKDOWN_ID")
	private Integer breakdownId;

	@Mark(name = "光缆id")
	@Column(name = "FIBERCABLE_ID")
	private Integer fibercableId;

	@Mark(name = "井口id")
	@Column(name = "WELLHEAD_ID")
	private Integer wellheadId;

	@Mark(name = "定位")
	@Column(name = "LOCATION")
	private String location;

	@Mark(name = "园区id")
	@Column(name = "CAMPUS_ID")
	private Integer campusId;

	@Mark(name = "备注")
	@Column(name = "REMARK")
	private String remark;

	@Mark(name = "状态")
	@Column(name = "STATUS")
	private Integer status;

	@Mark(name = "修改时间")
	@Column(name = "UPDATE_TIME")
	private Timestamp updateTime;

	@Mark(name = "修改人id")
	@Column(name = "UPDATE_PERSON_ID")
	private Integer updatePersonId;

	@Mark(name = "创建时间")
	@Column(name = "CREATE_TIME")
	private Timestamp createTime;

	@Mark(name = "创建人id")
	@Column(name = "CREATE_PERSON_ID")
	private Integer createPersonId;

	@Mark(name = "故障编号")
	@Column(name = "BREAKDOWN_CODE")
	private String breakdownCode;

	@Mark(name = "故障类型")
	@Column(name = "BREAKDOWN_TYPE")
	private Integer breakdownType;

	@Mark(name = "故障名称")
	@Column(name = "BREAKDOWN_NAME")
	private String breakdownName;

	public Integer getBreakdownId() {
		return breakdownId;
	}

	public void setBreakdownId(Integer breakdownId) {
		this.breakdownId = breakdownId;
	}

	public Integer getFibercableId() {
		return fibercableId;
	}

	public void setFibercableId(Integer fibercableId) {
		this.fibercableId = fibercableId;
	}

	public Integer getWellheadId() {
		return wellheadId;
	}

	public void setWellheadId(Integer wellheadId) {
		this.wellheadId = wellheadId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Integer getCampusId() {
		return campusId;
	}

	public void setCampusId(Integer campusId) {
		this.campusId = campusId;
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

	public Integer getUpdatePersonId() {
		return updatePersonId;
	}

	public void setUpdatePersonId(Integer updatePersonId) {
		this.updatePersonId = updatePersonId;
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

	public String getBreakdownCode() {
		return breakdownCode;
	}

	public void setBreakdownCode(String breakdownCode) {
		this.breakdownCode = breakdownCode;
	}

	public Integer getBreakdownType() {
		return breakdownType;
	}

	public void setBreakdownType(Integer breakdownType) {
		this.breakdownType = breakdownType;
	}

	public String getBreakdownName() {
		return breakdownName;
	}

	public void setBreakdownName(String breakdownName) {
		this.breakdownName = breakdownName;
	}

	@Override
	public String toString() {
		return "BreakDown [breakdownId=" + breakdownId + ", fibercableId=" + fibercableId + ", wellheadId=" + wellheadId
				+ ", location=" + location + ", campusId=" + campusId + ", remark=" + remark + ", status=" + status
				+ ", updateTime=" + updateTime + ", updatePersonId=" + updatePersonId + ", createTime=" + createTime
				+ ", createPersonId=" + createPersonId + ", breakdownCode=" + breakdownCode + ", breakdownType="
				+ breakdownType + ", breakdownName=" + breakdownName + "]";
	}

}