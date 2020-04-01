package com.ncu.springboot.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码错误异常
 * @Created by zhoufan
 * @Date 2019/10/21 13:46
 */
public class ValidateCodeException extends AuthenticationException {
    private static final long serialVersionUID = 5022575393500654458L;

    public ValidateCodeException(String message) {
        super(message);
    }
}
