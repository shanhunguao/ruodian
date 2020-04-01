package com.ncu.springboot.provider.user.bizservice;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.ncu.springboot.api.oauth2.bizservice.PostsBizService;
import com.ncu.springboot.biz.entity.Posts;
import com.ncu.springboot.provider.oauth2.service.impl.PostsService;

@Component
@Service
public class PostsBizServiceImpl implements PostsBizService{

	@Resource
	private PostsService postsService;
	
	@Override
	public Posts getDataById(Integer id) {
		return postsService.getDataById(id);
	}

	@Override
	public int insert(Posts posts) {
		return postsService.insert(posts);
	}

	@Override
	public int remove(Integer id) {
		return postsService.remove(id);
	}

	@Override
	public int update(Posts posts) {
		return postsService.update(posts);
	}

	@Override
	public int deleteDetil(int id) {
		return postsService.deleteDetil(id);
	}

	@Override
	public List<Posts> postDetil(Map<String, Object> map) {
		return postsService.postDetil(map);
	}

	@Override
	public Page<Posts> postList(Map<String, Object> map, Integer pageNum, Integer pageSize) {
		return postsService.postList(map, pageNum, pageSize);
	}

}
