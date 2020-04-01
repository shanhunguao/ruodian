package com.ncu.springboot.api.resource.entity;
import javax.persistence.Column;
import javax.persistence.Table;

import com.ncu.springboot.core.annotation.Mark;

import java.io.Serializable;
import javax.persistence.Id;
import java.lang.String;
import java.lang.Integer;
import java.sql.Timestamp;

@Table(name = "tb_line")
public class Line implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Mark(name = "线路id")
	@Column(name = "LINE_ID")
	private Integer lineId;

	@Mark(name = "线路名称")
	@Column(name = "LINE_NAME")
	private String lineName;

	@Mark(name = "负责人")
	@Column(name = "PRINCIPAL")
	private Integer principal;

	@Mark(name = "使用单位")
	@Column(name = "USE_DEPT")
	private Integer useDept;

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

	@Mark(name = "上联端口")
	@Column(name = "UP_PORT")
	private Integer upPort;

	@Mark(name = "下联端口")
	@Column(name = "DOWN_PORT")
	private Integer downPort;

	@Mark(name = "线路类型")
	@Column(name = "LINE_TYPE")
	private Integer lineType;

	@Mark(name = "备注")
	@Column(name = "REMARK")
	private String remark;

	@Mark(name = "修改时间")
	@Column(name = "UPDATE_TIME")
	private Timestamp updateTime;

	@Mark(name = "园区id")
	@Column(name = "CAMPUS_ID")
	private Integer campusId;

	@Mark(name = "光缆id")
	@Column(name = "FIBERCABLE_ID")
	private Integer fibercableId;

	@Mark(name = "线路编号")
	@Column(name = "LINE_CODE")
	private String lineCode;

	@Mark(name = "启用时间")
	@Column(name = "USE_TIME")
	private Timestamp useTime;

	@Mark(name = "修改人")
	@Column(name = "UPDATE_PERSON_ID")
	private Integer updatePersonId;

	public Integer getLineId() {
		return lineId;
	}

	public void setLineId(Integer lineId) {
		this.lineId = lineId;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public Integer getPrincipal() {
		return principal;
	}

	public void setPrincipal(Integer principal) {
		this.principal = principal;
	}

	public Integer getUseDept() {
		return useDept;
	}

	public void setUseDept(Integer useDept) {
		this.useDept = useDept;
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

	public Integer getUpPort() {
		return upPort;
	}

	public void setUpPort(Integer upPort) {
		this.upPort = upPort;
	}

	public Integer getDownPort() {
		return downPort;
	}

	public void setDownPort(Integer downPort) {
		this.downPort = downPort;
	}

	public Integer getLineType() {
		return lineType;
	}

	public void setLineType(Integer lineType) {
		this.lineType = lineType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getCampusId() {
		return campusId;
	}

	public void setCampusId(Integer campusId) {
		this.campusId = campusId;
	}

	public Integer getFibercableId() {
		return fibercableId;
	}

	public void setFibercableId(Integer fibercableId) {
		this.fibercableId = fibercableId;
	}

	public String getLineCode() {
		return lineCode;
	}

	public void setLineCode(String lineCode) {
		this.lineCode = lineCode;
	}

	public Timestamp getUseTime() {
		return useTime;
	}

	public void setUseTime(Timestamp useTime) {
		this.useTime = useTime;
	}

	public Integer getUpdatePersonId() {
		return updatePersonId;
	}

	public void setUpdatePersonId(Integer updatePersonId) {
		this.updatePersonId = updatePersonId;
	}

	@Override
	public String toString() {
		return "Line [lineId=" + lineId + ", lineName=" + lineName + ", principal=" + principal + ", useDept=" + useDept
				+ ", createPersonId=" + createPersonId + ", createTime=" + createTime + ", manageDept=" + manageDept
				+ ", status=" + status + ", upPort=" + upPort + ", downPort=" + downPort + ", lineType=" + lineType
				+ ", remark=" + remark + ", updateTime=" + updateTime + ", campusId=" + campusId + ", fibercableId="
				+ fibercableId + ", lineCode=" + lineCode + ", useTime=" + useTime + ", updatePersonId="
				+ updatePersonId + "]";
	}
	
}