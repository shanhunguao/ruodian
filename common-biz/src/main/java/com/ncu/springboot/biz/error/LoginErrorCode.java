package com.ncu.springboot.biz.error;

public enum LoginErrorCode implements ErrorType {

    UNKNOWN_ERROR(LOGIN_ERROR_CODE + 1, "未知错误"),
    USER_NEED_AUTHORITIES(LOGIN_ERROR_CODE + 2, "用户未登录"),
    USER_LOGIN_FAILED(LOGIN_ERROR_CODE + 3, "用户账号或密码错误"),
    USER_NO_ACCESS(LOGIN_ERROR_CODE + 4, "用户无权访问"),
    USER_LOGOUT_SUCCESS(LOGIN_ERROR_CODE + 5, "用户登出成功"),
    TOKEN_IS_BLACKLIST(LOGIN_ERROR_CODE + 6, "此token为黑名单"),
    LOGIN_IS_OVERDUE(LOGIN_ERROR_CODE + 7, "登录已失效"),
    LOGIN_SUCCESS(LOGIN_ERROR_CODE + 8, "登录成功"),
    SMSCODE_ERROR(LOGIN_ERROR_CODE + 9, "验证码错误"),
    MOBILE_ERROR(LOGIN_ERROR_CODE + 10, "该手机号不存在"),
    WXOPENID_ERROR(LOGIN_ERROR_CODE+11,"登录失败，请前往个人设置页绑定账号"),
    STAFF_ERROR(LOGIN_ERROR_CODE+12,"登录失败，用户未绑定员工账号"),
    WXOPENID_SUCCESS(LOGIN_ERROR_CODE+13,"账号绑定成功"),
    USER_NOUSER(USER_ERROR_CODE+1,"用户信息不存在"),
    GET_USER_ERROR(USER_ERROR_CODE+2,"获取用户信息失败"),
    QIYIWIXIN_ERROR(USER_ERROR_CODE+3,"获取企业用户信息失败");

    private int errorCode;
    private String errorMsg;

    LoginErrorCode(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}