package com.ncu.springboot.provider.resource.bizservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.osgi.service.component.annotations.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.ncu.springboot.api.resource.bizservice.LineBizService;
import com.ncu.springboot.api.resource.constant.LineConstant;
import com.ncu.springboot.api.resource.entity.Fibercable;
import com.ncu.springboot.api.resource.entity.Line;
import com.ncu.springboot.api.resource.entity.Port;
import com.ncu.springboot.common.util.Utils;
import com.ncu.springboot.biz.rs.Res;
import com.ncu.springboot.provider.resource.service.CampusService;
import com.ncu.springboot.provider.resource.service.FibercableService;
import com.ncu.springboot.provider.resource.service.LineService;
import com.ncu.springboot.provider.resource.service.PortService;
import com.ncu.springboot.util.UserUtil;

@Component
@Service
public class LineBizServiceImpl implements LineBizService {

	@Resource
	private LineService lineService;
	
	@Resource
	private CodeBizServiceImpl codeBizServiceImpl;
	
	@Resource
	private FibercableService fibercableService;
	
	@Resource
	private CampusService campusService;
	
	@Resource
	private PortService portService;
	
	@Autowired
	private UserUtil userUtil;

	public Integer getTotal(Integer portId,Integer[] campusIds,String lineCode,String lineName,Integer lineType,Integer status,Integer fibercableId) {
		return lineService.getTotal(portId,campusIds, lineCode, lineName, lineType, status,fibercableId);
	}

	public List<Map<String, Object>> queryList(Integer portId,Integer[] campusIds,String lineCode,String lineName,Integer lineType,Integer status,Integer fibercableId, Integer size, Integer num) {
		if(size != null && num !=null) {
			num = (num-1)*size;
			}
		return lineService.queryList(portId,campusIds, lineCode, lineName, lineType, status,fibercableId, size, num);
	}

	@Transactional
	public Integer addLines(List<Line> lines) {
		return lineService.addLines(lines);
	}

	@Transactional
	public Integer delLine(Integer[] lineIds) {
		List<Line> lines = new ArrayList<Line>();
		
		for (Integer lineId : lineIds) {
			Line line = new Line();
			line.setLineId(lineId);
			lines.add(line);
		}
		
		Line lineExample = new Line();
		lineExample.setStatus(LineConstant.DISCARD_STATUS);//废弃
		return lineService.delLine(lines,lineExample);
	}
	
	@Transactional
	public Integer delByPort(List<Port> ports) {
		List<Line> lines = new ArrayList<Line>();
		
		for (Port port : ports) {
			Line line = new Line();
			line.setDownPort(port.getPortId());
			lines.add(line);
		}
		for (Port port : ports) {
			Line line = new Line();
			line.setUpPort(port.getPortId());
			lines.add(line);
		}
		
		Line lineExample = new Line();
		lineExample.setStatus(LineConstant.BLICK_UP_STATUS);//停用
		return lineService.delLine(lines, lineExample);
	}
	
	@Transactional
	public Integer delByFibercable(List<Fibercable> fibercables) {
		List<Line> lines = new ArrayList<Line>();
		
		for (Fibercable fibercable : fibercables) {
			Line line = new Line();
			line.setFibercableId(fibercable.getFibercableId());
			lines.add(line);
		}
		
		Line lineExample = new Line();
		lineExample.setStatus(LineConstant.DISCARD_STATUS);//废弃
		return lineService.delLine(lines, lineExample);
	}

	public Integer editLine(Line line) {
		return lineService.editLine(line);
	}

	public Map<String, Object> query(Integer lineId,String lineCode) {
		return lineService.query(lineId,lineCode);
	}
	
	public List<Map<String,Object>> location(Integer lineId,Integer portId) {
		return lineService.location(lineId,portId);
	}

	@Override
	public List<Map<String, Object>> export(Integer portId,Integer[] campusIds,String lineCode,String lineName,Integer lineType,Integer status,Integer fibercableId) {
		return lineService.export(portId,campusIds, lineCode, lineName, lineType, status,fibercableId);
	}

	@Override
	public Res importExcel(List<Map<String, String>> datas, Integer userId, Integer status, Integer type) {
		List<Line> lines = new ArrayList<Line>();
		List<String> campusCodes = new ArrayList<String>();
		List<String> downPortCodes = new ArrayList<String>();
		List<String> upPortCodes = new ArrayList<String>();
		List<String> fibercableCodes = new ArrayList<String>();
		List<String> deptNames = new ArrayList<String>();
		List<String> employeeNames = new ArrayList<String>();

		String msg = null;

		for (int i = 0; i < datas.size(); i++) {
			campusCodes.add(datas.get(i).get("CAMPUS_CODE"));
			downPortCodes.add(datas.get(i).get("DOWN_PORT_CODE"));
			upPortCodes.add(datas.get(i).get("UP_PORT_CODE"));
			fibercableCodes.add(datas.get(i).get("FIBERCABLE_CODE"));
			employeeNames.add(datas.get(i).get("PRINCIPAL_NAME"));
			deptNames.add(datas.get(i).get("MANAGE_DEPT_NAME"));
			deptNames.add(datas.get(i).get("USE_DEPT_NAME"));
		}
		Map<String, String> deptIds = Utils.listToMap(userUtil.getDeptMapByDeptName(deptNames), "name", "id");
		Map<String, String> employeeIds = Utils.listToMap(userUtil.getEmployeeMapByEmployeeName(employeeNames), "employeeName", "employeeId");
		Map<String, String> campusIds = Utils.listToMap(campusService.selectIdByCode(campusCodes), "CAMPUS_CODE", "CAMPUS_ID");
		Map<String, String> downPortIds = Utils.listToMap(portService.selectIdByCode(downPortCodes), "PORT_CODE", "PORT_ID");
		Map<String, String> upPortIds = Utils.listToMap(portService.selectIdByCode(upPortCodes), "PORT_CODE", "PORT_ID");
		Map<String, String> fibercableIds = Utils.listToMap(fibercableService.selectIdByCode(fibercableCodes), "FIBERCABLE_CODE", "FIBERCABLE_ID");

		Map<String, String> lineStatus = Utils.listToMap(codeBizServiceImpl.query(status, null), "option", "codeId");
		Map<String, String> lineType = Utils.listToMap(codeBizServiceImpl.query(type, null), "option", "codeId");


		for (int i = 0; i < datas.size(); i++) {
			Line line = new Line();
			try {
				line.setLineName(datas.get(i).get("LINE_NAME"));
				line.setStatus(Integer.parseInt(lineStatus.get(datas.get(i).get("STATUS"))));
				line.setUpPort(Integer.parseInt(upPortIds.get(datas.get(i).get("UP_PORT_CODE"))));
				line.setDownPort(Integer.parseInt(downPortIds.get(datas.get(i).get("DOWN_PORT_CODE"))));
				line.setLineType(Integer.parseInt(lineType.get(datas.get(i).get("LINE_TYPE"))));
				line.setRemark(datas.get(i).get("REAMRK"));
				line.setCampusId(Integer.parseInt(campusIds.get(datas.get(i).get("CAMPUS_CODE"))));
				line.setUseTime(Utils.getTimesStamp(datas.get(i).get("USE_TIME")));
				line.setPrincipal(Integer.parseInt(employeeIds.get(datas.get(i).get("PRINCIPAL_NAME"))));
				line.setUseDept(Integer.parseInt(deptIds.get(datas.get(i).get("USE_DEPT_NAME"))));
				line.setManageDept(Integer.parseInt(deptIds.get(datas.get(i).get("MANAGE_DEPT_NAME"))));
				line.setFibercableId(Integer.parseInt(fibercableIds.get(datas.get(i).get("FIBERCABLE_CODE"))));
				line.setLineCode("DK"+Utils.getCodeByUUId());
			} catch (Exception e) {
				msg = "该条数据出错，请检查！\n"+
						"线路名称："+datas.get(i).get("LINE_NAME")+"\n"+
						"状态："+datas.get(i).get("STATUS")+"\n"+
						"上联端口编号："+datas.get(i).get("UP_PORT_CODE")+"\n"+
						"下联端口编号："+datas.get(i).get("DOWN_PORT_CODE")+"\n"+
						"线路类型："+datas.get(i).get("LINE_TYPE")+"\n"+
						"备注："+datas.get(i).get("REAMRK")+"\n"+
						"园区编号："+datas.get(i).get("CAMPUS_CODE")+"\n"+
						"启用时间："+datas.get(i).get("USE_TIME")+"\n"+
						"负责人："+datas.get(i).get("PRINCIPAL_NAME")+"\n"+
						"使用单位："+datas.get(i).get("USE_DEPT_NAME")+"\n"+
						"管理单位："+datas.get(i).get("MANAGE_DEPT_NAME")+"\n"+
						"光缆编号："+datas.get(i).get("FIBERCABLE_CODE");

				e.printStackTrace();
			}

			line.setCreateTime(Utils.getTimeStamp());
			line.setCreatePersonId(userId);
			
			lines.add(line);
		}

		Res res = Res.Res("0", "", null);
		if (msg != null) {
			res.setCode("1");
			res.setMessage(msg);
		} 

		Integer count = lineService.addLines(lines);
		if (count == 0) {
			res.setCode("1");
			res.setMessage("无数据！");
		} 

		return res;
	}

}
