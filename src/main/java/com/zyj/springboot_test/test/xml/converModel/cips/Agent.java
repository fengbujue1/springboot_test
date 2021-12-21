package com.zyj.springboot_test.test.xml.converModel.cips;


import com.zyj.springboot_test.test.xml.converModel.cips.interfaces.FinanInstIdentSetter;

/**
 * Description:
 * 代理机构相关信息
 *参考文档归档于：http://192.168.0.2/svn/doc/Doc.Dev/BP/开发备忘/Wiki/交易报文规范/TX0022-跨境人民币支付（CIPS）.xlsx
 *  *
 *
 * @Author 周赟吉
 * @Date 2021/11/17
 */
public class Agent implements FinanInstIdentSetter {
    /**
     * 名字
     */
    private String  name;
    /**
     *开户机构账号
     */
    private String accountNo;
    /**
     * 组织身份识别信息
     */
    private FinanInstIdent finanInstIdent;

    /**
     * 地址
     * 使用","分割，每个地址长度最多140个字节
     */
    private String postalAddress;

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String account) {
        this.accountNo = account;
    }

    public FinanInstIdent getFinanInstIdent() {
        return finanInstIdent;
    }

    public void setFinanInstIdent(FinanInstIdent finanInstIdent) {
        this.finanInstIdent = finanInstIdent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }
}
