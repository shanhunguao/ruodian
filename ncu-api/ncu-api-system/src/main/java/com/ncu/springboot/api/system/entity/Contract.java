package com.ncu.springboot.api.system.entity;


import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;


/**
 * @Created by zhoufan
 * @Date 2019/11/5 15:21
 */
@Table(name = "tb_contract")
public class Contract implements Serializable {
    @Column(name = "id")
    @Id
    private Integer id;
    private String contractName;
    private String projectName;
    private String contractParty;
    private String contractClient;
    private String contractPartyAddress;
    private String contractClientAddress;
    private String contractCode;
    private String projectCode;
    private Double contractAmount;
    private String payment;
    @Column(name = "contract_status")
    private Integer contractStatus;
    private Double amountPaid;
    @Column(name = "signing_time")
    private Date signingTime;
    private String makeMoney;
    @Column(name = "starting_time")
    private Date startingTime;
    private String afterMarker;
    @Column(name = "ending_time")
    private Date endingTime;
    @Column(name = "qulity_date")
    private Date qulityDate;
    private String partyMessage;
    private String secondPartyMessage;
    @Column(name = "contract_typeid")
    private Integer contractTypeid;
    private String contractFile;
    private String remark;
    private List<ContractDetails> contractDetails;
    private Date layoutTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getContractParty() {
        return contractParty;
    }

    public void setContractParty(String contractParty) {
        this.contractParty = contractParty;
    }

    public String getContractClient() {
        return contractClient;
    }

    public void setContractClient(String contractClient) {
        this.contractClient = contractClient;
    }

    public String getContractPartyAddress() {
        return contractPartyAddress;
    }

    public void setContractPartyAddress(String contractPartyAddress) {
        this.contractPartyAddress = contractPartyAddress;
    }

    public String getContractClientAddress() {
        return contractClientAddress;
    }

    public void setContractClientAddress(String contractClientAddress) {
        this.contractClientAddress = contractClientAddress;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public Double getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(Double contractAmount) {
        this.contractAmount = contractAmount;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public Integer getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(Integer contractStatus) {
        this.contractStatus = contractStatus;
    }

    public Double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public Date getSigningTime() {
        return signingTime;
    }

    public void setSigningTime(Date signingTime) {
        this.signingTime = signingTime;
    }

    public String getMakeMoney() {
        return makeMoney;
    }

    public void setMakeMoney(String makeMoney) {
        this.makeMoney = makeMoney;
    }

    public Date getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(Date startingTime) {
        this.startingTime = startingTime;
    }

    public String getAfterMarker() {
        return afterMarker;
    }

    public void setAfterMarker(String afterMarker) {
        this.afterMarker = afterMarker;
    }

    public Date getEndingTime() {
        return endingTime;
    }

    public void setEndingTime(Date endingTime) {
        this.endingTime = endingTime;
    }

    public Date getQulityDate() {
        return qulityDate;
    }

    public void setQulityDate(Date qulityDate) {
        this.qulityDate = qulityDate;
    }

    public String getPartyMessage() {
        return partyMessage;
    }

    public void setPartyMessage(String partyMessage) {
        this.partyMessage = partyMessage;
    }

    public String getSecondPartyMessage() {
        return secondPartyMessage;
    }

    public void setSecondPartyMessage(String secondPartyMessage) {
        this.secondPartyMessage = secondPartyMessage;
    }

    public Integer getContractTypeid() {
        return contractTypeid;
    }

    public void setContractTypeid(Integer contractTypeid) {
        this.contractTypeid = contractTypeid;
    }

    public String getContractFile() {
        return contractFile;
    }

    public void setContractFile(String contractFile) {
        this.contractFile = contractFile;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<ContractDetails> getContractDetails() {
        return contractDetails;
    }

    public void setContractDetails(List<ContractDetails> contractDetails) {
        this.contractDetails = contractDetails;
    }

    public Date getLayoutTime() {
        return layoutTime;
    }

    public void setLayoutTime(Date layoutTime) {
        this.layoutTime = layoutTime;
    }

    @Override
    public String toString() {
        return "Contract{" +
                "id=" + id +
                ", contractName='" + contractName + '\'' +
                ", projectName='" + projectName + '\'' +
                ", contractParty='" + contractParty + '\'' +
                ", contractClient='" + contractClient + '\'' +
                ", contractPartyAddress='" + contractPartyAddress + '\'' +
                ", contractClientAddress='" + contractClientAddress + '\'' +
                ", contractCode='" + contractCode + '\'' +
                ", projectCode='" + projectCode + '\'' +
                ", contractAmount=" + contractAmount +
                ", payment='" + payment + '\'' +
                ", contractStatus=" + contractStatus +
                ", amountPaid=" + amountPaid +
                ", signingTime=" + signingTime +
                ", makeMoney='" + makeMoney + '\'' +
                ", startingTime=" + startingTime +
                ", afterMarker='" + afterMarker + '\'' +
                ", endingTime=" + endingTime +
                ", qulityDate=" + qulityDate +
                ", partyMessage='" + partyMessage + '\'' +
                ", secondPartyMessage='" + secondPartyMessage + '\'' +
                ", contractTypeid=" + contractTypeid +
                ", contractFile='" + contractFile + '\'' +
                ", remark='" + remark + '\'' +
                ", contractDetails=" + contractDetails +
                ", layoutTime=" + layoutTime +
                '}';
    }
}
