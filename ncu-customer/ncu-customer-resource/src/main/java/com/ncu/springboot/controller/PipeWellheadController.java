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
import com.ncu.springboot.api.resource.bizservice.PipeWellheadBizService;
import com.ncu.springboot.api.resource.constant.WellheadConstant;
import com.ncu.springboot.api.resource.entity.PipeWellhead;
import com.ncu.springboot.common.util.ExcelUtil;
import com.ncu.springboot.common.util.Utils;
import com.ncu.springboot.log.LogUtil;
import com.ncu.springboot.biz.rs.Res;

@RestController
@RequestMapping("/pipeWellhead")
public class PipeWellheadController {
	
	@Reference
	private PipeWellheadBizService pipeWellheadBizService;
	
	@Reference
	private CodeBizService codeBizService;
	
	@Reference
	private UserCacheBizService userCacheBizService;
	
	@Autowired
	private LogUtil<PipeWellhead> logUtil;
	
	@RequestMapping("/getTotal")
	@Permission
	public Res getTotal(String wellheadCode,Integer[] campusIds,String wellheadType,String wellheadName,Integer status) {
		Integer data = pipeWellheadBizService.getTotal(wellheadCode,campusIds, wellheadType, wellheadName, status);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/queryList")
	@Permission
	public Res queryList(String wellheadCode,Integer[] campusIds,String wellheadType,String wellheadName,Integer status,Integer size,Integer num) {
		List<Map<String,Object>> data = pipeWellheadBizService.queryList(wellheadCode,campusIds, wellheadType, wellheadName, status, size, num);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/addWellhead")
	@Permission
	public Res addWellhead(PipeWellhead pipeWellhead) {
		pipeWellhead.setCreateTime(Utils.getTimeStamp());
		pipeWellhead.setWellheadCode("JK"+Utils.getCodeByUUId());
		Integer data = pipeWellheadBizService.addWellhead(pipeWellhead);
		logUtil.saveLog("add", "添加井口", pipeWellhead, null);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/delWellhead")
	@Permission
	public Res delWellhead(Integer[] wellheadIds) {
		if(wellheadIds == null || wellheadIds.length<1) {
			return Res.ERROR("1", "未选择对象！");
		}
		Integer data = pipeWellheadBizService.delWellhead(wellheadIds);
		logUtil.saveLog("delete", "删除井口", null, wellheadIds);
		return Res.SUCCESS(data);
		
	}
	
	@RequestMapping("/editWellhead")
	@Permission
	public Res editWellhead(PipeWellhead pipeWellhead) {
		pipeWellhead.setUpdateTime(Utils.getTimeStamp());
		Integer data = pipeWellheadBizService.editWellhead(pipeWellhead);
		logUtil.saveLog("edit", "编辑井口", pipeWellhead, null);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/location")
	@Permission
	public Res editWellhead(Integer wellheadId,String wellheadCode,Integer[] campusIds) {
		List<Map<String,Object>> data = pipeWellheadBizService.location(wellheadId,wellheadCode,campusIds);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/query")
	@Permission
	public Res query(Integer wellheadId,String wellheadCode) {
		Map<String, Object> data = pipeWellheadBizService.query(wellheadId,wellheadCode);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/importExample")
	public void importExample(HttpServletResponse response) {
		List<Map<String, Object>> datas = new ArrayList<Map<String,Object>>();
		Map<String, Object> data = new HashMap<String, Object>();
		
		//状态
		List<Map<String, Object>> status = codeBizService.query(WellheadConstant.STATUS, null);
		StringBuffer statusValue = new StringBuffer();
		for (Map<String, Object> map : status) {
			statusValue.append(map.get("option").toString());
		}
		data.put("STATUS", statusValue);
		
		//类型
		List<Map<String, Object>> type = codeBizService.query(WellheadConstant.TYPE, null);
		StringBuffer typeValue = new StringBuffer();
		for (Map<String, Object> map : type) {
			typeValue.append(map.get("option").toString());
		}
		data.put("WELLHEAD_TYPE", typeValue);
		datas.add(data);
		
		List<String> columns = new ArrayList<String>();
		columns.add("简介");
		columns.add("功率");
		columns.add("半径");
		columns.add("经度");
		columns.add("纬度");
		columns.add("井口名称");
		columns.add("备注");
		columns.add("状态");
		columns.add("井口类型");
		columns.add("深度");
		columns.add("设备间编号");
		columns.add("负责人");
		columns.add("使用单位");
		columns.add("管理单位");
		columns.add("园区编号");
		List<String> keys = new ArrayList<String>();
		keys.add("FUNCTION");
		keys.add("POWER");
		keys.add("DIAMETER");
		keys.add("LONGITUDE");
		keys.add("LATITUDE");
		keys.add("WELLHEAD_NAME");
		keys.add("REMARK");
		keys.add("STATUS");
		keys.add("WELLHEAD_TYPE");
		keys.add("DEPTH");
		keys.add("ROOM_CODE");
		keys.add("PRINCIPAL_NAME");
		keys.add("USE_DEPT_NAME");
		keys.add("MANAGE_DEPT_NAME");
		keys.add("CAMPUS_CODE");
		try {
			ExcelUtil.export(datas, "井口导入模板", columns,response,keys,"wellheadExample");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/export")
	public void export(HttpServletResponse response,String wellheadCode,Integer[] campusIds,String wellheadType,String wellheadName,Integer status) {
		List<Map<String, Object>> datas = pipeWellheadBizService.export(wellheadCode,campusIds, wellheadType, wellheadName, status);
		List<String> columns = new ArrayList<String>();
		columns.add("井口编号");
		columns.add("简介");
		columns.add("功率");
		columns.add("半径");
		columns.add("创建时间");
		columns.add("经度");
		columns.add("纬度");
		columns.add("井口名称");
		columns.add("备注");
		columns.add("状态");
		columns.add("修改时间");
		columns.add("井口类型");
		columns.add("升读");
		columns.add("设备间编号");
		columns.add("负责人");
		columns.add("负责人电话");
		columns.add("创建人");
		columns.add("修改人");
		columns.add("使用单位");
		columns.add("管理单位");
		columns.add("园区名称");
		List<String> keys = new ArrayList<String>();
		keys.add("WELLHEAD_CODE");
		keys.add("FUNCTION");
		keys.add("POWER");
		keys.add("DIAMETER");
		keys.add("CREATE_TIME");
		keys.add("LONGITUDE");
		keys.add("LATITUDE");
		keys.add("WELLHEAD_NAME");
		keys.add("REMARK");
		keys.add("STATUS");
		keys.add("UPDATE_TIME");
		keys.add("WELLHEAD_TYPE");
		keys.add("DEPTH");
		keys.add("ROOM_CODE");
		keys.add("PRINCIPAL_NAME");
		keys.add("PRINCIPAL_MOBILE");
		keys.add("CREATE_PERSON_NAME");
		keys.add("UPDATE_PERSON_NAME");
		keys.add("USE_DEPT_NAME");
		keys.add("MANAGE_DEPT_NAME");
		keys.add("CAMPUS_NAME");
		try {
			ExcelUtil.export(datas, "井口", columns,response,keys,"wellhead");
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
        keys.add("FUNCTION");
		keys.add("POWER");
		keys.add("DIAMETER");
		keys.add("LONGITUDE");
		keys.add("LATITUDE");
		keys.add("WELLHEAD_NAME");
		keys.add("REMARK");
		keys.add("STATUS");
		keys.add("WELLHEAD_TYPE");
		keys.add("DEPTH");
		keys.add("ROOM_CODE");
		keys.add("PRINCIPAL_NAME");
		keys.add("USE_DEPT_NAME");
		keys.add("MANAGE_DEPT_NAME");
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
		
		Res result = pipeWellheadBizService.importExcel(datas,employeeId,WellheadConstant.STATUS,WellheadConstant.TYPE);
		return result;
	}
}
