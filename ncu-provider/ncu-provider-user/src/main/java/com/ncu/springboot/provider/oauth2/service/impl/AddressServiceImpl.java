package com.ncu.springboot.provider.oauth2.service.impl;

import com.alibaba.fastjson.JSON;
import com.ncu.springboot.biz.entity.Address;
import com.ncu.springboot.common.util.HttpClientUtils;
import org.springframework.stereotype.Service;

/**
 * @Created by zhoufan
 * @Date 2020/3/20 10:01
 */
@Service
public class AddressServiceImpl implements AddressService {
    @Override
    public Address getAddress(String ip) {
        String s1 = HttpClientUtils.httpGet("https://restapi.amap.com/v3/ip?ip=" + ip + "&output=json&key=45d6f626870f92a3aa9a0b73f9bec80c");
        Address address = JSON.parseObject(s1, Address.class);
        return address;
    }


}
