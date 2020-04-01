package com.ncu.springboot.api.oauth2.bizservice;

import com.ncu.springboot.biz.entity.Address;

/**
 * @Created by zhoufan
 * @Date 2020/3/20 9:55
 *
 */
public interface AddressBizService {

    Address getAddress(String ip);
}
