package com.ncu.springboot.provider.resource.bizservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.osgi.service.component.annotations.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.ncu.springboot.api.resource.bizservice.PipeWellheadBizService;
import com.ncu.springboot.api.resource.bizservice.PipelineBizService;
import com.ncu.springboot.api.resource.constant.PipelineConstant;
import com.ncu.springboot.api.resource.entity.PipeWellhead;
import com.ncu.springboot.api.resource.entity.Pipeline;
import com.ncu.springboot.common.util.Utils;
import com.ncu.springboot.biz.rs.Res;
import com.ncu.springboot.provider.resource.service.PipeWellheadService;
import com.ncu.springboot.provider.resource.service.PipelineService;
import com.ncu.springboot.util.UserUtil;

@Service
@Component
public class PipelineBizServiceImpl implements PipelineBizService{

	@Resource
	private PipelineService pipelineService;

	@Resource
	private PipeWellheadBizService pipeWellheadBizService;

	@Resource
	private FibercableBizServiceImpl fibercableBizServiceImpl;

	@Resource
	private PipeWellheadService pipeWellheadService;

	@Resource
	private CodeBizServiceImpl codeBizServiceImpl;
	
	@Autowired
	private UserUtil userUtil;

	public Integer getTotal(String pipelineCode,Integer[] campusIds,Integer pipelineType,String pipelineName,Integer status,Integer wellheadId) {
		return pipelineService.getTotal(pipelineCode,campusIds, pipelineType, pipelineName, status, wellheadId);
	}

	@Transactional
	public List<Map<String, Object>> queryList(String pipelineCode,Integer[] campusIds,Integer pipelineType,String pipelineName,Integer status,Integer wellheadId, Integer size,Integer num) {
		if(size != null && num !=null) {
			num = (num-1)*size;
		}
		return pipelineService.queryList(pipelineCode,campusIds, pipelineType, pipelineName, status, wellheadId, size, num);
	}

	public Integer addPipeline(Pipeline pipeline) {
		Double distance = null;
		//计算两井口距离
		if(pipeline.getWellheadId1()!=null && pipeline.getWellheadId2()!=null){
			Map<String, Object> pipeWellhead = pipeWellheadBizService.query(pipeline.getWellheadId1(),null);
			if (pipeWellhead != null && pipeWellhead.get("latitude")!=null && pipeWellhead.get("longitude")!=null) {
				Double[] gpsFrom = {Double.parseDouble(pipeWellhead.get("latitude").toString()),Double.parseDouble(pipeWellhead.get("longitude").toString())};
				pipeWellhead = pipeWellheadBizService.query(pipeline.getWellheadId2(),null);
				if (pipeWellhead != null && pipeWellhead.get("latitude")!=null && pipeWellhead.get("longitude")!=null) {
					Double[] gpsTo = {Double.parseDouble(pipeWellhead.get("latitude").toString()),Double.parseDouble(pipeWellhead.get("longitude").toString())};
					distance = Utils.getDistanceMeter(gpsFrom, gpsTo);
				}
			}
		}
		pipeline.setLength(String.format("%.4f", distance));
		return pipelineService.addPipeline(pipeline);
	}

	@Transactional
	public Integer delPipeline(Integer[] pipelineIds) {
		List<Pipeline> pipelines = new ArrayList<Pipeline>();
		for (Integer pipelineId : pipelineIds) {
			Pipeline pipeline = new Pipeline();
			pipeline.setPipelineId(pipelineId);
			pipelines.add(pipeline);
		}
		Pipeline pipelineExample = new Pipeline();
		pipelineExample.setStatus(PipelineConstant.DISCARD_STATUS);//废弃
		//根据管道删除光缆
		fibercableBizServiceImpl.delByPipeLine(pipelines);
		return pipelineService.delPipeline(pipelines,pipelineExample);
	}

	@Transactional
	public Integer delByWellhead(List<PipeWellhead> wellheads) {
		List<Pipeline> pipelines = new ArrayList<Pipeline>();
		List<Pipeline> pipelines2 = new ArrayList<Pipeline>();
		Pipeline pipelineExample = new Pipeline();
		pipelineExample.setStatus(PipelineConstant.DISCARD_STATUS);//废弃

		for (PipeWellhead wellhead : wellheads) {

			if (wellhead.getWellheadId()!=null) {
				Pipeline pipeline = new Pipeline();
				pipeline.setWellheadId1(wellhead.getWellheadId());

				//找出所有修改对象，以便修改下级
				pipelines2.addAll(pipelineService.selectPipelines(pipeline));
				pipelines.add(pipeline);
			}

		}

		for (PipeWellhead wellhead : wellheads) {

			if (wellhead.getWellheadId()!=null) {
				Pipeline pipeline = new Pipeline();
				pipeline.setWellheadId2(wellhead.getWellheadId());

				//找出所有修改对象，以便修改下级
				pipelines2.addAll(pipelineService.selectPipelines(pipeline));
				pipelines.add(pipeline);
			}

		}
		//根据管道删除光缆
		fibercableBizServiceImpl.delByPipeLine(pipelines2);
		return pipelineService.delPipeline(pipelines, pipelineExample);
	}

	public Integer editPipeline(Pipeline pipeline) {
		Double distance = null;
		//计算两井口距离
		if(pipeline.getWellheadId1()!=null && pipeline.getWellheadId2()!=null){
			Map<String, Object> pipeWellhead = pipeWellheadBizService.query(pipeline.getWellheadId1(),null);
			if (pipeWellhead != null && pipeWellhead.get("latitude")!=null && pipeWellhead.get("longitude")!=null) {
				Double[] gpsFrom = {Double.parseDouble(pipeWellhead.get("latitude").toString()),Double.parseDouble(pipeWellhead.get("longitude").toString())};
				pipeWellhead = pipeWellheadBizService.query(pipeline.getWellheadId2(),null);
				if (pipeWellhead != null && pipeWellhead.get("latitude")!=null && pipeWellhead.get("longitude")!=null) {
					Double[] gpsTo = {Double.parseDouble(pipeWellhead.get("latitude").toString()),Double.parseDouble(pipeWellhead.get("longitude").toString())};
					distance = Utils.getDistanceMeter(gpsFrom, gpsTo);
				}
			}
		}
		pipeline.setLength(String.format("%.4f", distance));
		return pipelineService.editPipeline(pipeline);
	}

	public Map<String, Object> query(Integer pipelineId,String pipeCode) {
		return pipelineService.query(pipelineId,pipeCode);
	}

	public List<Map<String,Object>> location(Integer pipelineId,Integer[] campusIds,Integer wellheadId,String pipelineCode) {
		return pipelineService.location(pipelineId,campusIds,wellheadId,pipelineCode);
	}

	@Override
	public List<Map<String, Object>> export(String pipelineCode,Integer[] campusIds,Integer pipelineType,String pipelineName,Integer status,Integer wellheadId) {
		return pipelineService.export(pipelineCode,campusIds, pipelineType, pipelineName, status, wellheadId);
	}

	@Override
	public Res importExcel(List<Map<String, String>> datas, Integer userId, Integer status, Integer type) {
		List<Pipeline> pipelines = new ArrayList<Pipeline>();
		List<String> wellheadCode1 = new ArrayList<String>();
		List<String> wellheadCode2 = new ArrayList<String>();
		List<String> deptNames = new ArrayList<String>();
		List<String> employeeNames = new ArrayList<String>();

		String msg = null;

		for (int i = 0; i < datas.size(); i++) {
			wellheadCode1.add(datas.get(i).get("WELLHEAD_CODE1"));
			wellheadCode2.add(datas.get(i).get("WELLHEAD_CODE2"));
			deptNames.add(datas.get(i).get("MANAGE_DEPT_NAME"));
			deptNames.add(datas.get(i).get("USE_DEPT_NAME"));
			employeeNames.add(datas.get(i).get("PRINCIPAL_NAME"));
		}
		Map<String, String> deptIds = Utils.listToMap(userUtil.getDeptMapByDeptName(deptNames), "name", "id");
		Map<String, String> employeeIds = Utils.listToMap(userUtil.getEmployeeMapByEmployeeName(employeeNames), "employeeName", "employeeId");
		Map<String, String> wellheadIds1 = Utils.listToMap(pipeWellheadService.selectIdByCode(wellheadCode1), "WELLHEAD_CODE", "WELLHEAD_ID");
		Map<String, String> wellheadIds2 = Utils.listToMap(pipeWellheadService.selectIdByCode(wellheadCode2), "WELLHEAD_CODE", "WELLHEAD_ID");

		Map<String, String> pipelineStatus = Utils.listToMap(codeBizServiceImpl.query(status, null), "option", "codeId");
		Map<String, String> pipelineType = Utils.listToMap(codeBizServiceImpl.query(type, null), "option", "codeId");


		for (int i = 0; i < datas.size(); i++) {
			Pipeline pipeline = new Pipeline();
			try {
				pipeline.setFunction(datas.get(i).get("FUNCTION"));
				pipeline.setPower(datas.get(i).get("POWER"));
				pipeline.setDiameter(datas.get(i).get("DIAMETER"));
				pipeline.setStatus(Integer.parseInt(pipelineStatus.get(datas.get(i).get("STATUS"))));
				pipeline.setPipelineName(datas.get(i).get("PIPELINE_NAME"));
				pipeline.setRemark(datas.get(i).get("REMARK"));
				pipeline.setPipelineType(Integer.parseInt(pipelineType.get(datas.get(i).get("PIPELINE_TYPE"))));
				pipeline.setDepth(datas.get(i).get("DEPTH"));
				pipeline.setLength(datas.get(i).get("LENGTH"));
				pipeline.setPrincipal(Integer.parseInt(employeeIds.get(datas.get(i).get("PRINCIPAL_NAME"))));
				pipeline.setUseDept(Integer.parseInt(deptIds.get(datas.get(i).get("USE_DEPT_NAME"))));
				pipeline.setManageDept(Integer.parseInt(deptIds.get(datas.get(i).get("MANAGE_DEPT_NAME"))));
				pipeline.setWellheadId1(Integer.parseInt(wellheadIds1.get(datas.get(i).get("WELLHEAD_CODE1"))));
				pipeline.setWellheadId2(Integer.parseInt(wellheadIds2.get(datas.get(i).get("WELLHEAD_CODE2"))));
				pipeline.setPipelineCode("GD"+Utils.getCodeByUUId());
			} catch (Exception e) {
				msg = "该条数据出错，请检查！\n"+
						"简介："+datas.get(i).get("FUNCTION")+"\n"+
						"功率："+datas.get(i).get("POWER")+"\n"+
						"半径："+datas.get(i).get("DIAMETER")+"\n"+
						"状态："+datas.get(i).get("STATUS")+"\n"+
						"管道名称："+datas.get(i).get("PIPELINE_NAME")+"\n"+
						"备注："+datas.get(i).get("REMARK")+"\n"+
						"管道类型："+datas.get(i).get("PIPELINE_TYPE")+"\n"+
						"深度："+datas.get(i).get("DEPTH")+"\n"+
						"长度："+datas.get(i).get("LENGTH")+"\n"+
						"负责人："+datas.get(i).get("PRINCIPAL_NAME")+"\n"+
						"使用单位："+datas.get(i).get("USE_DEPT_NAME")+"\n"+
						"管理单位："+datas.get(i).get("MANAGE_DEPT_NAME")+"\n"+
						"井口编号："+datas.get(i).get("WELLHEAD_CODE1")+"\n"+
						"井口编号："+datas.get(i).get("WELLHEAD_CODE2");

				e.printStackTrace();
			}

			pipeline.setCreateTime(Utils.getTimeStamp());
			pipeline.setCreatePersonId(userId);

			pipelines.add(pipeline);
		}

		Res res = Res.Res("0", "", null);
		if (msg != null) {
			res.setCode("1");
			res.setMessage(msg);
		} 

		Integer count = pipelineService.addPipelines(pipelines);
		if (count == 0) {
			res.setCode("1");
			res.setMessage("无数据！");
		} 

		return res;
	}
}
