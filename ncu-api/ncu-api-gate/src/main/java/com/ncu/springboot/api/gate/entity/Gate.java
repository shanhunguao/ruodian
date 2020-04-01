package com.ncu.springboot.api.gate.entity;
import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import javax.persistence.Id;
import com.ncu.springboot.core.annotation.Mark;
import java.lang.String;
import java.lang.Integer;
import java.sql.Timestamp;

@Table(name = "gate_gate")
public class Gate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Mark(name = "")
	@Column(name = "id")
	private Integer id;

	@Mark(name = "")
	@Column(name = "gate_id")
	private String gateId;

	@Mark(name = "")
	@Column(name = "gate_name")
	private String gateName;

	@Mark(name = "")
	@Column(name = "campus_id")
	private Integer campusId;

	@Mark(name = "")
	@Column(name = "create_time")
	private Timestamp createTime;

	@Mark(name = "")
	@Column(name = "update_time")
	private Timestamp updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGateId() {
		return gateId;
	}

	public void setGateId(String gateId) {
		this.gateId = gateId;
	}
	
	public String getGateName() {
		return gateName;
	}

	public void setGateName(String gateName) {
		this.gateName = gateName;
	}

	public Integer getCampusId() {
		return campusId;
	}

	public void setCampusId(Integer campusId) {
		this.campusId = campusId;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String toString() {
		return "Gate [id=" + id + ", gateName=" + gateName + ", campusId=" + campusId + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + "]";
	}

}