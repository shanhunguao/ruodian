package com.ncu.springboot.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ncu.springboot.api.system.entity.Token;
import com.ncu.springboot.api.system.entity.WxUser;
import com.ncu.springboot.api.system.util.HttpClientUtils;
import com.ncu.springboot.api.oauth2.bizservice.UserBizService;
import com.ncu.springboot.biz.entity.Consumer;
import com.ncu.springboot.biz.entity.User;
import com.ncu.springboot.biz.error.LoginErrorCode;
import com.ncu.springboot.biz.rs.OtherResponseBody;
import com.ncu.springboot.biz.rs.Res;
import com.ncu.springboot.core.util.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Created by zhoufan
 * @Date 2019/12/17 14:24
 * 微信和qq回调函数
 */
@Controller
public class SocialController {
    @Reference
    private UserBizService userBizService;
    @Value("${wx.appId}")
    private String wx_appId;
    @Value("${wx.secret}")
    private String wx_secret;
    @Value("${qq.appId}")
    private String qq_appId;
    @Value("${qq.appKey}")
    private String qq_appKey;
    @Value("${qq.redirect_uri}")
    private String qq_redirect_uri;

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/qq_callback")
    public void qqCallback(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("执行QQ回调方法..........................");
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        if (code != null) {
            //获取Access_token
            String url = "https://graph.qq.com/oauth2.0/token?grant_type=authorization_code" +
                    "&client_id=" + qq_appId +
                    "&client_secret=" + qq_appKey +
                    "&code=" + code +
                    "&redirect_uri=" + qq_redirect_uri;
            String s = HttpClientUtils.httpGet(url);
            String AccessToken = s.substring(s.indexOf("=") + 1, s.indexOf("&"));
            //获取openId
            url = "https://graph.qq.com/oauth2.0/me?access_token=" + AccessToken;
            String openid = HttpClientUtils.httpGet(url);
            JSONObject json = parseJSONP(openid);
            String QQopenId = json.getString("openid");
            //获取QQ用户信息
            url = "https://graph.qq.com/user/get_user_info?access_token=" + AccessToken +
                    "&oauth_consumer_key=" + qq_appId +
                    "&openid=" + QQopenId;
            //返回用户的信息
            String user_info = HttpClientUtils.httpGet(url);
            log.info("openid = " + openid);
            JSONObject jsonObject = JSONObject.parseObject(user_info);
            if (state.contains(":")) {
                binding(null, QQopenId, state);
            } else {
                //QQ头像
                String headUrl = jsonObject.getString("figureurl_qq_2");
                Login(null, QQopenId, headUrl, state);
            }
        }
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(
                "<script>\n" +
                        "window.open('','_self','');\n" +
                        "window.close();\n" + "</script>");
        out.close();
    }


    /**
     * 自定义String转json对象
     */
    private static JSONObject parseJSONP(String jsonp) {
        int startIndex = jsonp.indexOf("(");
        int endIndex = jsonp.lastIndexOf(")");
        String json = jsonp.substring(startIndex + 1, endIndex);
        return JSONObject.parseObject(json);
    }


    /**
     * 微信回调方法
     */
    @RequestMapping("/wx_callback")
    public void wxCallback(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("执行微信回调方法..........................");
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        if (code != null) {
            WxUser wxUser = getWxUser(code, wx_appId, wx_secret);
            log.info("微信用户信息" + wxUser);
            //通过state是否包含":"字符判断用户是微信绑定还是微信登录
            if (state.contains(":")) {
                binding(wxUser.getOpenid(), null, state);
            } else {
                Login(wxUser.getOpenid(), null, wxUser.getHeadimgurl(), state);
            }
        }
//        防止微信回调跳转重定向页面
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(
                "<script>\n" +
                        "window.open('','_self','');\n" +
                        "window.close();\n" + "</script>");
        out.close();
    }
    
    /**
     * 登录
     *
     * @Parmeter WXopenId 微信openId
     * QQopenId QQopenId
     * headUrl 头像路径
     * state字符串 包含：用户ID:websocket客户端ID
     */
    private void Login(String WXopenId, String QQopenId, String headUrl, String state) throws IOException {
        User user = userBizService.checkOpenId(WXopenId, QQopenId);
        if (user == null) {
            sendClient(state, LoginErrorCode.WXOPENID_ERROR.getErrorCode(), LoginErrorCode.WXOPENID_ERROR.getErrorMsg(), null);
        } else {
            String NewToken = JwtTokenUtil.createToken(user.getUsercode(), false);
            //		查看用户以及关联的员工信息
            Consumer consumer = userBizService.findConsumer(user.getUsercode());
            if (consumer == null) {
                sendClient(state, LoginErrorCode.STAFF_ERROR.getErrorCode(), LoginErrorCode.STAFF_ERROR.getErrorMsg(), null);
            } else {
                consumer.setAvatar(headUrl);
                consumer.setToken(NewToken);
                sendClient(state, LoginErrorCode.LOGIN_SUCCESS.getErrorCode(), LoginErrorCode.LOGIN_SUCCESS.getErrorMsg(), consumer);
            }
        }
    }


    /**
     * 系统账号绑定微信用户
     *
     * @Parmeter wxUser 微信用户
     * state字符串 包含：用户ID:websocket客户端ID
     */
    private void binding(String WXopenId, String QQopenId, String state) throws IOException {
        String id = state.substring(0, state.indexOf(":"));
        String clientId = state.substring(state.indexOf(":") + 1);
        userBizService.binding(id, WXopenId, QQopenId);
        User user = userBizService.checkOpenId(WXopenId, QQopenId);
        String NewToken = JwtTokenUtil.createToken(user.getUsercode(), false);
        //		查看用户以及关联的员工信息
        Consumer consumer = userBizService.findConsumer(user.getUsercode());
        consumer.setToken(NewToken);
        sendClient(clientId, LoginErrorCode.WXOPENID_SUCCESS.getErrorCode(), LoginErrorCode.WXOPENID_SUCCESS.getErrorMsg(), consumer);
    }

    /**
     * 返回微信平台的用户信息
     *
     * @Parmeter code 微信平台的code
     * @retrun WxUser 微信用户
     */
    private WxUser getWxUser(String code, String app_id, String secret) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + app_id
                + "&secret=" + secret
                + "&code=" + code
                + "&grant_type=authorization_code";
//            通过微信平台回调的code获取调用微信平台的token,以获取调用微信平台接口的权限
        String s = HttpClientUtils.httpGet(url);
//            将String类型的json字符串转换成对象
        Token token = JSON.parseObject(s, Token.class);
        String user_url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + token.getAccess_token() + "&openid=" + wx_appId;
        //            获取微信用户信息
        String s2 = HttpClientUtils.httpGet(user_url);
        return JSON.parseObject(s2, WxUser.class);
    }

    /**
     * 微信websocket发送消息给指定客户端
     *
     * @Parmeter clientId 客户端ID code 状态码 message 状态信息 consumer 返回的data对象
     */
    private void sendClient(String clientId, int code, String message, Consumer consumer) throws IOException {
        OtherResponseBody otherResponseBody = new OtherResponseBody();
        WebSocketServer webSocketServer = new WebSocketServer();
        ObjectMapper objectMapper = new ObjectMapper();
        otherResponseBody.setCode(String.valueOf(code));
        otherResponseBody.setMessage(message);
        otherResponseBody.setData(consumer);
        webSocketServer.sendtoUser(objectMapper.writeValueAsString(otherResponseBody), clientId);
    }


    /**
     * 微信解绑
     */
    @RequestMapping("/unbindingWx")
    @ResponseBody
    public Res unbindingWx(String id) {
        try {
            userBizService.unbindingWx(id);
            return Res.SUCCESS("微信解绑成功");
        } catch (Exception e) {
            log.error("微信解绑失败", e);
            return Res.ERROR("微信解绑失败");
        }

    }

    /**
     * qq解绑
     */
    @RequestMapping("/unbindingQQ")
    @ResponseBody
    public Res unbindingQQ(String id) {
        try {
            userBizService.unbindingQQ(id);
            return Res.SUCCESS("QQ解绑成功");
        } catch (Exception e) {
            log.error("QQ解绑失败", e);
            return Res.ERROR("QQ解绑失败");
        }

    }


}