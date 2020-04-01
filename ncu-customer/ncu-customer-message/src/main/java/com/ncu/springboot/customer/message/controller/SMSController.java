package com.ncu.springboot.customer.message.controller;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ncu.springboot.api.message.bizservice.SMSBizService;
import com.ncu.springboot.api.message.entity.MessageSendResult;
import com.ncu.springboot.biz.rs.BaseResponseBody;
import com.ncu.springboot.biz.rs.ErrorResponseBody;
import com.ncu.springboot.biz.rs.SuccessResponseBody;
import com.ncu.springboot.core.util.DateUtil;
import com.ncu.springboot.customer.common.annotation.DateTime;

@RestController
@RequestMapping("/sms")
@Validated
public class SMSController {
	
	@Reference
	private SMSBizService smsBizService;

	@RequestMapping("/sendSmsCode")
	public BaseResponseBody sendSmsCode(@NotBlank(message = "手机号码不能为空") @Pattern(regexp = "^1(3|4|5|7|8)\\d{9}$", message = "手机号码格式错误") String mobile) {
		MessageSendResult result = smsBizService.sendSmsCode(mobile);
		if (result.getCode().equals("0")) {
			return SuccessResponseBody.getInstance().setMessage(result.getMsg());
		} else {
			return ErrorResponseBody.getInstance().setCode(result.getCode()).setMessage(result.getMsg());
		}
	}
	
	@RequestMapping("/sendSmsMessage")
	public BaseResponseBody sendSmsMessage(@NotBlank(message = "手机号码不能为空") @Pattern(regexp = "^1(3|4|5|7|8)\\d{9}$", message = "手机号码格式错误") String mobile, @NotBlank(message = "发送内容不能为空") String content) {
		MessageSendResult result = smsBizService.sendSmsMessage(mobile, content, 0);
		if (result.getCode().equals("0")) {
			return SuccessResponseBody.getInstance().setMessage(result.getMsg());
		} else {
			return ErrorResponseBody.getInstance().setCode(result.getCode()).setMessage(result.getMsg());
		}
	}
	
	@RequestMapping("/sendSmsMessageDelay")
	public BaseResponseBody sendSmsMessageDelay(@NotBlank(message = "手机号码不能为空") @Pattern(regexp = "^1(3|4|5|7|8)\\d{9}$", message = "手机号码格式错误") String mobile, @NotBlank(message = "发送内容不能为空") String content, @NotBlank(message = "发送时间不能为空") @DateTime(format = "yyyy-MM-dd HH:mm:ss", message = "发送的日期格式错误") String when) {
		System.out.println("when is " + DateUtil.strToLong(when, DateUtil.YYYYMMDDHHMMSS));
		MessageSendResult result = smsBizService.sendSmsMessage(mobile, content, DateUtil.strToLong(when, DateUtil.YYYYMMDDHHMMSS));
		if (result.getCode().equals("0")) {
			return SuccessResponseBody.getInstance().setMessage(result.getMsg());
		} else {
			return ErrorResponseBody.getInstance().setCode(result.getCode()).setMessage(result.getMsg());
		}
	}
	
}
