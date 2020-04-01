package com.ncu.springboot.provider.resource.bizservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.osgi.service.component.annotations.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.ncu.springboot.api.resource.bizservice.FibercableBizService;
import com.ncu.springboot.api.resource.constant.FibercableConstant;
import com.ncu.springboot.api.resource.entity.Fibercable;
import com.ncu.springboot.api.resource.entity.Pipeline;
import com.ncu.springboot.common.util.Utils;
import com.ncu.springboot.biz.rs.Res;
import com.ncu.springboot.provider.resource.service.CampusService;
import com.ncu.springboot.provider.resource.service.FibercableService;
import com.ncu.springboot.provider.resource.service.PipeWellheadService;
import com.ncu.springboot.util.UserUtil;

@Component
@Service
public class FibercableBizServiceImpl implements FibercableBizService {

	@Resource
	private FibercableService fibercableService;

	@Resource
	private PipelineBizServiceImpl pipelineBizServiceImpl;

	@Resource
	private LineBizServiceImpl lineBizServiceImpl;
	
	@Resource
	private CodeBizServiceImpl codeBizServiceImpl;
	
	@Resource
	private CampusService campusService;
	
	@Resource
	private PipeWellheadService pipeWellheadService;
	
	@Autowired
	private UserUtil userUtil;

	public Integer getTotal(Integer[] campusIds,Integer originWellhead,Integer endWellhead,String fibercableName,Integer status,Integer fibercableType,String fibercableCode) {
		return fibercableService.getTotal(campusIds, originWellhead, endWellhead, fibercableName,status,fibercableType,fibercableCode);
	}

	public List<Map<String, Object>> queryList(Integer[] campusIds,Integer originWellhead,Integer endWellhead,String fibercableName,Integer status,Integer fibercableType,String fibercableCode, Integer size, Integer num) {
		if(size != null && num !=null) {
			num = (num-1)*size;
		}
		return fibercableService.queryList(campusIds, originWellhead, endWellhead, fibercableName,status,fibercableType,fibercableCode, size, num);
	}

	@Transactional
	public Integer addFibercable(Fibercable fibercable,Integer[] pipelineIds) {
		Double length = 0.0;
		if (pipelineIds != null) {
			for (Integer pipelineId : pipelineIds) {
				Map<String, Object> pipeline = pipelineBizServiceImpl.query(pipelineId,null);
				if(pipeline!=null&&pipeline.get("length")!=null) {
					length += Double.parseDouble(pipeline.get("length").toString());
				}
			}
		}
		fibercable.setLength(length.toString());
		return fibercableService.addFibercable(fibercable,pipelineIds);
	}

	@Transactional
	public Integer delFibercable(Integer[] fibercableIds) {
		List<Fibercable> fibercables = new ArrayList<Fibercable>();

		for (Integer fibercableId : fibercableIds) {
			Fibercable fibercable = new Fibercable();
			fibercable.setFibercableId(fibercableId);
			fibercables.add(fibercable);
		}

		Fibercable fibercableExample = new Fibercable();
		fibercableExample.setStatus(FibercableConstant.DISCARD_STATUS);//废弃

		//根据光缆删除线路
		lineBizServiceImpl.delByFibercable(fibercables);
		return fibercableService.delFibercable(fibercables,fibercableExample);
	}

	@Transactional
	public Integer delByPipeLine(List<Pipeline> pipelines) {
		List<Integer> pipelineIds = new ArrayList<Integer>();
		List<Fibercable> fibercables = new ArrayList<Fibercable>();

		for (Pipeline pipeline : pipelines) {
			pipelineIds.add(pipeline.getPipelineId());
		}

		Integer status = FibercableConstant.DISCARD_STATUS;//废弃
		//根据光缆删除线路
		fibercables.addAll(fibercableService.selectByPipelineId(pipelineIds));
		lineBizServiceImpl.delByFibercable(fibercables);

		//如果管道id为空则没有过滤条件，所有的数据都会被修改
		if (pipelineIds.size()>0) {
			return fibercableService.delByPipeLine(pipelineIds,status);
		} else {
			return 0;
		}

	}

	@Transactional
	public Integer editFibercable(Fibercable fibercable,Integer[] pipelineIds) {
		Double length = 0.0;
		if (pipelineIds != null) {
			for (Integer pipelineId : pipelineIds) {
				Map<String, Object> pipeline = pipelineBizServiceImpl.query(pipelineId,null);
				if(pipeline!=null&&pipeline.get("length")!=null) {
					length += Double.parseDouble(pipeline.get("length").toString());
				}
			}
		}
		fibercable.setLength(length.toString());
		return fibercableService.editFibercable(fibercable,pipelineIds);
	}

	@Transactional
	public Map<String, Object> query(Integer fibercableId,String fibercablerCode) {
		Map<String, Object> fibercable = fibercableService.query(fibercableId,fibercablerCode);
		if(fibercable.get("originWellhead")==null) {
			return fibercable;
		}
		String wellheadId = fibercable.get("originWellhead").toString();
		List<Pipeline> pipelines = new ArrayList<Pipeline>();
		List<Map<String, Object>> results = fibercableService.location(fibercableId, null, null);

		boolean flag = false;
		int count = results.size();
		while (count>0) {
			flag = false;
			//遍历寻找管道
			for (int i=0;i<results.size();i++) {
				Map<String, Object> result = results.get(i);
				String[] wellheadIds = result.get("wellheadId").toString().split(",");

				if (wellheadIds.length!=2) {
					results.remove(i);
					break;
				}

				for (String id : wellheadIds) {
					if (id.equals(wellheadId) ) {	//如果id相同，则开始计算管道长度
						Pipeline pipeline = new Pipeline();
						pipeline.setPipelineId(Integer.parseInt(result.get("pipelineId").toString()));
						pipeline.setWellheadId1(Integer.parseInt(id));
						if (id == wellheadIds[0]) {
							pipeline.setWellheadId2(Integer.parseInt(wellheadIds[1]));
							wellheadId = pipeline.getWellheadId2().toString();
						}else {
							pipeline.setWellheadId2(Integer.parseInt(wellheadIds[0]));
							wellheadId = pipeline.getWellheadId2().toString();
						}
						pipelines.add(pipeline);
						results.remove(i);

						//只要找到井口id相同的值就可以跳出此次循环，寻找下一段管道
						flag = true;
						break;
					}
				}

				if (flag) {
					break;
				}
			}

			count--;
		}


		fibercable.put("pipelines", pipelines);
		return fibercable;
	}

	@Override
	public List<Map<String, Object>> export(Integer[] campusIds,Integer originWellhead,Integer endWellhead,String fibercableName,Integer status,Integer fibercableType,String fibercableCode) {
		return fibercableService.export(campusIds, originWellhead, endWellhead, fibercableName,status,fibercableType,fibercableCode);
	}

	@Override
	public List<Map<String, Object>> location(Integer fibercableId,String fibercableCode,Integer[] campusIds) {
		return fibercableService.location(fibercableId,fibercableCode,campusIds);
	}

	@Override
	public Res importExcel(List<Map<String, String>> datas, Integer userId, Integer status, Integer type) {
		List<Fibercable> fibercables = new ArrayList<Fibercable>();
		List<String> campusCodes = new ArrayList<String>();
		List<String> originWellheadCodes = new ArrayList<String>();
		List<String> endWellheadCodes = new ArrayList<String>();
		List<String> parentCodes = new ArrayList<String>();
		List<String> employeeNames = new ArrayList<String>();
		List<String> deptNames = new ArrayList<String>();

		String msg = null;

		for (int i = 0; i < datas.size(); i++) {
			campusCodes.add(datas.get(i).get("CAMPUS_CODE"));
			originWellheadCodes.add(datas.get(i).get("ORIGIN_WELLHEAD_CODE"));
			endWellheadCodes.add(datas.get(i).get("END_WELLHEAD_CODE"));
			parentCodes.add(datas.get(i).get("PARENT_CODE"));
			employeeNames.add(datas.get(i).get("MAINTAIN_PERSON_NAME"));
			deptNames.add(datas.get(i).get("MANAGE_DEPT_NAME"));
			deptNames.add(datas.get(i).get("USE_DEPT_NAME"));
		}

		Map<String, String> deptIds = Utils.listToMap(userUtil.getDeptMapByDeptName(deptNames), "name", "id");
		Map<String, String> employeeIds = Utils.listToMap(userUtil.getEmployeeMapByEmployeeName(employeeNames), "employeeName", "employeeId");
		Map<String, String> campusIds = Utils.listToMap(campusService.selectIdByCode(campusCodes), "CAMPUS_CODE", "CAMPUS_ID");
		Map<String, String> originWellheadIds = Utils.listToMap(pipeWellheadService.selectIdByCode(originWellheadCodes), "WELLHEAD_CODE", "WELLHEAD_ID");
		Map<String, String> endWellheadIds = Utils.listToMap(pipeWellheadService.selectIdByCode(endWellheadCodes), "WELLHEAD_CODE", "WELLHEAD_ID");
		Map<String, String> parentIds = Utils.listToMap(fibercableService.selectIdByCode(parentCodes), "FIBERCABLE_CODE", "FIBERCABLE_ID");

		Map<String, String> fibercableStatus = Utils.listToMap(codeBizServiceImpl.query(status, null), "option", "codeId");
		Map<String, String> fibercableType = Utils.listToMap(codeBizServiceImpl.query(type, null), "option", "codeId");


		for (int i = 0; i < datas.size(); i++) {
			Fibercable fibercable = new Fibercable();
			try {
				fibercable.setFibercableName(datas.get(i).get("FIBERCABLE_NAME"));
				fibercable.setFibercableType(Integer.parseInt(fibercableType.get(datas.get(i).get("FIBERCABLE_TYPE"))));
				fibercable.setTotalCoreNum(Integer.parseInt(datas.get(i).get("TOTAL_CORE_NUM")));
				fibercable.setLayoutTime(Utils.getFormatDateTime("YYYY-MM-DD", datas.get(i).get("LAYOUT_TIME")));
				fibercable.setStatus(Integer.parseInt(fibercableStatus.get(datas.get(i).get("STATUS"))));
				fibercable.setFunction(datas.get(i).get("FUNCTION"));
				fibercable.setOriginWellhead(Integer.parseInt(originWellheadIds.get(datas.get(i).get("ORIGIN_WELLHEAD_CODE"))));
				fibercable.setEndWellhead(Integer.parseInt(endWellheadIds.get(datas.get(i).get("END_WELLHEAD_CODE"))));
				fibercable.setParentId(Integer.parseInt(parentIds.get(datas.get(i).get("PARENT_CODE"))));
				fibercable.setLength(datas.get(i).get("LENGTH"));
				fibercable.setUseDept(Integer.parseInt(deptIds.get(datas.get(i).get("USE_DEPT_NAME"))));
				fibercable.setManageDept(Integer.parseInt(deptIds.get(datas.get(i).get("MANAGE_DEPT_NAME"))));
				fibercable.setMaintainPerson(Integer.parseInt(employeeIds.get(datas.get(i).get("MAINTAIN_PERSON_NAME"))));
				fibercable.setCampusId(Integer.parseInt(campusIds.get(datas.get(i).get("CAMPUS_CODE"))));
				fibercable.setFibercableCode("GL"+Utils.getCodeByUUId());
			} catch (Exception e) {
				msg = "该条数据出错，请检查！\n"+
						"光缆名称："+datas.get(i).get("FIBERCABLE_NAME")+"\n"+
						"光缆类型："+datas.get(i).get("FIBERCABLE_TYPE")+"\n"+
						"线芯总数："+datas.get(i).get("TOTAL_CORE_NUM")+"\n"+
						"安置时间："+datas.get(i).get("LAYOUT_TIME")+"\n"+
						"状态："+datas.get(i).get("STATUS")+"\n"+
						"简介："+datas.get(i).get("FUNCTION")+"\n"+
						"起始井口编号："+datas.get(i).get("ORIGIN_WELLHEAD_CODE")+"\n"+
						"终点井口编号："+datas.get(i).get("END_WELLHEAD_CODE")+"\n"+
						"上级光缆编号："+datas.get(i).get("PARENT_CODE")+"\n"+
						"长度："+datas.get(i).get("LENGTH")+"\n"+
						"负责人："+datas.get(i).get("PRINCIPAL_NAME")+"\n"+
						"使用单位："+datas.get(i).get("USE_DEPT_NAME")+"\n"+
						"管理单位："+datas.get(i).get("MANAGE_DEPT_NAME")+"\n"+
						"维护人："+datas.get(i).get("MAINTAIN_PERSON_NAME")+"\n"+
						"园区编号："+datas.get(i).get("CAMPUS_CODE");

				e.printStackTrace();
			}

			fibercable.setCreateTime(Utils.getTimeStamp());
			fibercable.setCreatePersonId(userId);
			
			fibercables.add(fibercable);
		}

		Res res = Res.Res("0", "", null);
		if (msg != null) {
			res.setCode("1");
			res.setMessage(msg);
		} 

		Integer count = fibercableService.addFibercables(fibercables);
		if (count == 0) {
			res.setCode("1");
			res.setMessage("无数据！");
		} 

		return res;
	}

}
