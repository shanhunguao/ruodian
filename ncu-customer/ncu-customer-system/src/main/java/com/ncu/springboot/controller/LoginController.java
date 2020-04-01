package com.ncu.springboot.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ncu.springboot.api.cache.bizservice.CommonCacheBizService;
import com.ncu.springboot.api.cache.bizservice.RoleCacheBizService;
import com.ncu.springboot.api.cache.bizservice.UserCacheBizService;
import com.ncu.springboot.api.cache.keys.RolePermissionCacheIdKeys;
import com.ncu.springboot.api.system.bizservice.SysLogService;
import com.ncu.springboot.api.system.entity.SysLog;
import com.ncu.springboot.api.system.util.IPUtils;
import com.ncu.springboot.api.oauth2.bizservice.UserBizService;
import com.ncu.springboot.biz.entity.Consumer;
import com.ncu.springboot.biz.entity.Role;
import com.ncu.springboot.biz.entity.User;
import com.ncu.springboot.biz.error.LoginErrorCode;
import com.ncu.springboot.biz.rs.OtherResponseBody;
import com.ncu.springboot.biz.rs.Res;
import com.ncu.springboot.core.util.JwtTokenUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;


@RestController
public class LoginController {

    @Reference
    private CommonCacheBizService commonCacheHelper;

    @Reference
    private UserCacheBizService userCacheHelper;

    @Reference
    private RoleCacheBizService roleCacheHelper;

    @Reference
    private SysLogService sysLogService;

    @Reference
    private UserBizService userBizService;

    @RequestMapping("/loginSuccess")
    public void loginSuccess(HttpServletRequest request, HttpServletResponse response) {

        String token = ((String) response.getHeader("token")).replace(JwtTokenUtil.TOKEN_PREFIX, "");
        User user = (User) request.getAttribute("user");

        String usercode = user.getUsercode();
        userCacheHelper.setUsercodeByToken(token, usercode);

        userCacheHelper.setUserInfoByUsercode(usercode, user);

        if (!commonCacheHelper.existKey(RolePermissionCacheIdKeys.getRoleIdsByUsercodeKey(usercode))) {
            for (Role role : user.getRoles()) {
                roleCacheHelper.addRoleIdByUsercode(usercode, role.getRoleId());
            }
        }
//		查看用户以及关联的员工信息
        Consumer consumer = userBizService.findConsumer(usercode);
        consumer.setRole(user.getRoles());
        consumer.setToken(token);
        OtherResponseBody otherResponseBody = new OtherResponseBody();
        otherResponseBody.setCode(String.valueOf(LoginErrorCode.LOGIN_SUCCESS.getErrorCode()));
        otherResponseBody.setData(consumer);
        otherResponseBody.setMessage(LoginErrorCode.LOGIN_SUCCESS.getErrorMsg());
        saveLog(request, usercode, "用户登入", "loginSuccess", "HttpServletRequest request, HttpServletResponse response");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            response.getWriter().println(objectMapper.writeValueAsString(otherResponseBody));
            response.getWriter().flush();
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }



    /**
     * 保存登入和登出日志
     *
     * @Parmeter
     * @retrun
     */
    private void saveLog(HttpServletRequest request, String usercode, String operation, String method, String params) {
        SysLog sysLog = new SysLog();
        Date date = new Date();
        String ip = IPUtils.getIpAddr(request);
        sysLog.setUsername(usercode);
        sysLog.setOperation(operation);
        sysLog.setMethod(method);
        sysLog.setParams(params);
        sysLog.setCreateTime(date);
        sysLog.setOperationType(1L);
        sysLog.setIp(ip);
        sysLogService.saveLog(sysLog);
    }

    @RequestMapping("/logout")
    public Res logout(String token, HttpServletRequest request, HttpServletResponse response) {
        String usercode = userCacheHelper.getUsercodeByToken(token);
        saveLog(request, usercode, "用户登出", "logout", "HttpServletRequest request, HttpServletResponse response");
        userCacheHelper.delUsercodeByToken(token);
        userCacheHelper.delUserInfoByUsercode(usercode);
        roleCacheHelper.delRoleIdByUsercode(usercode);
        return Res.SUCCESS("退出登录");
    }
}
