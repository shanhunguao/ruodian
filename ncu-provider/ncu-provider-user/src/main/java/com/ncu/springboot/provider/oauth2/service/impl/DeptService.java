package com.ncu.springboot.provider.oauth2.service.impl;

import java.util.List;

import com.ncu.springboot.biz.rs.Res;

import com.ncu.springboot.biz.entity.Dept;

/**
 * @ClassName: DeptService
 * @Description: 部门接口
 * @author: czy
 * @date: 2019年9月10日 上午9:20:34
 */
public interface DeptService {

    Dept getDataById(String deptId);

    void remove(String id);

    int insert(Dept dept); // 添加部门和职位明细

    Res update(Dept dept); // 修改

    List<Dept> deptList();

    List<Dept> selectDept(List<Dept> deptList);

    Dept find(Dept dept);
    
    List<String> getChild(List<String> deptIds);
}
