package com.ncu.springboot.api.system.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Created by zhoufan
 * @Date 2019/11/5 16:41
 */
@Table(name = "tb_contract_type")
public class ContractType implements Serializable {
    @Column(name = "id")
    @Id
    private Integer id;
    @Column(name = "contract_typeid")
    private Integer contractTypeid;
    private String contractTitle;
    @Column(name = "parent_id")
    private Integer parentId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getContractTypeid() {
        return contractTypeid;
    }

    public void setContractTypeid(Integer contractTypeid) {
        this.contractTypeid = contractTypeid;
    }

    public String getContractTitle() {
        return contractTitle;
    }

    public void setContractTitle(String contractTitle) {
        this.contractTitle = contractTitle;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return "ContractType{" +
                "id=" + id +
                ", contractTypeid=" + contractTypeid +
                ", contractTitle='" + contractTitle + '\'' +
                ", parentId=" + parentId +
                '}';
    }
}
