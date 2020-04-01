package com.ncu.springboot.dao;

import com.ncu.springboot.api.oauth2.entity.TbPermission;

import java.util.List;

/**
 * @Created by zhoufan
 * @Date 2020/3/30 17:29
 */
public interface TbPermissionMapper {
    List<TbPermission> selectByUserId(Integer userId);
}
