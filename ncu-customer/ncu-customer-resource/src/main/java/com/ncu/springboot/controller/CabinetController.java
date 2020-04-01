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
import com.ncu.springboot.api.resource.bizservice.CabinetBizService;
import com.ncu.springboot.api.resource.bizservice.CodeBizService;
import com.ncu.springboot.api.resource.constant.CabinetConstant;
import com.ncu.springboot.api.resource.entity.Cabinet;
import com.ncu.springboot.common.util.ExcelUtil;
import com.ncu.springboot.common.util.Utils;
import com.ncu.springboot.log.LogUtil;
import com.ncu.springboot.biz.rs.Res;

@RestController
@RequestMapping("/cabinet")
public class CabinetController {

	@Reference
	private CabinetBizService cabinetBizService;
	
	@Reference
	private CodeBizService codeBizService;
	
	@Reference
	private UserCacheBizService userCacheBizService;
	
	@Autowired
	private LogUtil<Cabinet> logUtil;
	
	@RequestMapping("/getTotal")
	@Permission
	public Res getTotal(String cabinetCode,Integer[] campusIds,String roomName,String cabinetName,Integer status) {
		Integer data = cabinetBizService.getTotal(cabinetCode,campusIds, roomName, cabinetName, status);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/queryList")
	@Permission
	public Res queryList(String cabinetCode,Integer[] campusIds,String roomName,String cabinetName,Integer status,Integer size,Integer num){
		List<Map<String,Object>> data = cabinetBizService.queryList(cabinetCode,campusIds, roomName, cabinetName, status, size, num);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/addCabinet")
	@Permission
	public Res addCabinet(Cabinet cabinet) {
		cabinet.setCreateTime(Utils.getTimeStamp());
		cabinet.setCabinetCode("JG"+Utils.getCodeByUUId());
		Integer data = cabinetBizService.addCabinet(cabinet);
		logUtil.saveLog("add", "添加机柜", cabinet, null);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/delCabinet")
	@Permission
	public Res delCabinet(Integer[] cabinetIds) {
		if(cabinetIds == null || cabinetIds.length<1) {
			return Res.ERROR("1", "未选择对象！");
		}
		Integer data = cabinetBizService.delCabinet(cabinetIds);
		logUtil.saveLog("delete", "删除机柜", null, cabinetIds);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/editCabinet")
	@Permission
	public Res editCabinet(Cabinet cabinet) {
		cabinet.setUpdateTime(Utils.getTimeStamp());
		Integer data = cabinetBizService.editCabinet(cabinet);
		logUtil.saveLog("edit", "编辑机柜", cabinet, null);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/query")
	@Permission
	public Res query(Integer cabinetId,String cabinetCode) {
		Map<String, Object> data = cabinetBizService.query(cabinetId,cabinetCode);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/importExample")
	public void importExample(HttpServletResponse response) {
		List<Map<String, Object>> datas = new ArrayList<Map<String,Object>>();
		Map<String, Object> data = new HashMap<String, Object>();
		
		//状态
		List<Map<String, Object>> status = codeBizService.query(CabinetConstant.STATUS, null);
		StringBuffer statusValue = new StringBuffer();
		for (Map<String, Object> map : status) {
			statusValue.append(map.get("option").toString());
		}
		data.put("STATUS", statusValue);
		
		//类型
		List<Map<String, Object>> type = codeBizService.query(CabinetConstant.TYPE, null);
		StringBuffer typeValue = new StringBuffer();
		for (Map<String, Object> map : type) {
			typeValue.append(map.get("option").toString());
		}
		data.put("CABINET_TYPE", typeValue);
		datas.add(data);
		
		List<String> columns = new ArrayList<String>();
		columns.add("高度");
		columns.add("状态");
		columns.add("机柜类型");
		columns.add("备注");
		columns.add("机柜型号");
		columns.add("机柜名称");
		columns.add("长度");
		columns.add("宽度");
		columns.add("备注");
		columns.add("面积");
		columns.add("定位");
		columns.add("领用人");
		columns.add("厂商序列号");
		columns.add("资产号");
		columns.add("层数");
		columns.add("负责人");
		columns.add("厂商");
		columns.add("使用单位");
		columns.add("管理单位");
		columns.add("维修人");
		columns.add("房间编号");
		List<String> keys = new ArrayList<String>();
		keys.add("HEIGHT");
		keys.add("STATUS");
		keys.add("CABINET_TYPE");
		keys.add("REMARK");
		keys.add("CABINET_MODEL");
		keys.add("CABINET_NAME");
		keys.add("LENGTH");
		keys.add("WIDTH");
		keys.add("FUNCTION");
		keys.add("AREA");
		keys.add("LOCATION");
		keys.add("RECEIVE");
		keys.add("SERIES");
		keys.add("PROPERTY_SERIES");
		keys.add("TIER");
		keys.add("PRINCIPAL_NAME");
		keys.add("MANUFACTOR_NAME");
		keys.add("USE_DEPT_NAME");
		keys.add("MANAGE_DEPT_NAME");
		keys.add("MAINTAIN_PERSON_NAME");
		keys.add("ROOM_CODE");
		
		try {
			ExcelUtil.export(datas, "机柜导入模板", columns,response,keys,"cabinetExample");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/export")
	public void export(HttpServletResponse response,String cabinetCode,Integer[] campusIds,String roomName,String cabinetName,Integer status) {
		List<Map<String, Object>> datas = cabinetBizService.export(cabinetCode,campusIds, roomName, cabinetName, status);
		List<String> columns = new ArrayList<String>();
		columns.add("机柜编号");
		columns.add("高度");
		columns.add("状态");
		columns.add("机柜类型");
		columns.add("备注");
		columns.add("机柜型号");
		columns.add("机柜名称");
		columns.add("修改时间");
		columns.add("创建时间");
		columns.add("长度");
		columns.add("宽度");
		columns.add("备注");
		columns.add("面积");
		columns.add("定位");
		columns.add("领用人");
		columns.add("厂商序列号");
		columns.add("资产号");
		columns.add("层数");
		columns.add("负责人");
		columns.add("负责人手机号");
		columns.add("创建人");
		columns.add("修改人");
		columns.add("厂商");
		columns.add("使用单位");
		columns.add("管理单位");
		columns.add("维修人");
		columns.add("维修人手机");
		columns.add("房间编号");
		List<String> keys = new ArrayList<String>();
		keys.add("CABINET_CODE");
		keys.add("HEIGHT");
		keys.add("STATUS");
		keys.add("CABINET_TYPE");
		keys.add("REMARK");
		keys.add("CABINET_MODEL");
		keys.add("CABINET_NAME");
		keys.add("UPDATE_TIME");
		keys.add("CREATE_TIME");
		keys.add("LENGTH");
		keys.add("WIDTH");
		keys.add("FUNCTION");
		keys.add("AREA");
		keys.add("LOCATION");
		keys.add("RECEIVE");
		keys.add("SERIES");
		keys.add("PROPERTY_SERIES");
		keys.add("TIER");
		keys.add("PRINCIPAL_NAME");
		keys.add("PRINCIPAL_MOBILE");
		keys.add("CREATE_PERSON_NAME");
		keys.add("UPDATE_PERSON_NAME");
		keys.add("MANUFACTOR_NAME");
		keys.add("USE_DEPT_NAME");
		keys.add("MANAGE_DEPT_NAME");
		keys.add("MAINTAIN_PERSON_NAME");
		keys.add("MAINTAIN_PERSON_MOBILE");
		keys.add("ROOM_CODE");
		
		try {
			ExcelUtil.export(datas, "机柜", columns,response,keys,"cabinet");
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
        keys.add("HEIGHT");
		keys.add("STATUS");
		keys.add("CABINET_TYPE");
		keys.add("REMARK");
		keys.add("CABINET_MODEL");
		keys.add("CABINET_NAME");
		keys.add("LENGTH");
		keys.add("WIDTH");
		keys.add("FUNCTION");
		keys.add("AREA");
		keys.add("LOCATION");
		keys.add("RECEIVE");
		keys.add("SERIES");
		keys.add("PROPERTY_SERIES");
		keys.add("TIER");
		keys.add("PRINCIPAL_NAME");
		keys.add("MANUFACTOR_NAME");
		keys.add("USE_DEPT_NAME");
		keys.add("MANAGE_DEPT_NAME");
		keys.add("MAINTAIN_PERSON_NAME");
		keys.add("ROOM_CODE");
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
		
		Res result = cabinetBizService.importExcel(datas,employeeId,CabinetConstant.STATUS,CabinetConstant.TYPE);
		return result;
	}
}
