package com.zyj.springboot_test.test.java.arithmetic;

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

        String text = "你好??////\\\\\\\\\\，世界";
        System.out.println("text:" + text);

        byte[] encode = encoder.encode(text.getBytes());
        System.out.println("encode:" + new String(encode));

        byte[] decode = decoder.decode(encode);
        System.out.println("decode:" + new String(decode));
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
