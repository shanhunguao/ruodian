package com.ncu.springboot.api.oauth2.bizservice;

import com.ncu.springboot.biz.entity.Dept;
import com.ncu.springboot.biz.rs.Res;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DeptBizService {

    Dept getDataById(String deptId);

    void remove(String id);

    Res update(Dept dept);

    int insert(Dept dept);

    List<Dept> deptList();

    List<Dept> selectDept(List<Dept> deptList);

    Dept find(Dept dept);
    
    List<String> getChild(List<String> deptIds);

}
