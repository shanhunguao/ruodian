package com.ncu.springboot.api.oauth2.bizservice;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.ncu.springboot.biz.entity.Posts;

public interface PostsBizService {

	Posts getDataById(Integer id);
	
	int insert(Posts posts);
	
	int remove(Integer id);
	
	int update(Posts posts);
	
	Page<Posts> postList(@Param("entity")Map<String , Object> map,@Param("pageNum")Integer pageNum,
			@Param("pageSize")Integer pageSize);
	
	int deleteDetil(int id);
	
	List<Posts> postDetil(@Param("entity")Map<String , Object> map);
}
