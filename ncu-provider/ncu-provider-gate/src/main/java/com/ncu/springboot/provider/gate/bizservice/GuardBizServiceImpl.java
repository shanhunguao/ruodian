package com.ncu.springboot.provider.gate.bizservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.osgi.service.component.annotations.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.ncu.springboot.api.cache.bizservice.RoleCacheBizService;
import com.ncu.springboot.api.gate.bizservice.GuardBizService;
import com.ncu.springboot.api.gate.constant.GateConstant;
import com.ncu.springboot.api.gate.entity.Gate;
import com.ncu.springboot.api.gate.entity.Log;
import com.ncu.springboot.api.gate.entity.SpecialLog;
import com.ncu.springboot.api.gate.entity.TempPersion;
import com.ncu.springboot.biz.rs.Res;
import com.ncu.springboot.common.util.Utils;
import com.ncu.springboot.provider.gate.service.ApplyService;
import com.ncu.springboot.provider.gate.service.CardService;
import com.ncu.springboot.provider.gate.service.GuardService;

@Component
@Service
public class GuardBizServiceImpl implements GuardBizService {

	@Resource
	private ApplyService applyService;

	@Resource
	private GuardService guardService;

	@Resource
	private CardService cardService;
	
	@Reference
	private RoleCacheBizService roleCacheBizService;

	public Res check(Log log,String userId) {
		String roleId = roleCacheBizService.getRoleIdByUsercode(userId);
		if (roleId.equals(GateConstant.ROLE_GUARD)) {
			log.setGuardId(userId);
		}else {
			log.setGuardId("999999");
		}
		return Res.SUCCESS(guardService.addLog(log));
	}
	
	public Res getInfo(String cardId, String userId) {
		if (!isGuard(userId)) {
			return Res.ERROR("无权审核出入！");
		}
		Map<String, Object> data = cardService.getUserInfo(null, cardId);
		if (data!=null&&data.containsKey("userId")&&data.get("userId")!=null&&!"".equals(data.get("userId"))) {//学生老师
			List<Map<String, Object>> applys = guardService.queryApply(data.get("userId").toString());
			if (applys!=null) {
				data.put("applys", applys);
			}
		}else {
			Map<String, Object> tempPersion = cardService.getTempPersionInfo(null, null,cardId);//临时人员
			if (tempPersion!=null&&!Utils.checkObjAllFieldsIsNull(tempPersion)) {
				data = tempPersion;
			}else{
				return Res.ERROR("此电子ID不存在！");
			}
		}
		return Res.SUCCESS(data);
	}

	public Res queryLog(String date, String userId,Integer size,Integer num) {
		if(size != null && num !=null) {
			num = (num-1)*size;
			}
		return Res.SUCCESS(guardService.queryLog(date, userId,size,num));
	}

	public boolean isGuard(String userId) {
		String roleId =  roleCacheBizService.getRoleIdByUsercode(userId);
		if (roleId.equals(GateConstant.ROLE_GUARD)||roleId.equals(GateConstant.ROLE_ADMIN)) {
			return true;
		}
		return false;
	}

	public Integer addGate(Gate gate) {
		return guardService.addGate(gate);
	}

	public Integer delGate(Integer id) {
		return guardService.delGate(id);
	}

	public Integer editGate(Gate gate) {
		return guardService.editGate(gate);
	}

	public List<Map<String, Object>> queryList(Integer id, Integer campusId,Integer size,Integer num) {
		if(size != null && num !=null) {
			num = (num-1)*size;
			}
		return guardService.queryList(id, campusId, size, num);
	}

	public Integer getTotal(Integer id, Integer campusId) {
		return guardService.getTotal(id, campusId);
	}

	public Res queryLogByRole(String date, String userId, Integer size, Integer num) {
		if (!isGuard(userId)) {
			return Res.ERROR("无权查看出入日志！");
		}
		if(size != null && num !=null) {
			num = (num-1)*size;
			}
		return Res.SUCCESS(guardService.queryLogByRole(date, userId,size,num));
	}

	public Res specialCheck(SpecialLog specialLog,String userId) {
		if (!isGuard(userId)) {
			return Res.ERROR("无权审核！");
		}
		return Res.SUCCESS(guardService.addSpecialLog(specialLog));
	}
	
	public Res querySpecialLog(String date, String userId, Integer size, Integer num) {
		if (!isGuard(userId)) {
			return Res.ERROR("无权查看出入日志！");
		}
		if(size != null && num !=null) {
			num = (num-1)*size;
			}
		return Res.SUCCESS(guardService.querySpecialLog(date, userId,size,num));
	}
}
