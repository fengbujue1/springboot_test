package com.zyj.springboot_test.test.java.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TestSHA256 {
    public static void main(String[] args) {
        try {
            String input = "huiyi1314"; // 需要计算 SHA-256 的输入数据
            // 获取 SHA-256 的 MessageDigest 实例
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // 计算哈希值
            byte[] hashBytes = digest.digest(input.getBytes());

            // 将字节数组转换为十六进制字符串
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }

            // 打印 SHA-256 哈希值
            System.out.println("SHA-256 Hash: " + hexString.toString());

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
