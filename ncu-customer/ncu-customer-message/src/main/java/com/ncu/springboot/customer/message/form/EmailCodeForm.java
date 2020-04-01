package com.ncu.springboot.customer.message.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class EmailCodeForm {

	@NotBlank(message = "邮箱地址不能为空")
	@Email(message = "邮箱地址格式错误")
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
