package com.ncu.springboot.provider.message.queue;

import java.util.List;

//电子邮件消息体
public class EmailMessageBody extends MessageBody {

	//消息id
	private Integer id;
	
	// 发件人
	private String from;

	// 收件人
	private String to;

	// 邮件主题
	private String subject;

	// 邮件内容
	private String content;

	// 附件
	private List<String> attachements;

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

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

	public List<String> getAttachements() {
		return attachements;
	}

	public void setAttachements(List<String> attachements) {
		this.attachements = attachements;
	}

	@Override
	public String toString() {
		return "EmailMessageBody [id=" + id + ", from=" + from + ", to=" + to + ", subject=" + subject + ", content="
				+ content + ", attachements=" + attachements + "]";
	}

}
