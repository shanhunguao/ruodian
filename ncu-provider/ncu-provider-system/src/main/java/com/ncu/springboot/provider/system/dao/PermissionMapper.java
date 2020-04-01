package com.ncu.springboot.provider.system.dao;

import com.ncu.springboot.biz.entity.Permission;
import com.ncu.springboot.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission> {
    List<Permission> selectUserMenu(String roleId);
    List<Permission> selectPermission();
    Permission findById(String menuId);
    List<String> findOne();
    List<String> findTwo(List<String> idList);
    List<String> findPark(@Param("userCode")String userCode,@Param("parentId")String parentId);
    String findPermissionId(String url);
    Integer findParentId(String parentId);
}
