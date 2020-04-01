package com.ncu.springboot.provider.resource.bizservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.osgi.service.component.annotations.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.ncu.springboot.api.resource.bizservice.DeviceBizService;
import com.ncu.springboot.api.resource.constant.DeviceConstant;
import com.ncu.springboot.api.resource.entity.Cabinet;
import com.ncu.springboot.api.resource.entity.Device;
import com.ncu.springboot.common.util.Utils;
import com.ncu.springboot.biz.rs.Res;
import com.ncu.springboot.provider.resource.service.CabinetService;
import com.ncu.springboot.provider.resource.service.DeviceService;
import com.ncu.springboot.util.UserUtil;

@Service
@Component
public class DeviceBizServiceImpl implements DeviceBizService {
	
	@Resource
	private DeviceService deviceService;
	
	@Resource
	private PortBizServiceImpl portBizServiceImpl;
	
	@Resource
	private CodeBizServiceImpl codeBizServiceImpl;
	
	@Resource
	private CabinetService cabinetService;
	
	@Autowired
	private UserUtil userUtil;
	
	@Override
	public Integer getTotal(String deviceCode,Integer[] campusIds,Integer cabinetId,String deviceName,Integer status,Integer deviceType,Integer buildingId,Integer roomId) {
		return deviceService.getTotal(deviceCode,campusIds, cabinetId, deviceName, status, deviceType,buildingId,roomId);
	}

	@Override
	public List<Map<String, Object>> queryList(String deviceCode,Integer[] campusIds,Integer cabinetId,String deviceName,Integer status,Integer deviceType,Integer buildingId,Integer roomId, Integer size, Integer num) {
		if(size != null && num !=null) {
			num = (num-1)*size;
			}
		return deviceService.queryList(deviceCode,campusIds, cabinetId, deviceName, status, deviceType,buildingId,roomId, size, num);
	}

	@Override
	public Integer addDevice(Device device) {
		return deviceService.addDevice(device);
	}

	@Transactional
	public Integer delDevice(Integer[] deviceIds) {
		List<Device> devices = new ArrayList<Device>();
		for (Integer deviceId : deviceIds) {
			Device device = new Device();
			device.setDeviceId(deviceId);
			devices.add(device);
		}
		//创建设备模板
		Device deviceExample = new Device();
		deviceExample.setStatus(DeviceConstant.DISCARD_STATUS);//废弃
		//关联删除端口
		portBizServiceImpl.delByDevice(devices);
		return deviceService.delDevice(devices,deviceExample);
	}

	@Transactional
	public Integer delByCabinet(List<Cabinet> cabinets) {
		List<Device> devices = new ArrayList<Device>();
		for (Cabinet cabinet : cabinets) {
			Device device = new Device();
			device.setCabinetId(cabinet.getCabinetId());
			devices.add(device);
		}
		//创建模板
		Device deviceExample = new Device();
		deviceExample.setStatus(DeviceConstant.IDLE_STATUS);//闲置
		//关联删除端口
		portBizServiceImpl.delByDevice(devices);
		return deviceService.delDevice(devices, deviceExample);
	}
	
	@Override
	public Integer editDevice(Device device) {
		return deviceService.editDevice(device);
	}

	@Override
	public Map<String, Object> query(Integer deviceId,String deviceCode) {
		return deviceService.query(deviceId,deviceCode);
	}

	@Override
	public List<Map<String, Object>> export(String deviceCode,Integer[] campusIds,Integer cabinetId,String deviceName,Integer status,Integer deviceType,Integer buildingId,Integer roomId) {
		return deviceService.export(deviceCode,campusIds, cabinetId, deviceName, status, deviceType,buildingId,roomId);
	}

	@Override
	public Res importExcel(List<Map<String, String>> datas, Integer userId, Integer status, Integer type) {
		List<Device> devices = new ArrayList<Device>();
		List<String> cabinetCodes = new ArrayList<String>();
		List<String> employeeNames = new ArrayList<String>();
		String msg = null;
		
		for (int i = 0; i < datas.size(); i++) {
			cabinetCodes.add(datas.get(i).get("CABINET_CODE"));
			employeeNames.add(datas.get(i).get("RECEIVE"));
			employeeNames.add(datas.get(i).get("MAINTAIN_PERSON_NAME"));
		}
		Map<String, String> employeeIds = Utils.listToMap(userUtil.getEmployeeMapByEmployeeName(employeeNames), "employeeName", "employeeId");
		Map<String, String> cabinetIds = Utils.listToMap(cabinetService.selectIdByCode(cabinetCodes), "CABINET_CODE", "CABINET_ID");
		
		Map<String, String> deviceStatus = Utils.listToMap(codeBizServiceImpl.query(status, null), "option", "codeId");
		Map<String, String> deviceType = Utils.listToMap(codeBizServiceImpl.query(type, null), "option", "codeId");
		
		
		for (int i = 0; i < datas.size(); i++) {
			Device device = new Device();
			try {
				device.setDeviceName(datas.get(i).get("DEVICE_NAME"));
				device.setPutRack(datas.get(i).get("PUT_RACK"));
				device.setStatus(Integer.parseInt(deviceStatus.get(datas.get(i).get("STATUS"))));
				device.setRemark(datas.get(i).get("REMARK"));
				device.setDeviceModel(datas.get(i).get("DEVICE_MODEL"));
				device.setArea(datas.get(i).get("AREA"));
				device.setFunction(datas.get(i).get("FUNCTION"));
				device.setSeries(datas.get(i).get("SERIES"));
				device.setPropertySeries(datas.get(i).get("PROPERTY_SERIES"));
				device.setDeviceType(Integer.parseInt(deviceType.get(datas.get(i).get("DEVICE_TYPE"))));
				device.setPower(datas.get(i).get("POWER"));
				device.setReceive(Integer.parseInt(employeeIds.get(datas.get(i).get("RECEIVE"))));
				device.setUserTier(datas.get(i).get("USER_TIER"));
				device.setTier(datas.get(i).get("TIER"));
				device.setHeight(datas.get(i).get("HEIGHT"));
				device.setLength(datas.get(i).get("LENGTH"));
				device.setWidth(datas.get(i).get("WIDTH"));
				device.setLayoutTime(Utils.getFormatDateTime("YYYY-MM-DD", datas.get(i).get("LAYOUT_TIME")));
//				device.setManufactor();
				device.setCabinetId(Integer.parseInt(cabinetIds.get(datas.get(i).get("CABINET_CODE"))));
				device.setMaintainPerson(Integer.parseInt(employeeIds.get(datas.get(i).get("MAINTAIN_PERSON_NAME"))));
				device.setDeviceCode("SB"+Utils.getCodeByUUId());
			} catch (Exception e) {
				msg = "该条数据出错，请检查！\n"+
					  "设备名称："+datas.get(i).get("DEVICE_NAME")+"\n"+
					  "安置机架："+datas.get(i).get("PUT_RACK")+"\n"+
					  "状态："+datas.get(i).get("STATUS")+"\n"+
					  "备注："+datas.get(i).get("REMARK")+"\n"+
					  "设备型号："+datas.get(i).get("DEVICE_MODEL")+"\n"+
					  "面积："+datas.get(i).get("AREA")+"\n"+
					  "简介："+datas.get(i).get("FUNCTION")+"\n"+
					  "厂商序列号："+datas.get(i).get("SERIES")+"\n"+
					  "资产号："+datas.get(i).get("PROPERTY_SERIES")+"\n"+
					  "设备类型："+datas.get(i).get("DEVICE_TYPE")+"\n"+
					  "功率："+datas.get(i).get("POWER")+"\n"+
					  "领用人："+datas.get(i).get("RECEIVE")+"\n"+
					  "使用层数："+datas.get(i).get("USER_TIER")+"\n"+
					  "所在层数："+datas.get(i).get("TIER")+"\n"+
					  "高："+datas.get(i).get("HEIGHT")+"\n"+
					  "长："+datas.get(i).get("LENGTH")+"\n"+
					  "宽："+datas.get(i).get("WIDTH")+"\n"+
					  "安置日期："+datas.get(i).get("LAYOUT_TIME")+"\n"+
					  "设备厂家："+datas.get(i).get("MANUFACTOR_NAME")+"\n"+
					  "机柜编号："+datas.get(i).get("CABINET_CODE")+"\n"+
					  "维修人："+datas.get(i).get("MAINTAIN_PERSON_NAME");

				e.printStackTrace();
			}
			
			device.setCreateTime(Utils.getTimeStamp());
			device.setCreatePersonId(userId);
			
			devices.add(device);
		}
		
		Res res = Res.Res("0", "", null);
		if (msg != null) {
			res.setCode("1");
			res.setMessage(msg);
		} 
		
		Integer count = deviceService.addDevices(devices);
		if (count == 0) {
			res.setCode("1");
			res.setMessage("无数据！");
		} 
		
		return res;
	}
	
	

}
