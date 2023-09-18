package com.zyj.springboot_test.test.java.inWork.icbc;

import com.icbc.api.utils.IcbcSignature;

/**
 * @Author 周赟吉
 * @Date 2023/8/27 23:26
 * @Description :
 */
public class Test2 {
    public static void main(String[] args) {
        // 合作方SM2公钥对应的国密CA证书路径
        String ca_sm_path = "C:\\Users\\king\\Documents\\WeChat Files\\wxid_97au6uo16vxk22\\FileStorage\\File\\2023-08\\DFCWGS6.y.3202.cer";
        // 工行SM2公钥对应的国密CA证书路径
        String ca_sm_icbc_path = "C:\\Users\\king\\Documents\\WeChat Files\\wxid_97au6uo16vxk22\\FileStorage\\File\\2023-08\\API_GATEWAY_ICBC_SM.cer";

        // 合作方SM2公钥对应的国密CA证书串
        String ca_sm = IcbcSignature.getCAInfoStr(ca_sm_path);

        // 工行SM2公钥对应的国密CA证书串
        String ca_sm_icbc = IcbcSignature.getCAInfoStr(ca_sm_icbc_path);
        System.out.println("ca_sm_icbc:  "+ca_sm_icbc);
        System.out.println("ca_sm:  "+ca_sm);
    }
}
