package com.ncu.springboot.api.message.entity;
import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import javax.persistence.Id;
import java.lang.String;
import java.lang.Integer;

@Table(name = "tb_message_to")
public class TbMessageTo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	private Integer id;

	@Column(name = "MESSAGE_ID")
	private Integer messageId;

	@Column(name = "OBJECT_ID")
	private Integer objectId;

	@Column(name = "RECEIVE_TO")
	private String receiveTo;

	@Column(name = "STATUS")
	private Integer status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMessageId() {
		return messageId;
	}

	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}

	public Integer getObjectId() {
		return objectId;
	}

	public void setObjectId(Integer objectId) {
		this.objectId = objectId;
	}

	public String getReceiveTo() {
		return receiveTo;
	}

	public void setReceiveTo(String receiveTo) {
		this.receiveTo = receiveTo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "TbMessageTo [id=" + id + ", messageId=" + messageId + ", objectId=" + objectId + ", receiveTo="
				+ receiveTo + ", status=" + status + "]";
	}
}