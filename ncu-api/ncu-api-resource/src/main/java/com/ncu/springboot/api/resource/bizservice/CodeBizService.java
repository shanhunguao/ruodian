package com.ncu.springboot.api.resource.bizservice;

import java.util.List;
import java.util.Map;

public interface CodeBizService {

	List<Map<String, Object>> query(Integer codeId,String option);
	
}
