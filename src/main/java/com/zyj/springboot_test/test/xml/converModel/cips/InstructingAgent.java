package com.zyj.springboot_test.test.xml.converModel.cips;

/**
 * Description:
 * 转账参与者相关信息
 *参考文档归档于：http://192.168.0.2/svn/doc/Doc.Dev/BP/开发备忘/Wiki/交易报文规范/TX0022-跨境人民币支付（CIPS）.xlsx
 *
 * @Author 周赟吉
 * @Date 2021/11/17
 */
public class InstructingAgent {
    /**
     * 直接参与者
     */
    private FinanInstIdent financialInstitutionIdentification;
    /**
     * 间接参与者
     */
    private FinanInstIdent branchIdentification;

    public FinanInstIdent getFinancialInstitutionIdentification() {
        return financialInstitutionIdentification;
    }

    public void setFinancialInstitutionIdentification(FinanInstIdent financialInstitutionIdentification) {
        this.financialInstitutionIdentification = financialInstitutionIdentification;
    }

    public FinanInstIdent getBranchIdentification() {
        return branchIdentification;
    }

    public void setBranchIdentification(FinanInstIdent branchIdentification) {
        this.branchIdentification = branchIdentification;
    }
}
