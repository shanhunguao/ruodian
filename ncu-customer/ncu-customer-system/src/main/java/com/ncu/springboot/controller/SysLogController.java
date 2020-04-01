package com.ncu.springboot.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.Page;
import com.ncu.springboot.api.system.bizservice.SysLogService;
import com.ncu.springboot.api.system.entity.SysLog;
import com.ncu.springboot.api.system.util.ExcelUtil;
import com.ncu.springboot.biz.rs.Res;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Created by zhoufan
 * @Date 2019/10/23 10:30
 */
@RestController
@RequestMapping("/Log")
public class SysLogController {
    @Reference
    private SysLogService sysLogService;


    private Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 查询日志
     */
    @RequestMapping("/findAll")
    public Res findAll(int pageNum, int pageSize) {
        try {
            Page<SysLog> all = sysLogService.findAll(pageNum, pageSize);
            return Res.SUCCESS(all);
        } catch (Exception e) {
            log.error("查询日志失败", e);
            return Res.ERROR("查询日志失败");
        }
    }

    @RequestMapping("/total")
    public Res total(){
        try {
            Integer total = sysLogService.total();
            return Res.SUCCESS(total);
        } catch (Exception e) {
            log.error("查询日志总数失败", e);
            return Res.ERROR("查询日志总数失败");
        }
    }


    /**
     * 条件查询日志
     */
    @RequestMapping("/find")
    public Res find(SysLog sysLog, int pageNum, int pageSize) {
        try {
            Page<SysLog> sysLogs = sysLogService.find(sysLog, pageNum, pageSize);
            return Res.SUCCESS(sysLogs);
        } catch (Exception e) {
            log.error("条件查询日志失败", e);
            return Res.ERROR("条件查询日志失败");
        }
    }

    @RequestMapping("/delete")
    public Res delete(Long id) {
        try {
            sysLogService.delete(id);
            return Res.SUCCESS("删除日志成功");
        } catch (Exception e) {
            log.error("删除日志失败", e);
            return Res.ERROR("删除日志失败");
        }
    }

    /**
     * EXCEL导出功能
     */
    @RequestMapping("/export")
    public void export(HttpServletResponse response) {
        List<Map<String, Object>> datas = sysLogService.export();
        List<String> columns = new ArrayList<String>();
        columns.add("ID");
        columns.add("用户名");
        columns.add("操作内容");
        columns.add("操作方法");
        columns.add("操作参数");
        columns.add("日志类型");
        columns.add("IP地址");
        columns.add("创建时间");
        List<String> keys = new ArrayList<String>();
        keys.add("ID");
        keys.add("USERNAME");
        keys.add("OPERATION");
        keys.add("METHOD");
        keys.add("PARAMS");
        keys.add("OPERATION_TYPE");
        keys.add("IP");
        keys.add("CREATE_TIME");
        try {
            ExcelUtil.export(datas, "日志", columns, response, keys);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }






}