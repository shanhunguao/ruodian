package com.ncu.springboot.provider.system.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ncu.springboot.api.system.bizservice.SysLogService;
import com.ncu.springboot.api.system.entity.SysLog;
import com.ncu.springboot.provider.system.dao.SysLogMapper;
import org.osgi.service.component.annotations.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Created by zhoufan
 * @Date 2019/10/23 10:32
 */
@Service
@Component
public class SysLogServiceImpl implements SysLogService {
    @Resource
    private SysLogMapper sysLogMapper;

    @Override
    public Page<SysLog> findAll(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return sysLogMapper.findAll(pageNum, pageSize);
    }

    @Transactional
    public void saveLog(SysLog sysLog) {
        sysLogMapper.insert(sysLog);
    }

    public Page<SysLog> find(SysLog sysLog, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return sysLogMapper.find(sysLog, pageNum, pageSize);
    }

    @Override
    public List<Map<String, Object>> export() {
        return sysLogMapper.export();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        SysLog sysLog = new SysLog();
        sysLog.setId(id);
        sysLogMapper.delete(sysLog);
    }

    @Override
    public Integer total() {
        return sysLogMapper.total();
    }


}