package com.ncu.springboot.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Created by zhoufan
 * @Date 2020/3/5 8:51
 */
@Controller
@RequestMapping("/sso")
public class SsoController {

    //   人员管控重定向地址
    @Value("${cas.control.redirect}")
    private String casControlRedirect;

    @Value("${cas.light.redirect}")
    private String casLightRedirect;

    @Value("${cas.rule.redirect}")
    private String casRuleRedirect;

    /**
     * pc登录
     */
    @RequestMapping("/login")
    public String login(HttpServletRequest request) {
        String type = request.getParameter("type");
        HttpSession session = request.getSession();
        setRedirectUrl(type, session);
        return "/sso/login";
    }



    private void setRedirectUrl(String type, HttpSession session) {
        if (type != null) {
            switch (type) {
                case "0":
                    session.setAttribute("casRedirect", casControlRedirect);
                    break;
                case "1":
                    session.setAttribute("casRedirect", casLightRedirect);
                    break;
                case "2":
                    session.setAttribute("casRedirect", casRuleRedirect);
                    break;
            }
        } else {
            session.setAttribute("casRedirect", casControlRedirect);
        }
    }


}
