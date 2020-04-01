package com.ncu.springboot.api.message.entity;
import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import javax.persistence.Id;
import java.lang.String;
import java.util.List;
import java.lang.Integer;

@Table(name = "tb_message_object")
public class TbMessageObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "OBJECT_ID")
	private Integer objectId;

	@Column(name = "MESSAGE_ID")
	private Integer messageId;

	@Column(name = "RECEIVE_OBJECT")
	private String receiveObject;

	@Column(name = "RECEIVE_TYPE")
	private Integer receiveType;
	
	private List<TbMessageTo> tbMessageTos;

	public List<TbMessageTo> getTbMessageTos() {
		return tbMessageTos;
	}

	public void setTbMessageTos(List<TbMessageTo> tbMessageTos) {
		this.tbMessageTos = tbMessageTos;
	}

	public Integer getObjectId() {
		return objectId;
	}

	public void setObjectId(Integer objectId) {
		this.objectId = objectId;
	}

	public Integer getMessageId() {
		return messageId;
	}

	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}

	public String getReceiveObject() {
		return receiveObject;
	}

	public void setReceiveObject(String receiveObject) {
		this.receiveObject = receiveObject;
	}

	public Integer getReceiveType() {
		return receiveType;
	}

	public void setReceiveType(Integer receiveType) {
		this.receiveType = receiveType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "TbMessageObject [objectId=" + objectId + ", messageId=" + messageId + ", receiveObject=" + receiveObject
				+ ", receiveType=" + receiveType + ", tbMessageTos=" + tbMessageTos + "]";
	}

}