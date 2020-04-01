package com.ncu.springboot.provider.resource.bizservice;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.osgi.service.component.annotations.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.ncu.springboot.api.resource.bizservice.CodeBizService;
import com.ncu.springboot.provider.resource.service.CodeService;

@Component
@Service
public class CodeBizServiceImpl implements CodeBizService {

	@Resource
	private CodeService codeService;
	
	public List<Map<String, Object>> query(Integer codeId, String option) {
		return codeService.query(codeId, option);
	}

}
