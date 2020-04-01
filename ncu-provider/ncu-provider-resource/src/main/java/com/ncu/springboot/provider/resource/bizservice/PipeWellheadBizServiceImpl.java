package com.ncu.springboot.provider.resource.bizservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.ncu.springboot.api.resource.bizservice.PipeWellheadBizService;
import com.ncu.springboot.api.resource.constant.WellheadConstant;
import com.ncu.springboot.api.resource.entity.PipeWellhead;
import com.ncu.springboot.common.util.Utils;
import com.ncu.springboot.biz.rs.Res;
import com.ncu.springboot.provider.resource.service.CampusService;
import com.ncu.springboot.provider.resource.service.EquipmentRoomService;
import com.ncu.springboot.provider.resource.service.PipeWellheadService;
import com.ncu.springboot.util.UserUtil;

@Component
@Service
public class PipeWellheadBizServiceImpl implements PipeWellheadBizService {
	
	@Resource
	private PipeWellheadService pipeWellheadService;
	
	@Resource
	private PipelineBizServiceImpl pipelineBizServiceImpl;
	
	@Resource
	private CodeBizServiceImpl codeBizServiceImpl;
	
	@Resource
	private CampusService campusService;
	
	@Resource
	private EquipmentRoomService equipmentRoomService;
	
	@Autowired
	private UserUtil userUtil;
	
	public Integer getTotal(String wellheadCode,Integer[] campusIds,String wellheadType,String wellheadName,Integer status) {
		return pipeWellheadService.getTotal(wellheadCode,campusIds, wellheadType, wellheadName, status);
	}
	
	public List<Map<String, Object>> queryList(String wellheadCode,Integer[] campusIds,String wellheadType,String wellheadName,Integer status,Integer size,Integer num) {
		if(size != null && num !=null) {
			num = (num-1)*size;
			}
		return pipeWellheadService.queryList(wellheadCode,campusIds, wellheadType, wellheadName, status, size, num);
	}

	public Integer addWellhead(PipeWellhead pipeWellhead) {
		return pipeWellheadService.addWellhead(pipeWellhead);
	}
	
	@Transactional
	public Integer delWellhead(Integer[] wellheadIds) {
		List<PipeWellhead> pipeWellheads = new ArrayList<PipeWellhead>();
		
		for (Integer wellheadId : wellheadIds) {
			PipeWellhead pipeWellhead = new PipeWellhead();
			pipeWellhead.setWellheadId(wellheadId);
			pipeWellheads.add(pipeWellhead);
		}
		
		PipeWellhead wellheadExample = new PipeWellhead();
		wellheadExample.setStatus(WellheadConstant.DISCARD_STATUS);//废弃
		
		//根据井口删除管道
		pipelineBizServiceImpl.delByWellhead(pipeWellheads);
		return pipeWellheadService.delWellhead(pipeWellheads,wellheadExample);
	}
	

	public Integer editWellhead(PipeWellhead pipeWellhead) {
		return pipeWellheadService.editWellhead(pipeWellhead);
	}

	public List<Map<String, Object>> location(Integer wellheadId,String wellheadCode,Integer[] campusIds) {
		return pipeWellheadService.location(wellheadId,wellheadCode,campusIds);
	}

	public Map<String, Object> query(Integer wellheadId,String wellheadCode) {
		return pipeWellheadService.query(wellheadId,wellheadCode);
	}
	
	public List<Map<String, Object>> export(String wellheadCode,Integer[] campusIds,String wellheadType,String wellheadName,Integer status) {
		List<Map<String, Object>> data = pipeWellheadService.export(wellheadCode,campusIds, wellheadType, wellheadName, status);
		return data;
	}

	@Override
	public Res importExcel(List<Map<String, String>> datas, Integer userId, Integer status, Integer type) {
		List<PipeWellhead> wellheads = new ArrayList<PipeWellhead>();
		List<String> campusCodes = new ArrayList<String>();
		List<String> roomCodes = new ArrayList<String>();
		List<String> deptNames = new ArrayList<String>();
		List<String> employeeNames = new ArrayList<String>();

		String msg = null;

		for (int i = 0; i < datas.size(); i++) {
			campusCodes.add(datas.get(i).get("CAMPUS_CODE"));
			roomCodes.add(datas.get(i).get("ROOM_CODE"));
			employeeNames.add(datas.get(i).get("PRINCIPAL_NAME"));
			deptNames.add(datas.get(i).get("MANAGE_DEPT_NAME"));
			deptNames.add(datas.get(i).get("USE_DEPT_NAME"));
		}
		Map<String, String> deptIds = Utils.listToMap(userUtil.getDeptMapByDeptName(deptNames), "name", "id");
		Map<String, String> employeeIds = Utils.listToMap(userUtil.getEmployeeMapByEmployeeName(employeeNames), "employeeName", "employeeId");
		Map<String, String> campusIds = Utils.listToMap(campusService.selectIdByCode(campusCodes), "CAMPUS_CODE", "CAMPUS_ID");
		Map<String, String> roomIds = Utils.listToMap(equipmentRoomService.selectIdByCode(roomCodes), "ROOM_CODE", "ROOM_ID");

		Map<String, String> wellheadStatus = Utils.listToMap(codeBizServiceImpl.query(status, null), "option", "codeId");
		Map<String, String> wellheadType = Utils.listToMap(codeBizServiceImpl.query(type, null), "option", "codeId");


		for (int i = 0; i < datas.size(); i++) {
			PipeWellhead wellhead = new PipeWellhead();
			try {
				wellhead.setFunction(datas.get(i).get("FUNCTION"));
				wellhead.setPower(datas.get(i).get("POWER"));
				wellhead.setDiameter(datas.get(i).get("DIAMETER"));
				wellhead.setLongitude(datas.get(i).get("LONGITUDE"));
				wellhead.setLatitude(datas.get(i).get("LATITUDE"));
				wellhead.setWellheadName(datas.get(i).get("WELLHEAD_NAME"));
				wellhead.setRemark(datas.get(i).get("REMARK"));
				wellhead.setStatus(Integer.parseInt(wellheadStatus.get(datas.get(i).get("STATUS"))));
				wellhead.setWellheadType(Integer.parseInt(wellheadType.get(datas.get(i).get("WELLHEAD_TYPE"))));
				wellhead.setDepth(datas.get(i).get("DEPTH"));
				if(Integer.parseInt(wellheadType.get(datas.get(i).get("WELLHEAD_TYPE")))==WellheadConstant.VIRTUAL_WELLHEAD_TYPE) {
					wellhead.setRoomId(Integer.parseInt(roomIds.get(datas.get(i).get("ROOM_CODE"))));
				}
				wellhead.setPrincipal(Integer.parseInt(employeeIds.get(datas.get(i).get("PRINCIPAL_NAME"))));
				wellhead.setUseDept(Integer.parseInt(deptIds.get(datas.get(i).get("USE_DEPT_NAME"))));
				wellhead.setManageDept(Integer.parseInt(deptIds.get(datas.get(i).get("MANAGE_DEPT_NAME"))));
				wellhead.setCampusId(Integer.parseInt(campusIds.get(datas.get(i).get("CAMPUS_CODE"))));
				wellhead.setWellheadCode("JK"+Utils.getCodeByUUId());
			} catch (Exception e) {
				msg = "该条数据出错，请检查！\n"+
						"简介："+datas.get(i).get("FUNCTION")+"\n"+
						"功率："+datas.get(i).get("POWER")+"\n"+
						"半径："+datas.get(i).get("DIAMETER")+"\n"+
						"经度："+datas.get(i).get("LONGITUDE")+"\n"+
						"纬度："+datas.get(i).get("LATITUDE")+"\n"+
						"井口名称："+datas.get(i).get("WELLHEAD_NAME")+"\n"+
						"备注："+datas.get(i).get("REMARK")+"\n"+
						"状态："+datas.get(i).get("STATUS")+"\n"+
						"井口类型："+datas.get(i).get("WELLHEAD_TYPE")+"\n"+
						"深度："+datas.get(i).get("DEPTH")+"\n"+
						"设备间编号："+datas.get(i).get("ROOM_CODE")+"\n"+
						"负责人："+datas.get(i).get("PRINCIPAL_NAME")+"\n"+
						"使用单位："+datas.get(i).get("USE_DEPT_NAME")+"\n"+
						"管理单位："+datas.get(i).get("MANAGE_DEPT_NAME")+"\n"+
						"园区编号："+datas.get(i).get("CAMPUS_CODE");

				e.printStackTrace();
			}

			wellhead.setCreateTime(Utils.getTimeStamp());
			wellhead.setCreatePersonId(userId);

			wellheads.add(wellhead);
		}

		Res res = Res.Res("0", "", null);
		if (msg != null) {
			res.setCode("1");
			res.setMessage(msg);
		} 

		Integer count = pipeWellheadService.addWellheads(wellheads);
		if (count == 0) {
			res.setCode("1");
			res.setMessage("无数据！");
		} 
		
		return res;
	}
}
