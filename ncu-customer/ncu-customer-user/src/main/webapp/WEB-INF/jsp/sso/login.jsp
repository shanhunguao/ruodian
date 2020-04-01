<%@ page import="com.ncu.springboot.controller.ToolAuthController" %>

<%@ page import="com.ncu.springboot.core.util.JwtTokenUtil" %>

<%@ page import="org.springframework.web.context.WebApplicationContext" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="org.springframework.beans.factory.annotation.Autowired" %>
<%@ page import="com.ncu.springboot.utils.RedisUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="supwisdom/CASUtils.jsp" %>
<%!


    public boolean doLogin(LoginUser loginUser, HttpServletRequest request) {
        HttpSession session = request.getSession();
        ServletContext application = session.getServletContext();
        // 如果使用了Spring可以用下面的方法获取spring的context对象
        WebApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(application);
//        重写自己的登录逻辑
        ToolAuthController service = (ToolAuthController) appContext.getBean("toolAuthController");
        return service.findUserInfo(loginUser.getAccount());
    }
%>
<%
    String targetUrl = CasUtils.getTargetUrl(request);
    if (CasUtils.isLogin(session)) {
        response.sendRedirect(targetUrl);
    } else {
        if (CasUtils.hasTicket(request)) {
            LoginUser loginUser = CasUtils.getLoginUser(request);
            if (loginUser.isLogin() && doLogin(loginUser, request)) {
                CasUtils.login(loginUser, session);
                String account = loginUser.getAccount();
                String token = JwtTokenUtil.createToken(account, false);
//                获取spring容器中的redisUtil的bean对象
                WebApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(application);
                RedisUtil redisUtil = (RedisUtil) appContext.getBean("redisUtil");
//                将token放入缓存中
                redisUtil.set("cas.token." + token, account, 4800L);
//                重定向前端页面
                response.sendRedirect(targetUrl + "?token=" + token);
            } else {
                response.sendRedirect(CasUtils.getLogoutUrl(request));
            }
        } else {
            String loginUrl = CasUtils.getLoginUrl(request);
            response.sendRedirect(loginUrl);
        }
    }
%>