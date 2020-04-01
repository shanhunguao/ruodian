package com.ncu.springboot.provider.gate.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.ncu.springboot.api.gate.entity.Apply;
import com.ncu.springboot.biz.rs.Res;
import com.ncu.springboot.dao.ApplyMapper;
import com.ncu.springboot.provider.gate.service.ApplyService;

@Service
public class ApplyServiceImpl implements ApplyService {
	
	@Resource
	private ApplyMapper applyMapper;

	public Res addApply(List<Apply> applys) {
		try {
			return Res.SUCCESS(applyMapper.insertList(applys));
		} catch (DuplicateKeyException e) {
			e.printStackTrace();
			return Res.ERROR("相同申请不能重复提交！");
		}
	}

	public Integer editApply(Apply apply) {
		return applyMapper.updateByPrimaryKeySelective(apply);
	}

	public Integer delApply(Integer id) {
		return applyMapper.deleteByPrimaryKey(id);
	}

	public Apply query(Integer id) {
		return applyMapper.selectByPrimaryKey(id);
	}

	public Integer getTotal(String userId,Integer isCheck) {
		return applyMapper.getTotal(userId,isCheck);
	}

	public List<Map<String, Object>> queryList(String userId,Integer isCheck,Integer size,Integer num) {
		return applyMapper.queryList(userId, isCheck, size, num);
		
	}

	public List<Map<String, Object>> queryListByRole(List<String> deptIds, List<String> userIds,String checkPersion,String status,String startTime,String endTime,Integer size,Integer num) {
		return applyMapper.queryListByRole(deptIds,userIds,checkPersion,status,startTime,endTime,size,num);
	}

	@Override
	public List<Map<String, Object>> getTotalByRole(List<String> deptIds, List<String> userIds, String checkPersion,
			String status, String startTime, String endTime) {
		return applyMapper.getTotalByRole(deptIds, userIds, checkPersion, status, startTime, endTime);
	}

	@Override
	public List<Map<String, Object>> getDeptByUserId(List<String> userIds) {
		return applyMapper.getDeptByUserId(userIds);
	}

	@Override
	public Map<String, Object> getCheckPersion(String userId) {
		return applyMapper.getCheckPersion(userId);
	}

	@Override
	public Map<String, Object> queryMap(Integer id) {
		return applyMapper.query(id);
	}

	@Override
	public Integer check(List<Integer> ids, String status,String userId,String remark) {
		return applyMapper.check(ids, status, userId,remark);
	}

	@Override
	public boolean isSafe(String userId) {
		Integer count = applyMapper.isSafe(userId);
		if (count!=null&&count>0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isSafeByMobile(String mobile) {
		Integer count = applyMapper.isSafeByMobile(mobile);
		if (count!=null&&count>0) {
			return true;
		}
		return false;
	}
}
