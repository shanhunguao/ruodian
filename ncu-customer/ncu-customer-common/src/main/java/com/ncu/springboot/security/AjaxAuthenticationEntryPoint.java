package com.ncu.springboot.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ncu.springboot.biz.error.LoginErrorCode;
import com.ncu.springboot.biz.rs.ResponseBodyWithData;

@Component
public class AjaxAuthenticationEntryPoint implements AuthenticationEntryPoint {

    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
    	ResponseBodyWithData ajaxResponseBody = new ResponseBodyWithData<>();
        ajaxResponseBody.setCode(String.valueOf(LoginErrorCode.USER_NEED_AUTHORITIES.getErrorCode()));
        ajaxResponseBody.setMessage(LoginErrorCode.USER_NEED_AUTHORITIES.getErrorMsg());
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().println(objectMapper.writeValueAsString(ajaxResponseBody));
        response.getWriter().flush();
    }

}
