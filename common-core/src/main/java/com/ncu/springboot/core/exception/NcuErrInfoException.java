package com.ncu.springboot.core.exception;

import com.ncu.springboot.core.util.CollectionUtil;

public class NcuErrInfoException extends RuntimeException {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    protected String errorNo;
    protected String errorInfo;

    public NcuErrInfoException(String errorNo, String errorInfo) {
        this(errorInfo, errorNo, errorInfo);
    }

    public NcuErrInfoException(Throwable cause, String errorNo, String errorInfo) {
        this(errorInfo, cause, errorNo, errorInfo);
    }

    public NcuErrInfoException(String message, String errorNo, String errorInfo) {
        super(message);
        this.errorNo = errorNo;
        this.errorInfo = errorInfo;
    }

    public NcuErrInfoException(String message, Throwable cause, String errorNo, String errorInfo) {
        super(message, cause);
        this.errorNo = errorNo;
        this.errorInfo = errorInfo;
    }

    public NcuErrInfoException(String errorNo) {
        this.errorNo = errorNo;
    }

    public NcuErrInfoException(Throwable cause, String errorNo) {
        super(cause);
        this.errorNo = errorNo;
    }

    public boolean isErrorOf(String errorNo) {
        return null != this.errorNo && this.errorNo.equals(errorNo);
    }

    public boolean isErrorIn(String... errorNos) {
        if (null != this.errorNo && !CollectionUtil.isEmpty(errorNos)) {
            for (String errorNo : errorNos) {
                if (this.errorNo.equals(errorNo)) {
                    return true;
                }
            }
        }
        return false;
    }

    public String getErrorNo() {
        return errorNo;
    }

    public void setErrorNo(String errorNo) {
        this.errorNo = errorNo;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

}
