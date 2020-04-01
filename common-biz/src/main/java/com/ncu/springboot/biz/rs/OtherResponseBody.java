package com.ncu.springboot.biz.rs;

import java.io.Serializable;

/**
 * @Created by zhoufan
 * @Date 2019/10/28 10:04
 */
public class OtherResponseBody<T> implements Serializable {
    private static final long serialVersionUID = 1412561677503091134L;
    private String code;
    private String message;
    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
