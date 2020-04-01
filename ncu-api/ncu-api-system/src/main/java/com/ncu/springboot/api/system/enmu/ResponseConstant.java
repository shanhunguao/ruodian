package com.ncu.springboot.api.system.enmu;

/**
 * @Created by zhoufan
 * @Date 2019/11/19 15:11
 */
public enum ResponseConstant {
    SUCCESS("0", "请求成功"),
    IVALID_QRCODE("101", "二维码已失效"),
    NOSCAN_SUCCESS("102", "二维码未扫描"),
    SCAN_SUCCESS("103", "二维码扫描成功"),
    CANCEL_SUCCESS("104", "取消登录成功"),
    LOGIN_SUCCESS("105", "确认登录成功");

    private String responseCode;

    private String responseMsg;

    ResponseConstant(String responseCode, String responseMsg) {
        this.responseCode = responseCode;
        this.responseMsg = responseMsg;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }
}
