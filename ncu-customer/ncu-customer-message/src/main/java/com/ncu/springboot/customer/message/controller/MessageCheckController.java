package com.ncu.springboot.customer.message.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ncu.springboot.api.message.bizservice.MessageCheckBizService;
import com.ncu.springboot.api.message.entity.MessageCheck;
import com.ncu.springboot.api.message.entity.MessageSendResult;
import com.ncu.springboot.biz.rs.Res;

@RestController
@RequestMapping("/messageCheck")
public class MessageCheckController {
	
	@Reference
	private MessageCheckBizService messageCheckBizService;
	
	@RequestMapping("/queryList")
	public Res queryList(String employeeId,Integer deptId) {
		List<MessageCheck> data = messageCheckBizService.queryList(employeeId, deptId);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/check")
	public Res check(Integer messageId,String employeeId,boolean check) {
		if (messageId==null) {
			return Res.ERROR("未选择消息！");
		}else if (employeeId==null||"".equals(employeeId)) {
			return Res.ERROR("用户数据错误，请联系管理员！");
		}
		MessageSendResult result = messageCheckBizService.check(messageId, employeeId, check);
		if (result.getCode().equals("0")) {
			return Res.SUCCESS(result.getMsg());
		} else {
			return Res.ERROR(result.getMsg());
		}
	}
	
	@RequestMapping("/addMessageCheck")
	public Res addMessageCheck(@RequestBody List<MessageCheck> messageChecks) {
		return messageCheckBizService.addMessageCheck(messageChecks);
	}
	
	@RequestMapping("/delMessageCheck")
	public Res delMessageCheck(List<Integer> ids) {
		if (ids == null || ids.size() < 1) {
			return Res.ERROR("未选择对象！");
		}
		Integer data = messageCheckBizService.delMessageCheck(ids);
		return Res.SUCCESS(data);
	}
}
