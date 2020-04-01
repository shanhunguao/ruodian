package com.ncu.springboot.provider.user.bizservice;

import com.alibaba.dubbo.config.annotation.Service;
import com.ncu.springboot.api.oauth2.bizservice.AddressBizService;
import com.ncu.springboot.biz.entity.Address;
import com.ncu.springboot.provider.oauth2.service.impl.AddressService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Created by zhoufan
 * @Date 2020/3/20 9:57
 */
@Component
@Service
public class AddressBizServiceImpl implements AddressBizService {

    @Resource
    private AddressService addressService;

    @Override
    public Address getAddress(String ip) {
        return addressService.getAddress(ip);
    }
}
