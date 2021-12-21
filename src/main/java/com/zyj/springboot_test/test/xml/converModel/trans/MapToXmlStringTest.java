package com.zyj.springboot_test.test.xml.converModel.trans;

import com.zyj.springboot_test.test.java.arithmetic.Base64Util;
import com.zyj.springboot_test.test.xml.converModel.cips.*;
import com.zyj.springboot_test.test.xml.converModel.cips.interfaces.FinanInstIdentSetter;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Description:
 * 参考文档归档于：http://192.168.0.2/svn/doc/Doc.Dev/BP/开发备忘/Wiki/交易报文规范/TX0022-跨境人民币支付（CIPS）.xlsx
 *
 * @author 周赟吉
 * @since 2021/12/14
 */
public class MapToXmlStringTest {
    public static void main(String[] args) {
        Map map = createMap();

    }

    private void transToObj(Map bcoMap) throws Exception{
        BankCipsOrder bankCipsOrder = new BankCipsOrder();
        Map bankCipsOrderMap = new HashMap();
        for (Object o : bcoMap.keySet()) {
            String key = (String) o;
            Object value = bcoMap.get(key);
            if (value instanceof Map) {
                if ("payer".equals(key)) {
                    CIPSAccount payer =getModel(key,value,CIPSAccount.class);
                    bankCipsOrder.setPayer(payer);
                } else if ("receiver".equals(key)) {
                    CIPSAccount receiver = getModel(key, value, CIPSAccount.class);
                    bankCipsOrder.setReceiver(receiver);
                }else if ("debtorAgent".equals(key)) {
                    Agent debtorAgent = getModel(key, value, Agent.class);
                    bankCipsOrder.setPayerAgent(debtorAgent);
                }else if ("creditorAgent".equals(key)) {
                    Agent creditorAgent = getModel(key, value, Agent.class);
                    bankCipsOrder.setReceiverAgent(creditorAgent);
                }else if ("charges".equals(key)) {
                    ChargesInformation chargesInformation = getModel(key, value, ChargesInformation.class);
                    bankCipsOrder.setChargesInformation(chargesInformation);
                }else if ("paymentDirectPart".equals(key)) {
                    FinanInstIdent paymentDirectPart = getFinanInstIdent((Map) value);
                    bankCipsOrder.setPaymentDirectPart(paymentDirectPart);
                }else if ("paymentInDirectPart".equals(key)) {
                    FinanInstIdent paymentInDirectPart = getFinanInstIdent((Map) value);
                    bankCipsOrder.setPaymentInDirectPart(paymentInDirectPart);
                }else if ("receiveDirectPart".equals(key)) {
                    FinanInstIdent receiveDirectPart = getFinanInstIdent((Map) value);
                    bankCipsOrder.setReceiveDirectPart(receiveDirectPart);
                }else if ("receiveInDirectPart".equals(key)) {
                    FinanInstIdent receiveInDirectPart = getFinanInstIdent((Map) value);
                    bankCipsOrder.setReceiveInDirectPart(receiveInDirectPart);
                }else if ("agency1".equals(key)) {
                    FinanInstIdent intermediaryAgent1 = getFinanInstIdent((Map) value);
                    bankCipsOrder.setIntermediaryAgent1(intermediaryAgent1);
                }else if ("agency2".equals(key)) {
                    FinanInstIdent intermediaryAgent2 = getFinanInstIdent((Map) value);
                    bankCipsOrder.setIntermediaryAgent2(intermediaryAgent2);
                }else if ("accessory".equals(key)) {
                    bankCipsOrder.setAccessory((Map)value);
                }else {
                    throw new RuntimeException("接受到的Event类中Body的BankCipsOrder标签下子标签错误,该元素不该为map,该元素为:" + key);
                }
            } else if (value instanceof String || value instanceof Integer || value instanceof Double) {
                if ("date".equals(key)||"valueDate".equals(key)||"expectDate".equals(key)) {
                    if ("expectDate".equals(key) && StringUtils.isBlank((String)value)) {
                        continue;
                    }
                    String da = (String) value;
                    if (da.length() != 8) {
                        throw new RuntimeException("接受到的Event类中Body的BankCipsOrder标签下子标签"+key+"错误,该日期字符串必须是8位长度的!");
                    } else {
                        try {
                            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
                            Date date = df.parse(da);
                            bankCipsOrderMap.put(key, date);

                        } catch (Exception e) {
                            throw new RuntimeException("接受到的Event类中Body的BankCipsOrder标签下子标签date错误,该日期字符串格式错误!", e);
                        }
                    }
                } else {
                    bankCipsOrderMap.put(key, value);
                }
            } else if (value == null) {
                //为空的话，啥也不用做
            } else {
                throw new RuntimeException("接受到的Event类中Body的BankCipsOrder标签下子标签类型错误,该元素类型为" + value.getClass() + "值为：" + value + ",该元素为:" + key);
            }

        }
        ConvertMapBean.convertMapToBean(bankCipsOrder, bankCipsOrderMap);
    }

    private static Map createMap() {
        return createBankCipsOrderBody();
    }

    private static void setdebtorAgent(Map<String, Object> debtorAgent) {
        debtorAgent.put("postalAddress", "5 SANLIHE ROAD, HAIDIAN DISTRICT，BEIJING,CHINA");
        debtorAgent.put("accountNo", "110060149012322052511");
        debtorAgent.put("name", "Minmetals Finance Co., LTD.");

        HashMap<String, String> finanInstIdent = new HashMap<>();
        finanInstIdent.put("CIPSID", "CN002007932");
        finanInstIdent.put("memberIdNo", "CN002007932");
        finanInstIdent.put("LEI", "300300C5000711000023");
        debtorAgent.put("finanInstIdent", finanInstIdent);//组织相关信息
    }

    private static void setReceiveAccount(Map<String, Object> receiver) {
        receiver.put("accountNo", "44444");
        receiver.put("name", "srxsrxsrxsrxsrxsrxsrxsrx");
        receiver.put("countryCode", "srxsrxsrxsrxsrxsrxsrxsrx");
        receiver.put("postalAddress", null);
        receiver.put("IDCard", "");
        receiver.put("IDCardType", "");
        receiver.put("EID", "");

        HashMap<String, String> finanInstIdent = new HashMap<>();
        finanInstIdent.put("CIPSID", "");
        finanInstIdent.put("organizationCode", "");
        finanInstIdent.put("LEI", "");
        receiver.put("finanInstIdent", finanInstIdent);//组织相关信息
    }

    private static void setOrderTxCipsAccount(Map<String, Object> payer) {
        payer.put("name", "五矿有色金属股份有限公司");//交通银行
        payer.put("postalAddress", "srxsrxsrxsrxsrxsrxsrxsrx");//交通银行
        payer.put("IDCard", "");//交通银行
        payer.put("IDCardType", "");//交通银行
        payer.put("EID", "");//交通银行


        // TODO 交通银行
        payer.put("accountNo", "202108311630380580797");//交通银行

        HashMap<String, String> finanInstIdent = new HashMap<>();
        finanInstIdent.put("LEI", "");
        finanInstIdent.put("organizationCode", null);
        finanInstIdent.put("CIPSID", "");
        payer.put("finanInstIdent", finanInstIdent);//组织相关信息
    }

    private static void setReceiveInDirectPart(Map<String, Object> receiveInDirectPart) {
        receiveInDirectPart.put("LEI", "");
        receiveInDirectPart.put("memberIdNo", "");
        receiveInDirectPart.put("CIPSID", "");
    }

    private static void setPaymentDirectPart(Map<String, Object> paymentDirectPart) {
        //todo organizationCode????
        paymentDirectPart.put("LEI", "");
        paymentDirectPart.put("memberIdNo", "");
//        paymentDirectPart.put("organizationCode", "");
        paymentDirectPart.put("CIPSID", "");
    }
    private static void setReceiveDirectPart(Map<String, Object> paymentDirectPart) {
        paymentDirectPart.put("LEI", "");
        paymentDirectPart.put("memberIdNo", "");
        paymentDirectPart.put("CIPSID", "");
    }
    private static void setAgency2(Map<String, Object> agency2) {
        agency2.put("LEI", "");
        agency2.put("memberIdNo", "");
        agency2.put("CIPSID", "");
        agency2.put("name", "");
    }
    private static void setAgency1(Map<String, Object> agency1) {
        agency1.put("LEI", "");
        agency1.put("memberIdNo", "");
        agency1.put("CIPSID", "");
        agency1.put("name", "");
    }

    private static void setCharges(Map<String, Object> charges) {
        charges.put("amount", "");
        charges.put("currencyCode", "");


        HashMap<String, String> finanInstIdent = new HashMap<>();
        finanInstIdent.put("LEI", "");
        finanInstIdent.put("memberIdNo", "");
        finanInstIdent.put("CIPSID", "");
        charges.put("finanInstIdent", finanInstIdent);//组织相关信息
    }

    private static void setCreditorAgent(Map<String, Object> creditorAgent) {
        creditorAgent.put("postalAddress", null);
        creditorAgent.put("accountNo", "45555");
        creditorAgent.put("name", "srxsrx");


        HashMap<String, String> finanInstIdent = new HashMap<>();
        finanInstIdent.put("LEI", "");
        finanInstIdent.put("memberIdNo", "");
        finanInstIdent.put("CIPSID", "");
        creditorAgent.put("finanInstIdent", finanInstIdent);//组织相关信息
    }

    public static Map<String, Object> createBankCipsOrderBody() {
        Map<String, Object> bankCipsOrder = new HashMap<String, Object>();

        bankCipsOrder.put("postScript", "附言");
        bankCipsOrder.put("chargeBearer", "2");//付款人承担费用

        //收款人信息
        Map<String, Object> receiver = new HashMap<String, Object>();
        setReceiveAccount(receiver);

        bankCipsOrder.put("exchangeRate", "2.0");//汇率

        //付款代理
        HashMap<String, Object> debtorAgent = new HashMap<>();
        setdebtorAgent(debtorAgent);
        bankCipsOrder.put("debtorAgent", debtorAgent);


        bankCipsOrder.put("billNum", "");//支票？
        bankCipsOrder.put("date",
                new SimpleDateFormat("yyyyMMdd").format(new Date()));
        int orderid = Math.abs((int) (System.currentTimeMillis()
                % Integer.MAX_VALUE));
        System.out.println("orderid="+orderid);

        bankCipsOrder.put("id", orderid);
        bankCipsOrder.put("amount", new Double("401.18"));
        // ORIG：初始发送；E-RE：出错重发
        bankCipsOrder.put("operation", null);
        bankCipsOrder.put("bankNo", "05"); // 受理银行所属银行系统编号


        HashMap<String, Object> receiveInDirectPart = new HashMap<>();
        setReceiveInDirectPart(receiveInDirectPart);
        bankCipsOrder.put("receiveInDirectPart", receiveInDirectPart);

        bankCipsOrder.put("billInfo", null);

        HashMap<String, Object> paymentDirectPart = new HashMap<>();
        setPaymentDirectPart(paymentDirectPart);
        bankCipsOrder.put("paymentDirectPart", paymentDirectPart);

        bankCipsOrder.put("toBankRemark", "");

        HashMap<String, Object> agency2 = new HashMap<>();
        setAgency2(agency2);
        bankCipsOrder.put("agency2", agency2);

        HashMap<String, Object> agency1 = new HashMap<>();
        setAgency1(agency1);
        bankCipsOrder.put("agency1", agency1);

        HashMap<String, Object> charges = new HashMap<>();
        setCharges(agency1);
        bankCipsOrder.put("charges", charges);

        HashMap<String, Object> creditorAgent = new HashMap<>();
        setCreditorAgent(creditorAgent);
        bankCipsOrder.put("creditorAgent", creditorAgent);

        bankCipsOrder.put("currencyCode", "USD");
//        bankCipsOrder.put("valueDate", new SimpleDateFormat("yyyyMMdd").format(new Date()));
        bankCipsOrder.put("valueDate", "2021-12-14 00:00:00.0 GMT+08:00");
        bankCipsOrder.put("settlementPriority", "3");

        bankCipsOrder.put("globalFingerPrint", "123");

        HashMap<String, Object> receiveDirectPart = new HashMap<>();
        setReceiveDirectPart(receiveDirectPart);
        bankCipsOrder.put("receiveDirectPart", receiveDirectPart);

        //TODO ?
//        HashMap<String, Object> paymentInDirectPart = new HashMap<>();
//        setReceiveDirectPart(paymentInDirectPart);
//        bankCipsOrder.put("paymentInDirectPart", paymentInDirectPart);

        Map<String, Object> payer = new HashMap<String, Object>();
        // 设置bp (付方，代理，收方)账户信息
        setOrderTxCipsAccount(payer);
        bankCipsOrder.put("payer", payer);// 付款人

        bankCipsOrder.put("proprietary", "0");

        bankCipsOrder.put("expectDate", "");


        //附件
        HashMap<String, byte[]> accessoryDatas = new HashMap<>();
        create(accessoryDatas);
//        bankCipsOrder.put("accessory", accessoryDatas);
        return bankCipsOrder;
    }

    private static void create(Map accessoryDatas) {
        String filePath = "C:\\Users\\DELL\\Desktop\\CDXXRL-IV-21 试用期考核及转正审批表 （3级）-开发类-周赟吉\\CDXXRL-IV-21 试用期考核及转正审批表 （3级）-开发类-周赟吉.pdf";
        BufferedReader bufferedReader;
        StringBuilder sb = new StringBuilder();
        byte[] tmp = new byte[1024*1024*30];
        int s;
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            while ((s = fileInputStream.read(tmp)) >0) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        byte[] encode = Base64Util.encode(tmp);
        accessoryDatas.put("CDXXRL-IV-21 试用期考核及转正审批表 （3级）-开发类-周赟吉.pdf",encode);

    }
    /**
     * 解析生成对应模型
     * 可生成的模型 CIPSAccount，Agent，ChargesInformation
     * @param key
     * @param value
     * @return
     * @throws Exception
     */
    private <T>T getModel (String key, Object value,Class<T> clazz) throws Exception{
        T model = clazz.newInstance();
        if(value instanceof Map){
            Map accountM = new HashMap();
            Map accountMap = (Map)value;
            FinanInstIdent finanInstIdent=null;
            for (Object account_k : accountMap.keySet()) {
                String key_account = (String) account_k;
                Object val_account = accountMap.get(key_account);
                if (val_account == null) {
                    continue;
                }

                if (val_account instanceof Map) {
                    Map finanInstIdentMap = null;
                    if ("finanInstIdent".equals(key_account)) {
                        finanInstIdentMap = ((Map) val_account);
                        finanInstIdent = getFinanInstIdent(finanInstIdentMap);
                    }
                } else if (val_account instanceof String || val_account instanceof Integer || val_account instanceof Double) {
                    accountM.put(key_account, val_account);
                } else {
                    throw new RuntimeException(
                            "接受到的Event类中Body的bankTxOrder标签下子标签" + key + "下子标签类型错误,不存在元素的类型,该元素为:"
                                    + val_account);
                }
            }
            FinanInstIdentSetter finanInstIdentSetter = (FinanInstIdentSetter) model;
            finanInstIdentSetter.setFinanInstIdent(finanInstIdent);
            ConvertMapBean.convertMapToBean(model,accountM);
        }else{
            throw new RuntimeException(
                    "接受到的Event类中Body的bankTxOrder标签下子标签"+key+"下子标签类型错误,不存在元素的类型,该元素为:"
                            + key);
        }
        return model;
    }

    private FinanInstIdent getFinanInstIdent(Map finanInstIdentMap) {
        if (finanInstIdentMap == null) {
            throw new RuntimeException("finanInstIdent" + "标签下的值转换为对象时失败！");
        }
        FinanInstIdent finanInstIdent = new FinanInstIdent();
        try {
            ConvertMapBean.convertMapToBean(finanInstIdent, finanInstIdentMap);
        } catch (Exception e) {
            throw new RuntimeException("finanInstIdent" + "标签下的值转换为对象时失败！");
        }
        return finanInstIdent;
    }

}
