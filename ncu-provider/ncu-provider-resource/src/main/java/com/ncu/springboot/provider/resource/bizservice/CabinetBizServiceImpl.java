package com.ncu.springboot.provider.resource.bizservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.osgi.service.component.annotations.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.ncu.springboot.api.resource.bizservice.CabinetBizService;
import com.ncu.springboot.api.resource.constant.CabinetConstant;
import com.ncu.springboot.api.resource.entity.Cabinet;
import com.ncu.springboot.api.resource.entity.EquipmentRoom;
import com.ncu.springboot.common.util.Utils;
import com.ncu.springboot.biz.rs.Res;
import com.ncu.springboot.provider.resource.service.CabinetService;
import com.ncu.springboot.provider.resource.service.EquipmentRoomService;
import com.ncu.springboot.util.UserUtil;

@Component
@Service
public class CabinetBizServiceImpl implements CabinetBizService {

	@Resource
	private CabinetService cabinetService;
	
	@Resource
	private DeviceBizServiceImpl deviceBizServiceImpl;
	
	@Resource
	private CodeBizServiceImpl codeBizServiceImpl;
	
	@Resource
	private EquipmentRoomService equipmentRoomService;
	
	@Autowired
	private UserUtil userUtil;
	
	@Override
	public Integer getTotal(String cabinetCode,Integer[] campusIds,String roomName,String cabinetName,Integer status) {
		return cabinetService.getTotal(cabinetCode,campusIds, roomName, cabinetName, status);
	}

	@Override
	public List<Map<String, Object>> queryList(String cabinetCode,Integer[] campusIds,String roomName,String cabinetName,Integer status,Integer size,Integer num) {
		if(size != null && num !=null) {
			num = (num-1)*size;
			}
		return cabinetService.queryList(cabinetCode,campusIds, roomName, cabinetName, status,size,num);
	}

	@Override
	public Integer addCabinet(Cabinet cabinet) {
		return cabinetService.addCabinet(cabinet);
	}

	@Transactional
	public Integer delCabinet(Integer[] cabinetIds) {
		List<Cabinet> cabinets = new ArrayList<Cabinet>();
		for (Integer cabinetId : cabinetIds) {
			Cabinet cabinet = new Cabinet();
			cabinet.setCabinetId(cabinetId);
			cabinets.add(cabinet);
		}
		//创建机柜模板
		Cabinet cabinetExample = new Cabinet();
		cabinetExample.setStatus(CabinetConstant.DISCARD_STATUS);//废弃
		//关联删除设备
		deviceBizServiceImpl.delByCabinet(cabinets);
		return cabinetService.delCabinet(cabinets,cabinetExample);
	}
	
	@Transactional
	public Integer delByRoom(List<EquipmentRoom> rooms) {
		List<Cabinet> cabinets = new ArrayList<Cabinet>();
		for (EquipmentRoom room : rooms) {
			Cabinet cabinet = new Cabinet();
			cabinet.setRoomId(room.getRoomId());
			cabinets.add(cabinet);
		}
		//创建机柜模板
		Cabinet cabinetExample = new Cabinet();
		cabinetExample.setStatus(CabinetConstant.IDLE_STATUS);//闲置
		//关联删除设备
		deviceBizServiceImpl.delByCabinet(cabinets);
		return cabinetService.delCabinet(cabinets, cabinetExample);
	}

	@Override
	public Integer editCabinet(Cabinet cabinet) {
		return cabinetService.editCabinet(cabinet);
	}

	@Override
	public Map<String, Object> query(Integer cabinetId,String cabinetCode) {
		return cabinetService.query(cabinetId,cabinetCode);
	}

	@Override
	public List<Map<String, Object>> export(String cabinetCode,Integer[] campusIds,String roomName,String cabinetName,Integer status) {
		return cabinetService.export(cabinetCode,campusIds, roomName, cabinetName, status);
	}

	@Override
	public Res importExcel(List<Map<String, String>> datas, Integer userId, Integer status,Integer type) {
		List<Cabinet> cabinets = new ArrayList<Cabinet>();
		List<String> roomCodes = new ArrayList<String>();
		List<String> deptNames = new ArrayList<String>();
		List<String> employeeNames = new ArrayList<String>();
		
		String msg = null;
		
		for (int i = 0; i < datas.size(); i++) {
			roomCodes.add(datas.get(i).get("ROOM_CODE"));
			deptNames.add(datas.get(i).get("MANAGE_DEPT_NAME"));
			deptNames.add(datas.get(i).get("USE_DEPT_NAME"));
			employeeNames.add(datas.get(i).get("RECEIVE"));
			employeeNames.add(datas.get(i).get("PRINCIPAL_NAME"));
			employeeNames.add(datas.get(i).get("MAINTAIN_PERSON_NAME"));
		}
		Map<String, String> deptIds = Utils.listToMap(userUtil.getDeptMapByDeptName(deptNames), "name", "id");
		Map<String, String> employeeIds = Utils.listToMap(userUtil.getEmployeeMapByEmployeeName(employeeNames), "employeeName", "employeeId");
		
		Map<String, String> roomIds = Utils.listToMap(equipmentRoomService.selectIdByCode(roomCodes), "ROOM_CODE", "ROOM_ID");
		
		Map<String, String> cabinetStatus = Utils.listToMap(codeBizServiceImpl.query(status, null), "option", "codeId");
		Map<String, String> cabinetType = Utils.listToMap(codeBizServiceImpl.query(type, null), "option", "codeId");
		
		
		for (int i = 0; i < datas.size(); i++) {
			Cabinet cabinet = new Cabinet();
			try {
				cabinet.setHeight(datas.get(i).get("HEIGHT"));
				cabinet.setStatus(Integer.parseInt(cabinetStatus.get(datas.get(i).get("STATUS"))));
				cabinet.setCabinetType(Integer.parseInt(cabinetType.get(datas.get(i).get("CABINET_TYPE"))));
				cabinet.setRemark(datas.get(i).get("REMARK"));
				cabinet.setCabinetModel(datas.get(i).get("CABINET_MODEL"));
				cabinet.setCabinetName(datas.get(i).get("CABINET_NAME"));
				cabinet.setLength(datas.get(i).get("LENGTH"));
				cabinet.setWidth(datas.get(i).get("WIDTH"));
				cabinet.setFunction(datas.get(i).get("FUNCTION"));
				cabinet.setArea(datas.get(i).get("AREA"));
				cabinet.setLocation(datas.get(i).get("LOCATION"));
				cabinet.setReceive(Integer.parseInt(employeeIds.get(datas.get(i).get("RECEIVE"))));	
				cabinet.setSeries(datas.get(i).get("SERIES"));
				cabinet.setPropertySeries(datas.get(i).get("PROPERTY_SERIES"));
				cabinet.setTier(Integer.parseInt(datas.get(i).get("TIER")));
				cabinet.setPrincipal(Integer.parseInt(employeeIds.get(datas.get(i).get("PRINCIPAL_NAME")))); 
//				cabinet.setManufactor();
				cabinet.setUseDept(Integer.parseInt(deptIds.get(datas.get(i).get("USE_DEPT_NAME"))));
				cabinet.setManageDept(Integer.parseInt(deptIds.get(datas.get(i).get("MANAGE_DEPT_NAME"))));
				cabinet.setMaintainPerson(Integer.parseInt(employeeIds.get(datas.get(i).get("MAINTAIN_PERSON_NAME"))));
				cabinet.setRoomId(Integer.parseInt(roomIds.get(datas.get(i).get("ROOM_CODE"))));
				
				cabinet.setCabinetCode("JG"+Utils.getCodeByUUId());
			} catch (Exception e) {
				msg = "该条数据出错，请检查！\n"+
					  "高度："+datas.get(i).get("HEIGHT")+"\n"+
					  "状态："+datas.get(i).get("STATUS")+"\n"+
					  "机柜类型："+datas.get(i).get("CABINET_TYPE")+"\n"+
					  "备注："+datas.get(i).get("REMARK")+"\n"+
					  "机柜型号："+datas.get(i).get("CABINET_MODEL")+"\n"+
					  "机柜名称："+datas.get(i).get("CABINET_NAME")+"\n"+
					  "长度："+datas.get(i).get("LENGTH")+"\n"+
					  "宽度："+datas.get(i).get("WIDTH")+"\n"+
					  "简介："+datas.get(i).get("FUNCTION")+"\n"+
					  "面积："+datas.get(i).get("AREA")+"\n"+
					  "定位："+datas.get(i).get("LOCATION")+"\n"+
					  "领用人："+datas.get(i).get("RECEIVE")+"\n"+
					  "厂商序列号："+datas.get(i).get("SERIES")+"\n"+
					  "资产号："+datas.get(i).get("PROPERTY_SERIES")+"\n"+
					  "层数："+datas.get(i).get("TIER")+"\n"+
					  "负责人："+datas.get(i).get("PRINCIPAL_NAME")+"\n"+
					  "厂商："+datas.get(i).get("MANUFACTOR_NAME")+"\n"+
					  "使用单位："+datas.get(i).get("USE_DEPT_NAME")+"\n"+
					  "管理单位："+datas.get(i).get("MANAGE_DEPT_NAME")+"\n"+
					  "维护人："+datas.get(i).get("MAINTAIN_PERSON_NAME")+"\n"+
					  "设备间编号："+datas.get(i).get("ROOM_CODE");

				e.printStackTrace();
			}
			
			cabinet.setCreateTime(Utils.getTimeStamp());
			cabinet.setCreatePersonId(userId);
			
			cabinets.add(cabinet);
		}
		
		Res res = Res.Res("0", "", null);
		if (msg != null) {
			res.setCode("1");
			res.setMessage(msg);
		} 
		
		Integer count = cabinetService.addCabinets(cabinets);
		if (count == 0) {
			res.setCode("1");
			res.setMessage("无数据！");
		} 
		
		return res;
	}

}
