package com.zyj.springboot_test.test.xml.converModel.cips;

/**
 * Description:
 * Financial Institution Identification
 * 金融机构识别信息
 * @Author 周赟吉
 * @Date 2021/11/17
 *
 * * 参考文档归档于：http://192.168.0.2/svn/doc/Doc.Dev/BP/开发备忘/Wiki/交易报文规范/TX0022-跨境人民币支付（CIPS）.xlsx
 *
 *
 * */
public class FinanInstIdent {
    /**
     * 企业组织机构代码
     *
     */
    private String organizationCode;

    /**
     * 机构名称
     */
    private String name;

    /**
     * 开户机构行号
     *
     */
    private String memberIdNo;
    /**
     * LEI 码
     */
    private String LEI;
    /**
     * CIPS身份识别
     * 填写 CIPS ID
     */
    private String CIPSID;


    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMemberIdNo() {
        return memberIdNo;
    }

    public void setMemberIdNo(String memberIdNo) {
        this.memberIdNo = memberIdNo;
    }

    public String getLEI() {
        return LEI;
    }

    public void setLEI(String LEI) {
        this.LEI = LEI;
    }

    public String getCIPSID() {
        return CIPSID;
    }

    public void setCIPSID(String CIPSID) {
        this.CIPSID = CIPSID;
    }
}
