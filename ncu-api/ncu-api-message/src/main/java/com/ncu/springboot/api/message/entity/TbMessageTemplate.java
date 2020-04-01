package com.ncu.springboot.api.message.entity;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import javax.persistence.Id;
import java.lang.String;
import java.lang.Integer;
import java.sql.Timestamp;

@Table(name = "tb_message_template")
public class TbMessageTemplate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	private Integer id;

	@Column(name = "SUBJECT")
	private String subject;

	@Column(name = "CONTENT")
	private String content;

	@Column(name = "CREATE_PERSON_ID")
	private Integer createPersonId;

	@Column(name = "CREATE_TIME")
	private Timestamp createTime;

	@Column(name = "UPDATE_TIME")
	private Timestamp updateTime;

	@Column(name = "UPDATE_PERSON_ID")
	private Integer updatePersonId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getUpdatePersonId() {
		return updatePersonId;
	}

	public void setUpdatePersonId(Integer updatePersonId) {
		this.updatePersonId = updatePersonId;
	}

	public String toString() {
		return "MessageTemplate [id=" + id + ", subject=" + subject + ", content=" + content + ", createPersonId="
				+ createPersonId + ", createTime=" + createTime + ", updateTime=" + updateTime + ", updatePersonId="
				+ updatePersonId + "]";
	}
}