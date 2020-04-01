package com.ncu.springboot.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ncu.springboot.api.oauth2.bizservice.PostsBizService;
import com.ncu.springboot.biz.entity.Posts;
import com.ncu.springboot.biz.rs.Res;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/posts")
public class PostsController {

	@Reference
	private PostsBizService postsBizService;
	
	@RequestMapping("/list")
	public Res list(Map<String , Object> param,@RequestParam(name = "pageNum", required = false)Integer pageNum,
					@RequestParam(name = "pageSize", required = false) Integer pageSize) {
		List<Posts> list = postsBizService.postList(param, pageNum, pageSize);
		Map<String , Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("rows", list);
		return Res.SUCCESS(jsonMap);
	}
	
	@RequestMapping("/from")
	public Res from(int id) {
		Posts posts = postsBizService.getDataById(id);
		Map<String , Object> map = new HashMap<String, Object>();
		map.put("fromObject", posts);
		return Res.SUCCESS(map);
	}
	
	@RequestMapping("/save")
	public Res save(Posts posts) {
		if (postsBizService.insert(posts) > 0) {
			return Res.SUCCESS();
		}
		return Res.ERROR();
	}
	
	@RequestMapping("/remove")
	public Res remove(int id) {
		if (postsBizService.remove(id) > 0) {
			return Res.SUCCESS();
		}
		return Res.ERROR();
	}
	
	@RequestMapping("/update")
	public Res update(Posts posts) {
		try {
			if (postsBizService.update(posts) > 0) {
				return Res.SUCCESS();
			}
			return Res.ERROR();
		}catch (Exception e) {
			return Res.ERROR("更新失败");
		}

	}
}
