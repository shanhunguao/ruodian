package com.ncu.springboot.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ncu.springboot.api.resource.bizservice.CodeBizService;
import com.ncu.springboot.biz.rs.Res;

@RestController
@RequestMapping("/code")
public class CodeController {

	@Reference
	private CodeBizService codeBizService;
	
	@RequestMapping("/query")
	public Res query(Integer codeId,String option) {
		List<Map<String, Object>> data = codeBizService.query(codeId, option);
		return Res.SUCCESS(data);
	}
}
