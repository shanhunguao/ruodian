package com.ncu.springboot.provider.gate.bizservice;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import org.osgi.service.component.annotations.Component;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

import com.ncu.springboot.api.cache.bizservice.RoleCacheBizService;
import com.ncu.springboot.api.gate.bizservice.CardBizService;
import com.ncu.springboot.api.gate.bizservice.PermissionBizService;
import com.ncu.springboot.api.gate.constant.GateConstant;
import com.ncu.springboot.api.gate.entity.Card;
import com.ncu.springboot.api.gate.entity.Student;
import com.ncu.springboot.api.gate.entity.Teacher;
import com.ncu.springboot.api.gate.entity.TempPersion;
import com.ncu.springboot.api.oauth2.bizservice.DeptBizService;
import com.ncu.springboot.biz.rs.Res;
import com.ncu.springboot.common.util.Utils;
import com.ncu.springboot.provider.gate.service.CardService;

@Component
@Service
public class CardBizServiceImpl implements CardBizService {

	@Resource
	private CardService cardService;

	@Reference
	private DeptBizService deptBizService;

	@Reference
	private RoleCacheBizService roleCacheBizService;

	@Reference
	private PermissionBizService permissionBizService;
	

	public Res getUserInfo(String userId,String cardId,String adminId) {
		if ((userId==null||"".equals(userId))&&(cardId==null||"".equals(cardId))) {
			userId = adminId;
		}
		Map<String, Object> data =  cardService.getUserInfo(userId, cardId);
		if (data!=null&&!data.isEmpty()&&data.containsKey("userId")) {
			userId = data.get("userId").toString();
		}else {
			return Res.ERROR("数据错误!");
		}
		String deptId = null;
		if ((Integer)data.get("type")==0) {//老师
			deptId = data.get("deptId").toString();
		}else {//学生
			deptId = data.get("major").toString();
		}
		if (userId!=adminId&&!userId.equals(adminId)) {//非本人
			//判断权限
			String roleId =  roleCacheBizService.getRoleIdByUsercode(adminId);
			if (GateConstant.ROLE_TEACHER.equals(roleId)||GateConstant.ROLE_STUDENT.equals(roleId)) {
				return Res.ERROR("无权限查看！");
			}
			if (GateConstant.ROLE_ADMIN.equals(roleId)||GateConstant.ROLE_GUARD.equals(roleId)) {//超管或者门卫
				return Res.SUCCESS(data);
			}
			List<Map<String, Object>> permissions = permissionBizService.queryList(adminId, null, null);
			List<String> childIds = new ArrayList<String>();
			for (Map<String, Object> permission : permissions) {
				if (permission.get("objectType").equals(GateConstant.PERMISSION_DEPT)) {//部门
					childIds.add(permission.get("objectId").toString());
					childIds = deptBizService.getChild(childIds);
					childIds.add(permission.get("objectId").toString());
				}
			}
			childIds = childIds.stream().distinct().collect(Collectors.toList());//去重
			if(ApplyBizServiceImpl.isCheck(childIds, deptId)) {//拥有权限
				return Res.SUCCESS(data);
			}
		}else {
			return Res.SUCCESS(data);//本人
		}
		return Res.ERROR("无权查看此条信息!");
	}

	@Transactional
	public String addCard(Student student, Teacher teacher,String imgPath,String adminId) {
		String userId = null;
		if (student.getUserId()!=null&&!"".equals(student.getUserId())) {
			userId = student.getUserId();
		}
		if (teacher.getUserId()!=null&&!"".equals(teacher.getUserId())) {
			userId = teacher.getUserId();
		}
		if (userId==adminId||userId.equals(adminId)) {//本人
			return cardService.addCard(student, teacher,imgPath);
		}
		//不是本人
		String roleId =  roleCacheBizService.getRoleIdByUsercode(adminId);
		if (GateConstant.ROLE_TEACHER.equals(roleId)||GateConstant.ROLE_STUDENT.equals(roleId)) {
			return "0";
		}
		if (GateConstant.ROLE_ADMIN.equals(roleId)) {//超管
			return cardService.addCard(student, teacher,imgPath);
		}
		List<Map<String, Object>> permissions = permissionBizService.queryList(adminId, null, null);
		
		//判读用户身份
		Map<String, Object> userInfo = cardService.getUserInfo(userId,null);
		//
		String deptId = null;
		if (userInfo.containsKey("deptId")) {
			deptId = userInfo.get("deptId").toString();
		}else if (userInfo.containsKey("major")) {
			deptId = userInfo.get("major").toString();
		}else if (userInfo.containsKey("college")) {
			deptId = userInfo.get("college").toString();
		}else {

		}
		//存在部门
		if (deptId!=null&&!"".equals(deptId)) {
			List<String> childIds = new ArrayList<String>();
			for (Map<String, Object> permission : permissions) {
				if (permission.get("objectType").equals(GateConstant.PERMISSION_DEPT)) {//部门
					childIds.add(permission.get("objectId").toString());
					childIds = deptBizService.getChild(childIds);
					childIds.add(permission.get("objectId").toString());
				}
			}
			childIds = childIds.stream().distinct().collect(Collectors.toList());//去重
			if(ApplyBizServiceImpl.isCheck(childIds, deptId)) {//拥有权限
				return cardService.addCard(student, teacher,imgPath);
			}
		}
		return "0";
	}

	@Override
	public Res editImgPath(String imgPath, String userId, String admin) {
		String roleId = roleCacheBizService.getRoleIdByUsercode(admin);
		if (roleId.equals(GateConstant.ROLE_ADMIN)||roleId.equals(GateConstant.ROLE_DEPT_ADMIN)||roleId.equals(GateConstant.ROLE_SUPERVISE)) {
			return cardService.editImgPath(imgPath, userId);
		}
		return Res.ERROR("非管理员无权更改他人照片");
	}

	@Transactional
	public Res addTempCard(TempPersion tempPersion, String imgPath,String admin) {
		String roleId = roleCacheBizService.getRoleIdByUsercode(admin);
		if (roleId.equals(GateConstant.ROLE_DEPT_ADMIN)) {
			Card card = new Card();
			card.setCardId(Utils.getCodeByUUId()+Utils.getCodeByUUId());
			card.setUserId(tempPersion.getUserId());
			card.setImgPath(imgPath);
			card.setCreateTime(Utils.getTimeStamp());
			tempPersion.setDeptId(cardService.getUserInfo(admin, null).get("deptId").toString());
			tempPersion.setCreatePersion(admin);
			try {
				cardService.addTempCard(card);
			} catch (DuplicateKeyException e) {
				e.printStackTrace();
				return Res.ERROR("该手机号已领取通行证");
			}
			cardService.addTempPersion(tempPersion);
			return Res.SUCCESS(card.getCardId());
		}else {
			return Res.ERROR("非部门管理员无法添加");
		}
	}

	@Override
	public Map<String, Object> getTempPersionInfo(String mobile, String idCard) {
		return cardService.getTempPersionInfo(mobile, idCard,null);
	}

	@Override
	public Res getUserInfoList(String userId, String adminId, String mobile, String userName) {
		//判断权限
		String roleId =  roleCacheBizService.getRoleIdByUsercode(adminId);

		if (GateConstant.ROLE_TEACHER.equals(roleId)||GateConstant.ROLE_STUDENT.equals(roleId)) {
			return Res.ERROR("无管理员权限！");
		}
		if (GateConstant.ROLE_ADMIN.equals(roleId)) {//超管
			return Res.SUCCESS(cardService.getUserInfoList(userId, mobile, userName,null));
		}
		List<Map<String, Object>> permissions = permissionBizService.queryList(adminId, null, null);

		List<String> deptIds = new ArrayList<String>();
		for (Map<String, Object> permission : permissions) {
			switch (permission.get("objectType").toString()) {
			case "1"://部门
				deptIds.add(permission.get("objectId").toString());
				break;
			default:
				break;
			}
		}
		if (deptIds==null||deptIds.size()<1) {
			return Res.ERROR("请联系上级管理员添加权限对象!");
		}
		List<String> childIds = new ArrayList<String>();
		childIds.addAll(deptIds);
		childIds = deptBizService.getChild(deptIds);
		childIds.addAll(deptIds);
		childIds = childIds.stream().distinct().collect(Collectors.toList());//去重
		List<Map<String, Object>> data = cardService.getUserInfoList(userId, mobile, userName,childIds);
		return Res.SUCCESS(data);
	}

	@Override
	public Res getUserInfoListByTeacher(String deptId, String userId, String mobile, String userName, Integer pageNum, Integer pageSize) {
		return Res.SUCCESS(cardService.getUserInfoListByTeacher(deptId,userId,mobile,userName,pageNum,pageSize));
	}

	@Override
	public Res getUserInfoListByTeacherCount(String deptId, String userId, String mobile, String userName) {
		return cardService.getUserInfoListByTeacherCount(deptId,userId,mobile,userName);
	}


}
