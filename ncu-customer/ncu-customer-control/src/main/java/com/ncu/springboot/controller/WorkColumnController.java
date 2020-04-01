package com.ncu.springboot.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ncu.springboot.api.control.bizservice.WorkColumnBizService;
import com.ncu.springboot.api.control.entity.WorkColumn;
import com.ncu.springboot.biz.rs.Res;
import com.ncu.springboot.common.util.Utils;

@RestController
@RequestMapping("/workColumn")
public class WorkColumnController {
	
	@Reference
	private WorkColumnBizService workColumnBizService;
	
	@RequestMapping("/getTotal")
	public Res getTotal (String name) {
		Integer data = workColumnBizService.getTotal(name);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/queryList")
	public Res queryList(String name,Integer size,Integer num) {
		List<Map<String, String>> data = workColumnBizService.queryList(name,size,num);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/addWorkColumn")
	public Res addWorkColumn(WorkColumn workColumn) {
		workColumn.setCreateTime(Utils.getTimeStamp());
		Integer data = workColumnBizService.addWorkColumn(workColumn);
		return Res.SUCCESS(data);
		}
	
	@RequestMapping("/editWorkColumn")
	public Res editWorkColumn(WorkColumn workColumn) {
		workColumn.setUpdateTime(Utils.getTimeStamp());
		Integer data = workColumnBizService.editWorkColumn(workColumn);
		return Res.SUCCESS(data);
		}
	
	@RequestMapping("/delWorkColumn")
	public Res delWorkColumn(List<Integer> ids) {
		if (ids==null||ids.size()<1) {
			Res.ERROR("未选择对象！");
		}
		Integer data = workColumnBizService.delWorkColumn(ids);
		return Res.SUCCESS(data);
		
	}
	
	@RequestMapping("/query")
	public Res query(Integer id) {
		WorkColumn workColumn = workColumnBizService.query(id);
		return Res.SUCCESS(workColumn);
		
	}

}
