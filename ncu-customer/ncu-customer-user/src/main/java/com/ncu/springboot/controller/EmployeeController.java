package com.ncu.springboot.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.Page;
import com.ncu.springboot.api.oauth2.bizservice.EmployeeBizService;
import com.ncu.springboot.biz.entity.Employee;
import com.ncu.springboot.biz.entity.UserInfos;
import com.ncu.springboot.biz.rs.Res;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Reference
    private EmployeeBizService employeeBizService;

    @RequestMapping("/list")
    public Res list(Employee employee, int pageNum,
                    int pageSize) {
        Page<Employee> list = employeeBizService.employeeList(employee, pageNum, pageSize);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("rows", list);
        return Res.SUCCESS(jsonMap);
    }


    /**
     * 根据部门ID查看部门下的员工
     */
    @RequestMapping("/findDept")
    public Res findDept(String deptId, int pageNum,
                        int pageSize) {
        List<Employee> dept = employeeBizService.findDept(deptId, pageNum, pageSize);
        return Res.SUCCESS(dept);
    }


    /**
     * 根据部门ID查看部门下的员工总数
     */
    @RequestMapping("/findDeptCount")
    public Res findDeptCount(String deptId) {
        Integer deptCount = employeeBizService.findDeptCount(deptId);
        return Res.SUCCESS(deptCount);
    }

    /**
     * 根据员工ID查询员工信息以及关联的部门,职位
     */
    @RequestMapping("/from")
    public Res from(String id) {
        Employee employee = employeeBizService.getDataById(id);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("fromObject", employee);
        return Res.SUCCESS(map);
    }


    @RequestMapping("/save")
    public Res save(Employee employee) {
        return employeeBizService.insert(employee);

    }

    @RequestMapping("/remove")
    public Res remove(int id) {
        return employeeBizService.remove(id);
    }

    @RequestMapping("/update")
    public Res update(Employee employee) {
        return employeeBizService.update(employee);

    }

    /**
     * 李宇豪依赖的接口
     */
    @RequestMapping("/find")
    public Res find(UserInfos userInfos) {
        List<UserInfos> userInfos1 = employeeBizService.find(userInfos);
        return Res.SUCCESS(userInfos1);
    }


}
