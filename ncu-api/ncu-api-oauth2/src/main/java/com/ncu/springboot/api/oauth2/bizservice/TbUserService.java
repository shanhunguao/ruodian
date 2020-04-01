package com.ncu.springboot.api.oauth2.bizservice;

import com.ncu.springboot.api.oauth2.entity.TbUser;

/**
 * @Created by zhoufan
 * @Date 2020/3/30 15:45
 */
public interface TbUserService {
    TbUser getByUsername(String username);
}
