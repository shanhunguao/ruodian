package com.ncu.springboot.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ncu.springboot.api.system.bizservice.ContractTypeService;
import com.ncu.springboot.api.system.entity.ContractType;
import com.ncu.springboot.api.system.util.Tree;
import com.ncu.springboot.biz.rs.Res;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Created by zhoufan
 * @Date 2019/11/5 16:50
 * 获取合同类型
 */
@RestController
@RequestMapping("/contractType")
public class ContractTypeController {
    @Reference
    private ContractTypeService contractTypeService;

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/findAll")
    public Res findAll(){
        try {
            Tree<ContractType> all = contractTypeService.findAll();
            return Res.SUCCESS(all);
        } catch (Exception e) {
            log.error("获取合同类型失败", e);
            return Res.ERROR("获取合同类型失败");
        }
    }
}
