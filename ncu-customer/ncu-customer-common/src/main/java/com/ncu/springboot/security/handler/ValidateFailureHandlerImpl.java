package com.ncu.springboot.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ncu.springboot.biz.error.LoginErrorCode;
import com.ncu.springboot.biz.rs.ResponseBodyWithData;
import com.ncu.springboot.security.exception.ValidateCodeException;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Created by zhoufan
 * @Date 2019/10/26 17:07
 */
@Component
public class ValidateFailureHandlerImpl implements ValidateFailureHandler {

    @Override
    public void onValidateFailure(HttpServletRequest var1, HttpServletResponse response, ValidateCodeException var3) throws IOException, ServletException {
        ResponseBodyWithData ajaxResponseBody = new ResponseBodyWithData<>();
        ajaxResponseBody.setCode(String.valueOf(LoginErrorCode.SMSCODE_ERROR.getErrorCode()));
        ajaxResponseBody.setMessage(var3.getMessage());
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().println(objectMapper.writeValueAsString(ajaxResponseBody));
        response.getWriter().flush();
    }

}
