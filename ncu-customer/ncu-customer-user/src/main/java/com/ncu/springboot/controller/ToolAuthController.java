package com.ncu.springboot.controller;


import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.ncu.springboot.api.cache.bizservice.RoleCacheBizService;
import com.ncu.springboot.api.cache.constants.CacheConstants;
import com.ncu.springboot.api.cache.keys.UserCacheIdKeys;
import com.ncu.springboot.api.system.entity.Ticket;
import com.ncu.springboot.api.system.entity.Tickets;
import com.ncu.springboot.api.system.entity.WeChatToken;
import com.ncu.springboot.api.system.entity.userinfo;
import com.ncu.springboot.api.system.util.HttpContextUtil;
import com.ncu.springboot.api.system.util.SecuritySHA1Utils;
import com.ncu.springboot.api.oauth2.bizservice.UserBizService;
import com.ncu.springboot.biz.entity.ControlUser;
import com.ncu.springboot.biz.entity.Student;
import com.ncu.springboot.biz.entity.Teacher;
import com.ncu.springboot.biz.entity.Temporary;
import com.ncu.springboot.biz.error.LoginErrorCode;
import com.ncu.springboot.biz.rs.Res;
import com.ncu.springboot.common.util.ToolAuthUtil;
import com.ncu.springboot.core.util.JwtTokenUtil;
import com.ncu.springboot.entity.AppWXUserResponse;
import com.ncu.springboot.utils.RedisUtil;
import com.ncu.springboot.utils.StringUtils;
import com.ncu.springboot.common.util.HttpClientUtils;

/**
 * @Created by zhoufan
 * @Date 2020/2/24 17:36
 */
@RestController
@RequestMapping("/ToolAuth")
public class ToolAuthController {

    //   统一身份认证接口地址
    @Value("${app.login.url}")
    private String appLoginUrl;
    //    应用提供的secret值
    @Value("${app.login.appsecret}")
    private String appLoginAppsecret;
    //   企业微信获取token的url
    @Value("${qiye.weixin.gettoken}")
    private String qiyeWeixinGettoken;
    //    获取userId的url
    @Value("${qiye.weixin.getuserinfo}")
    private String qiyeWeixinGetuserinfo;
    @Value("${app.weixin.getunionid}")
    private String appWXGetUnionId;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Reference
    private UserBizService userBizService;
    
    @Reference
    private RoleCacheBizService roleCacheBizService;

    @Autowired
    private RedisUtil redisUtil;

    @Resource
    private HttpServletResponse response;


    /**
     * app端 人员进出管控登录
     *
     * @Parmeter username 学生工号或者教师工号 pssword 密码
     * @retrun Res 返回登录用户信息已经token 并且缓存用户信息到redis
     */
    @RequestMapping(value = "/appLogin", method = RequestMethod.POST)
    public Res appLogin(String username, String password) {
//        校验统一身份认证用户是否存在
        if (checkTool(username, password)) {
            return getUserInfo(username);
        }
        return Res.Res(String.valueOf(LoginErrorCode.USER_LOGIN_FAILED.getErrorCode()), LoginErrorCode.USER_LOGIN_FAILED.getErrorMsg(), null);
    }

    /**
     * 登录成功返回用户信息
     *
     * @Parmeter username 工号
     * @retrun
     */
    private Res getUserInfo(String username) {
        try {
            ControlUser consumer = userBizService.findAppConsumer(username);
            logger.info("查询用户返回信息" + consumer);
            if (consumer != null) {
                //针对返回的教师和学生数据不一样，采用多态方式解决
                if (consumer instanceof Teacher) {
                    Teacher teacher = (Teacher) consumer;
//            0表示用户类型为老师
                    return getRes(username, teacher);
                } else if (consumer instanceof Student) {
                    Student student = (Student) consumer;
                    return getRes(username, student);
                }
                Temporary temporary = (Temporary) consumer;
                return getRes(username, temporary);
            }
            return Res.Res(String.valueOf(LoginErrorCode.USER_NOUSER.getErrorCode()), LoginErrorCode.USER_NOUSER.getErrorMsg(), null);
        } catch (Exception e) {
            logger.error("查询用户错误信息", e);
            return Res.Res(String.valueOf(LoginErrorCode.GET_USER_ERROR.getErrorCode()), LoginErrorCode.GET_USER_ERROR.getErrorMsg(), null);
        }

    }

    private Res getRes(String username, ControlUser appConsumer) {
        String token = JwtTokenUtil.createToken(username, false);
        appConsumer.setToken(token);
        roleCacheBizService.setRoleIdByUsercode(appConsumer.getUserCode(), appConsumer.getRole().getRoleId());
        response.setHeader("token", JwtTokenUtil.TOKEN_PREFIX + token);
        redisUtil.set(UserCacheIdKeys.getUsercodeByTokenKey(token), username);
        redisUtil.expire(UserCacheIdKeys.getUsercodeByTokenKey(token), CacheConstants.TOKEN_VALIDATE_TIME);
        redisUtil.set(UserCacheIdKeys.getUserInfoByUsercode(username), appConsumer);
        return Res.Res(String.valueOf(LoginErrorCode.LOGIN_SUCCESS.getErrorCode()), LoginErrorCode.LOGIN_SUCCESS.getErrorMsg(), appConsumer);
    }

    /**
     * 去统一身份认证查询用户是否存在
     *
     * @Parmeter username 工号 password 密码
     * @retrun
     */
    private boolean checkTool(String username, String password) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = df.format(new Date());
        String appsecret = appLoginAppsecret;
        String sign = ToolAuthUtil.HMACSHA1(timestamp + username + password, appsecret);
        String url = appLoginUrl +
                "/" + timestamp + "/arg2/" + sign + "/arg3/" + username + "/arg4/" + password;
        String s = HttpClientUtils.httpGet(url);
        logger.info("统一身份认证返回信息" + s);
        return s.contains("true");
    }


    /**
     * 企业微信通过code获取用户信息
     *
     * @Parmeter code 企业微信回调code
     * @retrun
     */
    @RequestMapping("/getWebChat")
    public Res getWebChat(String code) {
        String ip = HttpContextUtil.getIpAddress();
        System.out.println("ip = " + ip);
        String token = (String) redisUtil.get("weChat_token");
        logger.info("缓存中的token" + token);
//        查询token缓存
        if (token != null) {
            logger.info("查询缓存的token" + token);
            return getRes(code, token, ip);
        }

        long starTime = System.currentTimeMillis();
        String access_token = getToken();
        logger.info("查询数据库的token" + token);
        long endTime = System.currentTimeMillis();
        long Time = endTime - starTime;
        logger.info("调用企业微信接口耗时:" + Time);
        redisUtil.set("weChat_token", access_token, 5400);
        return getRes(code, access_token, ip);
    }
    
    @RequestMapping("/appLoginByWX")
    public Res appLoginByWX(String code) {
    	if (code != null) {
    		String url = appWXGetUnionId + "&code=" + code;
    		System.out.println("getUnionIdFromWx start_time is " + System.currentTimeMillis());
    		String s = HttpClientUtils.httpGet(url);
    		AppWXUserResponse response = JSON.parseObject(s, AppWXUserResponse.class);
    		System.out.println("getUnionIdFromWx end_time is " + System.currentTimeMillis());
    		String unionId = response.getUnionid();
    		System.out.println("getUserCodeByUnionId start_time is " + System.currentTimeMillis());
    		String userCode = userBizService.getUserCodeByUnionId(unionId);
    		return getUserInfo(userCode);
    	} else {
    		return Res.ERROR("参数错误");
    	}
    }

    private Res getRes(String code, String access_token, String ip) {
        userinfo userinfo = getUserinfo(code, access_token);
        String userId = userinfo.getUserId();
        userBizService.updateLog(userId, ip);
        return getUserInfo(userId);
    }

    /**
     * 获取token
     */
    private String getToken() {
        String url = qiyeWeixinGettoken;
        String s = HttpClientUtils.httpGet(url);
        WeChatToken weChatToken = JSON.parseObject(s, WeChatToken.class);
//        获取token
        return weChatToken.getAccess_token();
    }

    /**
     * 获取企业微信用户信息
     */
    private userinfo getUserinfo(String code, String token) {
        String codeUrl = qiyeWeixinGetuserinfo + token + "&code=" + code;
        String s1 = HttpClientUtils.httpGet(codeUrl);
        logger.info("企业微信用户信息" + s1);
        return JSON.parseObject(s1, userinfo.class);
    }

    /**
     * pc端自己的登录逻辑
     *
     * @Parmeter
     * @retrun
     */
    public boolean findUserInfo(String username) {
        if (userBizService.findAppConsumer(username) != null) {
            return true;
        }
        return false;
    }


    /**
     * 通过token获取用户信息
     */
    @RequestMapping("/loginByToken")
    public Res loginByToken(String token) {
        return getUserInfo((String) redisUtil.get("cas.token."+token));
    }


    @RequestMapping("/getTicket")
    public Res getTicket(String url) {
        if (!StringUtils.isEmpty(url)) {
            String ticket;
            String noncestr = "Wm3WZYTPz0wzccnW";
            Long timestamp = new Date().getTime();
            String signature = null;
            String token = (String) redisUtil.get("weChat_token");
            if (!StringUtils.isEmpty(token)) {
                ticket = getTicket2(token);
            } else {
                token = getToken();
                ticket = getTicket2(token);
            }
            try {
//          sha1加密  signature算法
                signature = SecuritySHA1Utils.shaEncode("jsapi_ticket=" + ticket + "&noncestr=" + noncestr + "&timestamp=" + timestamp + "&url=" + url);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return Res.SUCCESS(new Tickets("wxc74a3c82d0ad6c46", timestamp, noncestr, signature));
        }
        return Res.ERROR("url不能为空");
    }

    private String getTicket2(String token) {
        String weChat_ticket = (String) redisUtil.get("weChat_ticket");
        if (!StringUtils.isEmpty(weChat_ticket)) {
            return weChat_ticket;
        }
        String ticket = getTicket3(token);
        redisUtil.set("weChat_ticket", ticket, 4800);
        return ticket;
    }

    /**
     * 获取ticket
     */
    private String getTicket3(String token) {
        String s1 = HttpClientUtils.httpGet("https://qyapi.weixin.qq.com/cgi-bin/get_jsapi_ticket?access_token=" + token);
        Ticket ticket = JSON.parseObject(s1, Ticket.class);
        return ticket.getTicket();
    }


}
