package com.zyj.springboot_test.test.xml.converModel.cips;



import com.zyj.springboot_test.test.xml.converModel.cips.interfaces.FinanInstIdentSetter;

import java.math.BigDecimal;

/**
 * Description:
 * 费用承担相关信息
 *
 *
 * * 参考文档归档于：http://192.168.0.2/svn/doc/Doc.Dev/BP/开发备忘/Wiki/交易报文规范/TX0022-跨境人民币支付（CIPS）.xlsx
 *
 *
 * @Author 周赟吉
 * @Date 2021/11/17
 */
public class ChargesInformation implements FinanInstIdentSetter {
    /**
     * 货币符号
     *
     */
    private String currencyCode;
    /**
     * 金额
     *
     */
    private BigDecimal amount;

    /**
     * 费用承担方的身份识别信息
     */
    private FinanInstIdent finanInstIdent;

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public FinanInstIdent getFinanInstIdent() {
        return finanInstIdent;
    }

    public void setFinanInstIdent(FinanInstIdent finanInstIdent) {
        this.finanInstIdent = finanInstIdent;
    }
}
