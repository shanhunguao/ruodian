package com.ncu.springboot.security.handler;

import com.ncu.springboot.security.exception.ValidateCodeException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Created by zhoufan
 * @Date 2019/10/26 17:06
 */
public interface ValidateFailureHandler {

    void onValidateFailure(HttpServletRequest var1, HttpServletResponse var2, ValidateCodeException var3) throws IOException, ServletException;

}
