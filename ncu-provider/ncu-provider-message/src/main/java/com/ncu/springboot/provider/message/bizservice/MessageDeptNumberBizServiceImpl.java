package com.ncu.springboot.provider.message.bizservice;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.osgi.service.component.annotations.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.ncu.springboot.api.message.bizservice.MessageDeptNumberBizService;
import com.ncu.springboot.api.message.entity.MessageDeptNumber;
import com.ncu.springboot.provider.message.service.MessageDeptNumberService;

@Component
@Service
public class MessageDeptNumberBizServiceImpl implements MessageDeptNumberBizService {
	
	@Resource
	private MessageDeptNumberService messageDeptNumberService;

	public Integer getTotal(Integer deptId, String deptName) {
		return messageDeptNumberService.getTotal(deptId, deptName);
	}

	public List<Map<String, Object>> queryList(Integer deptId, String deptName, Integer size, Integer num) {
		if(size != null && num !=null) {
			num = (num-1)*size;
			}
		return messageDeptNumberService.queryList(deptId, deptName, size, num);
	}

	public Integer delDeptNumber(List<Integer> ids) {
		return messageDeptNumberService.delDeptNumber(ids);
	}

	public Integer addDeptNumber(MessageDeptNumber messageDeptNumber) {
		return messageDeptNumberService.addDeptNumber(messageDeptNumber);
	}

	public Integer editDeptNumber(MessageDeptNumber messageDeptNumber) {
		return messageDeptNumberService.editDeptNumber(messageDeptNumber);
	}

	public MessageDeptNumber query(Integer id) {
		return messageDeptNumberService.query(id);
	}

	public Integer addNumber(Integer id, Integer number) {
		return messageDeptNumberService.addNumber(id, number);
	}

}
