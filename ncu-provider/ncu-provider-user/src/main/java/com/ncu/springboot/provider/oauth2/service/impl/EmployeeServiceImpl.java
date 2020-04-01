package com.ncu.springboot.provider.oauth2.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ncu.springboot.biz.entity.Employee;
import com.ncu.springboot.biz.entity.UserInfos;
import com.ncu.springboot.biz.rs.Res;
import com.ncu.springboot.dao.EmployeeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Resource
    private EmployeeMapper employeeMapper;

    @Override
    public Employee getDataById(String id) {
        return employeeMapper.getDataById(id);
    }

    @Override
    @Transactional
    public Res insert(Employee employee) {
        if (employeeMapper.addEmployee(employee) > 0) {
            Employee employee2 = employeeMapper.findId(employee.getEmployeeId());
//            添加员工 部门 职位之间的关系
            employee.setId(employee2.getId());
            employeeMapper.addLelaition(employee);
            return Res.SUCCESS("添加员工成功");
        }
        return Res.ERROR("添加员工失败");
    }

    @Override
    @Transactional
    public Res remove(Integer id) {
        if (employeeMapper.deleteByPrimaryKey(id) > 0) {
            employeeMapper.delLelaition(id);
            return Res.ERROR("删除员工成功");
        }
        return Res.ERROR("删除员工失败");
    }

    @Override
    @Transactional
    public Res update(Employee employee) {
        if (employeeMapper.updateEmployee(employee) > 0) {
            employeeMapper.updateLelaition(employee);
            return Res.SUCCESS("修改员工成功");
        }
        return Res.ERROR("修改员工失败");

    }

    @Override
    public Page<Employee> employeeList(Employee employee, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return employeeMapper.employeeList(employee, pageNum, pageSize);
    }

    @Override
    public List<Employee> employeeDetil(Map<String, Object> map) {
        return employeeMapper.employeeDetil(map);
    }

    @Override
    public List<Employee> selectEmployee(List<Employee> EmployeeList) {
        List<Employee> employees = new ArrayList<Employee>();
        for (Employee employee : EmployeeList) {
            if (employee != null) {
                employees.addAll(employeeMapper.select(employee));
            }
        }
        //去重
        List<Employee> newList = employees.stream().distinct().collect(Collectors.toList());
        return newList;
    }

    @Override
    public Page<Employee> findDept(String deptId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return employeeMapper.findDept(deptId, pageNum, pageSize);
    }

    @Override
    public Integer findDeptCount(String deptId) {
        return employeeMapper.findDeptCount(deptId);
    }

    @Override
    public List<UserInfos> find(UserInfos userInfo) {
        return employeeMapper.find(userInfo);
    }


}
