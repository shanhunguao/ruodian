package com.ncu.springboot.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.Page;
import com.ncu.springboot.api.system.bizservice.ContractService;
import com.ncu.springboot.api.system.entity.Contract;
import com.ncu.springboot.biz.rs.Res;
import com.ncu.springboot.core.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Created by zhoufan
 * @Date 2019/11/5 15:38
 * 处理合同
 */
@RestController
@RequestMapping("/contract")
public class ContractController {

    @Reference
    private ContractService contractService;

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/total")
    public Res total() {
        try {
            Integer total = contractService.total();
            return Res.SUCCESS(total);
        } catch (Exception e) {
            log.error("查询合同总数", e);
            return Res.ERROR("查询合同总数");
        }
    }

    @RequestMapping("/find")
    public Res find(Contract contract, int pageNum, int pageSize) {
        try {
            Page<Contract> all = contractService.find(contract, pageNum, pageSize);
            return Res.SUCCESS(all);
        } catch (Exception e) {
            log.error("条件查询合同失败", e);
            return Res.ERROR("条件查询合同失败");
        }
    }

    @RequestMapping("/save")
    public Res save(Contract contract) {
        try {
//            合同编号不能为空
            if (!StringUtil.isEmpty(contract.getContractCode())) {
                contractService.save(contract);
                return Res.SUCCESS("添加合同成功");
            }
            return Res.ERROR("合同编号不能为空");

        } catch (Exception e) {
            log.error("添加合同失败", e);
            return Res.ERROR("添加合同失败");
        }

    }

    @RequestMapping("/delete")
    public Res delete(String id) {
        try {
            contractService.delete(id);
            return Res.SUCCESS("删除合同成功");
        } catch (Exception e) {
            log.error("删除合同失败", e);
            return Res.ERROR("删除合同失败");
        }
    }

    @RequestMapping("/update")
    public Res update(Contract contract) {
        try {
            contractService.update(contract);
            return Res.SUCCESS("更新合同成功");
        } catch (Exception e) {
            log.error("更新合同失败", e);
            return Res.ERROR("更新合同失败");
        }
    }


    @RequestMapping("/findId")
    public Res findId(String id) {
        try {
            Contract contract = contractService.findId(id);
            return Res.SUCCESS(contract);
        } catch (Exception e) {
            log.error("查看合同失败", e);
            return Res.ERROR("查看合同失败");
        }
    }


}
