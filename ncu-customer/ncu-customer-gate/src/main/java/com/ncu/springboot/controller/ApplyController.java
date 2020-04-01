package com.ncu.springboot.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ncu.springboot.api.cache.bizservice.UserCacheBizService;
import com.ncu.springboot.api.gate.bizservice.ApplyBizService;
import com.ncu.springboot.api.gate.entity.Apply;
import com.ncu.springboot.api.resource.bizservice.CampusBizService;
import com.ncu.springboot.biz.rs.Res;
import com.ncu.springboot.common.util.ExcelUtil;
import com.ncu.springboot.common.util.Utils;

@RestController
@RequestMapping("/apply")
public class ApplyController {

	@Reference
	private UserCacheBizService userCacheBizService;
	
	@Reference
	private ApplyBizService applyBizService;
	
	@Reference
	private CampusBizService campusBizService;
	
	@RequestMapping("/revocation")
	public Res revocation(HttpServletRequest request, Integer id) {
		String userId = userCacheBizService.getUsercodeByToken(request.getHeader("token"));
		if(userId==null||"".equals(userId)) {
			return Res.ERROR("请重新登录");
		}
		return applyBizService.revocation(id, userId);
	}
	
	@RequestMapping("/addApplyTest")
	public Res addApplyTest(HttpServletRequest request, String userId,Integer campusId) {
		String admin = userCacheBizService.getUsercodeByToken(request.getHeader("token"));
		if(admin==null||"".equals(admin)) {
			return Res.ERROR("请重新登录");
		}
		return applyBizService.addApplyTest(userId,campusId,admin);
	}
	
	@RequestMapping("/isSafe")
	public Res isSafe(HttpServletRequest request, String userId){
		String admin = userCacheBizService.getUsercodeByToken(request.getHeader("token"));
		if(admin==null||"".equals(admin)) {
			return Res.ERROR("请重新登录");
		}
		return Res.SUCCESS(applyBizService.isSafe(userId));
	}
	
	@RequestMapping("/check")
	public Res check(HttpServletRequest request, @RequestBody(required = true) List<Integer> ids, String remark, boolean check) {
		String userId = userCacheBizService.getUsercodeByToken(request.getHeader("token"));
		if(userId==null||"".equals(userId)) {
			return Res.ERROR("请重新登录");
		}
		if (ids == null ||ids.size() < 1) {
			return Res.ERROR("未选择对象");
		}
		return applyBizService.check(userId,ids,remark,check);
	}

	@RequestMapping("/addApply")
	public Res addApply(@RequestBody(required = false)List<Apply> applys,HttpServletRequest request) {
		for (int i = 0; i < applys.size(); i++) {
			if (applys.get(i).getCampusId()==null) {
				return Res.ERROR("校区不能为空！");
			}
		}
		String userId = userCacheBizService.getUsercodeByToken(request.getHeader("token"));
		if(userId==null||"".equals(userId)) {
			return Res.ERROR("请重新登录");
		}
		return applyBizService.addApply(applys,userId);
	}

	@RequestMapping("/editApply")
	public Res editApply(Apply apply,HttpServletRequest request) {
		String userId = userCacheBizService.getUsercodeByToken(request.getHeader("token"));
		if(userId==null||"".equals(userId)) {
			return Res.ERROR("请重新登录");
		}
		apply.setUpdateTime(Utils.getTimeStamp());
		return applyBizService.editApply(apply,userId);
	}

	@RequestMapping("/delApply")
	public Res delApply(Integer id,HttpServletRequest request) {
		String userId = userCacheBizService.getUsercodeByToken(request.getHeader("token"));
		if(userId==null||"".equals(userId)) {
			return Res.ERROR("请重新登录");
		}
		return applyBizService.delApply(id,userId);
	}

	@RequestMapping("/query")
	public Res query(Integer id,HttpServletRequest request) {
		String userId = userCacheBizService.getUsercodeByToken(request.getHeader("token"));
		if(userId==null||"".equals(userId)) {
			return Res.ERROR("请重新登录");
		}
		Map<String, Object> apply = applyBizService.query(id);
		return Res.SUCCESS(apply);
	}

	@RequestMapping("/getTotal")
	public Res getTotal(HttpServletRequest request,Integer isCheck) {
		String userId = userCacheBizService.getUsercodeByToken(request.getHeader("token"));
		if(userId==null||"".equals(userId)) {
			return Res.ERROR("请重新登录");
		}
		Integer data = applyBizService.getTotal(userId,isCheck);
		return Res.SUCCESS(data);
	}

	@RequestMapping("/queryList")
	public Res queryList(HttpServletRequest request,Integer isCheck,Integer size,Integer num) {
		String userId = userCacheBizService.getUsercodeByToken(request.getHeader("token"));
		if(userId==null||"".equals(userId)) {
			return Res.ERROR("请重新登录");
		}
		List<Map<String, Object>> data = applyBizService.queryList(userId,isCheck,size,num);
		return Res.SUCCESS(data);
	}
	
	@RequestMapping("/queryListByRole")
	public Res queryListByRole(HttpServletRequest request,String checkPersion,String status,String startTime,String endTime,Integer size,Integer num) {
		String userId = userCacheBizService.getUsercodeByToken(request.getHeader("token"));
		if(userId==null||"".equals(userId)) {
			return Res.ERROR("请重新登录");
		}
		return applyBizService.queryListByRole(userId,checkPersion,status,startTime,endTime,size,num);
	}
	
	@RequestMapping("/getTotalByRole")
	public Res getTotalByRole(HttpServletRequest request,String checkPersion,String status,String startTime,String endTime) {
		String userId = userCacheBizService.getUsercodeByToken(request.getHeader("token"));
		if(userId==null||"".equals(userId)) {
			return Res.ERROR("请重新登录");
		}
		return applyBizService.getTotalByRole(userId,checkPersion,status,startTime,endTime);
	}
	
	@RequestMapping("/importExample")
	public void importExample(HttpServletResponse response) {
		List<Map<String, Object>> datas = new ArrayList<Map<String,Object>>();
		Map<String, Object> data = new HashMap<String, Object>();
		
		//状态
		List<Map<String, Object>> status = campusBizService.queryList(null, null, null, null, null, null);
		StringBuffer value = new StringBuffer();
		for (Map<String, Object> map : status) {
			value.append(map.get("campusName").toString()+"\\");
		}
		data.put("start_time", "2020-2-20 8:00");
		data.put("end_time", "2020-2-20 16:00");
		data.put("campus_name", value.subSequence(0, value.length()-1));
		datas.add(data);
		
		List<String> columns = new ArrayList<String>();
		columns.add("工号\\学号");
		columns.add("入校时间");
		columns.add("离校时间");
		columns.add("事由");
		columns.add("园区");
		List<String> keys = new ArrayList<String>();
		keys.add("user_id");
		keys.add("start_time");
		keys.add("end_time");
		keys.add("remark");
		keys.add("campus_name");
		try {
			ExcelUtil.export(datas, "出入申请导入模板", columns,response,keys,"applyExample");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/importExcel")
	public Res importExcel(MultipartFile excelFile,HttpServletRequest request) {
		String userId = userCacheBizService.getUsercodeByToken(request.getHeader("token"));
		if(userId==null||"".equals(userId)) {
			return Res.ERROR("请重新登录");
		}
		String name = excelFile.getOriginalFilename();
		
        if (name.length() < 5 || !name.substring(name.length() - 4).equals(".xls")) {
        	return Res.ERROR("文件格式错误");
        }
        List<String> keys = new ArrayList<String>();
        keys.add("user_id");
		keys.add("start_time");
		keys.add("end_time");
		keys.add("remark");
		keys.add("campus_name");
        // 业务逻辑，通过excelFile.getInputStream()，处理Excel文件
		List<Map<String, String>> datas = new ArrayList<Map<String,String>>();
		try {
			datas = ExcelUtil.excelToData(excelFile.getInputStream(), keys);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Res result = applyBizService.importExcel(datas,userId);
		return result;
	}
	
}
