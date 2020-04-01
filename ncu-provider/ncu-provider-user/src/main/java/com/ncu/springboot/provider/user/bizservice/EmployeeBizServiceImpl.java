package com.ncu.springboot.provider.user.bizservice;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.ncu.springboot.api.oauth2.bizservice.EmployeeBizService;
import com.ncu.springboot.biz.entity.Employee;
import com.ncu.springboot.biz.entity.UserInfos;
import com.ncu.springboot.biz.rs.Res;
import com.ncu.springboot.provider.oauth2.service.impl.EmployeeService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Component
@Service
public class EmployeeBizServiceImpl implements EmployeeBizService {

    @Resource
    private EmployeeService employeeService;

    @Override
    public Employee getDataById(String id) {
        return employeeService.getDataById(id);
    }

    @Override
    public Res insert(Employee employee) {
        return employeeService.insert(employee);

    }

    @Override
    public Res remove(Integer id) {
        return employeeService.remove(id);
    }

    @Override
    public Res update(Employee employee) {
        return employeeService.update(employee);
    }

    @Override
    public Page<Employee> employeeList(Employee employee, int pageNum, int pageSize) {
        return employeeService.employeeList(employee, pageNum, pageSize);
    }

    @Override
    public List<Employee> selectEmployee(List<Employee> EmployeeList) {
        return employeeService.selectEmployee(EmployeeList);
    }

    @Override
    public Page<Employee> findDept(String deptId, int pageNum, int pageSize) {
        return employeeService.findDept(deptId, pageNum, pageSize);
    }

    @Override
    public Integer findDeptCount(String deptId) {
        return employeeService.findDeptCount(deptId);
    }

    @Override
    public List<UserInfos> find(UserInfos userInfos) {
        return employeeService.find(userInfos);
    }


    @Override
    public List<Employee> employeeDetil(Map<String, Object> map) {
        return employeeService.employeeDetil(map);
    }


}
