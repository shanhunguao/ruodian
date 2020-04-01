package com.ncu.springboot.api.gate.entity;
import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import javax.persistence.Id;
import com.ncu.springboot.core.annotation.Mark;
import java.lang.String;
import java.lang.Integer;
import java.sql.Timestamp;

@Table(name = "gate_log")
public class Log implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Mark(name = "")
	@Column(name = "id")
	private Integer id;

	@Mark(name = "")
	@Column(name = "status")
	private Integer status;

	@Mark(name = "")
	@Column(name = "temperature")
	private String temperature;

	@Mark(name = "")
	@Column(name = "user_id")
	private String userId;

	@Mark(name = "")
	@Column(name = "card_id")
	private String cardId;

	@Mark(name = "")
	@Column(name = "gate_id")
	private String gateId;

	@Mark(name = "")
	@Column(name = "guard_id")
	private String guardId;

	@Mark(name = "")
	@Column(name = "create_time")
	private Timestamp createTime;
	
	@Mark(name = "")
	@Column(name = "remark")
	private String remark;
	
	@Column(name = "ip")
	private String ip;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getGateId() {
		return gateId;
	}

	public void setGateId(String gateId) {
		this.gateId = gateId;
	}

	public String getGuardId() {
		return guardId;
	}

	public void setGuardId(String guardId) {
		this.guardId = guardId;
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

	@Override
	public String toString() {
		return "Log [id=" + id + ", status=" + status + ", temperature=" + temperature + ", userId=" + userId
				+ ", cardId=" + cardId + ", gateId=" + gateId + ", guardId=" + guardId + ", createTime=" + createTime
				+ ", remark=" + remark + ", ip=" + ip + "]";
	}
}