package com.ncu.springboot.api.resource.entity;
import javax.persistence.Column;
import javax.persistence.Table;

import com.ncu.springboot.core.annotation.Mark;

import java.io.Serializable;
import javax.persistence.Id;
import java.lang.String;
import java.lang.Integer;
import java.sql.Timestamp;

@Table(name = "tb_pipeline")
public class Pipeline implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Mark(name = "管道id")
	@Column(name = "PIPELINE_ID")
	private Integer pipelineId;

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

	@Mark(name = "创建人")
	@Column(name = "CREATE_PERSON_ID")
	private Integer createPersonId;

	@Mark(name = "状态")
	@Column(name = "STATUS")
	private Integer status;

	@Mark(name = "井口id2")
	@Column(name = "WELLHEAD_ID1")
	private Integer wellheadId1;

	@Mark(name = "井口id2")
	@Column(name = "WELLHEAD_ID2")
	private Integer wellheadId2;

	@Mark(name = "修改人")
	@Column(name = "UPDATE_TIME")
	private Timestamp updateTime;

	@Mark(name = "管理单位")
	@Column(name = "MANAGE_DEPT")
	private Integer manageDept;

	@Mark(name = "管道名称")
	@Column(name = "PIPELINE_NAME")
	private String pipelineName;

	@Mark(name = "创建时间")
	@Column(name = "CREATE_TIME")
	private Timestamp createTime;

	@Mark(name = "备注")
	@Column(name = "REMARK")
	private String remark;

	@Mark(name = "使用单位")
	@Column(name = "USE_DEPT")
	private Integer useDept;

	@Mark(name = "管道类型")
	@Column(name = "PIPELINE_TYPE")
	private Integer pipelineType;

	@Mark(name = "深度")
	@Column(name = "DEPTH")
	private String depth;

	@Mark(name = "修改人")
	@Column(name = "UPDATE_PERSON_ID")
	private Integer updatePersonId;

	@Mark(name = "管道编号")
	@Column(name = "PIPELINE_CODE")
	private String pipelineCode;

	@Mark(name = "长度")
	@Column(name = "LENGTH")
	private String length;
	
	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getPipelineCode() {
		return pipelineCode;
	}

	public void setPipelineCode(String pipelineCode) {
		this.pipelineCode = pipelineCode;
	}

	public Integer getPipelineId() {
		return pipelineId;
	}

	public void setPipelineId(Integer pipelineId) {
		this.pipelineId = pipelineId;
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

	public Integer getWellheadId1() {
		return wellheadId1;
	}

	public void setWellheadId1(Integer wellheadId1) {
		this.wellheadId1 = wellheadId1;
	}

	public Integer getWellheadId2() {
		return wellheadId2;
	}

	public void setWellheadId2(Integer wellheadId2) {
		this.wellheadId2 = wellheadId2;
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

	public String getPipelineName() {
		return pipelineName;
	}

	public void setPipelineName(String pipelineName) {
		this.pipelineName = pipelineName;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
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

	public Integer getPipelineType() {
		return pipelineType;
	}

	public void setPipelineType(Integer pipelineType) {
		this.pipelineType = pipelineType;
	}

	public String getDepth() {
		return depth;
	}

	public void setDepth(String depth) {
		this.depth = depth;
	}

	public Integer getUpdatePersonId() {
		return updatePersonId;
	}

	public void setUpdatePersonId(Integer updatePersonId) {
		this.updatePersonId = updatePersonId;
	}

	@Override
	public String toString() {
		return "Pipeline [pipelineId=" + pipelineId + ", function=" + function + ", power=" + power + ", diameter="
				+ diameter + ", principal=" + principal + ", createPersonId=" + createPersonId + ", status=" + status
				+ ", wellheadId1=" + wellheadId1 + ", wellheadId2=" + wellheadId2 + ", updateTime=" + updateTime
				+ ", manageDept=" + manageDept + ", pipelineName=" + pipelineName + ", createTime=" + createTime
				+ ", remark=" + remark + ", useDept=" + useDept + ", pipelineType=" + pipelineType + ", depth=" + depth
				+ ", updatePersonId=" + updatePersonId + ", pipelineCode=" + pipelineCode + ", length=" + length + "]";
	}

}