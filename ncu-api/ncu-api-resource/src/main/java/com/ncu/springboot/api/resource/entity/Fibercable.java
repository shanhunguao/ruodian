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
import java.util.List;

@Table(name = "tb_fibercable")
public class Fibercable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Mark(name = "光缆id")
	@Column(name = "FIBERCABLE_ID")
	private Integer fibercableId;

	@Mark(name = "光缆名称")
	@Column(name = "FIBERCABLE_NAME")
	private String fibercableName;

	@Mark(name = "光缆类型")
	@Column(name = "FIBERCABLE_TYPE")
	private Integer fibercableType;

	@Mark(name = "线芯数")
	@Column(name = "TOTAL_CORE_NUM")
	private Integer totalCoreNum;

	@Mark(name = "光缆编号")
	@Column(name = "FIBERCABLE_CODE")
	private String fibercableCode;

	@Mark(name = "安置时间")
	@Column(name = "LAYOUT_TIME")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date layoutTime;

	@Mark(name = "负责人")
	@Column(name = "PRINCIPAL")
	private Integer principal;

	@Mark(name = "创建时间")
	@Column(name = "CREATE_TIME")
	private Timestamp createTime;

	@Mark(name = "创建人")
	@Column(name = "CREATE_PERSON_ID")
	private Integer createPersonId;

	@Mark(name = "维护人")
	@Column(name = "MAINTAIN_PERSON")
	private Integer maintainPerson;

	@Mark(name = "管理单位")
	@Column(name = "MANAGE_DEPT")
	private Integer manageDept;

	@Mark(name = "状态")
	@Column(name = "STATUS")
	private Integer status;

	@Mark(name = "园区id")
	@Column(name = "CAMPUS_ID")
	private Integer campusId;

	@Mark(name = "修改时间")
	@Column(name = "UPDATE_TIME")
	private Timestamp updateTime;

	@Mark(name = "简介")
	@Column(name = "FUNCTION")
	private String function;

	@Mark(name = "使用单位")
	@Column(name = "USE_DEPT")
	private Integer useDept;

	@Mark(name = "起始井口")
	@Column(name = "ORIGIN_WELLHEAD")
	private Integer originWellhead;

	@Mark(name = "终点井口")
	@Column(name = "END_WELLHEAD")
	private Integer endWellhead;

	@Mark(name = "修改人")
	@Column(name = "UPDATE_PERSON_ID")
	private Integer updatePersonId;

	@Mark(name = "父级光缆")
	@Column(name = "PARENT_ID")
	private Integer parentId;

	@Mark(name = "长度")
	@Column(name = "LENGTH")
	private String length;
	
	private List<Pipeline> pipelines;
	
	public List<Pipeline> getPipelines() {
		return pipelines;
	}

	public void setPipelines(List<Pipeline> pipelines) {
		this.pipelines = pipelines;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getFibercableId() {
		return fibercableId;
	}

	public void setFibercableId(Integer fibercableId) {
		this.fibercableId = fibercableId;
	}

	public String getFibercableName() {
		return fibercableName;
	}

	public void setFibercableName(String fibercableName) {
		this.fibercableName = fibercableName;
	}

	public Integer getFibercableType() {
		return fibercableType;
	}

	public void setFibercableType(Integer fibercableType) {
		this.fibercableType = fibercableType;
	}

	public Integer getTotalCoreNum() {
		return totalCoreNum;
	}

	public void setTotalCoreNum(Integer totalCoreNum) {
		this.totalCoreNum = totalCoreNum;
	}

	public String getFibercableCode() {
		return fibercableCode;
	}

	public void setFibercableCode(String fibercableCode) {
		this.fibercableCode = fibercableCode;
	}

	public Date getLayoutTime() {
		return layoutTime;
	}

	public void setLayoutTime(Date layoutTime) {
		this.layoutTime = layoutTime;
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

	public Integer getCampusId() {
		return campusId;
	}

	public void setCampusId(Integer campusId) {
		this.campusId = campusId;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public Integer getUseDept() {
		return useDept;
	}

	public void setUseDept(Integer useDept) {
		this.useDept = useDept;
	}

	public Integer getOriginWellhead() {
		return originWellhead;
	}

	public void setOriginWellhead(Integer originWellhead) {
		this.originWellhead = originWellhead;
	}

	public Integer getEndWellhead() {
		return endWellhead;
	}

	public void setEndWellhead(Integer endWellhead) {
		this.endWellhead = endWellhead;
	}

	public Integer getUpdatePersonId() {
		return updatePersonId;
	}

	public void setUpdatePersonId(Integer updatePersonId) {
		this.updatePersonId = updatePersonId;
	}

	public Integer getPrincipal() {
		return principal;
	}

	public void setPrincipal(Integer principal) {
		this.principal = principal;
	}

	@Override
	public String toString() {
		return "Fibercable [fibercableId=" + fibercableId + ", fibercableName=" + fibercableName + ", fibercableType="
				+ fibercableType + ", totalCoreNum=" + totalCoreNum + ", fibercableCode=" + fibercableCode
				+ ", layoutTime=" + layoutTime + ", principal=" + principal + ", createTime=" + createTime
				+ ", createPersonId=" + createPersonId + ", maintainPerson=" + maintainPerson + ", manageDept="
				+ manageDept + ", status=" + status + ", campusId=" + campusId + ", updateTime=" + updateTime
				+ ", function=" + function + ", useDept=" + useDept + ", originWellhead=" + originWellhead
				+ ", endWellhead=" + endWellhead + ", updatePersonId=" + updatePersonId + ", parentId=" + parentId
				+ ", length=" + length + ", pipelines=" + pipelines + "]";
	}
}