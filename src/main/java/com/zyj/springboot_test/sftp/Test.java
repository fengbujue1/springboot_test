package com.zyj.springboot_test.sftp;

import java.util.List;

/**
 * @Author 周赟吉
 * @Date 2022/6/15 15:40
 * @Description :
 */
public class Test {
    public static void main(String[] args) {
        NstcSftpClient sftp = null;
//        sftp = new NstcSftpClient("192.168.20.42", 22, "sftpuser1", "zhouyunji123");
        sftp = new NstcSftpClient("47.96.0.106", 22, "sftp1", "zhouyunji123");
//        sftp = new NstcSftpClient("47.96.0.106", 22, "root", "Huiyi1314");
        sftp.setClientType(NstcSftpClient.JSCH);
        String pwd = sftp.pwd();
        System.out.println(pwd);
    }
}
