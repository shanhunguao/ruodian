package com.ncu.springboot.api.message.entity;
import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import javax.persistence.Id;
import java.lang.String;
import java.lang.Integer;
import java.sql.Timestamp;

@Table(name = "tb_message")
public class TbMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "MESSAGE_ID")
	private Integer messageId;

	@Column(name = "STATUS")
	private Integer status;

	@Column(name = "MESSAGE_TYPE")
	private Integer messageType;

	@Column(name = "SUBJECT")
	private String subject;

	@Column(name = "CONTENT")
	private String content;

	@Column(name = "SEND_TIME")
	private Timestamp sendTime;

	@Column(name = "SEND_PERSON_ID")
	private String sendPersonId;

	@Column(name = "CREATE_TIME")
	private Timestamp createTime;
	
	@Column(name = "URL")
	private String url;
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getMessageId() {
		return messageId;
	}

	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getMessageType() {
		return messageType;
	}

	public void setMessageType(Integer messageType) {
		this.messageType = messageType;
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

	public Timestamp getSendTime() {
		return sendTime;
	}

	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getSendPersonId() {
		return sendPersonId;
	}

	public void setSendPersonId(String sendPersonId) {
		this.sendPersonId = sendPersonId;
	}

	@Override
	public String toString() {
		return "TbMessage [messageId=" + messageId + ", status=" + status + ", messageType=" + messageType
				+ ", subject=" + subject + ", content=" + content + ", sendTime=" + sendTime + ", sendPersonId="
				+ sendPersonId + ", createTime=" + createTime + "]";
	}
}