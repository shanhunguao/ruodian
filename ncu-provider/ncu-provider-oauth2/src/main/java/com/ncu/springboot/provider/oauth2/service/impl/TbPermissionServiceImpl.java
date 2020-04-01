package com.ncu.springboot.provider.oauth2.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ncu.springboot.api.oauth2.bizservice.TbPermissionService;
import com.ncu.springboot.api.oauth2.entity.TbPermission;
import com.ncu.springboot.dao.TbPermissionMapper;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Created by zhoufan
 * @Date 2020/3/30 17:28
 */
@Service
public class TbPermissionServiceImpl implements TbPermissionService {
    @Resource
    private TbPermissionMapper tbPermissionMapper;

    @Override
    public List<TbPermission> selectByUserId(Integer userId) {
        return tbPermissionMapper.selectByUserId(userId);
    }
}
