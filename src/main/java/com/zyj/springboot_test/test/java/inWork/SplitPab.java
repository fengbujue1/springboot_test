package com.zyj.springboot_test.test.java.inWork;

import com.icbc.api.internal.apache.http.impl.cookie.S;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * @Author 周赟吉
 * @Date 2023/6/28 18:03
 * @Description :平安银行的报文切割工具
 */
public class SplitPab {
    public static void main(String[] args) throws IOException {
        String excelPath = "C:\\Users\\king\\Desktop\\新建文本文档 (2).txt";
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(excelPath),"utf-8"));
        String line;
        String prior = "";
        ArrayList<String> strings = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            if (line.contains("object")) {
                strings.add(line);
                continue;
            }
            if (line.startsWith("最大长度")
                    ||line.startsWith("最大值")
            ) {
                strings.add(processFir(prior) + line);
            }
            prior = line;
        }


        for (int i = 0; i < strings.size(); i++) {
            String s = strings.get(i);
            if (s.contains("object")) {
                System.out.println("}");
                System.out.println("public static class " + s.split("---")[0]+"{");
                continue;
            }
            String[] split = s.split("---");
            if (split.length < 7) {
                System.out.println("格式错误，长度小于7");
            }
            System.out.println("    /**\n" +
                    "     *" + split[1] + "\n" +
                    "     * 是否必输：" + split[3] + "\n" +
                    "     * 备注：" + split[5] + "\n" +
                    "     *" + split[6] + "\n" +
                    "     *" + (split.length>7?split[7]:"") + "\n" +
                    "     *" + (split.length>8?split[8]:"") + "\n" +
                    "     *" + (split.length>9?split[9]:"") + "\n" +
                    "     */");
            char[] chars = split[2].toCharArray();
            String s1 = String.valueOf(chars[1]).toUpperCase();
            String s2 = split[2].substring(2, split[2].length());
            System.out.println("    @JacksonXmlProperty(localName = \"" + split[0]  + "\")");
            System.out.println("    private " + s1 + s2 + " " + split[0] + ";");
        }
        System.out.println("}");


    }

    public static String processFir(String s){
        // 遍历字符，吧最后几个-三个三个做个区分
        char[] chars = s.toCharArray();
        ArrayList<String> strings = new ArrayList<>();
        int count = 0;
        char prior='a';
        for (int i = 0; i < chars.length; i++) {
            char aChar = chars[i];
            strings.add(String.valueOf(aChar));
            if (aChar == '-') {
                count++;
            }
            if (count == 3 && prior == '-' && aChar == '-') {
                strings.add(" ");
                count = 0;
            }
            prior = aChar;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strings.size(); i++) {
            sb.append(strings.get(i));
        }
        String s1 = sb.toString();
        return s1;
    };
}
