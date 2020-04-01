package com.ncu.springboot.provider.oauth2.service.impl;

import com.ncu.springboot.biz.entity.Address;

/**
 * @Created by zhoufan
 * @Date 2020/3/20 9:58
 */
public interface AddressService {
    Address getAddress(String ip);
}
