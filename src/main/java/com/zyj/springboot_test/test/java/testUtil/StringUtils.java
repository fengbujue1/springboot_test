package com.zyj.springboot_test.test.java.testUtil;

import java.util.Arrays;

/**
 * Description:
 * 参考文档归档于：http://192.168.0.2/svn/doc/Doc.Dev/BP/开发备忘/Wiki/交易报文规范/TX0022-跨境人民币支付（CIPS）.xlsx
 *
 * @author 周赟吉
 * @since 2021/11/24
 */
public class StringUtils {

    public static void main(String[] args) {
        String s = new String();
        s = fillBlank(null, true, ' ', 5);
        System.out.println(s);
        s = fillBlank("1234", true, ' ', 35);
        System.out.println(s);
    }

    /**
     *  使用给定字符 ，给目标字符串在左边或者右边填充到指定长度,
     *  如果字符本身超出指定长度，不做处理直接返回
     *  支持空字符串
     *
     * @param src 原始字符串
     * @param left 是否左边，true :左边  false:右边
     * @param fillChar 补充使用的字符
     * @param expectLength 期望长度
     * @return
     */
    private static String fillBlank(String src, boolean left, char fillChar,int expectLength) {
        if (org.apache.commons.lang.StringUtils.isBlank(src)) {
            char[] chars = new char[expectLength];
            Arrays.fill(chars, fillChar);
            return new String(chars);
        }

        if (src.length() >= expectLength) {
            if (src.length() > expectLength) {
                throw new RuntimeException("构建请求头异常，" + src + "超过期望长度：" + expectLength);
            } else {
                return src;
            }
        }
        if (left) {
            char[] chars = new char[expectLength];
            Arrays.fill(chars, 0, expectLength - src.length(), fillChar);
            int index = 0;
            for (int i = expectLength - src.length(); i < expectLength; i++) {
                chars[i] = src.charAt(index);
                index++;
            }
            return new String(chars);
        } else {
            char[] chars = new char[expectLength];
            Arrays.fill(chars, src.length(), expectLength, fillChar);
            int index = 0;
            for (int i = 0; i < src.length(); i++) {
                chars[i] = src.charAt(index);
                index++;
            }
            return new String(chars);
        }


    }
}
