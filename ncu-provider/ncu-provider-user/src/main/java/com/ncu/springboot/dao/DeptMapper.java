package com.ncu.springboot.dao;

import com.ncu.springboot.biz.entity.Dept;
import com.ncu.springboot.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface DeptMapper extends BaseMapper<Dept> {

    Dept getDataById(String id); // 根据id查找数据（单条数据）

    int remove(int id); // 删除

    List<Dept> deptList();

    Dept find(@Param("entity")Dept dept);

    int delDeptId(String deptId);

    Integer findEmployee(Integer employeeId);

    int findDept(Integer deptId);

    int addPostId(@Param("postId") Integer postId, @Param("employeeId") Integer employeeId);
    
    List<String> getChild(@Param("deptIds")List<String> deptIds);
}
