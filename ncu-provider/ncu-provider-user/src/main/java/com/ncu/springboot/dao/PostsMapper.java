package com.ncu.springboot.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.ncu.springboot.biz.entity.Posts;
import com.ncu.springboot.mybatis.mapper.BaseMapper;

public interface PostsMapper extends BaseMapper<Posts> {

	Posts getDataById(int id); // 根据id查找数据（单条数据）
	
	int remove(int id); // 删除
	
	Page<Posts> postList(@Param("entity")Map<String , Object> map); // 根据条件查询分页列表数据
	
	int deleteDetil(int id);
	
	List<Posts> postDetil(@Param("entity")Map<String , Object> map); // 根据条件查询数据
}
