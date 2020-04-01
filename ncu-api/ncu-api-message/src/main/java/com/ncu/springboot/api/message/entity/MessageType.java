package com.ncu.springboot.api.message.entity;

public enum MessageType {

	SMS_CODE(0, "/sms/code"),
	EMAIL_CODE(1, "/email/code");

	private int type;
	private String queueName;

	MessageType(int type, String queueName) {
		this.type = type;
		this.queueName = queueName;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

}
