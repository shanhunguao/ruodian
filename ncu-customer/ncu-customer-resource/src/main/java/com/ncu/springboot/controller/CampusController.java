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
import com.ncu.springboot.api.resource.bizservice.CampusBizService;
import com.ncu.springboot.api.resource.bizservice.CodeBizService;
import com.ncu.springboot.api.resource.constant.CampusConstant;
import com.ncu.springboot.api.resource.entity.Campus;
import com.ncu.springboot.common.util.ExcelUtil;
import com.ncu.springboot.common.util.Utils;
import com.ncu.springboot.log.LogUtil;
import com.ncu.springboot.biz.rs.Res;
@RestController
@RequestMapping("/campus")
public class CampusController {

	@Reference
	private CampusBizService campusBizService;
	
	@Reference
	private CodeBizService codeBizService;
	
	@Reference
	private UserCacheBizService userCacheBizService;
	
	@Autowired
	private LogUtil<Campus> logUtil;
	
	@RequestMapping("/getTotal")
	@Permission
	public Res getTotal(String campusCode,Integer manageDept,String campusName,Integer status) {
		Integer data = campusBizService.getTotal(campusCode,manageDept, campusName, status);
		return Res.SUCCESS(data);
	}

	@RequestMapping("/queryList")
	@Permission
	public Res queryList(String campusCode,Integer manageDept,String campusName,Integer status,Integer num,Integer size) {
		List<Map<String, Object>> data = campusBizService.queryList(campusCode,manageDept, campusName, status, num, size);
		return Res.SUCCESS(data);
	}

	@RequestMapping("/addCampus")
	@Permission
	public Res addCampus(Campus campus) {
		campus.setCreateTime(Utils.getTimeStamp());
		campus.setCampusCode("YQ"+Utils.getCodeByUUId());
		Integer data = campusBizService.addCampus(campus);
		logUtil.saveLog("add", "添加园区", campus, null);
		return Res.SUCCESS(data);
	}

	@RequestMapping("/delCampus")
	@Permission
	public Res delCampus(Integer[] campusIds) {
		if(campusIds == null || campusIds.length<1) {
			return Res.ERROR("1", "未选择对象！");
		}
		Integer data = campusBizService.delCampus(campusIds);
		logUtil.saveLog("delete", "删除园区", null, campusIds);
		return Res.SUCCESS(data);
	}

	@RequestMapping("/editCampus")
	@Permission
	public Res editCampus(Campus campus) {
		campus.setUpdateTime(Utils.getTimeStamp());
		Integer data = campusBizService.editCampus(campus);
		logUtil.saveLog("edit", "编辑园区", campus, null);
		return Res.SUCCESS(data);
	}

	@RequestMapping("/location")
	@Permission
	public Res location(Integer campusId,String campusCode,Integer manageDept) {
		Map<String, Object> data = campusBizService.location(campusId,campusCode,manageDept);
		return Res.SUCCESS(data);
	}

	@RequestMapping("/query")
	@Permission
	public Res query(Integer campusId,String campusCode) {
		Map<String, Object> data = campusBizService.query(campusId,campusCode);
		return Res.SUCCESS(data);
	}

	@RequestMapping("/importExample")
	public void importExample(HttpServletResponse response) {
		List<Map<String, Object>> datas = new ArrayList<Map<String,Object>>();
		Map<String, Object> data = new HashMap<String, Object>();
		
		//状态
		List<Map<String, Object>> status = codeBizService.query(CampusConstant.STATUS, null);
		StringBuffer value = new StringBuffer();
		for (Map<String, Object> map : status) {
			value.append(map.get("option").toString());
		}
		data.put("STATUS", value);
		datas.add(data);
		
		List<String> columns = new ArrayList<String>();
		columns.add("园区名称");
		columns.add("状态");
		columns.add("备注");
		columns.add("简介");
		columns.add("面积");
		columns.add("经度");
		columns.add("纬度");
		columns.add("负责人");
		columns.add("使用单位");
		columns.add("管理单位");
		
		List<String> keys = new ArrayList<String>();
		keys.add("CAMPUS_NAME");
		keys.add("STATUS");
		keys.add("REMARK");
		keys.add("FUNCTION");
		keys.add("AREA");
		keys.add("LONGITUDE");
		keys.add("LATITUDE");
		keys.add("PRINCIPAL_NAME");
		keys.add("USE_DEPT_NAME");
		keys.add("MANAGE_DEPT_NAME");
		
		try {
			ExcelUtil.export(datas, "园区导入模板", columns, response, keys,"campusExample");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/export")
	public void export(HttpServletResponse response,String campusCode,Integer manageDept,String campusName,Integer status) {
		List<Map<String, Object>> datas = campusBizService.export(campusCode,manageDept, campusName, status);
		List<String> columns = new ArrayList<String>();
		columns.add("园区名称");
		columns.add("创建时间");
		columns.add("状态");
		columns.add("备注");
		columns.add("简介");
		columns.add("面积");
		columns.add("经度");
		columns.add("纬度");
		columns.add("修改时间");
		columns.add("园区编号");
		columns.add("负责人");
		columns.add("负责人电话");
		columns.add("创建人");
		columns.add("修改人");
		columns.add("使用单位");
		columns.add("管理单位");
		
		List<String> keys = new ArrayList<String>();
		keys.add("CAMPUS_NAME");
		keys.add("CREATE_TIME");
		keys.add("STATUS");
		keys.add("REMARK");
		keys.add("FUNCTION");
		keys.add("AREA");
		keys.add("LONGITUDE");
		keys.add("LATITUDE");
		keys.add("UPDATE_TIME");
		keys.add("CAMPUS_CODE");
		keys.add("PRINCIPAL_NAME");
		keys.add("PRINCIPAL_MOBILE");
		keys.add("CREATE_PERSON_NAME");
		keys.add("UPDATE_PERSON_NAME");
		keys.add("USE_DEPT_NAME");
		keys.add("MANAGE_DEPT_NAME");
		
		try {
			ExcelUtil.export(datas, "园区", columns, response, keys,"campus");
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
        keys.add("CAMPUS_NAME");
		keys.add("STATUS");
		keys.add("REMARK");
		keys.add("FUNCTION");
		keys.add("AREA");
		keys.add("LONGITUDE");
		keys.add("LATITUDE");
		keys.add("PRINCIPAL_NAME");
		keys.add("USE_DEPT_NAME");
		keys.add("MANAGE_DEPT_NAME");
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
		Res result = campusBizService.importExcel(datas,employeeId,CampusConstant.STATUS);
		return result;
	}
}
