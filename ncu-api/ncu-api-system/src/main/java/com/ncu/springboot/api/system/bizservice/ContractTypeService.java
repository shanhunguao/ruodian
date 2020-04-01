package com.ncu.springboot.api.system.bizservice;

import com.ncu.springboot.api.system.entity.ContractType;
import com.ncu.springboot.api.system.util.Tree;

/**
 * @Created by zhoufan
 * @Date 2019/11/5 16:46
 */
public interface ContractTypeService {
    Tree<ContractType> findAll();
}
