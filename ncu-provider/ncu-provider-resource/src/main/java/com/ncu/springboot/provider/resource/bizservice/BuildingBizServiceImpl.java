package com.ncu.springboot.provider.resource.bizservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.osgi.service.component.annotations.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.ncu.springboot.api.resource.bizservice.BuildingBizService;
import com.ncu.springboot.api.resource.constant.BuildingConstant;
import com.ncu.springboot.api.resource.entity.Building;
import com.ncu.springboot.api.resource.entity.Campus;
import com.ncu.springboot.common.util.Utils;
import com.ncu.springboot.biz.rs.Res;
import com.ncu.springboot.provider.resource.service.BuildingService;
import com.ncu.springboot.provider.resource.service.CampusService;
import com.ncu.springboot.util.UserUtil;


@Component
@Service
public class BuildingBizServiceImpl implements BuildingBizService {
	
	@Resource
	private BuildingService buildingService;
	
	@Resource
	private EquipmentRoomBizServiceImpl	equipmentRoomBizServiceImpl;
	
	@Resource
	private CampusService campusService;
	
	@Resource
	private CodeBizServiceImpl codeBizServiceImpl;
	
	@Autowired
	private UserUtil userUtil;
	
	@Override
	public Integer getTotal(String buildingCode,Integer[] campusIds,String buildingName,Integer status) {
		return buildingService.getTotal(buildingCode,campusIds, buildingName, status);
	}

	@Override
	public List<Map<String, Object>> queryList(String buildingCode,Integer[] campusIds,String buildingName,Integer status, Integer size, Integer num) {
		if(size != null && num !=null) {
		num = (num-1)*size;
		}
		return buildingService.queryList(buildingCode,campusIds, buildingName, status, size, num);
	}

	@Override
	public Integer addBuilding(Building building) {
		return buildingService.addBuilding(building);
	}

	@Transactional
	public Integer delBuilding(Integer[] buildingIds) {
		List<Building> buildings = new ArrayList<Building>();
		for (Integer buildingId : buildingIds) {
			Building building = new Building();
			building.setBuildingId(buildingId);
			buildings.add(building);
		}
		//创建楼栋修改模板
		Building buildingExample = new Building();
		buildingExample.setStatus(BuildingConstant.DISCARD_STATUS);//废弃
		//关联删除设备间
		equipmentRoomBizServiceImpl.delByBuilding(buildings);
		return buildingService.delBuilding(buildings,buildingExample);
	}
	
	/**
	 * 目前业务不需要判断上级修改状态
	 * @param campus
	 * @return
	 */
	@Transactional
	public Integer delByCampus(List<Campus> campuss) {
		List<Building> buildings = new ArrayList<Building>();
		for (Campus campus : campuss) {
			Building building = new Building();
			building.setCampusId(campus.getCampusId());
			buildings.add(building);
		}
		//创建修改模板
		Building buildingExample = new Building();
		buildingExample.setStatus(BuildingConstant.DISCARD_STATUS);//废弃
		//关联删除设备间
		equipmentRoomBizServiceImpl.delByBuilding(buildings);
		return buildingService.delBuilding(buildings, buildingExample);
	}
	
	
	@Override
	public Integer editBuilding(Building building) {
		return buildingService.editBuilding(building);
	}

	@Override
	public List<Map<String, Object>> location(Integer buildingId,String buildingCode,Integer[] campusIds) {
		return buildingService.location(buildingId,buildingCode,campusIds);
	}

	@Override
	public Map<String, Object> query(Integer buildingId,String buildingCode) {
		return buildingService.query(buildingId,buildingCode);
	}

	@Override
	public List<Map<String, Object>> export(String buildingCode,Integer[] campusIds,String buildingName,Integer status) {
		return buildingService.export(buildingCode,campusIds, buildingName, status);
	}

	@Override
	public Res importExcel(List<Map<String, String>> datas, Integer userId, Integer status) {
		List<Building> buildings = new ArrayList<Building>();
		List<String> campusCodes = new ArrayList<String>();
		List<String> deptNames = new ArrayList<String>();
		String msg = null;
		
		for (int i = 0; i < datas.size(); i++) {
			campusCodes.add(datas.get(i).get("CAMPUS_CODE"));
			deptNames.add(datas.get(i).get("MANAGE_DEPT_NAME"));
			deptNames.add(datas.get(i).get("USE_DEPT_NAME"));
		}
		Map<String, String> deptIds = Utils.listToMap(userUtil.getDeptMapByDeptName(deptNames), "name", "id");
		
		Map<String, String> campusIds = Utils.listToMap(campusService.selectIdByCode(campusCodes), "CAMPUS_CODE", "CAMPUS_ID");
		
		Map<String, String> buildingStatus =Utils.listToMap(codeBizServiceImpl.query(status, null), "option", "codeId");
		
		
		
		for (int i = 0; i < datas.size(); i++) {
			Building building = new Building();
			try {
				building.setBuildingName(datas.get(i).get("BUILDING_NAME"));
				building.setRemark(datas.get(i).get("REMARK"));
				building.setLongitude(datas.get(i).get("LONGITUDE"));
				building.setLatitude(datas.get(i).get("LATITUDE"));
				building.setFunction(datas.get(i).get("FUNCTION"));
				building.setAera(datas.get(i).get("AERA"));
				String floorNum = datas.get(i).get("FLOOR_NUM");
				if (floorNum != null && !"".equals(floorNum)) {
					for (String str : floorNum.split(",")) {
						if(!str.matches("^-?[1-9]\\d*$")) {
							throw new RuntimeException("导入楼栋，楼层字段错误");
						}
					}
				}
				building.setFloorNum("["+floorNum+"]");
				building.setStatus(Integer.parseInt(buildingStatus.get(datas.get(i).get("STATUS"))));
				building.setManageDept(Integer.parseInt(deptIds.get(datas.get(i).get("MANAGE_DEPT_NAME"))));
				building.setUseDept(Integer.parseInt(deptIds.get(datas.get(i).get("USE_DEPT_NAME"))));
				building.setCampusId(Integer.parseInt(campusIds.get(datas.get(i).get("CAMPUS_CODE"))));
				building.setBuildingCode("LD"+Utils.getCodeByUUId());
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
			
			building.setCreateTime(Utils.getTimeStamp());
			building.setCreatePersonId(userId);
			
			buildings.add(building);
		}
		
		Res res = Res.Res("0", "", null);
		if (msg != null) {
			res.setCode("1");
			res.setMessage(msg);
		} 
		
		Integer count = buildingService.addBuildings(buildings);
		if (count == 0) {
			res.setCode("1");
			res.setMessage("无数据！");
		} 
		
		return res;
	}

	
}
