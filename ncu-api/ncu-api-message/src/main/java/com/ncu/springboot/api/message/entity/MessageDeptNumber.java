package com.ncu.springboot.api.message.entity;
import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import javax.persistence.Id;
import com.ncu.springboot.core.annotation.Mark;
import java.lang.String;
import java.lang.Integer;
import java.sql.Timestamp;

@Table(name = "tb_message_dept_number")
public class MessageDeptNumber implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Mark(name = "主键")
	@Column(name = "id")
	private Integer id;

	@Mark(name = "部门id")
	@Column(name = "dept_id")
	private Integer deptId;

	@Mark(name = "已发送数目")
	@Column(name = "sent_number")
	private Integer sentNumber;

	@Mark(name = "发发送总数")
	@Column(name = "sum_number")
	private Integer sumNumber;

	@Mark(name = "创建时间")
	@Column(name = "create_time")
	private Timestamp createTime;

	@Mark(name = "创建人")
	@Column(name = "create_persion_id")
	private String createPersionId;

	@Mark(name = "修改时间")
	@Column(name = "update_time")
	private Timestamp updateTime;

	@Mark(name = "修改人")
	@Column(name = "update_persion_id")
	private String updatePersionId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public Integer getSentNumber() {
		return sentNumber;
	}

	public void setSentNumber(Integer sentNumber) {
		this.sentNumber = sentNumber;
	}

	public Integer getSumNumber() {
		return sumNumber;
	}

	public void setSumNumber(Integer sumNumber) {
		this.sumNumber = sumNumber;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getCreatePersionId() {
		return createPersionId;
	}

	public void setCreatePersionId(String createPersionId) {
		this.createPersionId = createPersionId;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdatePersionId() {
		return updatePersionId;
	}

	public void setUpdatePersionId(String updatePersionId) {
		this.updatePersionId = updatePersionId;
	}

	@Override
	public String toString() {
		return "MessageDeptNumber [id=" + id + ", deptId=" + deptId + ", sentNumber=" + sentNumber + ", sumNumber="
				+ sumNumber + ", createTime=" + createTime + ", createPersionId=" + createPersionId + ", updateTime="
				+ updateTime + ", updatePersionId=" + updatePersionId + "]";
	}
}