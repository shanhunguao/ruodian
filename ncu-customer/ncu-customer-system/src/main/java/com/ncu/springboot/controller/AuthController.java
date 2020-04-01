package com.ncu.springboot.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ncu.springboot.api.cache.bizservice.UserCacheBizService;
import com.ncu.springboot.api.system.enmu.QrCodeEnmu;
import com.ncu.springboot.api.system.enmu.ResponseConstant;
import com.ncu.springboot.api.oauth2.bizservice.UserBizService;
import com.ncu.springboot.biz.entity.Consumer;
import com.ncu.springboot.biz.rs.OtherResponseBody;
import com.ncu.springboot.biz.rs.Res;
import com.ncu.springboot.core.util.JwtTokenUtil;
import com.ncu.springboot.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.UUID;

/**
 * APP扫码登录
 *
 * @Created by zhoufan
 * @Date 2019/11/13 14:52
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private RedisUtil redisUtil;

    @Reference
    private UserCacheBizService userCacheHelper;

    @Reference
    private UserBizService userBizService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 生成App二维码字符串
     */
    @RequestMapping("/getQrcodeContent")
    public Res getQrcodeContent() {
        String code = UUID.randomUUID().toString();
        logger.info("Code" + code);
        redisUtil.set(code, QrCodeEnmu.logout.toString(), 120);
        return Res.SUCCESS("获取二维码",code);
    }

    /**
     * App扫一扫统一入口
     *
     * @param code 二维码扫码到的字符串
     */
    @RequestMapping("/scanService")
    public Res scanService(String code) throws IOException {
        if (redisUtil.hasKey(code)) {
            redisUtil.del(code);
            sendClient(code, ResponseConstant.SCAN_SUCCESS.getResponseCode(), ResponseConstant.SCAN_SUCCESS.getResponseMsg(), null);
            return Res.Res(ResponseConstant.SCAN_SUCCESS.getResponseCode(), ResponseConstant.SCAN_SUCCESS.getResponseMsg(), "");
        }
        return Res.Res(ResponseConstant.IVALID_QRCODE.getResponseCode(), ResponseConstant.IVALID_QRCODE.getResponseMsg(), "");
    }

    /**
     * 扫码登录：确认/取消登录
     *  @param id websocket建立连接的uuid
     * token 安卓端的websocket
     * type 确认为 0 取消为其他
     *  @return 
     */
    @RequestMapping("/scanLogin")
    public Res scanLogin(String code, String token) throws IOException {
        String userCode = userCacheHelper.getUsercodeByToken(token);
        String pctoken = JwtTokenUtil.createToken(userCode, false);
        //		查看用户以及关联的员工信息
        Consumer consumer = userBizService.findConsumer(userCode);
        consumer.setToken(pctoken);
        sendClient(code, ResponseConstant.LOGIN_SUCCESS.getResponseCode(), ResponseConstant.LOGIN_SUCCESS.getResponseMsg(), consumer);
        return Res.Res(ResponseConstant.LOGIN_SUCCESS.getResponseCode(), ResponseConstant.LOGIN_SUCCESS.getResponseMsg(), "");

    }

    /**
     * 微信websocket发送消息给指定客户端
     *
     * @Parmeter clientId 客户端ID code 状态码 message 状态信息 consumer 返回的data对象
     */
    private void sendClient(String clientId, String code, String message, Consumer consumer) throws IOException {
        OtherResponseBody otherResponseBody = new OtherResponseBody();
        WebSocketServer webSocketServer = new WebSocketServer();
        ObjectMapper objectMapper = new ObjectMapper();
        otherResponseBody.setCode(code);
        otherResponseBody.setMessage(message);
        otherResponseBody.setData(consumer);
        webSocketServer.sendtoUser(objectMapper.writeValueAsString(otherResponseBody), clientId);
    }
}