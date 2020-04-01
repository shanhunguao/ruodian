package com.ncu.springboot.provider.oauth2.service.impl;

import com.ncu.springboot.biz.entity.Dept;
import com.ncu.springboot.biz.entity.Employee;
import com.ncu.springboot.biz.entity.Posts;
import com.ncu.springboot.biz.rs.Res;
import com.ncu.springboot.dao.DeptMapper;
import com.ncu.springboot.dao.EmployeeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeptServiceImpl implements DeptService {

    @Resource
    private DeptMapper deptMapper;

    @Resource
    private EmployeeMapper employeeMapper;

    @Override
    public Dept getDataById(String id) {
        Dept dept = deptMapper.getDataById(id);
        return dept;
    }

    @Override
    public int insert(Dept dept) {
        return deptMapper.insert(dept);
    }


    @Override
    @Transactional
    public void remove(String id) {
        if (deptMapper.deleteByPrimaryKey(id) > 0) {
            deptMapper.delDeptId(id);
        }
    }

    /**
     * 关联部门,职位和员工关系
     */
    @Override
    @Transactional
    public Res update(Dept dept) {
        dept.setUpdateTime(new Date());
        if (deptMapper.updateByPrimaryKey(dept) > 0) {
            List<Posts> postList = dept.getPostList();
//            检查员工是否已经分配了部门
            if (postList != null && !postList.isEmpty()) {
                for (Posts posts : postList) {
                    Employee employee = posts.getEmployee();
                    if (employee != null) {
                        Integer employee1 = deptMapper.findEmployee(employee.getId());
                        if (employee1 != null && !(dept.getId()).equals(employee1)) {
                            return Res.SUCCESS("员工" + employee.getEmployeeName() + "已分配了部门");
                        }
                    }
                }
                relevance(dept, postList);
                return Res.SUCCESS("更新员工成功");
            }
            return Res.SUCCESS();
        }
        return Res.ERROR("更新员工失败");
    }

    private void relevance(Dept dept, List<Posts> postList) {
        employeeMapper.delDeptLelaition(dept.getId());
        for (Posts posts : postList) {
            Employee employee = posts.getEmployee();
//                员工是否为空
            if (employee != null) {
//                        关联部门，职位与员工
                employee.setDeptId(String.valueOf(dept.getId()));
                employee.setPostsId(String.valueOf(posts.getId()));
                employeeMapper.addLelaition(employee);
            } else {
                Employee employee1 = new Employee();
//                        关联部门与职位
                employee1.setDeptId(String.valueOf(dept.getId()));
                employee1.setPostsId(String.valueOf(posts.getId()));
                employee1.setId(null);
                employeeMapper.addLelaition(employee1);
            }
        }
    }


    @Override
    public List<Dept> deptList() {
        return deptMapper.deptList();
    }

    @Override
    public List<Dept> selectDept(List<Dept> deptList) {
        List<Dept> depts = new ArrayList<Dept>();
        for (Dept dept : deptList) {
            if (dept != null) {
                depts.addAll(deptMapper.select(dept));
            }
        }
        //去重
        List<Dept> newList = depts.stream().distinct().collect(Collectors.toList());
        return newList;
    }

    @Override
    public Dept find(Dept dept) {
        return deptMapper.find(dept);
    }

    /*递归查询子部门id的方法*/
    public List<String> getChild(List<String> deptIds) {
        return deptMapper.getChild(deptIds);
    }
}
