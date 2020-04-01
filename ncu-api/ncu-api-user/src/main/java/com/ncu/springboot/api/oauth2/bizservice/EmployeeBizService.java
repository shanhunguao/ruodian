package com.ncu.springboot.api.oauth2.bizservice;

import com.github.pagehelper.Page;
import com.ncu.springboot.biz.entity.Employee;
import com.ncu.springboot.biz.entity.UserInfos;
import com.ncu.springboot.biz.rs.Res;

import java.util.List;
import java.util.Map;

public interface EmployeeBizService {

    Employee getDataById(String id);

    Res insert(Employee employee);

    Res remove(Integer id);

    Res update(Employee employee);

    List<Employee> employeeDetil(Map<String, Object> map);

    Page<Employee> employeeList(Employee employee, int pageNum,
                                int pageSize);

    List<Employee> selectEmployee(List<Employee> EmployeeList);

    Page<Employee> findDept(String deptId, int pageNum,
                            int pageSize);

    Integer findDeptCount(String deptId);

    List<UserInfos> find(UserInfos userInfos);


}
