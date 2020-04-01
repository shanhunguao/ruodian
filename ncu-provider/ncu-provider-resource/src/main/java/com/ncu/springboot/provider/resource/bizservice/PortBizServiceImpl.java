package com.ncu.springboot.provider.resource.bizservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.osgi.service.component.annotations.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.ncu.springboot.api.resource.bizservice.PortBizService;
import com.ncu.springboot.api.resource.constant.PortConstant;
import com.ncu.springboot.api.resource.entity.Device;
import com.ncu.springboot.api.resource.entity.Port;
import com.ncu.springboot.common.util.Utils;
import com.ncu.springboot.biz.rs.Res;
import com.ncu.springboot.provider.resource.service.DeviceService;
import com.ncu.springboot.provider.resource.service.PortService;

@Component
@Service
public class PortBizServiceImpl implements PortBizService {

	@Resource
	private PortService portService;

	@Resource
	private LineBizServiceImpl lineBizServiceImpl;

	@Resource
	private CodeBizServiceImpl codeBizServiceImpl;

	@Resource
	private DeviceService deviceService;

	public Integer getTotal(String portCode,Integer[] campusIds,String portName,Integer deviceId,Integer portType,Integer status,Integer buildingId,Integer roomId) {
		return portService.getTotal(portCode,campusIds, portName, deviceId, portType, status, buildingId, roomId);
	}

	@Override
	public List<Map<String, Object>> queryList(String portCode,Integer[] campusIds,String portName,Integer deviceId,Integer portType,Integer status,Integer buildingId,Integer roomId, Integer size, Integer num) {
		if(size != null && num !=null) {
			num = (num-1)*size;
		}
		return portService.queryList(portCode,campusIds, portName, deviceId, portType, status, buildingId, roomId, size, num);
	}

	@Override
	public Map<String, Object> query(Integer portId,String portCode) {
		return portService.query(portId,portCode);
	}

	@Override
	public Integer addPorts(List<Port> ports) {
		return portService.addPorts(ports);
	}

	@Transactional
	public Integer delPort(Integer[] portIds) {
		List<Port> ports = new ArrayList<Port>();
		for (Integer portId : portIds) {
			Port port = new Port();
			port.setPortId(portId);
			ports.add(port);
		}
		Port portExample = new Port();
		portExample.setStatus(PortConstant.LEISURE_STATUS);//空闲
		//关联删除线路
		lineBizServiceImpl.delByPort(ports);
		return portService.delPort(ports,portExample);
	}

	@Transactional
	public Integer delByDevice(List<Device> devices) {
		List<Port> ports = new ArrayList<Port>();
		for (Device device : devices) {
			Port port = new Port();
			port.setDeviceId(device.getDeviceId());
			ports.add(port);
		}
		Port portExample = new Port();
		portExample.setStatus(PortConstant.LEISURE_STATUS);//空闲
		//关联删除线路
		lineBizServiceImpl.delByPort(ports);
		return portService.delPort(ports, portExample);
	}

	@Transactional
	public Integer editPort(Port[] ports) {
		return portService.editPort(ports);
	}

	@Override
	public List<Map<String, Object>> export(String portCode,Integer[] campusIds,String portName,Integer deviceId,Integer portType,Integer status,Integer buildingId,Integer roomId) {
		return portService.export(portCode,campusIds, portName, deviceId, portType, status, buildingId, roomId);
	}

	@Override
	public Res importExcel(List<Map<String, String>> datas, Integer status, Integer type) {
		List<Port> ports = new ArrayList<Port>();
		List<String> deviceCodes = new ArrayList<String>();

		String msg = null;

		for (int i = 0; i < datas.size(); i++) {
			deviceCodes.add(datas.get(i).get("DEVICE_CODE"));
		}

		Map<String, String> deviceId = Utils.listToMap(deviceService.selectIdByCode(deviceCodes), "DEVICE_CODE", "DEVICE_ID");

		Map<String, String> portStatus = Utils.listToMap(codeBizServiceImpl.query(status, null), "option", "codeId");
		Map<String, String> portType = Utils.listToMap(codeBizServiceImpl.query(type, null), "option", "codeId");


		for (int i = 0; i < datas.size(); i++) {
			Port port = new Port();
			try {
				port.setPortName(datas.get(i).get("PORT_NAME"));
				port.setStatus(Integer.parseInt(portStatus.get(datas.get(i).get("STATUS"))));
				port.setPortType(Integer.parseInt(portType.get(datas.get(i).get("PORT_TYPE"))));
				port.setRemark(datas.get(i).get("REMARK"));
				port.setDeviceId(Integer.parseInt(deviceId.get(datas.get(i).get("DEVICE_CODE"))));
				port.setPortCode("DK"+Utils.getCodeByUUId());
			} catch (Exception e) {
				msg = "该条数据出错，请检查！\n"+
						"端口名称："+datas.get(i).get("PORT_NAME")+"\n"+
						"状态："+datas.get(i).get("STATUS")+"\n"+
						"端口类型："+datas.get(i).get("PORT_TYPE")+"\n"+
						"备注："+datas.get(i).get("REMARK")+"\n"+
						"设备编号："+datas.get(i).get("DEVICE_CODE");

				e.printStackTrace();
			}

			port.setCreateTime(Utils.getTimeStamp());

			ports.add(port);
		}

		Res res = Res.Res("0", "", null);
		if (msg != null) {
			res.setCode("1");
			res.setMessage(msg);
		} 

		Integer count = portService.addPorts(ports);
		if (count == 0) {
			res.setCode("1");
			res.setMessage("无数据！");
		} 

		return res;
	}

}
