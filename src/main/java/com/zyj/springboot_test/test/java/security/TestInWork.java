package com.zyj.springboot_test.test.java.security;

import com.zyj.springboot_test.test.java.arithmetic.Base64Util;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;

/**
 * @Author 周赟吉
 * @Date 2022/5/19 14:03
 * @Description :
 */
public class TestInWork {
    public static void main(String[] args) throws Exception {
//        test3();
        test2();
//        test1();
    }
    public static void test3() throws NoSuchAlgorithmException {
        Signature signature = Signature.getInstance("SHA1withRSA");
        String s = "5a4c89c1ec0d6e2a9d2028fcd60a302ade22c28f2e4ec819b0cf3f4986f8332725661f6fb103bb134763571ff388a21e50f2541dc70ffa6e6c19ce617b9a4ab75f878a604f7da585a707bf088eac173abb419ecc7a514a395c502f9086d3651ba28caed311c717f3eeb7263a429c19965faaf634c0718069b85e6eb089197fd396715a1ed027cabaeba6b85a6b0b8450544b3d7e4fde3f6953c203289854f2535bb7783d954db708065800d54722f97b30f672c106f200041bc1c566c0cb69ad259a83e44adad9c8c5c0bafbe5e67969abcf56859c53fbaace3bebf2086d01add94abbbe42636fe73e7def9caf3a014bb0a7aeb0df3f855ecaee9951956f9696";
        byte[] bytes = Base64Util.encode(s);
        System.out.println(bytes);

    }

    private static void test2() throws Exception {
        byte[] privateKey = FileUtils.readFileToByteArray(new File("D:/3.company/lt/user-rsa.p12"));
        byte[] publicKey = FileUtils.readFileToByteArray(new File("D:/3.company/lt/allinpay-pds.cer"));
        String text = "啊可是大家内存卡军事才能";
//        String result = SignUtil.signSHA1RSA_pkcs8(privateKey, text, "GBK");
        String result = SignUtil.signSHA1RSA_pkcs12(privateKey, text, "GBK");
//        String result = "017ebe0fb81c8923346ad74d398611b349b1e884b8c0a2781e48337acd3ec00771e125e7423cca8d677c3c75fc21299af2816cf420de680e6253e38b8fb8010d629b85f5ad9a55462126cdb74fa6a12f94826f360dfa6d69bd16618e479a36a918d94d7e8ec2b7635b7bebc5d4c4c3234a3684378fb6bc670a31ec7dff466fc573065402c0a501fa52e6206722fb8dc78d0408451560cb7185606662c9ffa96cc7375580224a5d62ad9d1edf8b273da5460747d69dfc442259dbe81755cceb390d1d1c5c83de3d87d1a92ef445542ce442358edde7bede5232117ba236fb33413aac30c6a08f3baaa0b5d16eb3602b0249ec6365bf14a747861716cfaf015823";
        System.out.println(result);
//        boolean pass = SignUtil.rsaCheckSign(publicKey, result, text, "GBK");
        String xmlMsg = "<?xml version=\"1.0\" encoding=\"GBK\"?><AIPG>\n" +
                "  <INFO>\n" +
                "    <TRX_CODE>300004</TRX_CODE>\n" +
                "    <VERSION>04</VERSION>\n" +
                "    <DATA_TYPE>2</DATA_TYPE>\n" +
                "    <REQ_SN>2005840000210653000000245118</REQ_SN>\n" +
                "    <RET_CODE>1000</RET_CODE>\n" +
                "    <ERR_MSG>签名不符</ERR_MSG>\n" +
                "    <SIGNED_MSG>017ebe0fb81c8923346ad74d398611b349b1e884b8c0a2781e48337acd3ec00771e125e7423cca8d677c3c75fc21299af2816cf420de680e6253e38b8fb8010d629b85f5ad9a55462126cdb74fa6a12f94826f360dfa6d69bd16618e479a36a918d94d7e8ec2b7635b7bebc5d4c4c3234a3684378fb6bc670a31ec7dff466fc573065402c0a501fa52e6206722fb8dc78d0408451560cb7185606662c9ffa96cc7375580224a5d62ad9d1edf8b273da5460747d69dfc442259dbe81755cceb390d1d1c5c83de3d87d1a92ef445542ce442358edde7bede5232117ba236fb33413aac30c6a08f3baaa0b5d16eb3602b0249ec6365bf14a747861716cfaf015823</SIGNED_MSG>\n" +
                "  </INFO>\n" +
                "</AIPG>";
        result = "017ebe0fb81c8923346ad74d398611b349b1e884b8c0a2781e48337acd3ec00771e125e7423cca8d677c3c75fc21299af2816cf420de680e6253e38b8fb8010d629b85f5ad9a55462126cdb74fa6a12f94826f360dfa6d69bd16618e479a36a918d94d7e8ec2b7635b7bebc5d4c4c3234a3684378fb6bc670a31ec7dff466fc573065402c0a501fa52e6206722fb8dc78d0408451560cb7185606662c9ffa96cc7375580224a5d62ad9d1edf8b273da5460747d69dfc442259dbe81755cceb390d1d1c5c83de3d87d1a92ef445542ce442358edde7bede5232117ba236fb33413aac30c6a08f3baaa0b5d16eb3602b0249ec6365bf14a747861716cfaf015823";
        boolean pass = SignUtil.rsaCheckSign_pkcs12(publicKey, result, xmlMsg, "GBK");
        System.out.println("验签：" + pass);
    }

    private static void test1 () throws Exception{
        byte[] privateKey = FileUtils.readFileToByteArray(new File("D:/3.company/pri.pri"));
        byte[] publicKey = FileUtils.readFileToByteArray(new File("D:/3.company/pub.pub"));
        String text = "<?xml version=\"1.0\"\r\n" +
                "  encoding=\"GBK\"?><AIPG>\r\n" +
                "<INFO>\r\n" +
                "<TRX_CODE>300000</TRX_CODE>\r\n" +
                "<VERSION>04</VERSION>\r\n" +
                "<DATA_TYPE>2</DATA_TYPE>\r\n" +
                "<REQ_SN>200604000004497-1526543036319</REQ_SN>\r\n" +
                "<RET_CODE>0000</RET_CODE>\r\n" +
                "<ERR_MSG>查询成功</ERR_MSG>\r\n" +
                "\r\n"+
                "</INFO>\r\n" +
                "<ACQUERYREP>\r\n" +
                "<ACNODE>\r\n" +
                "<ACCTNO>200604000004497000</ACCTNO>\r\n" +
                "<ACCTNAME>联调测试接口V-退款户</ACCTNAME>\r\n" +
                "<BALANCE>0</BALANCE>\r\n" +
                "<USABLEBAL>0</USABLEBAL>\r\n" +
                "<BALBY>2</BALBY>\r\n" +
                "<DEPOSIT>0</DEPOSIT>\r\n" +
                "<WITHDRAW>0</WITHDRAW>\r\n" +
                "<TRANSFERIN>0</TRANSFERIN>\r\n" +
                "<TRANSFEROUT>0</TRANSFEROUT>\r\n" +
                "<PAYABLE>0</PAYABLE>\r\n" +
                "<DEFCLR>0</DEFCLR>\r\n" +
                "</ACNODE>\r\n" +
                "</ACQUERYREP>\r\n" +
                "</AIPG>";
        String text2 = "<?xml version=\"1.0\"\r\n" +
                "  encoding=\"GBK\"?><AIPG>\r\n" +
                "<INFO>\r\n" +
                "<TRX_CODE>300000</TRX_CODE>\r\n" +
                "<VERSION>04</VERSION>\r\n" +
                "<DATA_TYPE>2</DATA_TYPE>\r\n" +
                "<REQ_SN>200604000004497-1526543036319</REQ_SN>\r\n" +
                "<RET_CODE>0000</RET_CODE>\r\n" +
                "<ERR_MSG>查询成功</ERR_MSG>\r\n" +
                "<SIGNED_MSG>Re0JzyuAWhYteGoXHKRoJRUFoTfh++7XknTPA6E+OMNJOogCiZG1N4F/eWDNCWXwzNoCi2/Ne05qqgtDn3bDjpQHwaVLx2jRRW3yvJQ3mIkPr5kE81+lwR1oAYruG8gSyLNnEF7WnZ33xml3SttZfM+DqYJeS9gRsHR99UaHyog=</SIGNED_MSG>\r\n"+
                "</INFO>\r\n" +
                "<ACQUERYREP>\r\n" +
                "<ACNODE>\r\n" +
                "<ACCTNO>200604000004497000</ACCTNO>\r\n" +
                "<ACCTNAME>联调测试接口V-退款户</ACCTNAME>\r\n" +
                "<BALANCE>0</BALANCE>\r\n" +
                "<USABLEBAL>0</USABLEBAL>\r\n" +
                "<BALBY>2</BALBY>\r\n" +
                "<DEPOSIT>0</DEPOSIT>\r\n" +
                "<WITHDRAW>0</WITHDRAW>\r\n" +
                "<TRANSFERIN>0</TRANSFERIN>\r\n" +
                "<TRANSFEROUT>0</TRANSFEROUT>\r\n" +
                "<PAYABLE>0</PAYABLE>\r\n" +
                "<DEFCLR>0</DEFCLR>\r\n" +
                "</ACNODE>\r\n" +
                "</ACQUERYREP>\r\n" +
                "</AIPG>";
        String result = SignUtil.signSHA1RSA_pkcs8(privateKey, text, "GBK");
        System.out.println(result);
        boolean pass1 = SignUtil.verifySHA1RSASign(publicKey, text2, "GBK");
//        boolean pass2 = SignUtil.rsaCheckSign(publicKey, result, text2, "GBK");
        System.out.println("验签：" + pass1);
//        System.out.println("验签：" + pass2);
    }
}
