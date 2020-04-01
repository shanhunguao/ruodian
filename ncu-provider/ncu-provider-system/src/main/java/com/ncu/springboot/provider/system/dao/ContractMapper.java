package com.ncu.springboot.provider.system.dao;

import com.github.pagehelper.Page;
import com.ncu.springboot.api.system.entity.Contract;
import com.ncu.springboot.api.system.entity.ContractDetails;
import com.ncu.springboot.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Created by zhoufan
 * @Date 2019/11/5 15:33
 */
public interface ContractMapper extends BaseMapper<Contract> {

    Page<Contract> find(@Param("contract") Contract contract, int pageNum, int pageSize);
    Integer total();
    Contract findId(Integer id);
    Integer updateBatch(List<ContractDetails> contractDetails);
    List<Integer> findIds(Integer id);
    Contract findDriver(Integer id);
}
