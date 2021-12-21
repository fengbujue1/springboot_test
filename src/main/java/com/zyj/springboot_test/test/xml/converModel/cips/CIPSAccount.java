package com.zyj.springboot_test.test.xml.converModel.cips;


import com.zyj.springboot_test.test.xml.converModel.cips.interfaces.FinanInstIdentSetter;

/**
 * Description:
 * 收/付款人相关信息
 *
 * 参考文档归档于：http://192.168.0.2/svn/doc/Doc.Dev/BP/开发备忘/Wiki/交易报文规范/TX0022-跨境人民币支付（CIPS）.xlsx
 *
 * @Author 周赟吉
 * @Date 2021/11/17
 */
public class CIPSAccount implements FinanInstIdentSetter {
    /**
     *人名
     *
     */
    private String name;
    /**
     * 账号
     */
    private String accountNo;
    /**
     * 地址 使用","分割，每个地址长度最多70个字符
     */
    private String postalAddress;

    /**
     * 隶属的组织身份识别信息
     */
    private FinanInstIdent finanInstIdent;

    /**
     * 个人证件号码
     */
    private String IDCard;

    /**
     * 证件类型
     * 01：第一代居民身份证
     * 02：第二代居民身份证
     * 03：临时身份证
     * ...等等具体可参考文档
     */
    private String IDCardType;

    /**
     * EID
     */
    private String EID;
    /**
     * 国家地区代码
     */
    private String countryCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public FinanInstIdent getFinanInstIdent() {
        return finanInstIdent;
    }

    public void setFinanInstIdent(FinanInstIdent finanInstIdent) {
        this.finanInstIdent = finanInstIdent;
    }

    public String getIDCard() {
        return IDCard;
    }

    public void setIDCard(String IDCard) {
        this.IDCard = IDCard;
    }

    public String getIDCardType() {
        return IDCardType;
    }

    public void setIDCardType(String IDCardType) {
        this.IDCardType = IDCardType;
    }

    public String getEID() {
        return EID;
    }

    public void setEID(String EID) {
        this.EID = EID;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
