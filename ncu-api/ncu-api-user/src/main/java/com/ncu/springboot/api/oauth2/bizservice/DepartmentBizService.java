package com.ncu.springboot.api.oauth2.bizservice;

import java.util.List;

import com.ncu.springboot.biz.entity.Department;
import com.ncu.springboot.biz.entity.Posts;

public interface DepartmentBizService {
	
	/**
	  *    添加部门
	 * @param dept
	 */
	void addDepartment(Department dept);
	
	/**
	  *     删除部门
	 * @param dept_id
	 */
	void deleteDepartment(String dept_id);
	
	/**
	  *    获取部门信息
	 * @param dept_id
	 * @return
	 */
	Department getDepartmentInfo(String dept_id);
	
	/**
	  *    获取所有部门
	 * @return
	 */
	List<Department> getAllDepartments();
	
	/**
	  *    获取所有岗位
	 * @param dept_id
	 * @return
	 */
	List<Posts> getAllPosts();
	
	/**
	  *    获取部门中的岗位及该岗位负责人信息
	 * @param dept_id
	 * @return
	 */
	List<Posts> getAllPostsOfDepartment(String dept_id);
	
}
