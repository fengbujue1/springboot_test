/***********************************************************************
 * Module:  AccountLabel.java
 * Author:  TJY
 * Purpose: Defines the Class AccountLabel
 ***********************************************************************/

package com.zyj.springboot_test.test.xml.converModel.cips;

import java.io.Serializable;
import java.util.List;

/**
 * BP�е������˻�.
 */
public class BPAccount extends ExtAttribute implements Serializable
{
   private static final long serialVersionUID = 1L;
   public final static Integer ACCOUNT_DISABLED = new Integer(0);//ʧЧ
   public final static Integer ACCOUNT_NORMAL = new Integer(1);//����
   public final static Integer ACCOUNT_FREEZE = new Integer(2);//����
   /**
    * �˻�ID<br>
    * δ��BP��ע����˻���Ϊnull.
    */
   private Integer           id;

	/**
	 * ԭϵͳ�˻�ID
	 */
	private String systemAccountId;

   /**
    * �ڲ�����ʹ�õ�������.<br>
    * ���ڽ��׶Է�ʱ���ⲿ�˻���Ϊnull.
    */
   private String            index;

   /**
    * Ԥ�����ͨѶ�ڵ���
    */
   private String            nodeNumber;

   /**
    * �˺�
    */
   private String            number;

   /**
    * ����
    */
   private String            name;
   
   /**
    * �ϼ��ʺ�
    */
   private String superNumber;

   /**
    * ���ִ���
    */
   private String            currencyCode;

   /**
    * �������<br/>
    * �������˾�ķ�֧����
    */
   private String            institutionNo;

   /**
    * ������������
    */

   /**
    * ��������������

   /**
    * �˻��ο��ţ� ������չ��
    */
   private String            referenceCode;

   /**
	 * ��ҵ���˱��(����Ա������˲�ѯ,��Ҫ������Ͽ)
	 */
	private String customMark;

	/**
	 * �ֽ��������(��������������������)
	 */
	private Integer cmsType;
	
	/**
	 * �����˻�����(������һ�����˻�������)
	 */
	private Integer accountType;
	
	/**
	 * �˻�״̬
	 */
	private Integer accountState = ACCOUNT_NORMAL;
	
	/**
	 * ��ͬ��Э�飩��
	 */
	private String contractNo;

	/**
	 * �������д�������
	 */

	/**
	 * ������������������˺�
	 */
	private String agentBankAccountNumber;

	/**
	 * ���ң�����������
	 */
	private String countryCode;
	/**
	 * ���й��ң�����������
	 */
	private String bankCountryCode;

	/**
	 * ���ң�����������
	 */
	private String countryName;

	/**
	 * ϵͳ��Դ��ʶ��
	 *
	 */
	private String appNo;
	   
   /**
    *  ����swift��
    */
   private String swiftCode;
	
	/**
	 * ��������(1������ֱ����0��swift��)
	 * 
	 */
	private Integer channelType;
	
	/**
	 * ��ַ
	 * 
	 */
	private String address;
	/**
	 * �������
	 */
	private String channelNo;
	

	private List controlList;

	/**
	 * ����˻�����
	 */
	private String financialAccType;

	/**
	 * �������˻���Ӧ��ģ��,�Զ��ŷָ��洢
	 */
	private String notNormalAcctAppNo;
	
	/**
	 * ���˻��ţ���ǰ�˻������˻������⻧ʱ�����˻��ţ�
	 */
	private String masterAccNum;

	/**
    * ��ȡ�������
    * @return String
    */
   public String getInstitutionNo()
   {
      return institutionNo;
   }

   /**
    * ���û������
    * @param institutionNo
    */
   public void setInstitutionNo(String institutionNo)
   {
      this.institutionNo = institutionNo;
   }

   public String getCurrencyCode()
   {
      return currencyCode;
   }

   public Integer getId()
   {
      return id;
   }

   public String getIndex()
   {
      return index;
   }

   public String getName()
   {
      return name;
   }

   public String getNodeNumber()
   {
      return nodeNumber;
   }

   public String getNumber()
   {
      return number;
   }


   public String getReferenceCode()
   {
      return referenceCode;
   }

   public void setCurrencyCode(String currencyCode)
   {
      this.currencyCode = currencyCode;
   }

   public void setId(Integer id)
   {
      this.id = id;
   }

   public void setIndex(String indexNumber)
   {
      this.index = indexNumber;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public void setNodeNumber(String definedFrontNodeId)
   {
      this.nodeNumber = definedFrontNodeId;
   }

   public void setNumber(String number)
   {
      this.number = number;
   }


   public void setReferenceCode(String referenceCode)
   {
      this.referenceCode = referenceCode;
   }

   public String toString()
   {
      return "{index=" + this.index + "; number=" + this.number + "; name=" + this.name + "}";
   }

   public String getCustomMark() {
	   return customMark;
   }

	public void setCustomMark(String customMark) {
		this.customMark = customMark;
	}

	public String getSuperNumber() {
		return superNumber;
	}

	public void setSuperNumber(String superNumber) {
		this.superNumber = superNumber;
	}
	public Integer getCmsType() {
		return cmsType;
	}

	public void setCmsType(Integer cmsType) {
		this.cmsType = cmsType;
	}

	public Integer getAccountType() {
		return accountType;
	}

	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}

	public Integer getAccountState() {
		return accountState;
	}

	public void setAccountState(Integer accountState) {
		this.accountState = accountState;
	}


	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getSystemAccountId() {
		return systemAccountId;
	}

	public void setSystemAccountId(String systemAccountId) {
		this.systemAccountId = systemAccountId;
	}


	public String getAgentBankAccountNumber() {
		return agentBankAccountNumber;
	}

	public void setAgentBankAccountNumber(String agentBankAccountNumber) {
		this.agentBankAccountNumber = agentBankAccountNumber;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getAppNo() {
		return appNo;
	}

	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}

	public String getSwiftCode() {
		return swiftCode;
	}

	public void setSwiftCode(String swiftCode) {
		this.swiftCode = swiftCode;
	}

	public Integer getChannelType() {
		return channelType;
	}

	public void setChannelType(Integer channelType) {
		this.channelType = channelType;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBankCountryCode() {
		return bankCountryCode;
	}

	public void setBankCountryCode(String bankCountryCode) {
		this.bankCountryCode = bankCountryCode;
	}

	public String getChannelNo() {
		return channelNo;
	}

	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}

	public List getControlList() {
		return controlList;
	}

	public void setControlList(List controlList) {
		this.controlList = controlList;
	}

	public String getNotNormalAcctAppNo() {
		return notNormalAcctAppNo;
	}

	public void setNotNormalAcctAppNo(String notNormalAcctAppNo) {
		this.notNormalAcctAppNo = notNormalAcctAppNo;
	}

	public String getFinancialAccType() {
		return financialAccType;
	}

	public void setFinancialAccType(String financialAccType) {
		this.financialAccType = financialAccType;
	}

	/**
	 * @return the masterAccNum
	 */
	public String getMasterAccNum()
	{
		return masterAccNum;
	}

	/**
	 * @param masterAccNum the masterAccNum to set
	 */
	public void setMasterAccNum(String masterAccNum)
	{
		this.masterAccNum = masterAccNum;
	}
}
