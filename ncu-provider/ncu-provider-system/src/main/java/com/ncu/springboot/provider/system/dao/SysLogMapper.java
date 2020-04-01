package com.ncu.springboot.provider.system.dao;

import com.github.pagehelper.Page;
import com.ncu.springboot.api.system.entity.SysLog;
import com.ncu.springboot.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Created by zhoufan
 * @Date 2019/10/23 10:26
 */
public interface SysLogMapper extends BaseMapper<SysLog> {
    Page<SysLog> findAll(int pageNum, int pageSize);

    Page<SysLog> find(@Param("sysLog") SysLog sysLog, int pageNum, int pageSize);

    List<Map<String, Object>> export();

    Integer total();
}