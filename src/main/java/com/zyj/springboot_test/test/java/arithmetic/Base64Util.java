package com.zyj.springboot_test.test.java.arithmetic;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.util.Base64;

/**
 * Description:
 *  base64 编解码算法
 * @author 周赟吉
 * @since 2021/12/13
 */
public class Base64Util {
    private static Base64.Encoder encoder = Base64.getEncoder();
    private static Base64.Decoder decoder = Base64.getDecoder();
    public static void main(String[] args) {
//        other();
        java();
    }

    /**
     * JDK自带的工具包
     */
    public static void java() {
        String text = "RDAwODk=";
        System.out.println("text:" + text);

//        byte[] encode = encoder.encode(text.getBytes());
//        System.out.println("encode:" + new String(encode));

        byte[] decode = decoder.decode(text.getBytes());
        System.out.println("decode:" + new String(decode));
    }

    /**
     * sun公司的工具包
     */
    public static void other() {
        String filePath = "C:\\Users\\DELL\\Desktop\\1.txt";
        BufferedReader bufferedReader;
        StringBuilder sb = new StringBuilder();
        byte[] tmp = new byte[1024];
        int s;
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            while ((s = fileInputStream.read(tmp)) >0) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        byte[] encode = com.sun.jersey.core.util.Base64.encode(tmp);
        System.out.println(new String(encode));
    }

    public static byte[] encode(String src){
        return encoder.encode(src.getBytes());
    }

    public static byte[] decode(String src){
        return decoder.decode(src.getBytes());
    }

    public static byte[] encode(byte[] src){
        return encoder.encode(src);
    }

    public static byte[] decode(byte[] src){
        return decoder.decode(src);
    }
}
