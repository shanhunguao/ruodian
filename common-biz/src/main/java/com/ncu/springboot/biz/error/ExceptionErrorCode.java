package com.ncu.springboot.biz.error;

public enum ExceptionErrorCode implements ErrorType {

    UNKNOWN_ERROR(EXCEPTION_ERROR_CODE + 1, "未知错误"),
    NULLEXCEPTION_ERROR(EXCEPTION_ERROR_CODE + 2, "空指针异常"),
    PARAM_ERROR(EXCEPTION_ERROR_CODE + 3, "参数错误");

    private int errorCode;
    private String errorMsg;
    
    ExceptionErrorCode(int errorCode, String errorMsg) {
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
