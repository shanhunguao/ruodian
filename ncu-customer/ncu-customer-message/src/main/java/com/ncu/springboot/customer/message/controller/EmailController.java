package com.ncu.springboot.customer.message.controller;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ncu.springboot.api.message.bizservice.EmailBizService;
import com.ncu.springboot.api.message.entity.MessageSendResult;
import com.ncu.springboot.biz.rs.BaseResponseBody;
import com.ncu.springboot.biz.rs.ErrorResponseBody;
import com.ncu.springboot.biz.rs.SuccessResponseBody;
import com.ncu.springboot.core.util.DateUtil;
import com.ncu.springboot.customer.common.annotation.DateTime;
import com.ncu.springboot.customer.message.form.EmailCodeForm;
import com.ncu.springboot.customer.message.form.EmailMessageForm;
import com.ncu.springboot.customer.message.form.EmailQueryForm;

@RestController
@RequestMapping("/email")
@Validated
public class EmailController {

	@Reference(timeout = 50000, retries = 0)
	private EmailBizService emailBizService;
	
	@RequestMapping("/sendEmailCode")
	public BaseResponseBody sendEmailCode(@NotBlank(message = "邮箱地址不能为空") @Email(message = "邮箱地址格式错误") String email) {
		
		MessageSendResult result = emailBizService.sendEmailCode(email);
		if (result.getCode().equals("0")) {
			return SuccessResponseBody.getInstance().setMessage(result.getMsg());
		} else {
			return ErrorResponseBody.getInstance().setCode(result.getCode()).setMessage(result.getMsg());
		}
	}
	
	@RequestMapping("/sendEmailCodeTwo")
	public BaseResponseBody sendEmailCode_two(@Valid EmailCodeForm emailCodeForm, BindingResult bindingResult) {
		
		String email = emailCodeForm.getEmail();
		
		MessageSendResult result = emailBizService.sendEmailCode(email);
		if (result.getCode().equals("0")) {
			return SuccessResponseBody.getInstance().setMessage(result.getMsg());
		} else {
			return ErrorResponseBody.getInstance().setCode(result.getCode()).setMessage(result.getMsg());
		}
	}
	
	@RequestMapping("/queryEmail")
	public BaseResponseBody queryEmail(@Valid EmailQueryForm emailQueryForm, BindingResult bindingResult) {
		return SuccessResponseBody.getInstance().setMessage("查询成功");
	}
	
	@RequestMapping("/sendEmailMessage")
	public BaseResponseBody sendEmailMessage(@Valid EmailMessageForm emailMessageForm, BindingResult bindingResult) {
		
		MessageSendResult result = emailBizService.sendEmailMessage(emailMessageForm.getTo(), emailMessageForm.getSubject(), emailMessageForm.getContent(), 0);
		if (result.getCode().equals("0")) {
			return SuccessResponseBody.getInstance().setMessage(result.getMsg());
		} else {
			return ErrorResponseBody.getInstance().setCode(result.getCode()).setMessage(result.getMsg());
		}
	}
	
	@RequestMapping("/sendEmailMessageDelay")
	public BaseResponseBody sendEmailMessageDelay(@Valid EmailMessageForm emailMessageForm, BindingResult bindingResult, @NotBlank(message = "发送时间不能为空") @DateTime(format = "yyyy-MM-dd HH:mm:ss", message = "发送的日期格式错误") String when) {
		
		MessageSendResult result = emailBizService.sendEmailMessage(emailMessageForm.getTo(), emailMessageForm.getSubject(), emailMessageForm.getContent(), DateUtil.strToLong(when, DateUtil.YYYYMMDDHHMMSS));
		if (result.getCode().equals("0")) {
			return SuccessResponseBody.getInstance().setMessage(result.getMsg());
		} else {
			return ErrorResponseBody.getInstance().setCode(result.getCode()).setMessage(result.getMsg());
		}
	}
	
}
