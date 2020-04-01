package com.ncu.springboot.biz.rs;

import java.io.Serializable;

public class BaseResponseBody implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1412561677503091133L;
    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public BaseResponseBody setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public BaseResponseBody setMessage(String message) {
        this.message = message;
        return this;
    }

}
