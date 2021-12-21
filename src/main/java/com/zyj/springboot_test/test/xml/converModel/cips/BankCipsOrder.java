package com.zyj.springboot_test.test.xml.converModel.cips;


import java.util.Date;
import java.util.Map;

/**
 * Description:
 * CIPS 转账 指令
 *
 *
 * 参考文档归档于：http://192.168.0.2/svn/doc/Doc.Dev/BP/开发备忘/Wiki/交易报文规范/TX0022-跨境人民币支付（CIPS）.xlsx
 *
 *
 * @Author 周赟吉
 * @Date 2021/11/17
 */
public class BankCipsOrder extends BankOrder {
    /**
     * 银行账户所属银行的编号
     * 如：交通银行：05
     *
     */
    private String bankNo;

    /**
     * 业务种类编码
     *
     */
    private String proprietary;
    /**
     * 起息日
     * yyyyMMdd
     */
    private Date valueDate;
    /**
     * 业务优先级
     * 可填数据：
     * HIGH
     * NORM
     * URGT
     */
    private String settlementPriority;
//    /**
//     * 结算时间
//     * yyyyMMddHHmmss
//     */
//    private Date debitDateTime;
    /**
     * 汇率
     * 最多 11 位数字，其中小数部分
     * 最多 10 位数字。
     */
    private Double exchangeRate;
    /**
     * 费用承担方
     *  DEBT(对应?MT：OUR)
     *  CRED(对应?MT：BEN)
     *  SHARSLEV（双方约定的方式）
     */
    private String chargeBearer;

    /**
     * 费用承担相关信息
     */
    private ChargesInformation chargesInformation;

    /**
     * 付款人信息
     */
    private CIPSAccount payer;

    /**
     * 付款方代理机构相关信息
     */
    private Agent payerAgent;

    /**
     * 付款方代理机构相关信息
     */
    private Agent receiverAgent;

    /**
     * 收款人信息
     */
    private CIPSAccount receiver;

    /**
     * 致收款人银行附言
     */
    private String toBankRemark;

    /**
     * 订单编号
     */
    private String billNum;

    /**
     * 订单描述
     */
    private String billInfo;

    /**
     * 附言
     */
    private String postScript;

    /**
     * 期望结算日期
     */
    private Date expectDate;

    /**
     * 付款方直接参与者相关信息
     */
    private FinanInstIdent paymentDirectPart;
    /**
     * 付款方间接参与者相关信息
     */
    private FinanInstIdent paymentInDirectPart;

    /**
     * 收款方直接参与者相关信息
     */
    private FinanInstIdent receiveDirectPart;
    /**
     * 收款方间接参与者相关信息
     */
    private FinanInstIdent receiveInDirectPart;

    /**
     * 中介机构1相关信息
     */
    private FinanInstIdent intermediaryAgent1;

    /**
     * 中介机构2相关信息
     */
    private FinanInstIdent intermediaryAgent2;

    /**
     * 付款方的BPAccount,用于server 到 front 发送请求的时候路由前置机节点
     */
    private BPAccount payerBPAccount;

    /**
     * 附件数据
     */
    private Map accessory;

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getProprietary() {
        return proprietary;
    }

    public void setProprietary(String proprietary) {
        this.proprietary = proprietary;
    }

    public Date getValueDate() {
        return valueDate;
    }

    public void setValueDate(Date valueDate) {
        this.valueDate = valueDate;
    }

    public String getSettlementPriority() {
        return settlementPriority;
    }

    public void setSettlementPriority(String settlementPriority) {
        this.settlementPriority = settlementPriority;
    }

    public Double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getChargeBearer() {
        return chargeBearer;
    }

    public void setChargeBearer(String chargeBearer) {
        this.chargeBearer = chargeBearer;
    }

    public ChargesInformation getChargesInformation() {
        return chargesInformation;
    }

    public void setChargesInformation(ChargesInformation chargesInformation) {
        this.chargesInformation = chargesInformation;
    }

    public CIPSAccount getPayer() {
        return payer;
    }

    public void setPayer(CIPSAccount payer) {
        this.payer = payer;
    }

    public Agent getPayerAgent() {
        return payerAgent;
    }

    public void setPayerAgent(Agent payerAgent) {
        this.payerAgent = payerAgent;
    }

    public Agent getReceiverAgent() {
        return receiverAgent;
    }

    public void setReceiverAgent(Agent receiverAgent) {
        this.receiverAgent = receiverAgent;
    }

    public CIPSAccount getReceiver() {
        return receiver;
    }

    public void setReceiver(CIPSAccount receiver) {
        this.receiver = receiver;
    }


    public FinanInstIdent getIntermediaryAgent1() {
        return intermediaryAgent1;
    }

    public void setIntermediaryAgent1(FinanInstIdent intermediaryAgent1) {
        this.intermediaryAgent1 = intermediaryAgent1;
    }

    public FinanInstIdent getIntermediaryAgent2() {
        return intermediaryAgent2;
    }

    public void setIntermediaryAgent2(FinanInstIdent intermediaryAgent2) {
        this.intermediaryAgent2 = intermediaryAgent2;
    }

    public FinanInstIdent getPaymentDirectPart() {
        return paymentDirectPart;
    }

    public void setPaymentDirectPart(FinanInstIdent paymentDirectPart) {
        this.paymentDirectPart = paymentDirectPart;
    }

    public FinanInstIdent getPaymentInDirectPart() {
        return paymentInDirectPart;
    }

    public void setPaymentInDirectPart(FinanInstIdent paymentInDirectPart) {
        this.paymentInDirectPart = paymentInDirectPart;
    }

    public FinanInstIdent getReceiveDirectPart() {
        return receiveDirectPart;
    }

    public void setReceiveDirectPart(FinanInstIdent receiveDirectPart) {
        this.receiveDirectPart = receiveDirectPart;
    }

    public FinanInstIdent getReceiveInDirectPart() {
        return receiveInDirectPart;
    }

    public void setReceiveInDirectPart(FinanInstIdent receiveInDirectPart) {
        this.receiveInDirectPart = receiveInDirectPart;
    }

    public String getToBankRemark() {
        return toBankRemark;
    }

    public void setToBankRemark(String toBankRemark) {
        this.toBankRemark = toBankRemark;
    }

    public String getBillNum() {
        return billNum;
    }

    public void setBillNum(String billNum) {
        this.billNum = billNum;
    }

    public String getBillInfo() {
        return billInfo;
    }

    public void setBillInfo(String billInfo) {
        this.billInfo = billInfo;
    }

    public String getPostScript() {
        return postScript;
    }

    public void setPostScript(String postScript) {
        this.postScript = postScript;
    }

    public Date getExpectDate() {
        return expectDate;
    }

    public void setExpectDate(Date expectDate) {
        this.expectDate = expectDate;
    }

    public BPAccount getPayerBPAccount() {
        return payerBPAccount;
    }

    public void setPayerBPAccount(BPAccount payerBPAccount) {
        this.payerBPAccount = payerBPAccount;
    }

    public Map getAccessory() {
        return accessory;
    }

    public void setAccessory(Map accessory) {
        this.accessory = accessory;
    }
}
