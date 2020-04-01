package com.ncu.springboot.api.resource.bizservice;

import java.util.List;
import java.util.Map;

public interface MapBizService {
	
	List<Map<String, Object>> queryList(String name);

}
