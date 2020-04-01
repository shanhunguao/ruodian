package com.ncu.springboot.controller;

import com.alibaba.fastjson.JSON;
import com.ncu.springboot.api.system.entity.Ticket;
import com.ncu.springboot.api.system.entity.Tickets;
import com.ncu.springboot.api.system.entity.WeChatToken;
import com.ncu.springboot.api.system.util.SecuritySHA1Utils;
import com.ncu.springboot.biz.rs.Res;
import com.ncu.springboot.common.util.HttpClientUtils;
import com.ncu.springboot.utils.RedisUtil;
import com.ncu.springboot.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Created by zhoufan
 * @Date 2020/3/31 15:52
 */
@RestController
@RequestMapping("/Ticket")
public class TicketController {

    @Value("${qiye.weixin.gettoken}")
    private String qiyeWeixinGettoken;

    @Autowired
    private RedisUtil redisUtil;


    @RequestMapping("/getTicket")
    public Res getTicket(String url,String noncestr ,String appId) {
        if (!StringUtils.isEmpty(url)) {
            String ticket;
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
            return Res.SUCCESS(new Tickets(appId, timestamp, noncestr, signature));
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


}
