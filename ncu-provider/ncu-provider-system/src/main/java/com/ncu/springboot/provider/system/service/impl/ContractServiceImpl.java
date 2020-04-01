package com.ncu.springboot.provider.system.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ncu.springboot.api.system.bizservice.ContractService;
import com.ncu.springboot.api.system.entity.Contract;
import com.ncu.springboot.api.system.entity.ContractDetails;
import com.ncu.springboot.provider.system.dao.ContractDetailsMapper;
import com.ncu.springboot.provider.system.dao.ContractMapper;
import org.osgi.service.component.annotations.Component;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Created by zhoufan
 * @Date 2019/11/5 15:35
 */
@Service
@Component
public class ContractServiceImpl implements ContractService {
    @Resource
    private ContractMapper contractMapper;
    @Resource
    private ContractDetailsMapper contractDetailsMapper;


    @Override
    public Page<Contract> find(Contract contract, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return contractMapper.find(contract, pageNum, pageSize);
    }

    @Override
    public Integer total() {
        return contractMapper.total();
    }

    @Override
    @Transactional
    public void save(Contract contract) {
        contractMapper.insert(contract);
        List<ContractDetails> contractDetails = contract.getContractDetails();
        for (ContractDetails contractDetail : contractDetails) {
            contractDetailsMapper.insert(contractDetail);
        }
    }

    @Override
    @Transactional
    public void delete(String id) {
        Contract contract = contractMapper.selectByPrimaryKey(id);
        Example example = new Example(ContractDetails.class);
//        以合同编号为条件删除合同明细
        example.createCriteria().andCondition("contract_code=", contract.getContractCode());
        contractDetailsMapper.deleteByExample(example);
        contractMapper.deleteByPrimaryKey(id);

    }

    @Override
    @Transactional
    public void update(Contract contract) {
//        查询合同明细表主键，通过主键更新合同明细表
        List<Integer> ids = contractMapper.findIds(contract.getId());
        List<ContractDetails> contractDetails = contract.getContractDetails();
        for (int i = 0; i < contractDetails.size(); i++) {
            for (int i1 = 0; i1 < ids.size(); i1++) {
                contractDetails.get(i).setId(ids.get(i));
            }
        }
        for (ContractDetails contractDetail : contractDetails) {
            contractDetailsMapper.updateByPrimaryKey(contractDetail);
        }
        contractMapper.updateByPrimaryKey(contract);

    }


    @Override
    public Contract findId(String id) {
        Contract contract = contractMapper.selectByPrimaryKey(id);
        Integer contractTypeid = contract.getContractTypeid();
//        判断合同类型 只有设备采购合同才有安置日期
        if (contractTypeid != null && contractTypeid.equals(10001)) {
            return contractMapper.findDriver(contract.getId());
        }
        return contractMapper.findId(Integer.parseInt(id));
    }


}
