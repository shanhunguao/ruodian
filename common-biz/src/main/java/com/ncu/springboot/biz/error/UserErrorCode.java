package com.ncu.springboot.biz.error;

public enum UserErrorCode implements ErrorType {
	
	USER_CODE_DUPLICATE(USER_ERROR_CODE + 1, "该用户编码已存在"),
	MOBILE_DUPLICATE(USER_ERROR_CODE + 2, "该电话号码已存在"),
	EMAIL_DUPLICATE(USER_ERROR_CODE + 3, "该Email地址已存在"),
	ID_CARD_DUPLICATE(USER_ERROR_CODE + 4, "该身份证号码已存在");
	
	private int errorCode;
    private String errorMsg;

    UserErrorCode(int errorCode, String errorMsg) {
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
