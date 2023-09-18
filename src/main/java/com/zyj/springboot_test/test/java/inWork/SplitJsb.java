package com.zyj.springboot_test.test.java.inWork;

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
public class SplitJsb {
    public static void main(String[] args) throws IOException {
        String excelPath = "C:\\Users\\king\\Desktop\\新建文本文档 (2).txt";
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(excelPath),"utf-8"));
        String line;
        String prior = "";
        ArrayList<String> strings = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            strings.add(line);
        }

        for (int i = 0; i < strings.size(); i++) {
            String s = strings.get(i);
            String[] split = s.split("---");
            System.out.println("    /**\n" +
                    "     *" + split[0] + "\n" +
//                    "     * 是否必输：" + split[3] + "\n" +
                    "     * 备注：" + (split.length>4?split[4]:"") + "\n" +
                    "     */");
            System.out.println("    @JacksonXmlProperty(localName = \"" + split[1]  + "\")");
            System.out.println("    private " + "String" + " " + split[1] + ";");
        }
    }
}
