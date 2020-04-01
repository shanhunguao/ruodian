package com.ncu.springboot.biz.error;

public enum FileErrorCode implements ErrorType {

    UNKNOWN_ERROR(FILE_ERROR_CODE + 1, "未知错误"),
    UPLOAD_FILE_ERROR(FILE_ERROR_CODE + 2, "文件类型不匹配");

    private int errorCode;
    private String errorMsg;
    
    FileErrorCode(int errorCode, String errorMsg) {
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
