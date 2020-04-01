package com.ncu.springboot.provider.control.bizservice;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.osgi.service.component.annotations.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.ncu.springboot.api.control.bizservice.WorkColumnBizService;
import com.ncu.springboot.api.control.entity.WorkColumn;
import com.ncu.springboot.provider.control.service.WorkColumnService;

@Component
@Service
public class WorkColumnBizServiceImpl implements WorkColumnBizService {
	
	@Resource
	private WorkColumnService workColumnService;

	public Integer getTotal(String name) {
		return workColumnService.getTotal(name);
	}

	public List<Map<String, String>> queryList(String name,Integer size,Integer num) {
		return workColumnService.queryList(name,size,num);
	}

	@Transactional
	public Integer addWorkColumn(WorkColumn workColumn) {
		return workColumnService.addWorkColumn(workColumn);
	}

	@Transactional
	public Integer editWorkColumn(WorkColumn workColumn) {
		return workColumnService.editWorkColumn(workColumn);
	}

	@Transactional
	public Integer delWorkColumn(List<Integer> ids) {
		return workColumnService.delWorkColumn(ids);
	}

	public WorkColumn query(Integer id) {
		return workColumnService.query(id);
	}

}
