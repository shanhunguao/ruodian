package com.ncu.springboot.api.gate.entity;
import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import javax.persistence.Id;
import com.ncu.springboot.core.annotation.Mark;
import java.lang.String;
import java.lang.Integer;
import java.sql.Timestamp;

@Table(name = "gate_permission")
public class Permission implements Serializable {

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
	@Column(name = "create_time")
	private Timestamp createTime;

	@Mark(name = "")
	@Column(name = "object_id")
	private String objectId;

	@Mark(name = "")
	@Column(name = "object_type")
	private Integer objectType;

	@Mark(name = "")
	@Column(name = "create_persion")
	private String createPersion;

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

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public Integer getObjectType() {
		return objectType;
	}

	public void setObjectType(Integer objectType) {
		this.objectType = objectType;
	}

	public String getCreatePersion() {
		return createPersion;
	}

	public void setCreatePersion(String createPersion) {
		this.createPersion = createPersion;
	}

	@Override
	public String toString() {
		return "Permission [id=" + id + ", userId=" + userId + ", createTime=" + createTime + ", objectId=" + objectId
				+ ", objectType=" + objectType + ", createPersion=" + createPersion + "]";
	}

}