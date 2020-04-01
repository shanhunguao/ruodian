package com.ncu.springboot.provider.resource.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.ncu.springboot.api.resource.entity.Campus;
import com.ncu.springboot.dao.CampusMapper;
import com.ncu.springboot.provider.resource.service.CampusService;

import tk.mybatis.mapper.entity.Example;

@Service
public class CampusServiceImpl implements CampusService {

	@Resource
	private CampusMapper campusMapper;
	
	public Integer getTotal(String campusCode,Integer manageDept, String campusName, Integer status) {
		return campusMapper.getTotal(campusCode,manageDept, campusName, status);
	}

	public List<Map<String, Object>> queryList(String campusCode,Integer manageDept, String campusName, Integer status,
			Integer num, Integer size) {
		return campusMapper.queryList(campusCode,manageDept, campusName, status, num, size);
	}

	public Integer addCampus(Campus campus) {
		return campusMapper.insert(campus);
	}

	public Integer delCampus(List<Campus> campuss,Campus campusExample) {
		Integer count = 0;
		for (Campus campus : campuss) {
			Example example = new Example(Campus.class);
			example.createCriteria().andEqualTo(campus);
			count += campusMapper.updateByExampleSelective(campusExample,campus);
		}
		return count;
	}

	public Integer editCampus(Campus campus) {
		return campusMapper.updateByPrimaryKeySelective(campus);
	}

	public Map<String, Object> location(Integer campusId,String campusCode,Integer manageDept) {
		return campusMapper.location(campusId,campusCode,manageDept);
	}

	public Map<String,Object> query(Integer campusId,String campusCode) {
		return campusMapper.query(campusId,campusCode);
	}

	public List<Map<String, Object>> export(String campusCode,Integer manageDept, String campusName, Integer status) {
		return campusMapper.export(campusCode,manageDept, campusName, status);
	}

	//单表查询
	public List<Campus> selectCampus(Campus campus) {
		return campusMapper.select(campus);
	}

	@Override
	public Integer addCampuss(List<Campus> campuss) {
		return campusMapper.insertList(campuss);
	}

	@Override
	public List<Map<String, Object>> selectIdByCode(List<String> campusCodes) {
		return campusMapper.selectIdByCode(campusCodes);
	}

	@Override
	public List<Map<String, Object>> selectIdByName(List<String> campusNames) {
		return campusMapper.selectIdByName(campusNames);
	}

}
