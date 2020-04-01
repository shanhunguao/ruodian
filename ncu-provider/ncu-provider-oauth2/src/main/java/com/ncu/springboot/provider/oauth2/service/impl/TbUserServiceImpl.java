package com.ncu.springboot.provider.oauth2.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ncu.springboot.api.oauth2.bizservice.TbUserService;
import com.ncu.springboot.api.oauth2.entity.TbUser;
import com.ncu.springboot.dao.TbUserMapper;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;

/**
 * @Created by zhoufan
 * @Date 2020/3/30 15:57
 */
@Service
public class TbUserServiceImpl implements TbUserService {

    @Resource
    private TbUserMapper tbUserMapper;
    @Override
    public TbUser getByUsername(String username) {
        Example example = new Example(TbUser.class);
        example.createCriteria().andEqualTo("username", username);
        return tbUserMapper.selectOneByExample(example);
    }
}
