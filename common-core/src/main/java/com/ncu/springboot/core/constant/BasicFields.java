package com.ncu.springboot.core.constant;

/**
 * 基础字段
 * */
public interface BasicFields {

    /**
     * 错误码
     */
    String ERROR_NO = "errorNo";

    /**
     * 错误信息
     */
    String ERROR_INFO = "errorInfo";

    /**
     * 结果列表
     */
    String RESULT_LIST = "resultList";

    /**
     * 权限列表
     */
    String RESOURCE_LIST = "resourceList";
    /**
     * 扩展错误码（外围系统的原始错误码）
     */
    String EXT_ERROR_NO = "extErrorNo";

    /**
     * 扩展错误信息（外围系统的原始错误信息）
     */
    String EXT_ERROR_INFO = "extErrorInfo";

    /**
     * 错误对象列表
     */
    String ERRORS = "errors";

    /**
     * 错误位置
     */
    String ERROR_ON = "errorOn";

    /**
     * 错误值
     */
    String ERROR_VALUE = "errorValue";

    /**
     * 消息/描述
     */
    String MESSAGE = "message";

    /**
     * 会话令牌（session token）
     */
    String ACCESS_TOKEN = "accessToken";
    /**
     * 手机号码
     */
    String MOBILE = "mobile";
    /**
     * 内容
     */
    String CONTENT = "content";
    /**
     * 标签
     */
    String TAG = "tag";
    /**
     * api-密钥
     */
    String APIKEY = "apikey";
    /**
     * 返回状态
     */
    String RETURNSTATUS = "returnstatus";
}
