package com.ncu.springboot.provider.gate.bizservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.osgi.service.component.annotations.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.ncu.springboot.api.gate.bizservice.ApplyBizService;
import com.ncu.springboot.api.gate.bizservice.PermissionBizService;
import com.ncu.springboot.api.gate.constant.GateConstant;
import com.ncu.springboot.api.gate.entity.Apply;
import com.ncu.springboot.api.resource.bizservice.CampusBizService;
import com.ncu.springboot.api.oauth2.bizservice.DeptBizService;
import com.ncu.springboot.biz.rs.Res;
import com.ncu.springboot.common.util.Utils;
import com.ncu.springboot.provider.gate.service.ApplyService;
import com.ncu.springboot.provider.gate.service.CardService;
import com.ncu.springboot.api.cache.bizservice.RoleCacheBizService;

@Component
@Service
public class ApplyBizServiceImpl implements ApplyBizService{

	@Resource
	private ApplyService applyService;

	@Resource
	private CardService cardService;

	@Reference
	private DeptBizService deptBizService;

	@Reference
	private RoleCacheBizService roleCacheBizService;

	@Reference
	private PermissionBizService permissionBizService;
	
	@Reference
	private CampusBizService campusBizService;

	public static boolean isCheck(List<String> childIds, String deptId) {
		for (String childId : childIds) {
			if (deptId.equals(childId)||deptId==childId) {
				return true;
			}
		}
		return false;
	}

	public Res check(String userId, List<Integer> ids,String remark,boolean check) {
		String roleId = roleCacheBizService.getRoleIdByUsercode(userId);
		String status = null;
		if (roleId.equals(GateConstant.ROLE_SUPERVISE)) {//督察
			if (check) {
				status = "4";
			}else {
				status = "5";
			}
			return Res.SUCCESS(applyService.check(ids, status, userId, remark));
		}
		if (roleId.equals(GateConstant.ROLE_DEPT_ADMIN)||roleId.equals(GateConstant.ROLE_CHECK)||roleId.equals(GateConstant.ROLE_ADMIN)) {
			if (check) {
				status = "1";
			}else {
				status = "2";
			}
			return Res.SUCCESS(applyService.check(ids, status, userId, remark));
		}
		return Res.SUCCESS("无权审核!");
	}



	public Res addApply(List<Apply> applys,String userId) {
		if (applys.size()>0&&applys!=null) {
			applys.get(0).setCreateTime(Utils.getTimeStamp());
		}
		return applyService.addApply(applys);
	}

	public Res editApply(Apply apply,String userId) {
		
		if (!Utils.checkObjAllFieldsIsNull(apply)) {//判断是否是空对象
			//本人
			String status = applyService.query(apply.getId()).getStatus();
			if(apply.getUserId().equals(userId)) {
				if ("3".equals(status)) {//只能修改撤回的申请
					return Res.SUCCESS(applyService.editApply(apply));
				}else {
					return Res.ERROR("只能修改撤回的申请！");
				}

			}
			//判断权限
			String roleId =  roleCacheBizService.getRoleIdByUsercode(userId);

			if (GateConstant.ROLE_ADMIN.equals(roleId)||GateConstant.ROLE_SUPERVISE.equals(roleId)) {//超管或者督查
				return Res.SUCCESS(applyService.editApply(apply));
			}

			//判读用户身份
			Map<String, Object> userInfo = cardService.getUserInfo(apply.getUserId(),null);
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

			if (GateConstant.ROLE_DEPT_ADMIN.equals(roleId)||GateConstant.ROLE_CHECK.equals(roleId)) {//管理员或者审批员
				List<Map<String, Object>> permissions = permissionBizService.queryList(userId, null, null);
				//存在部门
				if (deptId!=null&&!"".equals(deptId)) {
					List<String> childIds = new ArrayList<String>();
					for (Map<String, Object> permission : permissions) {
						//查询部门的所有子部门
						if (permission.get("objectType").equals(GateConstant.PERMISSION_DEPT)) {//部门
							List<String> childId = new ArrayList<String>();
							childId.add(permission.get("objectId").toString());
							childId = deptBizService.getChild(childIds);
							childId.add(permission.get("objectId").toString());
							childIds.addAll(childId);
						}
					}
					childIds = childIds.stream().distinct().collect(Collectors.toList());//去重
					if(isCheck(childIds, deptId)) {//拥有权限
						if ("0".equals(status)) {//只能审核待审核状态
							return Res.SUCCESS(applyService.editApply(apply));
						}else {
							return Res.ERROR("只能修改待审核的申请！");
						}
					}
				}
			}
		}else {
			return Res.ERROR("该条申请不存在！");
		}
		return Res.ERROR("无权限修改这条申请！");

	}

	public Res delApply(Integer id,String userId) {
		Apply apply = applyService.query(id);
		if (!Utils.checkObjAllFieldsIsNull(apply)) {//判断是否是空对象
			//本人
			if(apply.getUserId().equals(userId)) {
				if ("3".equals(apply.getStatus()) ) {//只能修改撤回的申请
					return Res.SUCCESS(applyService.delApply(id));
				}else {
					return Res.ERROR("只能删除撤回的申请！");
				}

			}
			//判断权限
			String roleId =  roleCacheBizService.getRoleIdByUsercode(userId);

			if (GateConstant.ROLE_ADMIN.equals(roleId)||GateConstant.ROLE_SUPERVISE.equals(roleId)) {//超管或者督查
				return Res.SUCCESS(applyService.delApply(id));
			}else {
				return Res.ERROR("无权删除他人申请！");
			}
		}else {
			return Res.ERROR("该条申请不存在！");
		}
		
	}

	public Map<String, Object> query(Integer id) {
		return applyService.queryMap(id);
	}

	public Integer getTotal(String userId,Integer isCheck) {
		return applyService.getTotal(userId,isCheck);
	}

	public List<Map<String, Object>> queryList(String userId,Integer isCheck,Integer size,Integer num) {
		if(size != null && num !=null) {
			num = (num-1)*size;
		}
		Map<String, Object> checkInfo = applyService.getCheckPersion(userId);
		List<Map<String, Object>> applys = applyService.queryList(userId,isCheck,size,num);
		for (int i = 0; i < applys.size(); i++) {
			if (!applys.get(i).containsKey("checkPermission")&&(applys.get(i).get("checkPermission") == null||"".equals(applys.get(i).get("checkPermission")))
					&&checkInfo!=null) {
				if (checkInfo.containsKey("checkPermissionName")) {
					applys.get(i).put("checkPermissionName", checkInfo.get("checkPermissionName"));
				}
				if (checkInfo.containsKey("checkPersionMobile")) {
					applys.get(i).put("checkPersionMobile", checkInfo.get("checkPersionMobile"));
				}
			}
		}
		return applys;
	}

	public Res queryListByRole(String userId,String checkPersion,String status,String startTime,String endTime,Integer size,Integer num) {
		//判断权限
		String roleId =  roleCacheBizService.getRoleIdByUsercode(userId);
		
		if (GateConstant.ROLE_ADMIN.equals(roleId)||GateConstant.ROLE_SUPERVISE.equals(roleId)) {//超管或者督查
			return Res.SUCCESS(applyService.queryListByRole(null,null,checkPersion,status,startTime,endTime,size,num));
		}
		List<Map<String, Object>> permissions = permissionBizService.queryList(userId, null, null);
		//查看是否拥有审核人该人的权限
		if(checkPersion!=null&&!"".equals(checkPersion)) {
			//判读用户身份
			Map<String, Object> userInfo = cardService.getUserInfo(checkPersion,null);
			String deptId = null;
			if (userInfo.containsKey("deptId")) {
				deptId = userInfo.get("deptId").toString();
			}else if (userInfo.containsKey("major")) {
				deptId = userInfo.get("major").toString();
			}else if (userInfo.containsKey("college")) {
				deptId = userInfo.get("college").toString();
			}else {

			}
			if (GateConstant.ROLE_DEPT_ADMIN.equals(roleId)||GateConstant.ROLE_CHECK.equals(roleId)) {//管理员或者审批员
				//存在部门
				if (deptId!=null&&!"".equals(deptId)) {
					List<String> childIds = new ArrayList<String>();
					for (Map<String, Object> permission : permissions) {
						//查询部门的所有子部门
						if (permission.get("objectType").equals(GateConstant.PERMISSION_DEPT)) {//部门
							List<String> childId = new ArrayList<String>();
							childId.add(permission.get("objectId").toString());
							childId = deptBizService.getChild(childIds);
							childId.add(permission.get("objectId").toString());
							childIds.addAll(childId);
						}
					}
					childIds = childIds.stream().distinct().collect(Collectors.toList());//去重
					if(!isCheck(childIds, deptId)) {//拥有权限
						return Res.ERROR("无权搜索该审核人");
					}
				}
			}
		}
		List<String> deptIds = new ArrayList<String>();
		List<String> userIds = new ArrayList<String>();
		for (Map<String, Object> permission : permissions) {
			switch (permission.get("objectType").toString()) {
			case "1"://部门
				deptIds.add(permission.get("objectId").toString());
				break;
			default:
				break;
			}
		}
		if ((userIds==null||userIds.size()<1)&&(deptIds==null||deptIds.size()<1)) {
			return Res.ERROR("请联系上级管理员添加权限对象!");
		}
		List<String> childIds = new ArrayList<String>();
		childIds.addAll(deptIds);
		childIds = deptBizService.getChild(deptIds);
		childIds.addAll(deptIds);
		childIds = childIds.stream().distinct().collect(Collectors.toList());//去重
		return Res.SUCCESS(applyService.queryListByRole(childIds,null,checkPersion,status,startTime,endTime,size,num));
	}

	public Res revocation(Integer id, String userId) {
		//仅本人可撤回
		Apply apply = applyService.query(id);
		if (!"0".equals(apply.getStatus())) {//只能在等待审核时撤回
			return Res.ERROR("只能在等待审核状态撤回！");
		}
		if (apply.getUserId().equals(userId)) {//本人
			apply.setStatus("3");
			return Res.SUCCESS(applyService.editApply(apply));
		}else {
			return Res.ERROR("只能本人撤回！");
		}
	}

	public Res getTotalByRole(String userId, String checkPersion, String status, String startTime, String endTime) {
		//判断权限
		String roleId =  roleCacheBizService.getRoleIdByUsercode(userId);

		if (GateConstant.ROLE_TEACHER.equals(roleId)||GateConstant.ROLE_STUDENT.equals(roleId)) {
			return Res.ERROR("无管理员权限！");
		}
		if (GateConstant.ROLE_ADMIN.equals(roleId)||GateConstant.ROLE_SUPERVISE.equals(roleId)) {//超管或者督查
			return Res.SUCCESS(applyService.getTotalByRole(null,null,checkPersion,status,startTime,endTime));
		}
		List<Map<String, Object>> permissions = permissionBizService.queryList(userId, null, null);
		//查看是否拥有审核人该人的权限
		if(checkPersion!=null&&!"".equals(checkPersion)) {
			//判读用户身份
			Map<String, Object> userInfo = cardService.getUserInfo(checkPersion,null);
			String deptId = null;
			if (userInfo.containsKey("deptId")) {
				deptId = userInfo.get("deptId").toString();
			}else if (userInfo.containsKey("major")) {
				deptId = userInfo.get("major").toString();
			}else if (userInfo.containsKey("college")) {
				deptId = userInfo.get("college").toString();
			}else {

			}
			if (GateConstant.ROLE_DEPT_ADMIN.equals(roleId)||GateConstant.ROLE_CHECK.equals(roleId)) {//管理员或者审批员
				//存在部门
				if (deptId!=null&&!"".equals(deptId)) {
					List<String> childIds = new ArrayList<String>();
					for (Map<String, Object> permission : permissions) {
						//查询部门的所有子部门
						if (permission.get("objectType").equals(GateConstant.PERMISSION_DEPT)) {//部门
							List<String> childId = new ArrayList<String>();
							childId.add(permission.get("objectId").toString());
							childId = deptBizService.getChild(childIds);
							childId.add(permission.get("objectId").toString());
							childIds.addAll(childId);
						}
					}
					childIds = childIds.stream().distinct().collect(Collectors.toList());//去重
					if(!isCheck(childIds, deptId)) {//拥有权限
						return Res.ERROR("无权搜索该审核人");
					}
				}
			}
		}
		List<String> deptIds = new ArrayList<String>();
		List<String> userIds = new ArrayList<String>();
		for (Map<String, Object> permission : permissions) {
			switch (permission.get("objectType").toString()) {
			case "1"://部门
				deptIds.add(permission.get("objectId").toString());
				break;
			default:
				break;
			}
		}
		if ((userIds==null||userIds.size()<1)&&(deptIds==null||deptIds.size()<1)) {
			return Res.ERROR("请联系上级管理员添加权限对象!");
		}
		List<String> childIds = new ArrayList<String>();
		childIds.addAll(deptIds);
		childIds = deptBizService.getChild(deptIds);
		childIds.addAll(deptIds);
		childIds = childIds.stream().distinct().collect(Collectors.toList());//去重
		List<Map<String, Object>> data = applyService.getTotalByRole(childIds,userIds,checkPersion,status,startTime,endTime);
		return Res.SUCCESS(data);
	}

	public Res importExcel(List<Map<String, String>> datas, String userId) {
		List<Apply> applys = new ArrayList<Apply>();
		List<String> campusNames = new ArrayList<String>();
		List<String> userIds = new ArrayList<String>();
		String msg = null;
		
		for (int i = 0; i < datas.size(); i++) {
			campusNames.add(datas.get(i).get("campus_name"));
			userIds.add(datas.get(i).get("user_id"));//所有员工
		}
		
		Map<String, String> campusIds = Utils.listToMap(campusBizService.selectIdByName(campusNames), "campus_name", "campus_id");
		Map<String, String> deptIds = Utils.listToMap(applyService.getDeptByUserId(userIds), "deptId","userId");//找出所有人员的所有部门
		
		String roleId =  roleCacheBizService.getRoleIdByUsercode(userId);
		//权限判断
		if (roleId==GateConstant.ROLE_ADMIN||roleId.equals(GateConstant.ROLE_ADMIN)) {//无操作
		}else if (roleId==GateConstant.ROLE_DEPT_ADMIN||roleId.equals(GateConstant.ROLE_DEPT_ADMIN)) {
			List<Map<String, Object>> permissions = permissionBizService.queryList(userId, null, null);
			List<String> childIds = new ArrayList<String>();
			for (Map<String, Object> permission : permissions) {
				//查询部门的所有子部门
				if (permission.get("objectType").equals(GateConstant.PERMISSION_DEPT)) {//部门
					List<String> childId = new ArrayList<String>();
					childId.add(permission.get("objectId").toString());
					childId = deptBizService.getChild(childIds);
					childId.add(permission.get("objectId").toString());
					childIds.addAll(childId);
				}
			}
			childIds = childIds.stream().distinct().collect(Collectors.toList());//去重
			for (String childId : childIds) {//查看是否有不属于管理部门内的
				if (deptIds.containsKey(childId)) {
					deptIds.remove(childId);
				}
			}
			//如果最后还有部门剩余，就不属于用户权限内的
			if (deptIds.size()>0) {
				StringBuffer unPermission = new StringBuffer();
				for (String key : deptIds.keySet()) {
					unPermission.append(deptIds.get(key)+",");
				}
				return Res.ERROR("这些部门的人您无权导入！工号为："+unPermission.toString().substring(0, unPermission.toString().length()-1));
			}
		}else {
			return Res.ERROR("无权限作批量导入!");
		}
		for (int i = 0; i < datas.size(); i++) {
			Apply apply = new Apply();
			try {
				apply.setCampusId(Integer.parseInt(campusIds.get(datas.get(i).get("campus_name"))));
				apply.setCheckPersion(userId);
				apply.setCreateTime(Utils.getTimeStamp());
				apply.setCheckTime(Utils.getTimeStamp());
				apply.setEndTime(Utils.getTimesStamp(datas.get(i).get("end_time").toString()));
				apply.setStartTime(Utils.getTimesStamp(datas.get(i).get("start_time").toString()));
				apply.setUserId(datas.get(i).get("user_id"));
				apply.setRemark(datas.get(i).get("remark"));
				apply.setStatus("3");
			} catch (Exception e) {
				msg = "该条数据出错，请检查！\n"+
					  "工号\\学号："+datas.get(i).get("user_id")+"\n"+
					  "入校时间："+datas.get(i).get("start_time")+"\n"+
					  "离校时间："+datas.get(i).get("end_time")+"\n"+
					  "事由："+datas.get(i).get("remark")+"\n"+
					  "校区："+datas.get(i).get("campus_name");
				e.printStackTrace();
			}
			applys.add(apply);
		}
		
		Res res = Res.Res("0", "", null);
		if (msg != null && !"".equals(msg)) {
			res.setCode("1");
			res.setMessage(msg);
		} 
		return applyService.addApply(applys);
	}

	@Override
	public Res addApplyTest(String userId,Integer campusId,String admin) {
		Apply apply = new Apply();
		apply.setStatus("4");
		apply.setCampusId(campusId);
		apply.setCheckPersion(admin);
		apply.setCheckTime(Utils.getTimeStamp());
		apply.setCreateTime(Utils.getTimeStamp());
		apply.setUserId(userId);
		apply.setStartTime(Utils.getTimesStamp(Utils.getDateTime().substring(0, 10)+" 00:00:00"));
		apply.setEndTime(Utils.getTimesStamp(Utils.getDateTime().substring(0, 10)+" 23:59:00"));
		List<Apply> applys = new ArrayList<Apply>();
		applys.add(apply);
		return applyService.addApply(applys);
	}

	@Override
	public boolean isSafe(String userId) {
		return applyService.isSafe(userId);
	}

}
