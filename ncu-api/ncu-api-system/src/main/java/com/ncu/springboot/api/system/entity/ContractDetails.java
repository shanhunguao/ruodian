package com.ncu.springboot.api.system.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


/**
 * @Created by zhoufan
 * @Date 2019/12/2 9:30
 */
@Table(name = "tb_contract_details")
public class ContractDetails implements Serializable {
    @Column(name = "id")
    @Id
    private Integer id;
    private String productCode;
    private String total;
    private String productName;
    private Double price;
    private Integer number;
    private String contractCode;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "ContractDetails{" +
                "id=" + id +
                ", productCode='" + productCode + '\'' +
                ", total='" + total + '\'' +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", number=" + number +
                ", contractCode='" + contractCode + '\'' +
                '}';
    }
}
