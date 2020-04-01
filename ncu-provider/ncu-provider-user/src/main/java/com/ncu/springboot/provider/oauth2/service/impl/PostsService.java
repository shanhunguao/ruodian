package com.ncu.springboot.provider.oauth2.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.ncu.springboot.biz.entity.Posts;


/**
 * 
 * @ClassName: PostsService
 * @Description: 职位接口
 * @author: www17
 * @date: 2019年9月10日 上午9:25:15
 */
public interface PostsService {

	Posts getDataById(Integer id);
	
	int insert(Posts posts);
	
	int remove(Integer id);
	
	int update(Posts posts);
	
	Page<Posts> postList(@Param("entity")Map<String , Object> map,@Param("pageNum")Integer pageNum,
			@Param("pageSize")Integer pageSize);
		
	int deleteDetil(int id); // 根据deptId删除明细
	
	List<Posts> postDetil(@Param("entity")Map<String , Object> map); // 根据条件查询职位明细
}
