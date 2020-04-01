package com.ncu.springboot.provider.resource.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.ncu.springboot.dao.CodeMapper;
import com.ncu.springboot.provider.resource.service.CodeService;

@Service
public class CodeServiceImpl implements CodeService {

	@Resource
	private CodeMapper codeMapper;
	
	public List<Map<String, Object>> query(Integer codeId, String option) {
		return codeMapper.query(codeId, option);
	}

}
