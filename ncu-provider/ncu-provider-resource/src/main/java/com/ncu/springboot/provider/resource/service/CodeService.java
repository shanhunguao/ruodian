package com.ncu.springboot.provider.resource.service;

import java.util.List;
import java.util.Map;

public interface CodeService {
	
	List<Map<String, Object>> query(Integer codeId,String option);
	
}
