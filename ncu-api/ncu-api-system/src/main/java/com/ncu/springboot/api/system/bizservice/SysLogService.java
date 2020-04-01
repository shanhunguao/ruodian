package com.ncu.springboot.api.system.bizservice;

import com.github.pagehelper.Page;
import com.ncu.springboot.api.system.entity.SysLog;

import java.util.List;
import java.util.Map;

/**
 * @Created by zhoufan
 * @Date 2019/10/23 10:31
 */
public interface SysLogService {
    void saveLog(SysLog sysLog);

    Page<SysLog> findAll(int pageNum, int pageSize);

    Page<SysLog> find(SysLog sysLog, int pageNum, int pageSize);

    List<Map<String, Object>> export();

    void delete(Long id);

    Integer total();
}