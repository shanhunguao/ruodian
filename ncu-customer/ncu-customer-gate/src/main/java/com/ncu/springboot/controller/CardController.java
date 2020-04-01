package com.ncu.springboot.controller;


import javax.servlet.http.HttpServletRequest;

import com.ncu.springboot.core.util.StringUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ncu.springboot.api.cache.bizservice.UserCacheBizService;
import com.ncu.springboot.api.gate.bizservice.CardBizService;
import com.ncu.springboot.api.gate.entity.Student;
import com.ncu.springboot.api.gate.entity.Teacher;
import com.ncu.springboot.api.gate.entity.TempPersion;
import com.ncu.springboot.biz.rs.Res;

@RestController
@RequestMapping("/card")
public class CardController {

	@Reference
	private CardBizService cardBizService;
	
	@Reference
	private UserCacheBizService userCacheBizService;

	@RequestMapping("/getUserInfo")
	public Res getUserInfo(HttpServletRequest request,String cardId,String userId) {
		String adminId = userCacheBizService.getUsercodeByToken(request.getHeader("token"));
		if(adminId==null||"".equals(adminId)) {
			return Res.ERROR("请重新登录");
		}
		return cardBizService.getUserInfo(userId,cardId,adminId);
	}

	@RequestMapping("/addCard")
	public Res addCard(HttpServletRequest request,String mobile,String idCard,String type,String imgPath,String userId) {
		if (imgPath==null||"".equals(imgPath)) {
			return Res.ERROR("必须上传照片!");
		}
		String admin = userCacheBizService.getUsercodeByToken(request.getHeader("token"));
		if(admin==null||"".equals(admin)) {
			return Res.ERROR("请重新登录");
		}
		Student student = new Student();
		Teacher teacher = new Teacher();
		if (userId==null||"".equals(userId)) {
			userId=admin;
		}
		if (type=="1"||"1".equals(type)) {
			student.setUserId(userId);
			student.setMobile(mobile);
			student.setIdCard(idCard);
		}else if (type=="0"||"0".equals(type)) {
			teacher.setUserId(userId);
			teacher.setMobile(mobile);
			teacher.setIdCard(idCard);
		}else {
			return Res.ERROR("用户身份不正确");
		}
		String data = cardBizService.addCard(student, teacher,imgPath,admin);
		if ("0".equals(data)) {
			return Res.ERROR("无权代领！");
		}
		if ("1".equals(data)) {
			return Res.ERROR("用户通行证已存在，不能重复生成！");
		}
		Res res = Res.SUCCESS();
		res.setData(data);
		return res;
	}
	
	@RequestMapping("/editImgPath")
	public Res editImgPath(String imgPath,String userId,HttpServletRequest request) {
		String admin = userCacheBizService.getUsercodeByToken(request.getHeader("token"));
		if(admin==null||"".equals(admin)) {
			return Res.ERROR("请重新登录");
		}
		if (imgPath==null||"".equals(imgPath)) {
			return Res.ERROR("必须上传照片!");
		}
		return cardBizService.editImgPath(imgPath, userId, admin);
	}
	
	@RequestMapping("addTempCard")
	public Res addTempCard(String imgPath,String mobile,String idCard,String sex,String userName,String remark,HttpServletRequest request) {
		String admin = userCacheBizService.getUsercodeByToken(request.getHeader("token"));
		if(admin==null||"".equals(admin)) {
			return Res.ERROR("请重新登录");
		}
		if (mobile==null||"".equals(mobile)) {
			return Res.ERROR("手机号不能为空");
		}
		TempPersion tempPersion = new TempPersion();
		tempPersion.setMobile(mobile);
		tempPersion.setIdCard(idCard);
		tempPersion.setSex(sex);
		tempPersion.setUserName(userName);
		tempPersion.setUserId(mobile);
		tempPersion.setRemark(remark);
		return cardBizService.addTempCard(tempPersion, imgPath,admin);
		
	}
	
	@RequestMapping("getTempPersionInfo")
	public Res getTempPersionInfo(String mobile,String idCard) {
		if ((mobile==null||"".equals(mobile))&&(idCard==null||"".equals(idCard))) {
			return Res.ERROR("手机号和身份证号不能同时为空");
		}
		return Res.SUCCESS(cardBizService.getTempPersionInfo(mobile, idCard));
	}

	@RequestMapping("/getUserInfoList")
	public Res getUserInfoList(HttpServletRequest request,String userId,String mobile,String userName) {
		String adminId = userCacheBizService.getUsercodeByToken(request.getHeader("token"));
		if(adminId==null||"".equals(adminId)) {
			return Res.ERROR("请重新登录");
		}
		return cardBizService.getUserInfoList(userId,adminId,mobile,userName);
	}


	@RequestMapping("/getUserInfoListByTeacher")
	public Res getUserInfoListByTeacher(String deptId,String userId,String mobile,String userName,@RequestParam(name = "pageNum", required = false)Integer pageNum,
										@RequestParam(name = "pageSize", required = false) Integer pageSize){
		if (StringUtil.isEmpty(deptId)) {
			return Res.ERROR("部门ID不能为空");
		}

		return cardBizService.getUserInfoListByTeacher(deptId, userId, mobile, userName, pageNum, pageSize);
	}


	@RequestMapping("/getUserInfoListByTeacherCount")
	public Res getUserInfoListByTeacherCount(String deptId,String userId,String mobile,String userName){
		if (StringUtil.isEmpty(deptId)) {
			return Res.ERROR("部门ID不能为空");
		}
		return cardBizService.getUserInfoListByTeacherCount(deptId, userId, mobile, userName);
	}





}
