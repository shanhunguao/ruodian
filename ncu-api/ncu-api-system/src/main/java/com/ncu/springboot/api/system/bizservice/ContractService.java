package com.ncu.springboot.api.system.bizservice;

import com.github.pagehelper.Page;
import com.ncu.springboot.api.system.entity.Contract;

/**
 * @Created by zhoufan
 * @Date 2019/11/5 15:35
 */
public interface ContractService {


    Page<Contract> find(Contract contract, int pageNum, int pageSize);

    void save(Contract contract);

    void delete(String id);

    void update(Contract contract);

    Integer total();

    Contract findId(String id);


}
