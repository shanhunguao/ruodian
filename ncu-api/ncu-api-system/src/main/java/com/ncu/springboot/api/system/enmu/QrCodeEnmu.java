package com.ncu.springboot.api.system.enmu;

/**
 * @Created by zhoufan
 * @Date 2019/11/19 9:46
 * 二维码内容状态
 */
public enum  QrCodeEnmu {
    scan,		//已被APP扫码状态
    login,		//处于登录状态
    cancel,		//已取消登录状态
    logout;		//尚未被扫码状态

}
