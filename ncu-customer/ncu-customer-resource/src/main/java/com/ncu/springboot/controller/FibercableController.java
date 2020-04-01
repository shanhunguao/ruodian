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
import com.ncu.springboot.api.resource.bizservice.CodeBizService;
import com.ncu.springboot.api.resource.bizservice.FibercableBizService;
import com.ncu.springboot.api.resource.constant.FibercableConstant;
import com.ncu.springboot.api.resource.entity.Fibercable;
import com.ncu.springboot.common.util.ExcelUtil;
import com.ncu.springboot.common.util.Utils;
import com.ncu.springboot.log.LogUtil;
import com.ncu.springboot.biz.rs.Res;

@RestController
@RequestMapping("/fibercable")
public class FibercableController {

	@Reference
	private FibercableBizService fibercableBizService;
	
	@Reference
	private CodeBizService codeBizService;
	
	@Reference
	private UserCacheBizService userCacheBizService;
	
	@Autowired
	private LogUtil<Fibercable> logUtil;
	
	@RequestMapping("/getTotal")
	@Permission
	public Res getTotal(Integer[] campusIds,Integer originWellhead,Integer endWellhead,String fibercableName,Integer status,Integer fibercableType,String fibercableCode) {
		Integer data = fibercableBizService.getTotal(campusIds, originWellhead, endWellhead, fibercableName,status,fibercableType,fibercableCode);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/queryList")
	@Permission
	public Res queryList(Integer[] campusIds,Integer originWellhead,Integer endWellhead,String fibercableName,Integer status,Integer fibercableType,String fibercableCode,Integer size,Integer num) {
		List<Map<String, Object>> data = fibercableBizService.queryList(campusIds, originWellhead, endWellhead, fibercableName,status,fibercableType,fibercableCode, size, num);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/addFibercable")
	@Permission
	public Res addFibercable(Fibercable fibercable,Integer[] pipelineIds) {
		fibercable.setFibercableCode("GL"+Utils.getCodeByUUId());
		fibercable.setCreateTime(Utils.getTimeStamp());
		Integer data = fibercableBizService.addFibercable(fibercable,pipelineIds);
		logUtil.saveLog("add", "添加光缆", fibercable, null);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/delFibercable")
	@Permission
	public Res delFibercable(Integer[] fibercableIds) {
		if(fibercableIds == null || fibercableIds.length<1) {
			return Res.ERROR("1", "未选择对象！");
		}
		Integer data = fibercableBizService.delFibercable(fibercableIds);
		logUtil.saveLog("delete", "删除光缆", null, fibercableIds);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/editFibercable")
	@Permission
	public Res editFibercable(Fibercable fibercable,Integer[] pipelineIds) {
		fibercable.setUpdateTime(Utils.getTimeStamp());
		Integer data = fibercableBizService.editFibercable(fibercable,pipelineIds);
		logUtil.saveLog("edit", "编辑光缆", fibercable, null);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/query")
	@Permission
	public Res query(Integer fibercableId,String fibercableCode) {
		Map<String, Object> data = fibercableBizService.query(fibercableId,fibercableCode);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/location")
	@Permission
	public Res location(Integer fibercableId,String fibercableCode,Integer[] campusIds) {
		List<Map<String, Object>> data = fibercableBizService.location(fibercableId,fibercableCode,campusIds);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/importExample")
	public void importExample(HttpServletResponse response) {
		List<Map<String, Object>> datas = new ArrayList<Map<String,Object>>();
		Map<String, Object> data = new HashMap<String, Object>();
		
		//状态
		List<Map<String, Object>> status = codeBizService.query(FibercableConstant.STATUS, null);
		StringBuffer statusValue = new StringBuffer();
		for (Map<String, Object> map : status) {
			statusValue.append(map.get("option").toString());
		}
		data.put("STATUS", statusValue);
		
		//类型
		List<Map<String, Object>> type = codeBizService.query(FibercableConstant.TYPE, null);
		StringBuffer typeValue = new StringBuffer();
		for (Map<String, Object> map : type) {
			typeValue.append(map.get("option").toString());
		}
		data.put("FIBERCABLE_TYPE", typeValue);
		datas.add(data);
		
		List<String> columns = new ArrayList<String>();
		columns.add("光缆名称");
		columns.add("光缆类型");
		columns.add("线芯总数");
		columns.add("安置时间");
		columns.add("状态");
		columns.add("简介");
		columns.add("起始井口编号");
		columns.add("终点井口编号");
		columns.add("上级光缆编号");
		columns.add("长度");
		columns.add("负责人");
		columns.add("使用单位");
		columns.add("管理单位");
		columns.add("维护人");
		columns.add("园区编号");
		List<String> keys = new ArrayList<String>();
		keys.add("FIBERCABLE_NAME");
		keys.add("FIBERCABLE_TYPE");
		keys.add("TOTAL_CORE_NUM");
		keys.add("LAYOUT_TIME");
		keys.add("STATUS");
		keys.add("FUNCTION");
		keys.add("ORIGIN_WELLHEAD_CODE");
		keys.add("END_WELLHEAD_CODE");
		keys.add("PARENT_CODE");
		keys.add("LENGTH");
		keys.add("PRINCIPAL_NAME");
		keys.add("USE_DEPT_NAME");
		keys.add("MANAGE_DEPT_NAME");
		keys.add("MAINTAIN_PERSON_NAME");
		keys.add("CAMPUS_CODE");
		try {
			ExcelUtil.export(datas, "光缆导入模板 ", columns,response,keys,"fibercableExample");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/export")
	public void export(HttpServletResponse response,Integer[] campusIds,Integer originWellhead,Integer endWellhead,String fibercableName,Integer status,Integer fibercableType,String fibercableCode) {
		List<Map<String, Object>> datas = fibercableBizService.export(campusIds, originWellhead, endWellhead, fibercableName,status,fibercableType,fibercableCode);
		List<String> columns = new ArrayList<String>();
		columns.add("光缆编号");
		columns.add("光缆名称");
		columns.add("光缆类型");
		columns.add("线芯总数");
		columns.add("安置时间");
		columns.add("创建时间");
		columns.add("状态");
		columns.add("修改时间");
		columns.add("简介");
		columns.add("起始井口编号");
		columns.add("终点井口编号");
		columns.add("上级光缆编号");
		columns.add("长度");
		columns.add("负责人");
		columns.add("负责人电话");
		columns.add("创建人");
		columns.add("修改人");
		columns.add("使用单位");
		columns.add("管理单位");
		columns.add("维护人");
		columns.add("维护人电话");
		columns.add("园区名称");
		List<String> keys = new ArrayList<String>();
		keys.add("FIBERCABLE_CODE");
		keys.add("FIBERCABLE_NAME");
		keys.add("FIBERCABLE_TYPE");
		keys.add("TOTAL_CORE_NUM");
		keys.add("LAYOUT_TIME");
		keys.add("CREATE_TIME");
		keys.add("STATUS");
		keys.add("UPDATE_TIME");
		keys.add("FUNCTION");
		keys.add("ORIGIN_WELLHEAD_CODE");
		keys.add("END_WELLHEAD_CODE");
		keys.add("PARENT_CODE");
		keys.add("LENGTH");
		keys.add("PRINCIPAL_NAME");
		keys.add("PRINCIPAL_MOBILE");
		keys.add("CREATE_PERSON_NAME");
		keys.add("UPDATE_PERSON_NAME");
		keys.add("USE_DEPT_NAME");
		keys.add("MANAGE_DEPT_NAME");
		keys.add("MAINTAIN_PERSON_NAME");
		keys.add("MAINTAIN_PERSON_MOBILE");
		keys.add("CAMPUS_NAME");
		try {
			ExcelUtil.export(datas, "光缆  ", columns,response,keys,"fibercable");
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
        keys.add("FIBERCABLE_NAME");
		keys.add("FIBERCABLE_TYPE");
		keys.add("TOTAL_CORE_NUM");
		keys.add("LAYOUT_TIME");
		keys.add("STATUS");
		keys.add("FUNCTION");
		keys.add("ORIGIN_WELLHEAD_CODE");
		keys.add("END_WELLHEAD_CODE");
		keys.add("PARENT_CODE");
		keys.add("LENGTH");
		keys.add("PRINCIPAL_NAME");
		keys.add("USE_DEPT_NAME");
		keys.add("MANAGE_DEPT_NAME");
		keys.add("MAINTAIN_PERSON_NAME");
		keys.add("CAMPUS_CODE");
        // 业务逻辑，通过excelFile.getInputStream()，处理Excel文件
		List<Map<String, String>> datas = new ArrayList<Map<String,String>>();
		try {
			datas = ExcelUtil.excelToData(excelFile.getInputStream(), keys);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Integer employeeId = null;
		try {
			employeeId = Integer.parseInt(userCacheBizService.getUserInfoByUsercode(userCode).getEmployee().getEmployeeId());
		} catch (Exception e) {
			e.printStackTrace();
			return Res.ERROR("用户信息出错，请尝试重新登录");
		}
		
		Res result = fibercableBizService.importExcel(datas,employeeId,FibercableConstant.STATUS,FibercableConstant.TYPE);
		return result;
	}
}
