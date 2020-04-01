package com.ncu.springboot.provider.control.service;

import java.util.List;
import java.util.Map;

import com.ncu.springboot.api.control.entity.WorkColumn;

public interface WorkColumnService {

	Integer getTotal (String name);

	List<Map<String, String>> queryList(String name, Integer size, Integer num);

	Integer addWorkColumn(WorkColumn workColumn);

	Integer editWorkColumn(WorkColumn workColumn);

	Integer delWorkColumn(List<Integer> ids);

	WorkColumn query(Integer id);
}
