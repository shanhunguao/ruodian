package com.ncu.springboot.api.oauth2.bizservice;

import com.ncu.springboot.api.oauth2.entity.TbPermission;

import java.util.List;

/**
 * @Created by zhoufan
 * @Date 2020/3/30 17:12
 */
public interface TbPermissionService {
    List<TbPermission> selectByUserId(Integer userId);

}
