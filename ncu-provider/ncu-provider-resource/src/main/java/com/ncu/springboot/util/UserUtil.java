package com.ncu.springboot.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ncu.springboot.api.oauth2.bizservice.DeptBizService;
import com.ncu.springboot.api.oauth2.bizservice.EmployeeBizService;
import com.ncu.springboot.api.oauth2.bizservice.UserBizService;
import com.ncu.springboot.biz.entity.Dept;
import com.ncu.springboot.biz.entity.Employee;
import com.ncu.springboot.biz.entity.User;
import com.ncu.springboot.common.util.Utils;

@Component
public class UserUtil {

	@Reference
	private DeptBizService deptBizService;

	@Reference
	private EmployeeBizService employeeBizService;
	
	@Reference
	private UserBizService userBizService;

	public List<Map<String, Object>> getDeptMapByDeptName(List<String> deptNames) {
		//去重
		deptNames = deptNames.stream().distinct().collect(Collectors.toList());
		List<Dept> deptList = new ArrayList<Dept>();
		for (String deptName : deptNames) {
			Dept dept = new Dept();
			dept.setName(deptName);
			deptList.add(dept);
		}
		return Utils.objectToMap(deptBizService.selectDept(deptList));
	}

	public List<Map<String, Object>> getEmployeeMapByEmployeeName(List<String> employeeNames){
		//去重
		employeeNames = employeeNames.stream().distinct().collect(Collectors.toList());
		List<Employee> employeeList = new ArrayList<Employee>();
		for (String employeeName : employeeNames) {
			Employee employee = new Employee();
			employee.setEmployeeName(employeeName);
			employeeList.add(employee);
		}
		return Utils.objectToMap(employeeBizService.selectEmployee(employeeList));
	}
	
	public List<Map<String, Object>> getUserMapByUserCode(List<String> userCodes){
		//去重
		userCodes = userCodes.stream().distinct().collect(Collectors.toList());
		List<User> userList = new ArrayList<User>();
		for (String userCode : userCodes) {
			User user = new User();
			user.setUsercode(userCode);
			userList.add(user);
		}
		return Utils.objectToMap(userBizService.selectUser(userList));
	}

}
