package com.ncu.springboot.aspect;

/**
 * @Created by zhoufan
 * @Date 2019/11/6 15:35
 */

import com.alibaba.dubbo.config.annotation.Reference;
import com.ncu.springboot.annotation.LogAnno;
import com.ncu.springboot.api.cache.bizservice.UserCacheBizService;
import com.ncu.springboot.api.system.bizservice.SysLogService;
import com.ncu.springboot.api.system.entity.SysLog;
import com.ncu.springboot.api.system.util.HttpContextUtil;
import com.ncu.springboot.api.system.util.HttpContextUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Date;

/**
 * AOP实现日志记录
 *
 * @author zhouhaungfan
 */
@Order(3)
@Component
@Aspect
public class LogAopAspect {
    // 日志mapper，这里省事少写了service
    @Reference
    private SysLogService sysLogService;

    @Reference
    private UserCacheBizService userCacheHelper;


    /**
     * 环绕通知记录日志通过注解匹配到需要增加日志功能的方法
     *
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("@annotation(com.ncu.springboot.annotation.LogAnno)")
    public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
        // 创建一个日志对象(准备记录日志)
        SysLog log = new SysLog();
        // 1.方法执行前的处理，相当于前置通知
        // 获取方法签名
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        // 获取方法
        Method method = methodSignature.getMethod();
        // 获取方法上面的注解
        LogAnno logAnno = method.getAnnotation(LogAnno.class);
        // 获取方法上的类路径
        String className = pjp.getTarget().getClass().getName();
        //  获取方法名
        String methodName = method.getName();
        // 获取操作描述的属性值
        String operateType = logAnno.operateType();
        // 请求的方法参数名称和值
        Object[] args = pjp.getArgs();
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNames = u.getParameterNames(method);
        if (args != null && paramNames != null) {
            String params = "";
            for (int i = 0; i < args.length; i++) {
                params += "  " + paramNames[i] + ": " + args[i];
            }
            log.setParams(params);
        }
        log.setOperation(operateType);// 操作说明
        // 设置操作人，获取request对象
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        //获取token
        String token = (String) request.getParameter("token");
        //获取用户名
        String usercode = userCacheHelper.getUsercodeByToken(token);
        String ip = HttpContextUtil.getIpAddress();
        log.setUsername(usercode);
        log.setIp(ip);
        log.setMethod(className + "." + methodName+ "()");
        log.setOperationType(0L);
        Object result = null;
        try {
            // 让代理方法执行
            result = pjp.proceed();
            // 2.相当于后置通知(方法成功执行之后走这里)
        } catch (SQLException e) {
//            添加错误日志
            log.setOperationType(2L);
            // 3.相当于异常通知部分
        } finally {
            // 4.相当于最终通知
            log.setCreateTime(new Date());// 设置操作日期
            sysLogService.saveLog(log);// 添加日志记录
        }
        return result;
    }

}
