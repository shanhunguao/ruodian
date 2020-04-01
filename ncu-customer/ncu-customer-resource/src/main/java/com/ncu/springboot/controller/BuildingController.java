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
import com.ncu.springboot.api.resource.bizservice.BuildingBizService;
import com.ncu.springboot.api.resource.bizservice.CodeBizService;
import com.ncu.springboot.api.resource.constant.BuildingConstant;
import com.ncu.springboot.api.resource.entity.Building;
import com.ncu.springboot.common.util.ExcelUtil;
import com.ncu.springboot.common.util.Utils;
import com.ncu.springboot.log.LogUtil;
import com.ncu.springboot.biz.rs.Res;

@RestController
@RequestMapping("/building")
public class BuildingController {

	@Reference
	private BuildingBizService buildingBizService;
	
	@Reference
	private CodeBizService codeBizService;
	
	@Reference
	private UserCacheBizService userCacheBizService;
	
	@Autowired
	private LogUtil<Building> logUtil;
	
	@RequestMapping("/getTotal")
	@Permission
	public Res getTotal(String buildingCode,Integer[] campusIds,String buildingName,Integer status) {
		Integer data = buildingBizService.getTotal(buildingCode,campusIds, buildingName, status);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/queryList")
	@Permission
	public Res queryList(String buildingCode,Integer[] campusIds,String buildingName,Integer status,Integer size,Integer num){
		List<Map<String, Object>> data = buildingBizService.queryList(buildingCode,campusIds, buildingName, status, size, num);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/addBuilding")
	@Permission
	public Res addBuilding(Building building) {
		building.setBuildingCode("LD"+Utils.getCodeByUUId());
		building.setCreateTime(Utils.getTimeStamp());
		Integer data = buildingBizService.addBuilding(building);
		logUtil.saveLog("add", "添加楼栋", building, null);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/delBuilding")
	@Permission
	public Res delBuilding(Integer[] buildingIds) {
		if(buildingIds == null || buildingIds.length<1) {
			return Res.ERROR("1", "未选择对象！");
		}
		Integer data = buildingBizService.delBuilding(buildingIds);
		logUtil.saveLog("delete", "删除楼栋", null, buildingIds);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/editBuilding")
	@Permission
	public Res editBuilding(Building building) {
		building.setUpdateTime(Utils.getTimeStamp());
		Integer data = buildingBizService.editBuilding(building);
		logUtil.saveLog("edit", "编辑楼栋", building, null);
		return Res.SUCCESS(data);
		
	}
	
	@RequestMapping("/location")
	@Permission
	public Res location(Integer buildingId,String buildingCode,Integer[] campusIds){
		List<Map<String, Object>> data = buildingBizService.location(buildingId,buildingCode,campusIds);
		return Res.SUCCESS(data);
	}
	  
	@RequestMapping("/query")
	@Permission
	public Res query(Integer buildingId,String buildingCode) {
		Map<String, Object> data = buildingBizService.query(buildingId,buildingCode);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/importExample")
	public void importExample(HttpServletResponse response) {
		List<Map<String, Object>> datas = new ArrayList<Map<String,Object>>();
		Map<String, Object> data = new HashMap<String, Object>();
		
		//状态
		List<Map<String, Object>> status = codeBizService.query(BuildingConstant.STATUS, null);
		StringBuffer value = new StringBuffer();
		for (Map<String, Object> map : status) {
			value.append(map.get("option").toString());
		}
		data.put("STATUS", value);
		data.put("FLOOR_NUM", "例：-2,-1,1,2,3");
		datas.add(data);
		
		List<String> columns = new ArrayList<String>();
		columns.add("楼栋名称");
		columns.add("状态");
		columns.add("备注");
		columns.add("经度");
		columns.add("纬度");
		columns.add("管理单位");
		columns.add("使用单位");
		columns.add("用途");
		columns.add("面积");
		columns.add("楼层");
		columns.add("园区编号");
		List<String> keys = new ArrayList<String>();
		keys.add("BUILDING_NAME");
		keys.add("STATUS");
		keys.add("REMARK");
		keys.add("LONGITUDE");
		keys.add("LATITUDE");
		keys.add("MANAGE_DEPT_NAME");
		keys.add("USE_DEPT_NAME");
		keys.add("FUNCTION");
		keys.add("AERA");
		keys.add("FLOOR_NUM");
		keys.add("CAMPUS_CODE");
		try {
			ExcelUtil.export(datas, "楼栋导入模板", columns,response,keys,"buildingExample");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/export")
	public void export(HttpServletResponse response,String buildingCode,Integer[] campusIds,String buildingName,Integer status) {
		List<Map<String, Object>> datas = buildingBizService.export(buildingCode,campusIds, buildingName, status);
		List<String> columns = new ArrayList<String>();
		columns.add("楼栋编号");
		columns.add("楼栋名称");
		columns.add("状态");
		columns.add("备注");
		columns.add("经度");
		columns.add("纬度");
		columns.add("管理单位");
		columns.add("使用单位");
		columns.add("创建时间");
		columns.add("负责人");
		columns.add("负责人电话");
		columns.add("创建人");
		columns.add("修改人");
		columns.add("修改时间");
		columns.add("用途");
		columns.add("面积");
		columns.add("楼层");
		columns.add("园区名称");
		List<String> keys = new ArrayList<String>();
		keys.add("BUILDING_CODE");
		keys.add("BUILDING_NAME");
		keys.add("STATUS");
		keys.add("REMARK");
		keys.add("LONGITUDE");
		keys.add("LATITUDE");
		keys.add("MANAGE_DEPT_NAME");
		keys.add("USE_DEPT_NAME");
		keys.add("CREATE_TIME");
		keys.add("PRINCIPAL_NAME");
		keys.add("PRINCIPAL_MOBILE");
		keys.add("CREATE_PERSON_NAME");
		keys.add("UPDATE_PERSON_NAME");
		keys.add("UPDATE_TIME");
		keys.add("FUNCTION");
		keys.add("AERA");
		keys.add("FLOOR_NUM");
		keys.add("CAMPUS_NAME");
		try {
			ExcelUtil.export(datas, "楼栋", columns,response,keys,"building");
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
        keys.add("BUILDING_NAME");
		keys.add("STATUS");
		keys.add("REMARK");
		keys.add("LONGITUDE");
		keys.add("LATITUDE");
		keys.add("MANAGE_DEPT_NAME");
		keys.add("USE_DEPT_NAME");
		keys.add("FUNCTION");
		keys.add("AERA");
		keys.add("FLOOR_NUM");
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
		
		
		Res result = buildingBizService.importExcel(datas,employeeId,BuildingConstant.STATUS);
		return result;
	}
}
