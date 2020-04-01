package com.ncu.springboot.AOP;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ncu.springboot.biz.entity.Dept;
import com.ncu.springboot.biz.rs.BaseRequestBody;
import com.ncu.springboot.biz.rs.Res;
import com.ncu.springboot.api.cache.bizservice.UserCacheBizService;
import com.ncu.springboot.api.resource.bizservice.CampusBizService;
import com.ncu.springboot.api.system.bizservice.MenuService;
import com.ncu.springboot.api.oauth2.bizservice.DeptBizService;

import org.apache.commons.beanutils.ConvertUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


@Order(1)
@Component
@Aspect
public class PermissionAOP {

	@Reference
	private MenuService menuService;

	@Reference
	private UserCacheBizService userCacheHelper;

	@Reference
	private CampusBizService campusBizService;

	@Reference
	private DeptBizService deptBizService;

	@Around("@annotation(com.ncu.springboot.AOP.Permission)")
	public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		String url = request.getContextPath()+request.getServletPath();
		Map<String, Object> params = getArgs(joinPoint);

		//获取用户名
		String usercode = (String) request.getParameter("userCode");
		//园区id
		List<String> campusNames = new ArrayList<String>();
		Map<String, Object> campus = new HashMap<String, Object>();

		//判断是否存在key值和是否有value值
		if(request.getParameter("campusIds") != null) {
			//将String数组转成Integer数组
			Integer[] campusIds = (Integer[])ConvertUtils.convert(request.getParameter("campusIds").split(","), Integer.class);
			for (Integer campusdId : campusIds) {
				campus = campusBizService.query(campusdId,null);
				if (campus!=null && campus.get("campusName")!=null && !"".equals(campus.get("campusName"))) {
					campusNames.add(campus.get("campusName").toString());
				}
			}
		} else if(request.getParameter("manageDept") != null) {
			Dept dept = new Dept();
			dept.setId(request.getParameter("manageDept"));
			List<Dept> deptList = new ArrayList<Dept>();
			deptList.add(dept);
			deptList = deptBizService.selectDept(deptList);
			if (deptList != null && deptList.size()>0) {
				dept = deptList.get(0);
				if (dept!=null && dept.getName()!=null && !"".equals(dept.getName())) {
					campusNames.add(dept.getName());
				}
			}
		}  else if (params.get("data")!=null) {
			Integer[] campusIds = ((BaseRequestBody<?>)params.get("data")).getCampusIds();
			usercode = ((BaseRequestBody<?>)params.get("data")).getUserCode();
			for (Integer campusdId : campusIds) {
				campus = campusBizService.query(campusdId,null);
				if (campus!=null && campus.get("campusName")!=null && !"".equals(campus.get("campusName"))) {
					campusNames.add(campus.get("campusName").toString());
				}
			}
		}else {
			//没有任何关于判断权限字段
			return Res.ERROR("权限不足！");
		}

		if (campusNames.size() == 0 || !menuService.findPark(usercode,url,campusNames)) {
			return Res.ERROR("权限不足！");
		}
		Object result = joinPoint.proceed();
		return result;
	}

	private Map<String, Object> getArgs(ProceedingJoinPoint joinPoint) {
		Map<String, Object> param = new HashMap<>();

		Object[] paramValues = joinPoint.getArgs();
		String[] paramNames = ((CodeSignature)joinPoint.getSignature()).getParameterNames();

		for (int i = 0; i < paramNames.length; i++) {
			param.put(paramNames[i], paramValues[i]);
		}

		return param;
	}

}


