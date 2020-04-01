package com.ncu.springboot.customer.message.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class EmailMessageForm {

	@NotBlank(message = "邮箱地址不能为空")
	@Email(message = "邮箱地址格式错误")
	private String to;

	@NotBlank(message = "邮件主题不能为空")
	private String subject;

	@NotBlank(message = "邮件内容不能为空")
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
