package com.ncu.springboot.provider.system.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ncu.springboot.api.system.bizservice.ContractTypeService;
import com.ncu.springboot.api.system.entity.ContractType;
import com.ncu.springboot.api.system.util.Tree;
import com.ncu.springboot.api.system.util.TreeUtils;
import com.ncu.springboot.provider.system.dao.ContractTypeMapper;
import org.osgi.service.component.annotations.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Created by zhoufan
 * @Date 2019/11/5 16:46
 */
@Service
@Component
public class ContractTypeServiceImpl implements ContractTypeService {
    @Resource
    private ContractTypeMapper contractTypeMapper;

    @Override
    public Tree<ContractType> findAll() {
        List<ContractType> contractTypes = contractTypeMapper.selectAll();
        List<Tree<ContractType>> trees = getTrees(contractTypes);
        return TreeUtils.builds(trees);
    }

    private List<Tree<ContractType>> getTrees(List<ContractType> contractTypes) {
        List<Tree<ContractType>> trees = new ArrayList<>();
        contractTypes.forEach(contractType -> {
            Tree<ContractType> tree = new Tree<>();
            tree.setId(String.valueOf(contractType.getContractTypeid()));
            tree.setParentId(String.valueOf(contractType.getParentId()));
            tree.setText(contractType.getContractTitle());
            trees.add(tree);
        });
        return trees;
    }
}
