package com.ncu.springboot.AOP;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ncu.springboot.api.system.bizservice.SysLogService;
import com.ncu.springboot.api.system.entity.SysLog;
import com.ncu.springboot.api.system.util.HttpContextUtil;
import com.ncu.springboot.biz.rs.Res;
import com.ncu.springboot.common.util.Utils;

@Order(3)
@Component
@Aspect
public class LogAOP {
	
	@Reference
	private SysLogService syslogService;
	
	@AfterReturning(returning="result",pointcut = "@annotation(com.ncu.springboot.AOP.Log)")
	public Object aroundAdvice(JoinPoint  joinPoint,Res result) throws Throwable {
		
		SysLog syslog = new SysLog();
		
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		String url = request.getContextPath()+request.getServletPath();
		Map<String, Object> params = getArgs(joinPoint);
		String ip = HttpContextUtil.getIpAddress();
		//获取用户名
		String usercode = (String) request.getParameter("userCode");

		
		// 获取方法签名
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        // 获取方法
        Method method = methodSignature.getMethod();
		Log log = method.getAnnotation(Log.class);
		String operation = log.operateType();
		
		syslog.setOperation(operation);
		syslog.setUsername(usercode);
		syslog.setMethod(url);
//		if (operation.indexOf("编辑") != -1) {
//			syslog.setParams(getParamStr(params));
//		}else {
			syslog.setParams(params.toString());
//		}
		syslog.setOperationType(new Long(0));
		if (result.getCode()=="1") {
			syslog.setOperationType(new Long(1));
			syslog.setParams(result.getMessage());
		}
		syslog.setIp(ip);
		syslog.setCreateTime(Utils.getTimeStamp());
		syslogService.saveLog(syslog);
		return result;
	}
	
	private Map<String, Object> getArgs(JoinPoint joinPoint) {
		Map<String, Object> param = new HashMap<>();

		Object[] paramValues = joinPoint.getArgs();
		String[] paramNames = ((CodeSignature)joinPoint.getSignature()).getParameterNames();

		for (int i = 0; i < paramNames.length; i++) {
			param.put(paramNames[i], paramValues[i]);
		}

		return param;
	}
	
//	private String getParamStr(Map<String, Object> params) {
//		StringBuffer paramStr = new StringBuffer();
//		for(String key:params.keySet()) {
//			paramStr.append(key+"改为:"+params.get(key).toString()+"。");
//		}
//		return paramStr.toString();
//	}
}
