package com.ncu.springboot.provider.oauth2.service.impl;

import com.github.pagehelper.Page;
import com.ncu.springboot.biz.entity.Employee;
import com.ncu.springboot.biz.entity.UserInfos;
import com.ncu.springboot.biz.rs.Res;

import java.util.List;
import java.util.Map;


/**
 * @ClassName: EmployeeService
 * @Description: 员工接口
 * @author: czy
 * @date: 2019年9月10日 上午9:20:56
 */
public interface EmployeeService {

    Employee getDataById(String id);

    Res insert(Employee employee);

    Res remove(Integer id);

    Res update(Employee employee);

    Page<Employee> employeeList(Employee employee, int pageNum,
                                int pageSize);

    List<Employee> employeeDetil(Map<String, Object> map);

    List<Employee> selectEmployee(List<Employee> EmployeeList);

    Page<Employee> findDept(String deptId, int pageNum, int pageSize);

    Integer findDeptCount(String deptId);

    List<UserInfos> find(UserInfos userInfo);


}
