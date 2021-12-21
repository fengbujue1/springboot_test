package com.zyj.springboot_test.test.xml.converModel.cips;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BankOrder extends ExtAttribute implements Serializable {
	public static final Integer STATE_WAITING = new Integer(0);

	public static final Integer STATE_READY = new Integer(1);
	public static final Integer STATE_SENDING = new Integer(2);
	public static final Integer STATE_SUCCESS = new Integer(3);
	public static final Integer STATE_FAILED = new Integer(4);
	public static final Integer STATE_CANCELSURE = new Integer(5);
	public static final Integer STATE_DISUSED = new Integer(-1);
	private static final long serialVersionUID = 1L;

	/**
	 * ָ��ID
	 */
	private Integer id;

	/**
	 * ��ָ��ID
	 */
	private Integer masterId;

	/**
	 * Դָ���ID
	 */
	private String srcPacketId;

	/**
	 * ָ������. ��ӦBTXCode
	 * <ul>
	 * <li>�������ʽ�ת TXD
	 * <li>���Ƕ����ʽ�ת TXN
	 * <li>�������� TXA
	 * 
	 * <li>�����۸��� TWP
	 * <li>���������� TPP
	 * <li>��������ҵ TWC
	 * <li>��������ҵ TPC
	 * </ul>
	 * 
	 */
	private String type;

	/**
	 * ҵ������
	 * 
	 */
	private String bizType;

	/**
	 * ����
	 * 
	 */
	private Date date;

	/**
	 * ���ִ���
	 * 
	 */
	private String currencyCode;

	/**
	 * ���
	 * 
	 */
	private BigDecimal amount;

	/**
	 * ����Ա��
	 */
	private String clearUserNumber;

	/**
	 * ��;
	 * 
	 */
	private String explain;

	/**
	 * ��ҵ�ͻ����˱�ʶ
	 * 
	 */
	private String customMark;

	/**
	 * ԭʼҵ���ʶ
	 * 
	 */
	private String sourceId;

	/**
	 * ��ע
	 * 
	 */
	private String comment;

	/**
	 * У����
	 * 
	 */
	private String fingerPrint;

	/**
	 * ȫ��ָ����
	 */
	private String globalFingerPrint;

	/**
	 * �״�ͨѶʱ��
	 */
	private Date firstExTime;

	/**
	 * ĩ��ͨѶʱ��
	 */
	private Date lastExTime;

	/**
	 * ͨѶ����
	 */
	private Integer messageCount;

	/**
	 * �´�����ʱ��
	 */
	private Date nextSureTime;
	/**
	 * ָ���ʱ��
	 */
	private Date createTime;

	/**
	 * ͨѶʱʹ�õĽڵ�ID
	 */
	private String frontNodeId;

	/**
	 * ����ָ��ͨѶʱ���ɵ���ϢID�����ɰ��������ڣ�.
	 */
	private String messageId;

	/**
	 * ����ָ��ͨѶʱ���ɵİ�ID�����ɰ��������ڣ�.
	 */
	private String packetId;

	/**
	 * ָ��״̬
	 */
	private Integer state = STATE_WAITING;

	/**
	 * ���估ִ��״̬
	 */
	private Integer transStatus = 1;

	/**
	 * ���ж��������ɵ�ID
	 */
	private String serverReturnId;

	/**
	 * ���ж˷��صĳ������
	 */
	private String serverErrorCode;

	/**
	 * ���ж˷��صĳ�����Ϣ
	 */
	private String serverErrorText;

	/**
	 * ָ����������Դ�BankOrderOperation�л�ȡ
	 */
	private String operation;

	/**
	 * ���״���
	 * 
	 */
	private String transCode;

	/**
	 * ϵͳ��Դ��ʶ��
	 * 
	 */
	private String appNo;

	/**
	 * ��������(1������ֱ����0��swift��)
	 * 
	 */
	private Integer channelType;
	/**
	 * ���б���
	 */
	private String bankCurrencyCode;
	/**
	 * �������
	 */
	private String channelNo;
	/**
	 * �����������ɱ�ǣ�1��ʾ���ڽ�����������
	 */
	private String doingUnsureFlag;
	/**
	 * �������ɴ���
	 */
	private Integer drivingUnsureCount;
	/**
	 * ���һ�η����������ɵ�ʱ��
	 */
	private Date drivingUnsureLastTime;
	/**
	 * ��Ʊ���
	 */
	private String refundFlag;
	/**
	 * ��¼��Ϣ����ʱ��
	 */
	private String sendTime;

	public BankOrder() {
		super();
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public String getComment() {
		return comment;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public String getCustomMark() {
		return customMark;
	}

	public Date getDate() {
		return date;
	}

	public String getExplain() {
		return explain;
	}

	public String getFingerPrint() {
		return fingerPrint;
	}

	public Date getFirstExTime() {
		return firstExTime;
	}

	public String getFrontNodeId() {
		return frontNodeId;
	}

	public Integer getId() {
		return id;
	}

	public Date getLastExTime() {
		return lastExTime;
	}

	public Integer getMessageCount() {
		return messageCount;
	}

	public String getMessageId() {
		return messageId;
	}

	public Date getNextSureTime() {
		return nextSureTime;
	}

	public String getPacketId() {
		return packetId;
	}

	public String getServerErrorCode() {
		return serverErrorCode;
	}

	public String getServerErrorText() {
		return serverErrorText;
	}

	public String getServerReturnId() {
		return serverReturnId;
	}

	public String getSourceId() {
		return sourceId;
	}

	public Integer getState() {
		return state;
	}

	public String getStateText() {
		return getStateText(state);
	}

	public String getTransferStatusText() {
		return "1";
	}

	public Integer getTransStatus() {
		return transStatus;
	}

	/**
	 * ָ������. ��ӦBTXCode
	 * <ul>
	 * <li>�������ʽ�ת TXD
	 * <li>���Ƕ����ʽ�ת TXN
	 * <li>�������� TXA
	 * 
	 * <li>�����۸��� TWP
	 * <li>���������� TPP
	 * <li>��������ҵ TWC
	 * <li>��������ҵ TPC
	 * </ul>
	 * 
	 */
	public String getType() {
		return type;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public void setCustomMark(String customMark) {
		this.customMark = customMark;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	public void setFingerPrint(String fingerPrint) {
		this.fingerPrint = fingerPrint;
	}

	public void setFirstExTime(Date firstExTime) {
		this.firstExTime = firstExTime;
	}

	public void setFrontNodeId(String frontNodeId) {
		this.frontNodeId = frontNodeId;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setLastExTime(Date lastExTime) {
		this.lastExTime = lastExTime;
	}

	public void setMessageCount(Integer messageCount) {
		this.messageCount = messageCount;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public void setNextSureTime(Date nextSureTime) {
		this.nextSureTime = nextSureTime;
	}

	public void setPacketId(String packetId) {
		this.packetId = packetId;
	}

	public void setServerErrorCode(String serverErrorCode) {
		this.serverErrorCode = serverErrorCode;
	}

	public void setServerErrorText(String serverErrorText) {
		this.serverErrorText = serverErrorText;
	}

	public void setServerReturnId(String serverReturnId) {
		this.serverReturnId = serverReturnId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public void setTransStatus(Integer transStatus) {
		this.transStatus = transStatus;
	}

	public String getClearUserNumber() {
		return clearUserNumber;
	}

	public void setClearUserNumber(String clearUserNumber) {
		this.clearUserNumber = clearUserNumber;
	}

	/**
	 * ָ������. ��ӦBTXCode
	 * <ul>
	 * <li>�������ʽ�ת TXD
	 * <li>���Ƕ����ʽ�ת TXN
	 * <li>�������� TXA
	 * 
	 * <li>�����۸��� TWP
	 * <li>���������� TPP
	 * <li>��������ҵ TWC
	 * <li>��������ҵ TPC
	 * </ul>
	 * 
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * ����ҵ������<br/>
	 * 2010-01-06:��Ͽ�����ӿ���ӣ����ڱ�BP3���׸���ϸ���Ľӿ�����
	 * 
	 * @return String
	 */
	public String getBizType() {
		return bizType;
	}

	/**
	 * ����ҵ������<br/>
	 * 2010-01-06:��Ͽ�����ӿ���ӣ����ڱ�BP3���׸���ϸ���Ľӿ�����
	 * 
	 * @param bizType
	 */
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public static String getStateText(Integer state) {
		if (STATE_WAITING.equals(state))
			return "WAITING";
		if (STATE_READY.equals(state))
			return "READY";
		if (STATE_SENDING.equals(state))
			return "WSENDING";
		if (STATE_SUCCESS.equals(state))
			return "SUCCESS";
		if (STATE_FAILED.equals(state))
			return "FAILED";
		if (STATE_DISUSED.equals(state))
			return "DISUSED";

		return "UNKNOWN";
	}

	/**
	 * ���Դ�BankOrderOperation��ȡ��Ӧ�Ĳ���ֵ
	 * 
	 * @return String
	 */
	public String getOperation() {
		if (operation == null) {
			return "1";
		}
		return operation;
	}

	/**
	 * ���Դ�BankOrderOperation��ȡ��Ӧ�Ĳ���ֵ
	 * 
	 * @param operation
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getGlobalFingerPrint() {
		return globalFingerPrint;
	}

	public void setGlobalFingerPrint(String globalFingerPrint) {
		this.globalFingerPrint = globalFingerPrint;
	}

	public Integer getMasterId() {
		return masterId;
	}

	public void setMasterId(Integer masterId) {
		this.masterId = masterId;
	}

	public String getSrcPacketId() {
		return srcPacketId;
	}

	public void setSrcPacketId(String srcPacketId) {
		this.srcPacketId = srcPacketId;
	}

	public String getTransCode() {
		return transCode;
	}

	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}

	public String getAppNo() {
		return appNo;
	}

	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}

	public Integer getChannelType() {
		return channelType;
	}

	public void setChannelType(Integer channelType) {
		this.channelType = channelType;
	}

	public String getBankCurrencyCode() {
		return bankCurrencyCode;
	}

	public void setBankCurrencyCode(String bankCurrencyCode) {
		this.bankCurrencyCode = bankCurrencyCode;
	}

	public String getChannelNo() {
		return channelNo;
	}

	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getDoingUnsureFlag() {
		return doingUnsureFlag;
	}

	public void setDoingUnsureFlag(String doingUnsureFlag) {
		this.doingUnsureFlag = doingUnsureFlag;
	}

	public Integer getDrivingUnsureCount() {
		return drivingUnsureCount;
	}

	public void setDrivingUnsureCount(Integer drivingUnsureCount) {
		this.drivingUnsureCount = drivingUnsureCount;
	}

	public Date getDrivingUnsureLastTime() {
		return drivingUnsureLastTime;
	}

	public void setDrivingUnsureLastTime(Date drivingUnsureLastTime) {
		this.drivingUnsureLastTime = drivingUnsureLastTime;
	}

	public String getRefundFlag() {
		return refundFlag;
	}

	public void setRefundFlag(String refundFlag) {
		this.refundFlag = refundFlag;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

}