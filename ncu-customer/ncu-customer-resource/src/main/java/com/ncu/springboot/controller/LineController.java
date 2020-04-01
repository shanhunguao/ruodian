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
import com.ncu.springboot.api.resource.bizservice.LineBizService;
import com.ncu.springboot.api.resource.constant.LineConstant;
import com.ncu.springboot.api.resource.entity.Line;
import com.ncu.springboot.common.util.ExcelUtil;
import com.ncu.springboot.common.util.Utils;
import com.ncu.springboot.log.LogUtil;
import com.ncu.springboot.biz.rs.Res;

@RestController
@RequestMapping("/line")
public class LineController {
	
	@Reference
	private LineBizService lineBizService;
	
	@Reference
	private CodeBizService codeBizService;
	
	@Reference
	private UserCacheBizService userCacheBizService;
	
	@Autowired
	private LogUtil<Line> logUtil;
	
	@RequestMapping("/getTotal")
	@Permission
	public Res getTotal(Integer portId,Integer[] campusIds,String lineCode,String lineName,Integer lineType,Integer status,Integer fibercableId) {
		Integer data = lineBizService.getTotal(portId,campusIds, lineCode, lineName, lineType, status,fibercableId);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/queryList")
	@Permission
	public Res queryList(Integer portId,Integer[] campusIds,String lineCode,String lineName,Integer lineType,Integer status,Integer fibercableId,Integer size,Integer num){
		List<Map<String, Object>> data = lineBizService.queryList(portId,campusIds, lineCode, lineName, lineType, status,fibercableId, size, num);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/addLine")
	@Permission
	public Res addLine(List<Line> lines) {
		for (int i = 0; i < lines.size(); i++) {
			lines.get(i).setCreateTime(Utils.getTimeStamp());
			lines.get(i).setLineCode("XL"+Utils.getCodeByUUId());
		}
		Integer data = lineBizService.addLines(lines);
		for (int i = 0; i < lines.size(); i++) {
			logUtil.saveLog("add", "添加线路", lines.get(i), null);
		}
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/delLine")
	@Permission
	public Res delLine(Integer[] lineIds) {
		if(lineIds == null || lineIds.length<1) {
			return Res.ERROR("1", "未选择对象！");
		}
		Integer data = lineBizService.delLine(lineIds);
		logUtil.saveLog("delete", "删除线路", null, lineIds);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/editLine")
	@Permission
	public Res editLine(Line line) {
		line.setUpdateTime(Utils.getTimeStamp());
		Integer data = lineBizService.editLine(line);
		logUtil.saveLog("edit", "编辑线路", line, null);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/query")
	@Permission
	public Res query(Integer lineId,String lineCode) {
		Map<String, Object> data = lineBizService.query(lineId,lineCode);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/location")
	@Permission
	public Res location(Integer lineId,Integer portId){
		List<Map<String,Object>> data = lineBizService.location(lineId,portId);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/importExample")
	public void importExample(HttpServletResponse response) {
		List<Map<String, Object>> datas = new ArrayList<Map<String,Object>>();
		Map<String, Object> data = new HashMap<String, Object>();
		
		//状态
		List<Map<String, Object>> status = codeBizService.query(LineConstant.STATUS, null);
		StringBuffer statusValue = new StringBuffer();
		for (Map<String, Object> map : status) {
			statusValue.append(map.get("option").toString());
		}
		data.put("STATUS", statusValue);
		
		//类型
		List<Map<String, Object>> type = codeBizService.query(LineConstant.TYPE, null);
		StringBuffer typeValue = new StringBuffer();
		for (Map<String, Object> map : type) {
			typeValue.append(map.get("option").toString());
		}
		data.put("LINE_TYPE", typeValue);
		datas.add(data);
		
		List<String> columns = new ArrayList<String>();
		columns.add("线路名称");
		columns.add("状态");
		columns.add("上联端口编号");
		columns.add("下联端口编号");
		columns.add("线路类型");
		columns.add("备注");
		columns.add("园区编号");
		columns.add("启用时间");
		columns.add("负责人");
		columns.add("使用单位");
		columns.add("管理单位");
		columns.add("光缆编号");
		List<String> keys = new ArrayList<String>();
		keys.add("LINE_NAME");
		keys.add("STATUS");
		keys.add("UP_PORT_CODE");
		keys.add("DOWN_PORT_CODE");
		keys.add("LINE_TYPE");
		keys.add("REAMRK");
		keys.add("CAMPUS_CODE");
		keys.add("USE_TIME");
		keys.add("PRINCIPAL_NAME");
		keys.add("USE_DEPT_NAME");
		keys.add("MANAGE_DEPT_NAME");
		keys.add("FIBERCABLE_CODE");

		try {
			ExcelUtil.export(datas, "线路导入模板", columns,response,keys,"lineExample");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/export")
	public void export(HttpServletResponse response,Integer portId,Integer[] campusIds,String lineCode,String lineName,Integer lineType,Integer status,Integer fibercableId) {
		List<Map<String, Object>> datas = lineBizService.export(portId,campusIds, lineCode, lineName, lineType, status,fibercableId);
		List<String> columns = new ArrayList<String>();
		columns.add("线路编号");
		columns.add("线路名称");
		columns.add("创建时间");
		columns.add("状态");
		columns.add("上联端口编号");
		columns.add("下联端口编号");
		columns.add("线路类型");
		columns.add("备注");
		columns.add("修改时间");
		columns.add("园区编号");
		columns.add("启用时间");
		columns.add("负责人");
		columns.add("负责人电话");
		columns.add("创建人");
		columns.add("修改人");
		columns.add("使用单位");
		columns.add("管理单位");
		columns.add("光缆编号");
		List<String> keys = new ArrayList<String>();
		keys.add("LINE_CODE");
		keys.add("LINE_NAME");
		keys.add("CREATE_TIME");
		keys.add("STATUS");
		keys.add("UP_PORT_CODE");
		keys.add("DOWN_PORT_CODE");
		keys.add("LINE_TYPE");
		keys.add("REAMRK");
		keys.add("UPDATE_TIME");
		keys.add("CAMPUS_CODE");
		keys.add("USE_TIME");
		keys.add("PRINCIPAL_NAME");
		keys.add("PRINCIPAL_MOBILE");
		keys.add("CREATE_PERSON_NAME");
		keys.add("UPDATE_PERSON_NAME");
		keys.add("USE_DEPT_NAME");
		keys.add("MANAGE_DEPT_NAME");
		keys.add("FIBERCABLE_CODE");

		try {
			ExcelUtil.export(datas, "线路", columns,response,keys,"line");
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
    	keys.add("LINE_NAME");
		keys.add("STATUS");
		keys.add("UP_PORT_CODE");
		keys.add("DOWN_PORT_CODE");
		keys.add("LINE_TYPE");
		keys.add("REAMRK");
		keys.add("CAMPUS_CODE");
		keys.add("USE_TIME");
		keys.add("PRINCIPAL_NAME");
		keys.add("USE_DEPT_NAME");
		keys.add("MANAGE_DEPT_NAME");
		keys.add("FIBERCABLE_CODE");
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
		
		Res result = lineBizService.importExcel(datas,employeeId,LineConstant.STATUS,LineConstant.TYPE);
		return result;
	}

}
