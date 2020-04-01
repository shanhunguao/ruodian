package com.ncu.springboot.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ncu.springboot.AOP.Permission;
import com.ncu.springboot.api.cache.bizservice.UserCacheBizService;
import com.ncu.springboot.api.resource.bizservice.BreakdownBizService;
import com.ncu.springboot.api.resource.bizservice.CodeBizService;
import com.ncu.springboot.api.resource.constant.BreakdownConstant;
import com.ncu.springboot.api.resource.entity.Breakdown;
import com.ncu.springboot.common.util.ExcelUtil;
import com.ncu.springboot.common.util.Utils;
import com.ncu.springboot.log.LogUtil;
import com.ncu.springboot.biz.rs.Res;

@RestController
@RequestMapping("/breakdown")
public class BreakdownController {
	
	@Reference
	private BreakdownBizService breakdownBizService;
	
	@Reference
	private CodeBizService codeBizService;
	
	@Reference
	private UserCacheBizService userCacheBizService;
	
	@Autowired
	private LogUtil<Breakdown> logUtil;

	@RequestMapping("/getTotal")
	@Permission
	public Res getTotal(String breakdownCode,Integer[] campusIds,String breakdownName,Integer status,Integer fibercableId,Integer breakdownType) {
		Integer data = breakdownBizService.getTotal(breakdownCode, campusIds, breakdownName, status, fibercableId, breakdownType);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/queryList")
	@Permission
	public Res queryList(String breakdownCode,Integer[] campusIds,String breakdownName,Integer status,Integer fibercableId,Integer breakdownType,Integer size,Integer num) {
		List<Map<String, Object>> data =breakdownBizService.queryList(breakdownCode, campusIds, breakdownName, status, fibercableId, breakdownType,size,num);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/addBreakdown")
	@Permission
	public Res addBuilding(Breakdown breakdown) {
		breakdown.setCreateTime(Utils.getTimeStamp());
		breakdown.setBreakdownCode("GZ"+Utils.getCodeByUUId());
		Integer data = breakdownBizService.addBreakdown(breakdown);
		logUtil.saveLog("add", "添加故障", breakdown, null);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/delBreakdown")
	@Permission
	public Res delBuilding(Integer[] breakdownIds) {
		if(breakdownIds == null || breakdownIds.length<1) {
			return Res.ERROR("1", "未选择对象！");
		}
		Integer data = breakdownBizService.delBreakdown(breakdownIds);
		logUtil.saveLog("deleta", "删除故障", null, breakdownIds);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/editBreakdown")
	@Permission
	public Res editBuilding(Breakdown breakdown) {
		breakdown.setUpdateTime(Utils.getTimeStamp());
		Integer data = breakdownBizService.editBreakdown(breakdown);
		logUtil.saveLog("edit", "修改故障", breakdown, null);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/location")
	@Permission
	public Res location(Integer breakdownId,Integer campusId) {
		List<Map<String, Object>> data = breakdownBizService.location(breakdownId, campusId);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/query")
	@Permission
	public Res query(Integer breakdownId,String breakdownCode) {
		Map<String, Object> data = breakdownBizService.query(breakdownId,breakdownCode);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/importExample")
	public void importExample(HttpServletResponse response) {
		List<Map<String, Object>> datas = new ArrayList<Map<String,Object>>();
		Map<String, Object> data = new HashMap<String, Object>();
		
		//故障点状态码120000
		List<Map<String, Object>> status = codeBizService.query(BreakdownConstant.STATUS, null);
		StringBuffer statusValue = new StringBuffer();
		for (Map<String, Object> map : status) {
			statusValue.append(map.get("option").toString());
		}
		data.put("STATUS", statusValue);
		
		//故障点类型121000
		List<Map<String, Object>> type = codeBizService.query(BreakdownConstant.TYPE, null);
		StringBuffer typeValue = new StringBuffer();
		for (Map<String, Object> map : type) {
			typeValue.append(map.get("option").toString());
		}
		data.put("BREAKDOWN_TYPE", typeValue);
		datas.add(data);
		
		List<String> columns = new ArrayList<String>();
		columns.add("光缆编号");
		columns.add("井口编号");
		columns.add("故障定位");
		columns.add("园区编号");
		columns.add("备注");
		columns.add("状态");
		columns.add("故障类型");
		columns.add("故障名称");
		List<String> keys = new ArrayList<String>();
		keys.add("FIBERCABLE_CODE");
		keys.add("WELLHEAD_CODE");
		keys.add("LOCATION");
		keys.add("CAMPUS_CODE");
		keys.add("REMARK");
		keys.add("STATUS");
		keys.add("BREAKDOWN_TYPE");
		keys.add("BREAKDOWN_NAME");
		try {
			ExcelUtil.export(datas, "故障点导入模板 ", columns,response,keys,"breakdownExample");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/export")
	public void export(HttpServletResponse response,String breakdownCode,Integer[] campusIds,String breakdownName,Integer status,Integer fibercableId,Integer breakdownType) {
		List<Map<String, Object>> datas = breakdownBizService.export(breakdownCode, campusIds, breakdownName, status, fibercableId, breakdownType);
		List<String> columns = new ArrayList<String>();
		columns.add("光缆编号");
		columns.add("井口编号");
		columns.add("故障定位");
		columns.add("园区名称");
		columns.add("备注");
		columns.add("状态");
		columns.add("修改时间");
		columns.add("修改人");
		columns.add("创建时间");
		columns.add("创建人");
		columns.add("故障编码");
		columns.add("故障类型");
		columns.add("故障名称");
		List<String> keys = new ArrayList<String>();
		keys.add("FIBERCABLE_CODE");
		keys.add("WELLHEAD_CODE");
		keys.add("LOCATION");
		keys.add("CAMPUS_NAME");
		keys.add("REMARK");
		keys.add("STATUS");
		keys.add("UPDATE_TIME");
		keys.add("UPDATE_PERSON_NAME");
		keys.add("CREATE_TIME");
		keys.add("CREATE_PERSON_NAME");
		keys.add("BREAKDOWN_CODE");
		keys.add("BREAKDOWN_TYPE");
		keys.add("BREAKDOWN_NAME");
		try {
			ExcelUtil.export(datas, "故障点 ", columns,response,keys,"breakdown");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/importExcel")
	public Res importExcel(MultipartFile excelFile,String userCode) {
		String name = excelFile.getOriginalFilename();
		
        if (name.length() < 5 || !name.substring(name.length() - 4).equals(".xls")) {
        	return Res.ERROR("文件格式错误");
        }
        List<String> keys = new ArrayList<String>();
		keys.add("FIBERCABLE_CODE");
		keys.add("WELLHEAD_CODE");
		keys.add("LOCATION");
		keys.add("CAMPUS_CODE");
		keys.add("REMARK");
		keys.add("STATUS");
		keys.add("BREAKDOWN_TYPE");
		keys.add("BREAKDOWN_NAME");
        // 业务逻辑，通过excelFile.getInputStream()，处理Excel文件
		List<Map<String, String>> datas = new ArrayList<Map<String,String>>();
		try {
			datas = ExcelUtil.excelToData(excelFile.getInputStream(), keys);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Integer employeeId = null;
		try {
//			employeeId = Integer.parseInt(userCacheBizService.getUserInfoByUsercode(userCode).getEmployee().getEmployeeId());
			employeeId = Integer.parseInt(userCacheBizService.getUserInfoByUsercode(userCode).getEmployee().getEmployeeId());
		} catch (Exception e) {
			e.printStackTrace();
			return Res.ERROR("用户信息出错，请尝试重新登录");
		}
		
		Res result = breakdownBizService.importExcel(datas,employeeId,BreakdownConstant.STATUS,BreakdownConstant.TYPE);
		return result;
	}
}
