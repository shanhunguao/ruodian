package com.ncu.springboot.api.gate.entity;
import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import javax.persistence.Id;
import com.ncu.springboot.core.annotation.Mark;
import java.lang.String;
import java.lang.Integer;
import java.sql.Timestamp;

@Table(name = "gate_apply")
public class Apply implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Mark(name = "")
	@Column(name = "id")
	private Integer id;

	@Mark(name = "")
	@Column(name = "user_id")
	private String userId;

	@Mark(name = "")
	@Column(name = "card_id")
	private String cardId;

	@Mark(name = "")
	@Column(name = "create_time")
	private Timestamp createTime;

	@Mark(name = "")
	@Column(name = "update_time")
	private Timestamp updateTime;

	@Mark(name = "")
	@Column(name = "start_time")
	private Timestamp startTime;

	@Mark(name = "")
	@Column(name = "end_time")
	private Timestamp endTime;

	@Mark(name = "")
	@Column(name = "status")
	private String status;

	@Mark(name = "")
	@Column(name = "remark")
	private String remark;

	@Mark(name = "")
	@Column(name = "check_remark")
	private String checkRemark;

	@Mark(name = "")
	@Column(name = "check_persion")
	private String checkPersion;

	@Mark(name = "")
	@Column(name = "check_time")
	private Timestamp checkTime;
	
	@Column(name = "campus_id")
	private Integer campusId;
	
	@Column(name = "file_path")
	private String filePath;
	
	public Integer getCampusId() {
		return campusId;
	}

	public void setCampusId(Integer campusId) {
		this.campusId = campusId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
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

	public String getCheckRemark() {
		return checkRemark;
	}

	public void setCheckRemark(String checkRemark) {
		this.checkRemark = checkRemark;
	}

	public String getCheckPersion() {
		return checkPersion;
	}

	public void setCheckPersion(String checkPersion) {
		this.checkPersion = checkPersion;
	}

	public Timestamp getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Timestamp checkTime) {
		this.checkTime = checkTime;
	}
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String toString() {
		return "Apply [id=" + id + ", userId=" + userId + ", cardId=" + cardId + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + ", startTime=" + startTime + ", endTime=" + endTime + ", status="
				+ status + ", remark=" + remark + ", checkRemark=" + checkRemark + ", checkPersion=" + checkPersion
				+ ", checkTime=" + checkTime + ", campusId=" + campusId + ", filePath=" + filePath + "]";
	}

}