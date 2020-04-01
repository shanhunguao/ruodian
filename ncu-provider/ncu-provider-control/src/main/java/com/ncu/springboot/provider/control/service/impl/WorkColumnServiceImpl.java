package com.ncu.springboot.provider.control.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.ncu.springboot.api.control.entity.WorkColumn;
import com.ncu.springboot.dao.WorkColumnMapper;
import com.ncu.springboot.provider.control.service.WorkColumnService;

@Service
public class WorkColumnServiceImpl implements WorkColumnService {

	@Resource
	private WorkColumnMapper workColumnMapper;
	
	public Integer getTotal(String name) {
		return workColumnMapper.getTotal(name);
	}

	public List<Map<String, String>> queryList(String name, Integer size, Integer num) {
		return workColumnMapper.queryList(name, size, num);
	}

	public Integer addWorkColumn(WorkColumn workColumn) {
		return workColumnMapper.insert(workColumn);
	}

	public Integer editWorkColumn(WorkColumn workColumn) {
		return workColumnMapper.updateByPrimaryKeySelective(workColumn);
	}

	public Integer delWorkColumn(List<Integer> ids) {
		return workColumnMapper.deleteByPrimaryKey(ids);
	}

	public WorkColumn query(Integer id) {
		return workColumnMapper.selectByPrimaryKey(id);
	}

}
