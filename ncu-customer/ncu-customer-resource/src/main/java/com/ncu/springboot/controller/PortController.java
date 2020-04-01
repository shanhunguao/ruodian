package com.ncu.springboot.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ncu.springboot.AOP.Permission;
import com.ncu.springboot.api.cache.bizservice.UserCacheBizService;
import com.ncu.springboot.api.resource.bizservice.CodeBizService;
import com.ncu.springboot.api.resource.bizservice.PortBizService;
import com.ncu.springboot.api.resource.constant.PortConstant;
import com.ncu.springboot.api.resource.entity.Port;
import com.ncu.springboot.common.util.ExcelUtil;
import com.ncu.springboot.common.util.Utils;
import com.ncu.springboot.log.LogUtil;
import com.ncu.springboot.biz.rs.BaseRequestBody;
import com.ncu.springboot.biz.rs.Res;

@RequestMapping("/port")
@RestController
public class PortController {
	
	@Reference
	private PortBizService portBizService;
	
	@Reference
	private CodeBizService codeBizService;
	
	@Reference
	private UserCacheBizService userCacheBizService;
	
	@Autowired
	private LogUtil<Port> logUtil;
	
	@RequestMapping("/getTotal")
	@Permission
	public Res getTotal(String portCode,Integer[] campusIds,String portName,Integer deviceId,Integer portType,Integer status,Integer buildingId,Integer roomId) {
		Integer data = portBizService.getTotal(portCode,campusIds, portName, deviceId, portType, status, buildingId, roomId);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/queryList")
	@Permission
	public Res queryList(String portCode,Integer[] campusIds,String portName,Integer deviceId,Integer portType,Integer status,Integer buildingId,Integer roomId,Integer size,Integer num){
		List<Map<String, Object>> data = portBizService.queryList(portCode,campusIds, portName, deviceId, portType, status, buildingId, roomId,size,num);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/addPort")
	@Permission
	public Res addPort(@RequestBody BaseRequestBody<List<Port>> data){
		List<Port> ports = data.getData();
		for (int i = 0;i<ports.size();i++) {
			ports.get(i).setCreateTime(Utils.getTimeStamp());
			ports.get(i).setPortCode("DK"+Utils.getCodeByUUId());
		}
		Integer resulet = portBizService.addPorts(ports);
		for (Port port : ports) {
			logUtil.saveLog("add", "添加端口", port, null);
		}
		return Res.SUCCESS(resulet);
	}
	
	@RequestMapping("/delPort")
	@Permission
	public Res delPort(Integer[] portIds){
		if(portIds == null || portIds.length<1) {
			return Res.ERROR("1", "未选择对象!");
		}
		Integer data = portBizService.delPort(portIds);
		logUtil.saveLog("delete", "删除端口", null, portIds);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/editPort")
	@Permission
	public Res editPort(@RequestBody BaseRequestBody<Port[]> data){
		Port[] ports = data.getData();
		for (int i = 0; i < ports.length; i++) {
			ports[i].setUpdateTime(Utils.getTimeStamp());
		}
		Integer result = portBizService.editPort(ports);
		return Res.SUCCESS(result);
	}
	
	@RequestMapping("/query")
	@Permission
	public Res query(Integer portId,String portCode){
		Map<String, Object> data = portBizService.query(portId,portCode);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/importExample")
	public void importExample(HttpServletResponse response) {
		List<Map<String, Object>> datas = new ArrayList<Map<String,Object>>();
		Map<String, Object> data = new HashMap<String, Object>();
		
		//状态
		List<Map<String, Object>> status = codeBizService.query(PortConstant.STATUS, null);
		StringBuffer statusValue = new StringBuffer();
		for (Map<String, Object> map : status) {
			statusValue.append(map.get("option").toString());
		}
		data.put("STATUS", statusValue);
		
		//类型
		List<Map<String, Object>> type = codeBizService.query(PortConstant.TYPE, null);
		StringBuffer typeValue = new StringBuffer();
		for (Map<String, Object> map : type) {
			typeValue.append(map.get("option").toString());
		}
		data.put("PORT_TYPE", typeValue);
		datas.add(data);
		
		List<String> columns = new ArrayList<String>();
		columns.add("端口名称");
		columns.add("状态");
		columns.add("端口类型");
		columns.add("备注");
		columns.add("设备编号");
		List<String> keys = new ArrayList<String>();
		keys.add("PORT_NAME");
		keys.add("STATUS");
		keys.add("PORT_TYPE");
		keys.add("REMARK");
		keys.add("DEVICE_CODE");
		
		try {
			ExcelUtil.export(datas, "端口导入模板 ", columns,response,keys,"portExample");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/export")
	public void export(HttpServletResponse response,String portCode,Integer[] campusIds,String portName,Integer deviceId,Integer portType,Integer status,Integer buildingId,Integer roomId) {
		List<Map<String, Object>> datas = portBizService.export(portCode,campusIds, portName, deviceId, portType, status, buildingId, roomId);
		List<String> columns = new ArrayList<String>();
		columns.add("端口编号");
		columns.add("端口名称");
		columns.add("创建时间");
		columns.add("状态");
		columns.add("端口类型");
		columns.add("修改时间");
		columns.add("备注");
		columns.add("修改人");
		columns.add("设备编号");
		List<String> keys = new ArrayList<String>();
		keys.add("PORT_CODE");
		keys.add("PORT_NAME");
		keys.add("CREATE_TIME");
		keys.add("STATUS");
		keys.add("PORT_TYPE");
		keys.add("UPDATE_TIME");
		keys.add("REMARK");
		keys.add("UPDATE_PERSON_NAME");
		keys.add("DEVICE_CODE");

		
		try {
			ExcelUtil.export(datas, "端口 ", columns,response,keys,"portExample");
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
		keys.add("PORT_NAME");
		keys.add("STATUS");
		keys.add("PORT_TYPE");
		keys.add("REMARK");
		keys.add("DEVICE_CODE");
        // 业务逻辑，通过excelFile.getInputStream()，处理Excel文件
		List<Map<String, String>> datas = new ArrayList<Map<String,String>>();
		try {
			datas = ExcelUtil.excelToData(excelFile.getInputStream(), keys);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Res result = portBizService.importExcel(datas,PortConstant.STATUS,PortConstant.TYPE);
		return result;
	}
}
