package com.zyj.springboot_test.test.java.arithmetic;

import java.util.Base64;

/**
 * Description:
 *  base64 编解码算法
 * @author 周赟吉
 * @since 2021/12/13
 */
public class Base64Util {
    public static void main(String[] args) {
        Base64.Encoder encoder = Base64.getEncoder();
        Base64.Decoder decoder = Base64.getDecoder();
        String text = "你好??////\\\\\\\\\\，世界";
        System.out.println("text:" + text);

        byte[] encode = encoder.encode(text.getBytes());
        System.out.println("encode:" + new String(encode));

        byte[] decode = decoder.decode(encode);
        System.out.println("decode:" + new String(decode));
    }
}
