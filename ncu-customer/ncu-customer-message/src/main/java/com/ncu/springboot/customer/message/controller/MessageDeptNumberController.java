package com.ncu.springboot.customer.message.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ncu.springboot.api.message.bizservice.MessageDeptNumberBizService;
import com.ncu.springboot.api.message.entity.MessageDeptNumber;
import com.ncu.springboot.biz.rs.Res;
import com.ncu.springboot.common.util.Utils;

@RestController
@RequestMapping("/deptNumber")
public class MessageDeptNumberController {

	@Reference
	private MessageDeptNumberBizService messageDeptNumberBizService;{
		
	}

	@RequestMapping("/getTotal")
	public Res getTotal(Integer deptId,String deptName) {
		Integer data = messageDeptNumberBizService.getTotal(deptId, deptName);
		return Res.SUCCESS(data);
	}

	@RequestMapping("/queryList")
	public Res queryList(Integer deptId,String deptName,Integer size,Integer num) {
		List<Map<String, Object>> data = messageDeptNumberBizService.queryList(deptId, deptName, size, num);
		return Res.SUCCESS(data);
	}

	@RequestMapping("/delDeptNumber")
	public Res delDeptNumber(List<Integer> ids) {
		if (ids.size()<1||ids==null) {
			return Res.ERROR("未选择对象!");
		}
		Integer data = messageDeptNumberBizService.delDeptNumber(ids);
		return Res.SUCCESS(data);
	}

	@RequestMapping("/addDeptNumber")
	public Res addDeptNumber(MessageDeptNumber messageDeptNumber) {
		messageDeptNumber.setCreateTime(Utils.getTimeStamp());
		Integer data = messageDeptNumberBizService.addDeptNumber(messageDeptNumber);
		return Res.SUCCESS(data);
	}

	@RequestMapping("/editDeptNumber")
	public Res editDeptNumber(MessageDeptNumber messageDeptNumber) {
		messageDeptNumber.setUpdateTime(Utils.getTimeStamp());
		Integer data = messageDeptNumberBizService.editDeptNumber(messageDeptNumber);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/query")
	public Res query(Integer id) {
		MessageDeptNumber messageDeptNumber = messageDeptNumberBizService.query(id);
		return Res.SUCCESS(messageDeptNumber);
	}
	
	@RequestMapping("/addNumber")
	public Res addNumber(Integer id,Integer number) {
		if(id == null || number == null || number == 0) {
			return Res.ERROR("数据错误！");
		}
		Integer data = messageDeptNumberBizService.addNumber(id, number);
		if (data == 0) {
			return Res.ERROR("部门不存在或数据错误！");
		}
		return Res.SUCCESS(data);
	}
}
