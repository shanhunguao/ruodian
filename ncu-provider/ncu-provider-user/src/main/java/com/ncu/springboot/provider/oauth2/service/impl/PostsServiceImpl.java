package com.ncu.springboot.provider.oauth2.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ncu.springboot.biz.entity.Posts;
import com.ncu.springboot.dao.PostsMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class PostsServiceImpl implements PostsService{

	@Resource
	private PostsMapper postsMapper;
	
	@Override
	public Posts getDataById(Integer id) {
		return postsMapper.getDataById(id);
	}

	@Override
	public int insert(Posts posts) {
		return postsMapper.insert(posts);
	}

	@Override
	public int remove(Integer id) {
		return postsMapper.deleteByPrimaryKey(id);
	}


	@Override
	public int update(Posts posts) {
		return postsMapper.updateByPrimaryKeySelective(posts);
	}

	@Override
	public int deleteDetil(int id) {
		return postsMapper.deleteDetil(id);
	}

	@Override
	public List<Posts> postDetil(Map<String, Object> map) {
		return postsMapper.postDetil(map);
	}

	@Override
	public Page<Posts> postList(Map<String, Object> map, Integer pageNum, Integer pageSize) {
		if (pageNum!=null || pageSize!=null) {
			PageHelper.startPage(pageNum, pageSize);
		}
		return postsMapper.postList(map);
	}

}
