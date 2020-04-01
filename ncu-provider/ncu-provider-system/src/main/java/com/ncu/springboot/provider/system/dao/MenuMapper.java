package com.ncu.springboot.provider.system.dao;

import com.ncu.springboot.api.system.entity.Menu;
import com.ncu.springboot.mybatis.mapper.BaseMapper;

import java.util.List;

public interface MenuMapper extends BaseMapper<Menu> {


    List<Menu> findUserMenus(String userName);


}