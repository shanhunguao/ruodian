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
import com.ncu.springboot.api.resource.bizservice.PipelineBizService;
import com.ncu.springboot.api.resource.constant.PipelineConstant;
import com.ncu.springboot.api.resource.entity.Pipeline;
import com.ncu.springboot.common.util.ExcelUtil;
import com.ncu.springboot.common.util.Utils;
import com.ncu.springboot.log.LogUtil;
import com.ncu.springboot.biz.rs.Res;

@RestController
@RequestMapping("/pipeline")
public class PipelineController {

	@Reference
	private PipelineBizService pipelineBizService;
	
	@Reference
	private CodeBizService codeBizService;
	
	@Reference
	private UserCacheBizService userCacheBizService;
	
	@Autowired
	private LogUtil<Pipeline> logUtil;
	
	@RequestMapping("/getTotal")
	@Permission
	public Res getTotal(String pipelineCode,Integer[] campusIds,Integer pipelineType,String pipelineName,Integer status,Integer wellheadId) {
		Integer data = pipelineBizService.getTotal(pipelineCode,campusIds, pipelineType, pipelineName, status, wellheadId);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/queryList")
	@Permission
	public Res queryList(String pipelineCode,Integer[] campusIds,Integer pipelineType,String pipelineName,Integer status,Integer wellheadId, Integer size,Integer num) {
		List<Map<String,Object>> data = pipelineBizService.queryList(pipelineCode,campusIds, pipelineType, pipelineName, status, wellheadId, size,num);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/addPipeline")
	@Permission
	public Res addPipeline(Pipeline pipeline){
		pipeline.setCreateTime(Utils.getTimeStamp());
		pipeline.setPipelineCode("GD"+Utils.getCodeByUUId());
		Integer data = pipelineBizService.addPipeline(pipeline);
		logUtil.saveLog("add", "添加管道", pipeline, null);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/delPipeline")
	@Permission
	public Res delPipeline(Integer[] pipelineIds){
		if(pipelineIds == null || pipelineIds.length<1) {
			return Res.ERROR("1", "未选择对象！");
		}
		Integer data = pipelineBizService.delPipeline(pipelineIds);
		logUtil.saveLog("delete", "删除管道", null, pipelineIds);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/editPipeline")
	@Permission
	public Res editPipeline(Pipeline pipeline){
		pipeline.setUpdateTime(Utils.getTimeStamp());
		Integer data = pipelineBizService.editPipeline(pipeline);
		logUtil.saveLog("edit", "编辑管道", pipeline, null);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/query")
	@Permission
	public Res query(Integer pipelineId,String pipelineCode) {
		Map<String, Object> pipeline = pipelineBizService.query(pipelineId,pipelineCode);
		return Res.SUCCESS(pipeline);
	}
	
	@RequestMapping("/location")
	@Permission
	public Res location(Integer pipelineId,Integer[] campusIds,Integer wellheadId,String pipelineCode){
		List<Map<String,Object>> data = pipelineBizService.location(pipelineId,campusIds,wellheadId,pipelineCode);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/importExample")
	public void importExample(HttpServletResponse response) {
		List<Map<String, Object>> datas = new ArrayList<Map<String,Object>>();
		Map<String, Object> data = new HashMap<String, Object>();
		
		//状态
		List<Map<String, Object>> status = codeBizService.query(PipelineConstant.STATUS, null);
		StringBuffer statusValue = new StringBuffer();
		for (Map<String, Object> map : status) {
			statusValue.append(map.get("option").toString());
		}
		data.put("STATUS", statusValue);
		
		//类型
		List<Map<String, Object>> type = codeBizService.query(PipelineConstant.TYPE, null);
		StringBuffer typeValue = new StringBuffer();
		for (Map<String, Object> map : type) {
			typeValue.append(map.get("option").toString());
		}
		data.put("PIPELINE_TYPE", typeValue);
		datas.add(data);
		
		List<String> columns = new ArrayList<String>();
		columns.add("简介");
		columns.add("功率");
		columns.add("半径");
		columns.add("状态");
		columns.add("管道名称");
		columns.add("备注");
		columns.add("管道类型");
		columns.add("深度");
		columns.add("长度");
		columns.add("负责人");
		columns.add("使用单位");
		columns.add("管理单位");
		columns.add("井口编号");
		columns.add("井口编号");
		List<String> keys = new ArrayList<String>();
		keys.add("FUNCTION");
		keys.add("POWER");
		keys.add("DIAMETER");
		keys.add("STATUS");
		keys.add("PIPELINE_NAME");
		keys.add("REMARK");
		keys.add("PIPELINE_TYPE");
		keys.add("DEPTH");
		keys.add("LENGTH");
		keys.add("PRINCIPAL_NAME");
		keys.add("USE_DEPT_NAME");
		keys.add("MANAGE_DEPT_NAME");
		keys.add("WELLHEAD_CODE1");
		keys.add("WELLHEAD_CODE2");
		try {
			ExcelUtil.export(datas, "管道导入模板", columns,response,keys,"pipelineExample");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/export")
	public void export(HttpServletResponse response,String pipelineCode,Integer[] campusIds,Integer pipelineType,String pipelineName,Integer status,Integer wellheadId) {
		List<Map<String, Object>> datas = pipelineBizService.export(pipelineCode,campusIds, pipelineType, pipelineName, status, wellheadId);
		List<String> columns = new ArrayList<String>();
		columns.add("简介");
		columns.add("功率");
		columns.add("半径");
		columns.add("状态");
		columns.add("管道名称");
		columns.add("备注");
		columns.add("管道类型");
		columns.add("深度");
		columns.add("长度");
		columns.add("负责人");
		columns.add("使用单位");
		columns.add("管理单位");
		columns.add("井口编号");
		columns.add("井口编号");
		List<String> keys = new ArrayList<String>();
		keys.add("FUNCTION");
		keys.add("POWER");
		keys.add("DIAMETER");
		keys.add("STATUS");
		keys.add("PIPELINE_NAME");
		keys.add("REMARK");
		keys.add("PIPELINE_TYPE");
		keys.add("DEPTH");
		keys.add("LENGTH");
		keys.add("PRINCIPAL_NAME");
		keys.add("USE_DEPT_NAME");
		keys.add("MANAGE_DEPT_NAME");
		keys.add("WELLHEAD_CODE1");
		keys.add("WELLHEAD_CODE2");
		try {
			ExcelUtil.export(datas, "管道", columns,response,keys,"pipeline");
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
        keys.add("FUNCTION");
		keys.add("POWER");
		keys.add("DIAMETER");
		keys.add("STATUS");
		keys.add("PIPELINE_NAME");
		keys.add("REMARK");
		keys.add("PIPELINE_TYPE");
		keys.add("DEPTH");
		keys.add("LENGTH");
		keys.add("PRINCIPAL_NAME");
		keys.add("USE_DEPT_NAME");
		keys.add("MANAGE_DEPT_NAME");
		keys.add("WELLHEAD_CODE1");
		keys.add("WELLHEAD_CODE2");
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
		
		Res result = pipelineBizService.importExcel(datas,employeeId,PipelineConstant.STATUS,PipelineConstant.TYPE);
		return result;
	}
}
