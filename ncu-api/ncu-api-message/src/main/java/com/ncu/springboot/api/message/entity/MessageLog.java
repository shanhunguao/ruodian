package com.ncu.springboot.api.message.entity;
import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import javax.persistence.Id;
import com.ncu.springboot.core.annotation.Mark;
import java.lang.String;
import java.lang.Integer;
import java.sql.Timestamp;

@Table(name = "tb_message_log")
public class MessageLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Mark(name = "主键")
	@Column(name = "ID")
	private Integer id;

	@Mark(name = "消息id")
	@Column(name = "MESSAGE_ID")
	private Integer messageId;

	@Mark(name = "操作")
	@Column(name = "OPERATE")
	private String operate;

	@Mark(name = "操作人")
	@Column(name = "OPERATE_PERSION_ID")
	private String operatePersionId;

	@Mark(name = "操作时间")
	@Column(name = "OPERATE_TIME")
	private Timestamp operateTime;

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

	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}

	public String getOperatePersionId() {
		return operatePersionId;
	}

	public void setOperatePersionId(String operatePersionId) {
		this.operatePersionId = operatePersionId;
	}

	public Timestamp getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Timestamp operateTime) {
		this.operateTime = operateTime;
	}

	@Override
	public String toString() {
		return "MessageLog [id=" + id + ", messageId=" + messageId + ", operate=" + operate + ", operatePersionId="
				+ operatePersionId + ", operateTime=" + operateTime + "]";
	}

}