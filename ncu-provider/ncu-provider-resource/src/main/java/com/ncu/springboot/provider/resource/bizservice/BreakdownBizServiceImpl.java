package com.ncu.springboot.provider.resource.bizservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.osgi.service.component.annotations.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.ncu.springboot.api.resource.bizservice.BreakdownBizService;
import com.ncu.springboot.api.resource.entity.Breakdown;
import com.ncu.springboot.common.util.Utils;
import com.ncu.springboot.biz.rs.Res;
import com.ncu.springboot.provider.resource.service.BreakdownService;
import com.ncu.springboot.provider.resource.service.CampusService;
import com.ncu.springboot.provider.resource.service.FibercableService;
import com.ncu.springboot.provider.resource.service.PipeWellheadService;

@Service
@Component
public class BreakdownBizServiceImpl implements BreakdownBizService{


	@Resource
	private BreakdownService breakdownService;
	
	@Resource
	private CodeBizServiceImpl codeBizServiceImpl;
	
	@Resource 
	private FibercableService fibercableService;
	
	@Resource
	private PipeWellheadService pipeWellheadService;
	
	@Resource
	private CampusService campusService;
	
	public Integer getTotal(String breakdownCode, Integer[] campusIds, String breakdownName, Integer status,
			Integer fibercableId, Integer breakdownType) {
		return breakdownService.getTotal(breakdownCode, campusIds, breakdownName, status, fibercableId, breakdownType);
	}

	public List<Map<String, Object>> queryList(String breakdownCode, Integer[] campusIds, String breakdownName,
			Integer status, Integer fibercableId, Integer breakdownType,Integer size,Integer num) {
		if(size != null && num !=null) {
			num = (num-1)*size;
		}
		return breakdownService.queryList(breakdownCode, campusIds, breakdownName, status, fibercableId, breakdownType, size, num);
	}

	public Integer addBreakdown(Breakdown breakdown) {
		return breakdownService.addBreakdown(breakdown);
	}

	@Transactional
	public Integer delBreakdown(Integer[] breakdownIds) {
		return breakdownService.delBreakdown(breakdownIds);
	}

	public Integer editBreakdown(Breakdown breakdown) {
		return breakdownService.editBreakdown(breakdown);
	}

	public List<Map<String, Object>> location(Integer breakdownId, Integer campusId) {
		return breakdownService.location(breakdownId, campusId);
	}

	public Map<String, Object> query(Integer breakdownId,String breakdownCode) {
		return breakdownService.query(breakdownId,breakdownCode);
	}

	public List<Map<String, Object>> export(String breakdownCode, Integer[] campusIds, String breakdownName,
			Integer status, Integer fibercableId, Integer breakdownType) {
		return breakdownService.export(breakdownCode, campusIds, breakdownName, status, fibercableId, breakdownType);
	}

	@Transactional
	public Res importExcel(List<Map<String, String>> datas,Integer userId,Integer statusCode,Integer typeCode) {
		List<Breakdown> breakdowns = new ArrayList<Breakdown>();
		List<String> fibercableCodes = new ArrayList<String>();
		List<String> wellheadCodes = new ArrayList<String>();
		List<String> campusCodes = new ArrayList<String>();
		
		String msg = null;
		
		for (int i = 0; i < datas.size(); i++) {
			fibercableCodes.add(datas.get(i).get("FIBERCABLE_CODE"));
			wellheadCodes.add(datas.get(i).get("WELLHEAD_CODE"));
			campusCodes.add(datas.get(i).get("CAMPUS_CODE"));
		}
		
		Map<String, String> fibercableIds = Utils.listToMap(fibercableService.selectIdByCode(fibercableCodes), "FIBERCABLE_CODE", "FIBERCABLE_ID");
		Map<String, String> wellheadIds = Utils.listToMap(pipeWellheadService.selectIdByCode(wellheadCodes), "WELLHEAD_CODE", "WELLHEAD_ID");
		Map<String, String> campusIds = Utils.listToMap(campusService.selectIdByCode(campusCodes), "CAMPUS_CODE", "CAMPUS_ID");
		
		Map<String, String> breakdownStatus =Utils.listToMap(codeBizServiceImpl.query(statusCode, null), "option", "codeId");
		Map<String, String> types = Utils.listToMap(codeBizServiceImpl.query(typeCode, null), "option", "codeId");
		
		
		
		for (int i = 0; i < datas.size(); i++) {
			Breakdown breakdown = new Breakdown();
			try {
				breakdown.setBreakdownName(datas.get(i).get("BREAKDOWN_NAME"));
				breakdown.setLocation(datas.get(i).get("LOCATION"));
				breakdown.setRemark(datas.get(i).get("REMARK"));
				breakdown.setStatus(Integer.parseInt(breakdownStatus.get(datas.get(i).get("STATUS"))));
				breakdown.setBreakdownType(Integer.parseInt(types.get(datas.get(i).get("BREAKDOWN_TYPE"))));
				breakdown.setFibercableId(Integer.parseInt(fibercableIds.get(datas.get(i).get("FIBERCABLE_CODE"))));
				breakdown.setWellheadId(Integer.parseInt(wellheadIds.get(datas.get(i).get("WELLHEAD_CODE"))));
				breakdown.setCampusId(Integer.parseInt(campusIds.get(datas.get(i).get("CAMPUS_CODE"))));
				breakdown.setBreakdownCode("GZ"+Utils.getCodeByUUId());
			} catch (Exception e) {
				msg = "该条数据出错，请检查！\n"+
					  "光缆编号："+datas.get(i).get("FIBERCABLE_CODE")+"\n"+
					  "井口编号："+datas.get(i).get("WELLHEAD_CODE")+"\n"+
					  "定位："+datas.get(i).get("LOCATION")+"\n"+
					  "园区编号："+datas.get(i).get("CAMPUS_CODE")+"\n"+
					  "备注："+datas.get(i).get("REMARK")+"\n"+
					  "状态："+datas.get(i).get("STATUS")+"\n"+
					  "故障类型："+datas.get(i).get("BREAKDOWN_TYPE")+"\n"+
					  "故障名称："+datas.get(i).get("BREAKDOWN_NAME");
				e.printStackTrace();
			}
			
			breakdown.setCreateTime(Utils.getTimeStamp());
			breakdown.setCreatePersonId(userId);
			
			breakdowns.add(breakdown);
		}
		Res res = Res.Res("0", "", null);
		if (msg != null) {
			res.setCode("1");
			res.setMessage(msg);
		} 
		
		Integer count = breakdownService.addBreakdowns(breakdowns);
		if (count == 0) {
			res.setCode("1");
			res.setMessage("无数据！");
		} 
		
		return res;
	}

}
