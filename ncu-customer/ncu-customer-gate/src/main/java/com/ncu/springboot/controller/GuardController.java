package com.ncu.springboot.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ncu.springboot.api.cache.bizservice.UserCacheBizService;
import com.ncu.springboot.api.gate.bizservice.GuardBizService;
import com.ncu.springboot.api.gate.entity.Gate;
import com.ncu.springboot.api.gate.entity.Log;
import com.ncu.springboot.api.gate.entity.SpecialLog;
import com.ncu.springboot.biz.rs.Res;
import com.ncu.springboot.common.util.Utils;

@RestController
@RequestMapping("/guard")
public class GuardController {

	@Reference
	private GuardBizService guardBizService;
	
	@Reference
	private UserCacheBizService userCacheBizService;

	@RequestMapping("/check")
	public Res check(Log log,HttpServletRequest request) {	
		String  userId = userCacheBizService.getUsercodeByToken(request.getHeader("token"));
		if(userId==null||"".equals(userId)) {
			return Res.ERROR("请重新登录");
		}
		log.setIp(request.getRemoteAddr());
		log.setCreateTime(Utils.getTimeStamp());
		log.setGuardId(userId);
		if (log.getStatus()==2&&(log.getRemark()==null||"".equals(log.getRemark()))) {
			return Res.ERROR("无许可进入时必须填写！");
		}
		return guardBizService.check(log,userId);
	}

	@RequestMapping("/getInfo")
	public Res getInfo(String cardId,HttpServletRequest request) {
		String  userId = userCacheBizService.getUsercodeByToken(request.getHeader("token"));
		if(userId==null||"".equals(userId)) {
			return Res.ERROR("请重新登录");
		}
		return guardBizService.getInfo(cardId, userId);
	}

	@RequestMapping("/queryLogByRole")
	public Res queryLogByLog(String date,HttpServletRequest request,Integer size,Integer num) {
		String  userId = userCacheBizService.getUsercodeByToken(request.getHeader("token"));
		if(userId==null||"".equals(userId)) {
			return Res.ERROR("请重新登录");
		}
		return guardBizService.queryLogByRole(date, userId,size, num);
	}
	
	@RequestMapping("/queryLog")
	public Res queryLog(String date,HttpServletRequest request,Integer size,Integer num) {
		String  userId = userCacheBizService.getUsercodeByToken(request.getHeader("token"));
		if(userId==null||"".equals(userId)) {
			return Res.ERROR("请重新登录");
		}
		return guardBizService.queryLog(date, userId,size, num);
	}
	
	
	@RequestMapping("/addGate")
	public Res addGate(Gate gate) {
		gate.setCreateTime(Utils.getTimeStamp());
		Integer data = guardBizService.addGate(gate);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/delGate")
	public Res delGate(Integer id) {
		if (id==null) {
			return Res.ERROR("未选择对象!");
		}
		Integer data = guardBizService.delGate(id);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/editGate")
	public Res editGate(Gate gate) {
		gate.setUpdateTime(Utils.getTimeStamp());
		Integer data = guardBizService.editGate(gate);
		return Res.SUCCESS(data);
		
	}
	
	@RequestMapping("/queryList")
	public Res queryList(Integer id,Integer campusId,Integer size,Integer num) {
		List<Map<String, Object>> data = guardBizService.queryList(id,campusId,size,num);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/getTotal")
	public Res getTotal(Integer id,Integer campusId) {
		Integer data = guardBizService.getTotal(id,campusId);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/specialCheck")
	public Res specialCheck(HttpServletRequest request,SpecialLog specialLog) {
		String  userId = userCacheBizService.getUsercodeByToken(request.getHeader("token"));
		if(userId==null||"".equals(userId)) {
			return Res.ERROR("请重新登录");
		}
		specialLog.setCreateTime(Utils.getTimeStamp());
		specialLog.setGuardId(userId);
		return guardBizService.specialCheck(specialLog,userId);
	}
	
	@RequestMapping("/querySpecialLog")
	public Res querySpecialLog(String date,HttpServletRequest request,Integer size,Integer num) {
		String  userId = userCacheBizService.getUsercodeByToken(request.getHeader("token"));
		if(userId==null||"".equals(userId)) {
			return Res.ERROR("请重新登录");
		}
		return guardBizService.querySpecialLog(date, userId, size, num);
	}
}
