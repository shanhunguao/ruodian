package com.ncu.springboot.provider.message.entity;

import java.io.Serializable;

public class MailInfo implements Serializable {

	// 邮件接收人
	private String to;
	// 邮件主题
	private String subject;
	// 邮件内容
	private String content;

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
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

}
