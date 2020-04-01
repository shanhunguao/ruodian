package com.ncu.springboot.api.resource.entity;

import javax.persistence.Column;
import javax.persistence.Table;

import com.ncu.springboot.core.annotation.Mark;

import java.io.Serializable;
import javax.persistence.Id;
import java.lang.String;
import java.lang.Integer;

@Table(name = "tb_code")
public class Code implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Mark(name = "代码id")
	@Column(name = "CODE_ID")
	private Integer codeId;

	@Mark(name = "属性")
	@Column(name = "OPTION")
	private String option;

	@Mark(name = "状态")
	@Column(name = "STATUS")
	private String status;

	@Mark(name = "备注")
	@Column(name = "REMARK")
	private String remark;

	public Integer getCodeId() {
		return codeId;
	}

	public void setCodeId(Integer codeId) {
		this.codeId = codeId;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "Code [codeId=" + codeId + ", option=" + option + ", status=" + status + ", remark=" + remark + "]";
	}
	
}