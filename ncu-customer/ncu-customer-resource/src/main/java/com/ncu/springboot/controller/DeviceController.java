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
import com.ncu.springboot.api.resource.bizservice.DeviceBizService;
import com.ncu.springboot.api.resource.constant.DeviceConstant;
import com.ncu.springboot.api.resource.entity.Device;
import com.ncu.springboot.common.util.ExcelUtil;
import com.ncu.springboot.common.util.Utils;
import com.ncu.springboot.log.LogUtil;
import com.ncu.springboot.biz.rs.Res;

@RestController
@RequestMapping("/device")
public class DeviceController {

	@Reference
	private DeviceBizService deviceBizService;
	
	@Reference
	private CodeBizService codeBizService;
	
	@Reference
	private UserCacheBizService userCacheBizService;
	
	@Autowired
	private LogUtil<Device> logUtil;
	
	@RequestMapping("/getTotal")
	@Permission
	public Res getTotal(String deviceCode,Integer[] campusIds,Integer cabinetId,String deviceName,Integer status,Integer deviceType,Integer buildingId,Integer roomId) {
		Integer data = deviceBizService.getTotal(deviceCode,campusIds, cabinetId, deviceName, status, deviceType,buildingId,roomId);
		return Res.SUCCESS(data);
	}

	@RequestMapping("/queryList")
	@Permission
	public Res queryList(String deviceCode,Integer[] campusIds,Integer cabinetId,String deviceName,Integer status,Integer deviceType,Integer buildingId,Integer roomId, Integer size, Integer num) {
		List<Map<String,Object>> data = deviceBizService.queryList(deviceCode,campusIds, cabinetId, deviceName, status, deviceType,buildingId,roomId,size, num);
		return Res.SUCCESS(data);
	}

	@RequestMapping("/addDevice")
	@Permission
	public Res addDevice(Device device) {
		device.setCreateTime(Utils.getTimeStamp());
		device.setDeviceCode("SB"+Utils.getCodeByUUId());
		Integer data = deviceBizService.addDevice(device);
		logUtil.saveLog("add", "添加设备", device, null);
		return Res.SUCCESS(data);
	}

	@RequestMapping("/delDevice")
	@Permission
	public Res delDevice(Integer[] deviceIds) {
		if(deviceIds == null || deviceIds.length<1) {
			return Res.ERROR("1", "未选择对象！");
		}
		Integer data = deviceBizService.delDevice(deviceIds);
		logUtil.saveLog("delete", "删除设备", null, deviceIds);
		return Res.SUCCESS(data);
	}

	@RequestMapping("/editDevice")
	@Permission
	public Res editDevice(Device device) {
		device.setUpdateTime(Utils.getTimeStamp());
		Integer data = deviceBizService.editDevice(device);
		logUtil.saveLog("edit", "编辑设备", device, null);
		return Res.SUCCESS(data);
	}

	@RequestMapping("/query")
	@Permission
	public Res query(Integer deviceId,String deviceCode) {
		Map<String, Object> data = deviceBizService.query(deviceId,deviceCode);
		return Res.SUCCESS(data);
	}

	@RequestMapping("/importExample")
	public void importExample(HttpServletResponse response) {
		List<Map<String, Object>> datas = new ArrayList<Map<String,Object>>();
		Map<String, Object> data = new HashMap<String, Object>();
		
		//状态
		List<Map<String, Object>> status = codeBizService.query(DeviceConstant.STATUS, null);
		StringBuffer statusValue = new StringBuffer();
		for (Map<String, Object> map : status) {
			statusValue.append(map.get("option").toString());
		}
		data.put("STATUS", statusValue);
		
		//类型
		List<Map<String, Object>> type = codeBizService.query(DeviceConstant.TYPE, null);
		StringBuffer typeValue = new StringBuffer();
		
		for (Map<String, Object> map : type) {
			typeValue.append(map.get("option").toString());
		}
		data.put("DEVICE_TYPE", typeValue);
		datas.add(data);
		
		List<String> columns = new ArrayList<String>();
		columns.add("设备名称");
		columns.add("安置机架");
		columns.add("状态");
		columns.add("备注");
		columns.add("设备型号");
		columns.add("面积");
		columns.add("简介");
		columns.add("厂商序列号");
		columns.add("资产号");
		columns.add("设备类型");
		columns.add("功率");
		columns.add("领用人");
		columns.add("使用层数");
		columns.add("所在层数");
		columns.add("高");
		columns.add("长");
		columns.add("宽");
		columns.add("安置日期");
		columns.add("设备厂家");
		columns.add("机柜编号");
		columns.add("维修人");
		List<String> keys = new ArrayList<String>();
		keys.add("DEVICE_NAME");
		keys.add("PUT_RACK");
		keys.add("STATUS");
		keys.add("REMARK");
		keys.add("DEVICE_MODEL");
		keys.add("AREA");
		keys.add("FUNCTION");
		keys.add("SERIES");
		keys.add("PROPERTY_SERIES");
		keys.add("DEVICE_TYPE");
		keys.add("POWER");
		keys.add("RECEIVE");
		keys.add("USER_TIER");
		keys.add("TIER");
		keys.add("HEIGHT");
		keys.add("LENGTH");
		keys.add("WIDTH");
		keys.add("LAYOUT_TIME");
		keys.add("MANUFACTOR_NAME");
		keys.add("CABINET_CODE");
		keys.add("MAINTAIN_PERSON_NAME");
		try {
			ExcelUtil.export(datas, "设备导入模板", columns,response,keys,"deviceExample");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/export")
	public void export(HttpServletResponse response,String deviceCode,Integer[] campusIds,Integer cabinetId,String deviceName,Integer status,Integer deviceType,Integer buildingId,Integer roomId) {
		List<Map<String, Object>> datas = deviceBizService.export(deviceCode,campusIds, cabinetId, deviceName, status, deviceType,buildingId,roomId);
		List<String> columns = new ArrayList<String>();
		columns.add("设备编号");
		columns.add("设备名称");
		columns.add("安置机架");
		columns.add("创建时间");
		columns.add("状态");
		columns.add("备注");
		columns.add("设备型号");
		columns.add("修改时间");
		columns.add("面积");
		columns.add("简介");
		columns.add("厂商序列号");
		columns.add("资产号");
		columns.add("设备类型");
		columns.add("功率");
		columns.add("领用人");
		columns.add("使用层数");
		columns.add("所在层数");
		columns.add("高");
		columns.add("长");
		columns.add("宽");
		columns.add("安置日期");
		columns.add("创建人");
		columns.add("修改人");
		columns.add("设备厂家");
		columns.add("机柜编号");
		columns.add("维修人");
		List<String> keys = new ArrayList<String>();
		keys.add("DEVICE_CODE");
		keys.add("DEVICE_NAME");
		keys.add("PUT_RACK");
		keys.add("CREATE_TIME");
		keys.add("STATUS");
		keys.add("REMARK");
		keys.add("DEVICE_MODEL");
		keys.add("UPDATE_TIME");
		keys.add("AREA");
		keys.add("FUNCTION");
		keys.add("SERIES");
		keys.add("PROPERTY_SERIES");
		keys.add("DEVICE_TYPE");
		keys.add("POWER");
		keys.add("RECEIVE");
		keys.add("USER_TIER");
		keys.add("TIER");
		keys.add("HEIGHT");
		keys.add("LENGTH");
		keys.add("WIDTH");
		keys.add("LAYOUT_TIME");
		keys.add("CREATE_PERSON_NAME");
		keys.add("UPDATE_PERSON_NAME");
		keys.add("MANUFACTOR_NAME");
		keys.add("CABINET_CODE");
		keys.add("MAINTAIN_PERSON_NAME");
		try {
			ExcelUtil.export(datas, "设备", columns,response,keys,"device");
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
        keys.add("DEVICE_NAME");
		keys.add("PUT_RACK");
		keys.add("STATUS");
		keys.add("REMARK");
		keys.add("DEVICE_MODEL");
		keys.add("AREA");
		keys.add("FUNCTION");
		keys.add("SERIES");
		keys.add("PROPERTY_SERIES");
		keys.add("DEVICE_TYPE");
		keys.add("POWER");
		keys.add("RECEIVE");
		keys.add("USER_TIER");
		keys.add("TIER");
		keys.add("HEIGHT");
		keys.add("LENGTH");
		keys.add("WIDTH");
		keys.add("LAYOUT_TIME");
		keys.add("MANUFACTOR_NAME");
		keys.add("CABINET_CODE");
		keys.add("MAINTAIN_PERSON_NAME");
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
		
		Res result = deviceBizService.importExcel(datas,employeeId,DeviceConstant.STATUS,DeviceConstant.TYPE);
		return result;
	}
}
