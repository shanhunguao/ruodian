package com.ncu.springboot.api.control.entity;
import javax.persistence.Column;
import javax.persistence.Table;

import com.ncu.springboot.core.annotation.Mark;

import java.io.Serializable;
import javax.persistence.Id;
import java.lang.String;
import java.lang.Integer;
import java.sql.Timestamp;

@Table(name = "zongzhi_work_column")
public class WorkColumn implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Mark(name = "编号")
	@Column(name = "id")
	private Integer id;

	@Column(name = "name")
	@Mark(name = "栏目名称")
	private String name;

	@Column(name = "create_persion_id")
	@Mark(name = "创建者")
	private Integer createPersionId;

	@Column(name = "create_time")
	@Mark(name = "创建时间")
	private Timestamp createTime;

	@Column(name = "update_persion_id")
	@Mark(name = "更新者")
	private Integer updatePersionId;

	@Column(name = "update_time")
	@Mark(name = "更新时间")
	private Timestamp updateTime;

	@Column(name = "remarks")
	@Mark(name = "备注信息")
	private String remarks;

	@Column(name = "del_flag")
	@Mark(name = "删除标记")
	private Integer delFlag;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCreatePersionId() {
		return createPersionId;
	}

	public void setCreatePersionId(Integer createPersionId) {
		this.createPersionId = createPersionId;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Integer getUpdatePersionId() {
		return updatePersionId;
	}

	public void setUpdatePersionId(Integer updatePersionId) {
		this.updatePersionId = updatePersionId;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	@Override
	public String toString() {
		return "WorkColumn [id=" + id + ", name=" + name + ", createPersionId=" + createPersionId + ", createTime="
				+ createTime + ", updatePersionId=" + updatePersionId + ", updateTime=" + updateTime + ", remarks="
				+ remarks + ", delFlag=" + delFlag + "]";
	}

}