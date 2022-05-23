package com.zyj.springboot_test.test.java.security;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * @Author 周赟吉
 * @Date 2022/5/19 14:03
 * @Description :
 */
public class TestInWork {
    public static void main(String[] args) throws Exception {
//        test2();
        test1();
    }

    private static void test2() throws Exception {
        byte[] privateKey = FileUtils.readFileToByteArray(new File("D:/3.company/pri.pri"));
        byte[] publicKey = FileUtils.readFileToByteArray(new File("D:/3.company/pub.pub"));
        String text = "啊可是大家内存卡军事才能";
        String result = SignUtil.signSHA1RSA(privateKey, text, "GBK");
        System.out.println(result);
        boolean pass = SignUtil.rsaCheckSign(publicKey, result, text, "GBK");
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
        String result = SignUtil.signSHA1RSA(privateKey, text, "GBK");
        System.out.println(result);
        boolean pass1 = SignUtil.verifySHA1RSASign(publicKey, text2, "GBK");
//        boolean pass2 = SignUtil.rsaCheckSign(publicKey, result, text2, "GBK");
        System.out.println("验签：" + pass1);
//        System.out.println("验签：" + pass2);
    }
}
