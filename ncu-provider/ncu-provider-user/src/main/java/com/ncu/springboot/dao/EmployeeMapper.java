package com.ncu.springboot.dao;

import com.github.pagehelper.Page;
import com.ncu.springboot.biz.entity.Employee;
import com.ncu.springboot.biz.entity.UserInfos;
import com.ncu.springboot.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface EmployeeMapper extends BaseMapper<Employee> {

    Employee getDataById(String id); // 根据id查找数据（单条数据）

    int remove(int id); // 删除

    Page<Employee> employeeList(@Param("entity") Employee employee, @Param("pageNum") int pageNum,
                                @Param("pageSize") int pageSize); // 根据条件查询分页列表数据

    List<Employee> employeeDetil(Map<String, Object> map);

    Employee findId(String employeeId);

    Page<Employee> findDept(String deptId,@Param("pageNum") int pageNum,
    @Param("pageSize") int pageSize);

    List<UserInfos> find(@Param("entity")UserInfos userInfo);
    /**改变员工 部门 职位之间的关系*/
    Integer addLelaition(@Param("entity")Employee employee);
    Integer delLelaition(Integer id);

    Integer  updateLelaition(@Param("entity")Employee employee);
    //    删除部门关联的信息
    Integer delDeptLelaition(String deptId);
    Integer addEmployee(@Param("entity")Employee employee);
    Integer updateEmployee(@Param("entity")Employee employee);

    Integer findDeptCount(String deptId);
}
