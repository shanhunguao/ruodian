package com.ncu.springboot.provider.resource.bizservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.osgi.service.component.annotations.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.ncu.springboot.api.resource.bizservice.CampusBizService;
import com.ncu.springboot.api.resource.constant.CampusConstant;
import com.ncu.springboot.api.resource.entity.Campus;
import com.ncu.springboot.common.util.Utils;
import com.ncu.springboot.biz.rs.Res;
import com.ncu.springboot.provider.resource.service.CampusService;
import com.ncu.springboot.util.UserUtil;

@Component
@Service
public class CampusBizServiceImpl implements CampusBizService{
	
	@Resource
	private CampusService campusService;
	
	@Resource
	private BuildingBizServiceImpl buildingBizServiceImpl;
	
	@Resource
	private CodeBizServiceImpl codeBizServiceImpl;
	
	@Autowired
	private UserUtil userUtil;
	
	public Integer getTotal(String campusCode,Integer manageDept, String campusName, Integer status) {
		return campusService.getTotal(campusCode,manageDept, campusName, status);
	}

	public List<Map<String, Object>> queryList(String campusCode,Integer manageDept, String campusName, Integer status,
			Integer num, Integer size) {
		if(size != null && num !=null) {
			num = (num-1)*size;
			}
		return campusService.queryList(campusCode,manageDept, campusName, status, num, size);
	}

	public Integer addCampus(Campus campus) {
		return campusService.addCampus(campus);
	}

	@Transactional
	public Integer delCampus(Integer[] campusIds) {
		List<Campus> campuss = new ArrayList<Campus>();//园区
		for (Integer campusId : campusIds) {
			Campus campus = new Campus();
			campus.setCampusId(campusId);
			campuss.add(campus);
		}
		Campus Campusexample = new Campus();
		Campusexample.setStatus(CampusConstant.DISCARD_STATUS);//废弃
		//关联删除楼栋
		buildingBizServiceImpl.delByCampus(campuss);
		return campusService.delCampus(campuss,Campusexample);
	}

	public Integer editCampus(Campus campus) {
		return campusService.editCampus(campus);
	}

	public Map<String, Object> location(Integer campusId,String campusCode,Integer manageDept) {
		return campusService.location(campusId,campusCode,manageDept);
	}

	public Map<String, Object> query(Integer campusId,String campusCode) {
		return campusService.query(campusId,campusCode);
	}

	public List<Map<String, Object>> export(String campusCode,Integer manageDept, String campusName, Integer status) {
		return campusService.export(campusCode,manageDept, campusName, status);
	}

	@Override
	public Res importExcel(List<Map<String, String>> datas, Integer userId, Integer status) {
		List<Campus> campuss = new ArrayList<Campus>();
		List<String> deptNames = new ArrayList<String>();
		String msg = null;
		
		for (int i = 0; i < datas.size(); i++) {
			deptNames.add(datas.get(i).get("MANAGE_DEPT_NAME"));
			deptNames.add(datas.get(i).get("USE_DEPT_NAME"));
		}
		
		Map<String, String> deptIds = Utils.listToMap(userUtil.getDeptMapByDeptName(deptNames), "name", "id");
		Map<String, String> campusStatus =Utils.listToMap(codeBizServiceImpl.query(status, null), "option", "codeId");
		
		for (int i = 0; i < datas.size(); i++) {
			Campus campus = new Campus();
			try {
				campus.setCampusName(datas.get(i).get("CAMPUS_NAME"));
				campus.setRemark(datas.get(i).get("REMARK"));
				campus.setFunction(datas.get(i).get("FUNCTION"));
				campus.setArea(datas.get(i).get("AREA"));
				campus.setLongitude(datas.get(i).get("LONGITUDE"));
				campus.setLatitude(datas.get(i).get("LATITUDE"));
				campus.setPrincipal(userId);//目前没有服务能通过工号获得map
				campus.setManageDept(Integer.parseInt(deptIds.get(datas.get(i).get("MANAGE_DEPT_NAME"))));
				campus.setUseDept(Integer.parseInt(deptIds.get(datas.get(i).get("USE_DEPT_NAME"))));
				campus.setStatus(Integer.parseInt(campusStatus.get(datas.get(i).get("STATUS"))));
				campus.setCampusCode("YQ"+Utils.getCodeByUUId());
			} catch (Exception e) {
				msg = "该条数据出错，请检查！\n"+
					  "园区名称："+datas.get(i).get("CAMPUS_NAME")+"\n"+
					  "状态："+datas.get(i).get("STATUS")+"\n"+
					  "备注："+datas.get(i).get("REMARK")+"\n"+
					  "简介："+datas.get(i).get("FUNCTION")+"\n"+
					  "面积："+datas.get(i).get("AREA")+"\n"+
					  "经度："+datas.get(i).get("LONGITUDE")+"\n"+
					  "纬度："+datas.get(i).get("LATITUDE")+"\n"+
					  "负责人："+datas.get(i).get("PRINCIPAL_NAME")+"\n"+
					  "管理单位："+datas.get(i).get("MANAGE_DEPT_NAME")+"\n"+
					  "使用单位："+datas.get(i).get("USE_DEPT_NAME");
				e.printStackTrace();
			}
			
			campus.setCreateTime(Utils.getTimeStamp());
			campus.setCreatePersonId(userId);
			
			campuss.add(campus);
		}
		
		Res res = Res.Res("0", "", null);
		if (msg != null) {
			res.setCode("1");
			res.setMessage(msg);
		} 
		
		Integer count = campusService.addCampuss(campuss);
		if (count == 0) {
			res.setCode("1");
			res.setMessage("无数据！");
		} 
		
		return res;
	}

	@Override
	public List<Map<String, Object>> selectIdByName(List<String> campusNames) {
		return campusService.selectIdByName(campusNames);
	}
	
}
