package com.ncu.springboot.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ncu.springboot.biz.error.LoginErrorCode;
import com.ncu.springboot.biz.rs.ResponseBodyWithData;

@Component
public class AjaxAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	
    @Override
    public void onAuthenticationSuccess(HttpServletRequest arg0, HttpServletResponse response,
            Authentication arg2) throws IOException, ServletException {
    	System.out.println("asdfsdafdsafsdfsad");
    	ResponseBodyWithData ajaxResponseBody = new ResponseBodyWithData<>();
        ajaxResponseBody.setCode(String.valueOf(LoginErrorCode.LOGIN_SUCCESS.getErrorCode()));
        ajaxResponseBody.setMessage(LoginErrorCode.LOGIN_SUCCESS.getErrorMsg());
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().println(objectMapper.writeValueAsString(ajaxResponseBody));
        response.getWriter().flush();
    }

}
