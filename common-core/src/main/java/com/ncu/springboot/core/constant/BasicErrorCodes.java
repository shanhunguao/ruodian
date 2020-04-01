package com.ncu.springboot.core.constant;

/**
 * 基础错误码
 * */
public interface BasicErrorCodes {
    
    /*
     * 执行成功：0
     */
    /**
     * 执行成功
     */
    String SUCCESS = "0";
    
    /*
     * 通用错误：1 ~ 99
     */
    /**
     * 执行错误
     */
    String COMMON_ERROR = "1";
    /**
     * 业务服务错误
     */
    String SERVICE_ERROR = "2";
    /**
     * 非法访问
     */
    String ILLEGAL_ACCESS = "3";
    /**
     * 无此功能
     */
    String NO_SUCH_FUNCTION = "4";
    /**
     * 并发修改失败
     */
    String CONCURRENT_MODIFY = "10";
    /**
     * 重复请求
     */
    String DUPLICATE_REQUEST = "11";
    /**
     * 访问超时（包括connectTimeout的情况）
     */
    String ACCESS_TIMEOUT = "12";
    /**
     * 处理超时（包括socketTimeout的情况）
     */
    String PROCESSING_TIMEOUT = "13";
    /**
     * 未登录或已超时
     */
    String NOT_LOGGED = "20";
    /**
     * 没有权限
     */
    String NO_PERMISSION = "21";

    /**
     * AccessToken无效
     */
    String ACCESS_TOKEN_INVALID = "22";

    /**
     * 操作不受支持
     */
    String OPERATION_NOT_SUPPORT = "23";


    // 调用周边系统失败可以50 ~ 99
    
    /* 
     * 参数错误：100 ~ 999
     */
    /**
     * 参数错误
     */
    String PARAM_ERROR = "100";
    
    /* 
     * 业务错误：1000 ~
     */
}
